package net.enganxe.meetupuhc.player;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;

import static net.enganxe.meetupuhc.Main.config;

public class Scoreboards {
    public static void update(FastBoard board){
        if (!Main.started) {
            String onlineplayers = String.valueOf(Bukkit.getOnlinePlayers().size());
            String line1 = config.getConfig().getString("scoreboard.hubscoreboard.line1");
            String line2 = config.getConfig().getString("scoreboard.hubscoreboard.line2");
            String line3 = config.getConfig().getString("scoreboard.hubscoreboard.line3");
            if (line1.contains("%players%")){
                line1 = line1.replace("%players%", onlineplayers);
            }
            if (line2.contains("%players%")){
                line2 = line2.replace("%players%", onlineplayers);
            }
            if (line3.contains("%players%")){
                line3 = line3.replace("%players%", onlineplayers);
            }
            board.updateLines((ChatColor.translateAlternateColorCodes('&', line1)),
                              (ChatColor.translateAlternateColorCodes('&', line2)),
                              (ChatColor.translateAlternateColorCodes('&', line3)));

            /* board.updateLines(
                    ChatColor.GOLD + "Players: " + ChatColor.WHITE + Bukkit.getOnlinePlayers().size(),
                    "",
                    ChatColor.GOLD + "enganxe.net"); */
        }
        if (Main.started) {
            String kills = String.valueOf(board.getPlayer().getStatistic(Statistic.PLAYER_KILLS));
            String palive = String.valueOf(board.getPlayer().getStatistic(Statistic.PLAYER_KILLS));
            String line1 = config.getConfig().getString("scoreboard.gamescoreboard.line1");
            String line2 = config.getConfig().getString("scoreboard.gamescoreboard.line2");
            String line3 = config.getConfig().getString("scoreboard.gamescoreboard.line3");
            String line4 = config.getConfig().getString("scoreboard.gamescoreboard.line4");
            String line5 = config.getConfig().getString("scoreboard.gamescoreboard.line5");
            if (line1.contains("%aliveplayers%")){
                line1 = line1.replace("%aliveplayers%", palive);
            }
            if (line2.contains("%aliveplayers%")){
                line2 = line2.replace("%aliveplayers%", palive);
            }
            if (line3.contains("%aliveplayers%")){
                line3 = line3.replace("%aliveplayers%", palive);
            }
            if (line4.contains("%aliveplayers%")){
                line4 = line3.replace("%aliveplayers%", palive);
            }
            if (line5.contains("%aliveplayers%")){
                line5 = line3.replace("%aliveplayers%", palive);
            }
            // kills
            if (line1.contains("%kills%")){
                line1 = line1.replace("%kills%", kills);
            }
            if (line2.contains("%kills%")){
                line2 = line2.replace("%kills%", kills);
            }
            if (line3.contains("%kills%")){
                line3 = line3.replace("%kills%", kills);
            }
            if (line4.contains("%kills%")){
                line4 = line3.replace("%kills%", kills);
            }
            if (line5.contains("%kills%")){
                line5 = line3.replace("%kills%", kills);
            }
            //border
            if (line1.contains("%border%")){
                line1 = line1.replace("%border%", getBorderSize());
            }
            if (line2.contains("%border%")){
                line2 = line2.replace("%border%", getBorderSize());
            }
            if (line3.contains("%border%")){
                line3 = line3.replace("%border%", getBorderSize());
            }
            if (line4.contains("%border%")){
                line4 = line3.replace("%border%", getBorderSize());
            }
            if (line5.contains("%border%")){
                line5 = line3.replace("%border%", getBorderSize());
            }
            board.updateLines(
                    (ChatColor.translateAlternateColorCodes('&', line1)),
                    (ChatColor.translateAlternateColorCodes('&', line2)),
                    (ChatColor.translateAlternateColorCodes('&', line3)),
                    (ChatColor.translateAlternateColorCodes('&', line4)),
                    (ChatColor.translateAlternateColorCodes('&', line5))
            );
                    /*ChatColor.GOLD + "Players Alive: " + ChatColor.WHITE + Main.PlayersAlive.size(),
                    ChatColor.GOLD + "Kills: " + ChatColor.WHITE + board.getPlayer().getStatistic(Statistic.PLAYER_KILLS),
                    ChatColor.GOLD + "Border: " + ChatColor.WHITE + getBorderSize(),
                    "",
                    ChatColor.GOLD + "enganxe.net");*/
        }
    }
    public static String getBorderSize() {
        double wB = Bukkit.getServer().getWorld(config.getConfig().getString("worlds.meetup_world")).getWorldBorder().getSize();
        int wBint = (int) wB;
        String wBsize = String.valueOf(wBint);
        return wBsize;
    }
}
