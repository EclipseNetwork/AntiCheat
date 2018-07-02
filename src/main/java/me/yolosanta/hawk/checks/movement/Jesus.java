package me.yolosanta.hawk.checks.movement;

import me.yolosanta.hawk.Hawk;
import me.yolosanta.hawk.checks.Check;
import me.yolosanta.hawk.checks.CheckType;
import me.yolosanta.hawk.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Jesus extends Check {
    public Jesus() {
        super("Jesus", CheckType.MOVEMENT, true);
    }

    public static boolean isOnWater(Location player, int blocks) {
        for (int i = player.getBlockY(); i > player.getBlockY() - blocks; i--) {
            Block newloc = new Location(player.getWorld(), player.getBlockX(), i, player.getBlockZ()).getBlock();
            if (newloc.getType() != Material.AIR) {
                return newloc.isLiquid();
            }
        }
        return false;
    }

    public static boolean isHoveringOverWater(Location player) {
        return isOnWater(player, 25);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) { // Work In Progress
        Player p = e.getPlayer();
        PlayerData data = Hawk.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (p.getLocation().add(0, -0.3, 0).getBlock().isLiquid()) {
                boolean onWater = isHoveringOverWater(p.getLocation());
                Location to = e.getTo();
                Location from = e.getFrom();
                double motion = (to.getY() - from.getY());
                boolean goingUp;
                goingUp = e.getTo().getY() > e.getTo().getY();
                if (onWater) {
                    if (motion > 0.10000 && !goingUp) {
                        //p.sendMessage("1");
                    }
                }
            }
        }
    }
}
