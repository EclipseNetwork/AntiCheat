package me.yolosanta.hawk.checks.player;

import me.yolosanta.hawk.checks.Check;
import me.yolosanta.hawk.checks.CheckType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class ImpossiblePitch extends Check {
    public ImpossiblePitch() {
        super("ImpossiblePitch", CheckType.MOVEMENT, true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        double Pitch = e.getPlayer().getLocation().getPitch();
        if (Pitch > 90 || Pitch < -90) {
            flag(e.getPlayer(), "Players head went back too far.");
        }
    }
}
