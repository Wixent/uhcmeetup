package net.enganxe.meetupuhc.player;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;

public class Scoreboards {
    public static void update(FastBoard board){
        if (!Main.started) {
            board.updateLines(
                    ChatColor.GOLD + "Players: " + ChatColor.RESET + Bukkit.getOnlinePlayers().size(),
                    "",
                    ChatColor.GOLD + "enganxe.net");
        }
        if (Main.started) {
            board.updateLines(
                    ChatColor.GOLD + "Players Alive: " + ChatColor.RESET + Main.PlayersAlive.size(),
                    ChatColor.GOLD + "Kills: " + ChatColor.RESET + board.getPlayer().getStatistic(Statistic.PLAYER_KILLS),
                    ChatColor.GOLD + "Border: " + ChatColor.RESET + getBorderSize(),
                    "",
                    ChatColor.GOLD + "enganxe.net");
        }
    }
    public static String getBorderSize() {
        double wB = Bukkit.getServer().getWorld("world").getWorldBorder().getSize();
        int wBint = (int) wB;
        String wBsize = String.valueOf(wBint);
        return wBsize;
    }
}
