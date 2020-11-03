package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.guis.UI;
import net.enganxe.meetupuhc.guis.UI2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.enganxe.meetupuhc.Main.config;

public class StatsCommand implements CommandExecutor {
    private Main plugin;
    public static int playerkills;
    public static int playerdeaths;
    public static int playerwins;
    public static String targetname;

    public StatsCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("stats").setExecutor(this);

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            return true;
        }
        Player player = (Player) sender;
        String p = player.getName();
        if (args.length == 0) {
            playerkills = config.getConfig().getInt("stats.players." + p + ".kills");
            playerdeaths = config.getConfig().getInt("stats.players." + p + ".deaths");
            playerwins = config.getConfig().getInt("stats.players." + p + ".wins");
            player.openInventory(UI.GUI(player));
        }
        else if (args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null){
                targetname = target.getName();
                playerkills = config.getConfig().getInt("stats.players." + targetname + ".kills");
                playerdeaths = config.getConfig().getInt("stats.players." + targetname + ".deaths");
                playerwins = config.getConfig().getInt("stats.players." + targetname + ".wins");
                player.openInventory(UI2.GUI(player));

            } else{
                player.sendMessage(ChatColor.RED + "Mention an Online User");
            }
        }
        return false;
    }
}
