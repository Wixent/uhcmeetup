package net.enganxe.meetupuhc;

import net.enganxe.meetupuhc.events.AutoStartEvent;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;

public class Scoreboards {
    public static void hubScoreboard(FastBoard board){
        board.updateLines(
                "",
                "Players: " + ChatColor.GOLD + Bukkit.getOnlinePlayers().size(),
                "",
                ChatColor.GOLD + "enganxe.net");
    }
    public static void CountdownS(FastBoard board){
        board.updateLines(
                "",
                "Players: " + ChatColor.GOLD + Bukkit.getOnlinePlayers().size(),
                "Starting in: " + ChatColor.GOLD + AutoStartEvent.time,
                "",
                ChatColor.GOLD + "enganxe.net");
    }
    public static void GameScoreboard(FastBoard board){
        board.updateLines(
                "Players Alive: " + ChatColor.GOLD + Main.PlayersAlive.size(),
                "Kills: " + ChatColor.GOLD + board.getPlayer().getStatistic(Statistic.PLAYER_KILLS),
                "Border: " + ChatColor.GOLD + getBorderSize(),
                "",
                ChatColor.GOLD + "enganxe.net");
    }
    public static String getBorderSize() {
        double wB = Bukkit.getServer().getWorld("world").getWorldBorder().getSize();
        int wBint = (int) wB;
        String wBsize = String.valueOf(wBint);
        return wBsize;
    }
}
