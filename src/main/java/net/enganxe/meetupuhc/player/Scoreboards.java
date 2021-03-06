package net.enganxe.meetupuhc.player;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.events.AutoStartEvent;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import net.enganxe.meetupuhc.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;

import java.util.List;

import static net.enganxe.meetupuhc.Main.*;

public class Scoreboards {
    public static boolean first;
    public static boolean first1;
    public static void update(FastBoard board) {
        if (!Main.started && !starting) {
            List<String> lines = Main.config.getConfig().getStringList("scoreboard.hubscoreboard");
            String onlineplayers = String.valueOf(Bukkit.getOnlinePlayers().size());
            for (int i = 0; i < lines.toArray().length; i++) {
                String currentLine = lines.get(i);
                if (currentLine.contains("%online%")) {
                    currentLine = currentLine.replace("%online%", onlineplayers);
                }

                board.updateLine(i, Utils.chat(currentLine));
            }
        } else if (starting && !started){
            List<String> lines = Main.config.getConfig().getStringList("scoreboard.countdownscoreboard");
            String onlineplayers = String.valueOf(Bukkit.getOnlinePlayers().size());
            if (first1){
                board.updateLines("");
                first1 = false;
            }
            for (int i = 0; i < lines.toArray().length; i++) {
                String currentLine = lines.get(i);
                if (currentLine.contains("%online%")) {
                    currentLine = currentLine.replace("%online%", onlineplayers);
                }
                if (currentLine.contains("%countdown%")) {
                    currentLine = currentLine.replace("%countdown%", "" + AutoStartEvent.time);
                }

                board.updateLine(i, Utils.chat(currentLine));
            }
        } else if (Main.started && !starting) {
                List<String> lines = Main.config.getConfig().getStringList("scoreboard.gamescoreboard");
                String kills = String.valueOf(board.getPlayer().getStatistic(Statistic.PLAYER_KILLS));
                String palive = String.valueOf(Main.PlayersAlive.size());
                if (first){
                    board.updateLines("");
                    first = false;
                }
                for (int i = 0; i < lines.toArray().length; i++) {
                    String currentLine = lines.get(i);
                    if (currentLine.contains("%aliveplayers%")) {
                        currentLine = currentLine.replace("%aliveplayers%", palive);
                    }
                    if (currentLine.contains("%kills%")) {
                        currentLine = currentLine.replace("%kills%", kills);
                    }
                    if (currentLine.contains("%border%")) {
                        currentLine = currentLine.replace("%border%", getBorderSize());
                    }
                    if (currentLine.contains("%time%")) {
                        currentLine = currentLine.replace("%time%", AutoStartEvent.timer);
                    }

                    board.updateLine(i, Utils.chat(currentLine));
                }
            }
    }
    public static String getBorderSize() {
        double wB = Bukkit.getServer().getWorld(config.getConfig().getString("worlds.meetup_world")).getWorldBorder().getSize() / 2;
        int wBint = (int) wB;
        String wBsize = String.valueOf(wBint);
        return wBsize;
    }
}
