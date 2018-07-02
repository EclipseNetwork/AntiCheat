package me.yolosanta.hawk.discord;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;

public class Main extends BukkitRunnable {

    public static JDA jda;
    public static int players = 0;

    public static void main(String[] args) {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("NDYyNzQyMjI2MzUxNjg1NjMy.DhmRzA.rTGGwEOFj9Iz-1QqyqURzlKwVsU")
                    .setStatus(OnlineStatus.ONLINE)

                    .buildBlocking();

        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("NDYyNzQyMjI2MzUxNjg1NjMy.DhmRzA.rTGGwEOFj9Iz-1QqyqURzlKwVsU")
                    .setGame(Game.playing("with " + players + " users!"))
                    .buildBlocking();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
