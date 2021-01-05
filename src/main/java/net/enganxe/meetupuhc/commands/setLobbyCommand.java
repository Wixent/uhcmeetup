package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class setLobbyCommand implements CommandExecutor {
    private Main plugin;

    public setLobbyCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("setlobby").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        if (!sender.hasPermission("meetup.admin")){
            sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command");
            return true;
        }
        if (p.getWorld() != Bukkit.getWorld(Main.config.getConfig().getString("worlds.lobby_world"))){
            sender.sendMessage(Utils.chat("&cYou need to be in the lobby world to do this, check the config file"));
            return true;
        }
        Location l = p.getLocation();
        saveLobbyLocation(l);
        p.sendMessage(Utils.chat("&aLobby spawn has been setted"));
        return false;
    }

    public static void saveLobbyLocation(Location location) {
        Main.config.getConfig().set("lobby.x", Integer.valueOf(location.getBlockX()));
        Main.config.getConfig().set("lobby.y", Integer.valueOf(location.getBlockY()));
        Main.config.getConfig().set("lobby.z", Integer.valueOf(location.getBlockZ()));
        Main.config.getConfig().set("lobby.yaw", Float.valueOf(location.getYaw()));
        Main.config.getConfig().set("lobby.pitch", Float.valueOf(location.getPitch()));
        Main.config.saveConfig();
    }

    public static Location getLobbyLocation() {
        try {
            return new Location(Bukkit.getWorld(Main.config.getConfig().getString("worlds.lobby_world")), Main.config.getConfig().getInt("lobby.x"), Main.config.getConfig().getInt("lobby.y"), Main.config.getConfig().getInt("lobby.z"), Main.config.getConfig().getInt("lobby.yaw"), Main.config.getConfig().getInt("lobby.pitch"));
        } catch (Exception e) {
            return null;
        }
    }
}
