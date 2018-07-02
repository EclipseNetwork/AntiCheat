package me.yolosanta.hawk.checks;

import me.yolosanta.hawk.Hawk;
import me.yolosanta.hawk.discord.EmbededMessages;
import me.yolosanta.hawk.discord.Main;
import net.dv8tion.jda.core.JDA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class Check implements Listener {

    private JDA jda;
    private String name;
    private CheckType type;
    private boolean enabled;

    public Check(String name, CheckType type, boolean enabled) {
        this.name = name;
        this.type = type;
        this.enabled = enabled;

        if (enabled) Bukkit.getPluginManager().registerEvents(this, Hawk.getInstance());
    }

    protected void flag(Player player, String data) {
        Hawk.getInstance().getDataManager().addViolation(player, this);
        for (Player playerLoop : Bukkit.getOnlinePlayers()) {
            if (playerLoop.hasPermission("hawk.alerts")) {
                String dataV = String.valueOf(data != null ? ChatColor.DARK_GRAY + "(" + ChatColor.RED + data + ChatColor.DARK_GRAY + ")" : "");
                String vl = String.valueOf(Hawk.getInstance().getDataManager().getViolatonsPlayer(player, this));
                playerLoop.sendMessage(ChatColor.DARK_GRAY + "(" + ChatColor.YELLOW + "!" + ChatColor.DARK_GRAY + ") " + ChatColor.YELLOW + player.getName() + ChatColor.DARK_GRAY + " might be using " + ChatColor.YELLOW + getName() + ChatColor.YELLOW + " VL: " + vl + " " + ChatColor.DARK_GRAY + dataV);
                EmbededMessages.sendCheatLog(Main.jda.getTextChannelsByName("anticheat", true).get(0));
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (this.enabled) {
            Bukkit.getPluginManager().registerEvents(this, Hawk.getInstance());
        } else {
            HandlerList.unregisterAll(this);
        }
    }

    public CheckType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}