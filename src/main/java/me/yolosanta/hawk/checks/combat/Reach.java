package me.yolosanta.hawk.checks.combat;

import me.yolosanta.hawk.checks.Check;
import me.yolosanta.hawk.checks.CheckType;
import me.yolosanta.hawk.events.PluginEvents.PacketAttackEvent;
import me.yolosanta.hawk.packetcore.PacketTypes;
import me.yolosanta.hawk.util.MathUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class Reach extends Check {

    public Reach() {
        super("Reach", CheckType.COMBAT, true);
    }

    @EventHandler
    public void onAttack(PacketAttackEvent e) {
        if (e.getType() != PacketTypes.USE
                || e.getEntity() == null) {
            return;
        }

        Player player = e.getPlayer();
        Entity entity = e.getEntity();

        double distance = MathUtils.getHorizontalDistance(player.getLocation(), entity.getLocation()) - 0.35;
        double maxReach = 4.728;
        double yawDifference = 180 - Math.abs(Math.abs(player.getEyeLocation().getYaw()) - Math.abs(entity.getLocation().getYaw()));

        maxReach += Math.abs(player.getVelocity().length() + entity.getVelocity().length()) * 0.4;
        maxReach += yawDifference * 0.01;

        if (maxReach < 4.728) maxReach = 4.728;


        if (distance > maxReach) {
            flag(player, MathUtils.trim(3, distance) + " > " + MathUtils.trim(3, maxReach));
        }
    }
}
