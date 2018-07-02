package me.yolosanta.hawk.commands;

import me.yolosanta.hawk.Hawk;
import me.yolosanta.hawk.checks.Check;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HawkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            // Banana
        } else if (args[0].equalsIgnoreCase("toggle")) {
            if (!sender.hasPermission("hawk.admin")
                    && !sender.hasPermission("hawk.toggle")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            if (args.length == 2) {
                Check checkName = Hawk.getInstance().getDataManager().getCheckByName(args[1]);

                if (checkName == null) {
                    sender.sendMessage(ChatColor.RED + "Check '" + args[1] + "' does not exist!");
                    return true;
                }

                checkName.setEnabled(!checkName.isEnabled());
                sender.sendMessage(ChatColor.DARK_GRAY + "(" + ChatColor.RED + "!" + ChatColor.DARK_GRAY + ") " + ChatColor.GRAY + "Set check's state to: " + (checkName.isEnabled() ? ChatColor.GREEN : ChatColor.RED) + checkName.isEnabled());
                return true;
            }
            sender.sendMessage(ChatColor.RED + "Invalid arguments.");
            return true;
        } else if (args[0].equalsIgnoreCase("status")) {
            if (!sender.hasPermission("hawk.admin")
                    && !sender.hasPermission("hawk.status")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            List<String> checkNames = new ArrayList<>();

            for (Check checkLoop : Hawk.getInstance().getDataManager().getChecks()) {
                checkNames.add((checkLoop.isEnabled() ? ChatColor.GREEN + checkLoop.getName() : ChatColor.RED + checkLoop.getName()) + ChatColor.GRAY);
            }

            sender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------------");
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Hawk Status");
            sender.sendMessage("");
            sender.sendMessage(ChatColor.GRAY + "Checks: " + checkNames.toString());
            sender.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------------------------");
        }

        return true;
    }
}
