package me.yolosanta.hawk.events;

import me.yolosanta.hawk.Hawk;
import me.yolosanta.hawk.data.PlayerData;
import me.yolosanta.hawk.events.PluginEvents.PacketPlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PacketListener implements Listener {

    @EventHandler
    public void onPacketPlayerEvent(PacketPlayerEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Hawk.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (data.getLastPlayerPacketDiff() > 200) {
                data.setLastDelayedPacket(System.currentTimeMillis());
            }
            data.setLastPlayerPacket(System.currentTimeMillis());
        }
    }
}
