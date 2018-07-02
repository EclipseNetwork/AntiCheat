package me.yolosanta.hawk.discord;

import me.yolosanta.hawk.checks.Check;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.util.Calendar;
import java.util.TimeZone;

public class EmbededMessages {

    private static Check check;
    private static Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    private static int year = cal.get(Calendar.YEAR);
    private static int month = cal.get(Calendar.MONTH) + 1;
    private static int day = cal.get(Calendar.DATE);
    private static String monthdayyear = month + "/" + day + "/" + year;

    public EmbededMessages(Check check) {
        check = check;
    }

    public static void sendCheatLog(TextChannel channel) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#940704"));
        builder.setThumbnail("https://images-ext-1.discordapp.net/external/nayQZbWqtPJjyxRDXUC_gXizhE82YE048nAYQ4GmHfY/https/pbs.twimg.com/profile_images/916846337114521600/SRscveGj_400x400.jpg");
        builder.setFooter("EclipseHCF Development Team | " + monthdayyear, "https://images-ext-1.discordapp.net/external/nayQZbWqtPJjyxRDXUC_gXizhE82YE048nAYQ4GmHfY/https/pbs.twimg.com/profile_images/916846337114521600/SRscveGj_400x400.jpg");

        builder.addField("Hack: ", check.getName(), false);
        builder.addField("User: ", "YoloSanta", false);
        channel.sendMessage(builder.build()).queue();
    }
}
