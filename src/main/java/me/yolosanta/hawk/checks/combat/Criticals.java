package me.yolosanta.hawk.checks.combat;

import me.yolosanta.hawk.Hawk;
import me.yolosanta.hawk.checks.Check;
import me.yolosanta.hawk.checks.CheckType;
import me.yolosanta.hawk.data.PlayerData;
import me.yolosanta.hawk.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Criticals extends Check {

    public Criticals() {
        super("Criticals", CheckType.COMBAT, true);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getDamager();

        if (!Bukkit.getOnlinePlayers().contains(player)) {
            return;
        }

        PlayerData data = Hawk.getInstance().getDataManager().getData(player);

        if (data.getAboveBlockTicks() > 0
                || PlayerUtils.isInWeb(player)
                || data.getWaterTicks() > 0
                || PlayerUtils.hasSlabsNear(player.getLocation())) {
            return;
        }

        int verbose = data.getCriticalsVerbose();

        if (player.getFallDistance() > 0 && data.getFallDistance() == 0) {
            if (++verbose > 2) {
                flag(player, "Illegitimate critical packet");
                verbose = 0;
            }
        } else {
            verbose = 0;
        }

        data.setCriticalsVerbose(verbose);
    }
}
