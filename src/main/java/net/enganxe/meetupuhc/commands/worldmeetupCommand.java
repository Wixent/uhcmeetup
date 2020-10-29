package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.enganxe.meetupuhc.Main.config;

public class worldmeetupCommand implements CommandExecutor {
    private Main plugin;

    public worldmeetupCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("worldmeetup").setExecutor(this); // Hice este comando para testear si funciona lo de las seeds de los mundos

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            return true;
        }
        Player p = (Player) sender;
        Location loc = new Location(Bukkit.getWorld(config.getConfig().getString("worlds.meetup_world")), 0, 100, 0);
        p.teleport(loc);
        return false;
    }
}
