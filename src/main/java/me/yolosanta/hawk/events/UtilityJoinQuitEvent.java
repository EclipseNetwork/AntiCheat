package me.yolosanta.hawk.events;


import me.yolosanta.hawk.Hawk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UtilityJoinQuitEvent implements Listener {


    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent e) {
        Hawk.getInstance().getDataManager().addPlayerData(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerQuitEvent e) {
        Hawk.getInstance().getDataManager().removePlayerData(e.getPlayer());
    }
}
