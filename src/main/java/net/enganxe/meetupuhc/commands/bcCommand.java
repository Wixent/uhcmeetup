package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class bcCommand implements CommandExecutor {
    private Main plugin;
    public bcCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("bc").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("meetup.admin")){
            return true;
        }
        
        return false;
    }
}
