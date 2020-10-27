package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private Main plugin;

    public ReloadCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("uhcmeetup").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("meetup.admin")){
            sender.sendMessage(ChatColor.RED + "You don't have permission tu execute this command");
            return true;
        }
        if (args.length == 0){
            sender.sendMessage(ChatColor.RED + "Usage: /uhcmeetup reload");
            return true;
        }
        if (args.length > 0){
            if (args[0].equalsIgnoreCase("reload")){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Main.config.getConfig().getString("messages.reload")));
                Main.config.reloadConfig();
            }
        }
        return false;
    }
}
