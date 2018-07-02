package me.yolosanta.hawk.checks.player;

import me.yolosanta.hawk.Hawk;
import me.yolosanta.hawk.checks.Check;
import me.yolosanta.hawk.checks.CheckType;
import me.yolosanta.hawk.data.PlayerData;
import me.yolosanta.hawk.events.PluginEvents.PacketPlayerEvent;
import me.yolosanta.hawk.packetcore.PacketCore;
import me.yolosanta.hawk.util.SetBackSystem;
import me.yolosanta.hawk.util.TimerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class MorePackets extends Check {
    private Map<UUID, Integer> packets;
    private Map<UUID, Integer> verbose;
    private Map<UUID, Long> lastPacket;
    private List<Player> toCancel;

    public MorePackets() {
        super("MorePackets", CheckType.MISC, true);
        packets = new HashMap<>();
        verbose = new HashMap<>();
        toCancel = new ArrayList<>();
        lastPacket = new HashMap<>();
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        if (packets.containsKey(e.getPlayer().getUniqueId())) {
            packets.remove(e.getPlayer().getUniqueId());
        }
        if (verbose.containsKey(e.getPlayer().getUniqueId())) {
            verbose.remove(e.getPlayer().getUniqueId());
        }
        if (lastPacket.containsKey(e.getPlayer().getUniqueId())) {
            lastPacket.remove(e.getPlayer().getUniqueId());
        }
        if (toCancel.contains(e.getPlayer())) {
            toCancel.remove(e.getPlayer());
        }
    }

    @EventHandler
    public void packetPlayer(PacketPlayerEvent event) {
        Player player = event.getPlayer();
        PlayerData data = Hawk.getInstance().getDataManager().getData(player);

        int packets = this.packets.getOrDefault(player.getUniqueId(), 0);
        long Time = this.lastPacket.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
        int verbose = this.verbose.getOrDefault(player.getUniqueId(), 0);

        if ((System.currentTimeMillis() - data.getLastPacket()) > 100L) {
            toCancel.add(player);
        }
        double threshold = 42;
        if (TimerUtils.elapsed(Time, 1000L)) {
            if (toCancel.remove(player) && packets <= 67) {
                this.packets.put(player.getUniqueId(), 0);
                return;
            }
            if (packets > threshold + PacketCore.movePackets.getOrDefault(player.getUniqueId(), 0)) {
                verbose++;
            } else {
                verbose = 0;
            }

            //Bukkit.broadcastMessage(packets + ", " + verbose);

            if (verbose > 2) {
                flag(player, "Type: A");
                SetBackSystem.setBack(player);
            }
            packets = 0;
            Time = System.currentTimeMillis();
            PacketCore.movePackets.remove(player.getUniqueId());
        }
        packets++;

        this.packets.put(player.getUniqueId(), packets);
        this.verbose.put(player.getUniqueId(), verbose);
        this.lastPacket.put(player.getUniqueId(), Time);
    }
}
