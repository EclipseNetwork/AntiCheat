package me.yolosanta.hawk;

import me.yolosanta.hawk.checks.Check;
import me.yolosanta.hawk.commands.HawkCommand;
import me.yolosanta.hawk.data.DataManager;
import me.yolosanta.hawk.discord.Main;
import me.yolosanta.hawk.events.UtilityJoinQuitEvent;
import me.yolosanta.hawk.events.UtilityMoveEvent;
import me.yolosanta.hawk.packetcore.PacketCore;
import me.yolosanta.hawk.util.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Hawk extends JavaPlugin implements Listener {

    public static int onlinePlayers;
    private static long MS_PluginLoad;
    private static String coreVersion;
    private static Hawk instance;
    private DataManager dataManager;

    public static Hawk getInstance() {
        return instance;
    }

    public void onEnable() {
        new Main().runTaskTimer(this, 0, 100);
        Main.main(null);
        instance = this;
        dataManager = new DataManager();
        registerCommands();
        registerListeners();
        loadChecks();
        new Ping(this);
        addDataPlayers();
        PacketCore.init();
        MS_PluginLoad = TimerUtils.nowlong();
        coreVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    public void onDisable() {
        saveChecks();
    }

    private void registerCommands() {
        getCommand("hawk").setExecutor(new HawkCommand());
    }

    private void loadChecks() {
        for (Check check : getDataManager().getChecks()) {
            if (getConfig().get("checks." + check.getName() + ".enabled") != null) {
                check.setEnabled(getConfig().getBoolean("checks." + check.getName() + ".enabled"));
            } else {
                getConfig().set("checks." + check.getName() + ".enabled", check.isEnabled());
                saveConfig();
            }
        }
    }

    private void saveChecks() {
        for (Check check : getDataManager().getChecks()) {
            getConfig().set("checks." + check.getName() + ".enabled", check.isEnabled());
            saveConfig();
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new UtilityMoveEvent(), this);
        getServer().getPluginManager().registerEvents(new UtilityJoinQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new SetBackSystem(), this);
        getServer().getPluginManager().registerEvents(new VelocityUtils(), this);
        getServer().getPluginManager().registerEvents(new NEW_Velocity_Utils(), this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    private void addDataPlayers() {
        for (Player playerLoop : Bukkit.getOnlinePlayers()) {
            getInstance().getDataManager().addPlayerData(playerLoop);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Main.players++;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Main.players--;
    }
}
