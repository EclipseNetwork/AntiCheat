package me.yolosanta.hawk.checks.player;

import me.yolosanta.hawk.Hawk;
import me.yolosanta.hawk.checks.Check;
import me.yolosanta.hawk.checks.CheckType;
import me.yolosanta.hawk.data.PlayerData;
import me.yolosanta.hawk.util.PlayerUtils;
import me.yolosanta.hawk.util.SetBackSystem;
import me.yolosanta.hawk.util.TimerUtils;
import me.yolosanta.hawk.util.VelocityUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GroundSpoofCheck extends Check {
    public GroundSpoofCheck() {
        super("Ground Spoof", CheckType.MISC, true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Hawk.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (e.getTo().getY() > e.getFrom().getY()) {
                return;
            }
            if (data.isLastBlockPlaced_GroundSpoof()) {
                if (TimerUtils.elapsed(data.getLastBlockPlacedTicks(), 500L)) {
                    data.setLastBlockPlaced_GroundSpoof(false);
                }
                return;
            }
            Location to = e.getTo();
            Location from = e.getFrom();
            double diff = to.toVector().distance(from.toVector());
            int dist = PlayerUtils.getDistanceToGround(p);
            if (p.getLocation().add(0, -1.50, 0).getBlock().getType() != Material.AIR) {
                data.setGroundSpoofVL(0);
                return;
            }
            if (e.getTo().getY() > e.getFrom().getY() || PlayerUtils.isOnGround4(p) || VelocityUtils.didTakeVelocity(p)) {
                data.setGroundSpoofVL(0);
                return;
            }
            if (p.isOnGround() && diff > 0.0 && !PlayerUtils.isOnGround(p) && dist >= 2 && e.getTo().getY() < e.getFrom().getY()) {
                if (data.getGroundSpoofVL() >= 4) {
                    if (data.getAirTicks() >= 10) {
                        flag(p, "Spoofed On-Ground Packet. [NoFall]");
                    } else {
                        flag(p, "Spoofed On-Ground Packet.");
                    }
                    SetBackSystem.setBack(p);
                } else {
                    data.setGroundSpoofVL(data.getGroundSpoofVL() + 1);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Hawk.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (!data.isLastBlockPlaced_GroundSpoof()) {
                data.setLastBlockPlaced_GroundSpoof(true);
                data.setLastBlockPlacedTicks(TimerUtils.nowlong());
            }
        }
    }
}
