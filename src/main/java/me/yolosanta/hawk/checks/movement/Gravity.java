package me.yolosanta.hawk.checks.movement;

import me.yolosanta.hawk.Hawk;
import me.yolosanta.hawk.checks.Check;
import me.yolosanta.hawk.checks.CheckType;
import me.yolosanta.hawk.data.PlayerData;
import me.yolosanta.hawk.util.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Gravity extends Check {
    public Gravity() {
        super("Gravity", CheckType.MOVEMENT, true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PlayerData data = Hawk.getInstance().getDataManager().getData(p);
        if (data != null) {
            double diff = MathUtils.getVerticalDistance(e.getFrom(), e.getTo());
            double LastY = data.getLastY_Gravity();
            double MaxG = 5;
            if (PlayerUtils.wasOnSlime(p)) {
                data.setGravity_VL(0);
                return;
            }
            if (e.getTo().getY() < e.getFrom().getY()) {
                return;
            }
            if (BlockUtils.isHalfBlock(p.getLocation().add(0, -1.50, 0).getBlock()) || BlockUtils.isNearHalfBlock(p) || BlockUtils.isStair(p.getLocation().add(0, 1.50, 0).getBlock()) || BlockUtils.isNearStiar(p) || NEW_Velocity_Utils.didTakeVel(p)
                    || PlayerUtils.wasOnSlime(p)) {
                data.setGravity_VL(0);
                return;
            }
            if (p.getLocation().getBlock().getType() != Material.CHEST &&
                    p.getLocation().getBlock().getType() != Material.TRAPPED_CHEST && p.getLocation().getBlock().getType() != Material.ENDER_CHEST && data.getAboveBlockTicks() == 0) {
                if (!PlayerUtils.onGround2(p) && !PlayerUtils.isOnGround3(p) && !PlayerUtils.isOnGround(p)) {
                    if ((((ServerUtils.isBukkitVerison("1_7") || ServerUtils.isBukkitVerison("1_8")) && Math.abs(p.getVelocity().getY() - LastY) > 0.000001)
                            || (!ServerUtils.isBukkitVerison("1_7") && !ServerUtils.isBukkitVerison("1_8") && Math.abs(p.getVelocity().getY() - diff) > 0.000001))
                            && !PlayerUtils.onGround2(p)
                            && e.getFrom().getY() < e.getTo().getY()
                            && (p.getVelocity().getY() >= 0 || p.getVelocity().getY() < (-0.0784 * 5)) && !VelocityUtils.didTakeVelocity(p) && p.getNoDamageTicks() == 0.0) {
                        if (data.getGravity_VL() >= MaxG) {
                            flag(p, "Player's motion was changed to an unexpected value.");
                            SetBackSystem.setBack(p);
                        } else {
                            data.setGravity_VL(data.getGravity_VL() + 1);
                        }
                    } else {
                        data.setGravity_VL(0);
                    }
                }
            }
            data.setLastY_Gravity(diff);
        }
    }
}
