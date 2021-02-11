package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.utils.Utils;
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

        if (args.length == 0){
            sender.sendMessage(Utils.chat("&dUHC Meetup 1.16"));
            sender.sendMessage(Utils.chat("&dAuthor: Wixent"));
            sender.sendMessage(Utils.chat("&dPlugin Version: 1.03"));
            if (sender.hasPermission("meetup.admin")) {
                sender.sendMessage(Utils.chat("&cReload Config: /uhcmeetup reload"));
            }
        }
        if (args.length > 0){
            if (args[0].equalsIgnoreCase("reload")){
                if (!sender.hasPermission("meetup.admin")){
                    sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command");
                    return true;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        Main.config.getConfig().getString("messages.reload")));
                Main.config.reloadConfig();
            }
        }
        return false;
    }
}
