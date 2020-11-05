package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.guis.TopUI;
import net.enganxe.meetupuhc.guis.UI;
import net.enganxe.meetupuhc.guis.UI2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.enganxe.meetupuhc.Main.config;

public class TopStatsCommand implements CommandExecutor {
    private Main plugin;
    public static int maxKills1 = 0;
    public static int maxKills2 = 0;
    public static int maxKills3 = 0;
    public static String maxKillsPlayer1 = "";
    public static String maxKillsPlayer2 = "";
    public static String maxKillsPlayer3 = "";
    public static int maxDeaths1 = 0;
    public static int maxDeaths2 = 0;
    public static int maxDeaths3 = 0;
    public static String maxDeathsPlayer1 = "";
    public static String maxDeathsPlayer2 = "";
    public static String maxDeathsPlayer3 = "";
    public static int maxWins1 = 0;
    public static int maxWins2 = 0;
    public static int maxWins3 = 0;
    public static String maxWinsPlayer1 = "";
    public static String maxWinsPlayer2 = "";
    public static String maxWinsPlayer3 = "";
    public static int gamesPlayed;

    public TopStatsCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("topstats").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            return true;
        }
        gamesPlayed = config.getConfig().getInt("stats.Played");
        for (String playerName : config.getConfig().getConfigurationSection("stats.players").getKeys(false)) {
            int kills = config.getConfig().getInt("stats.players" + playerName + ".kills");
            if (kills > maxKills1) {
                maxKills1 = kills;
                maxKillsPlayer1 = config.getConfig().getString("stats.players." + playerName + ".name") ;
            }
            if(kills > maxKills2 && maxKills1 >= maxKills2) {
                maxKills2 = kills;
                maxKillsPlayer2 = config.getConfig().getString("stats.players." + playerName + ".name");
            }
            if(kills > maxKills3 && maxKills2 >= maxKills3)  {
                maxKills3 = kills;
                maxKillsPlayer3 = config.getConfig().getString("stats.players." + playerName + ".name");
            }
        }
        for (String playerName : config.getConfig().getConfigurationSection("stats.players").getKeys(false)) {
            int deaths = config.getConfig().getInt("stats.players" + playerName + ".deaths");
            if (deaths > maxDeaths1) {
                maxDeaths1 = deaths;
                maxDeathsPlayer1 = config.getConfig().getString("stats.players." + playerName + ".name") ;
            }
            if(deaths > maxDeaths2 && maxDeaths1 >= maxDeaths2) {
                maxDeaths2 = deaths;
                maxDeathsPlayer2 = config.getConfig().getString("stats.players." + playerName + ".name");
            }
            if(deaths > maxDeaths3 && maxDeaths2 >= maxDeaths3)  {
                maxDeaths3 = deaths;
                maxDeathsPlayer3 = config.getConfig().getString("stats.players." + playerName + ".name");
            }
        }
        for (String playerName : config.getConfig().getConfigurationSection("stats.players").getKeys(false)) {
            int wins = config.getConfig().getInt("stats.players" + playerName + ".wins");
            if (wins > maxWins1) {
                maxWins1 = wins;
                maxWinsPlayer1 = config.getConfig().getString("stats.players." + playerName + ".name") ;
            }
            if(wins > maxWins2 && maxWins1 >= maxWins2) {
                maxWins2 = wins;
                maxWinsPlayer2 = config.getConfig().getString("stats.players." + playerName + ".name");
            }
            if(wins > maxWins3 && maxWins2 >= maxWins3)  {
                maxWins3 = wins;
                maxWinsPlayer3 = config.getConfig().getString("stats.players." + playerName + ".name");
            }
        }
        Player player = (Player) sender;
        player.openInventory(TopUI.GUI(player));
        return false;
    }
}
