package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ktCommand implements CommandExecutor {
    private Main plugin;
    public ktCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("kt").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return false;
    }
}
