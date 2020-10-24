package net.enganxe.meetupuhc;

import net.enganxe.meetupuhc.events.AutoStartEvent;
import net.enganxe.meetupuhc.events.DeathEvent;
import net.enganxe.meetupuhc.events.HubEvents;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    public static boolean starting;
    public static boolean started;
    public static ArrayList<Player> PlayersAlive;
    public static int PlayersToStart = 2;
    public static int kills;

    public static Map<String, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new HubEvents(), this);
        getServer().getPluginManager().registerEvents(new AutoStartEvent(this), this);
        new DeathEvent(this);
        started = false;
        starting = false;
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : boards.values()) {
                board.updateTitle(ChatColor.GOLD + "Enganxe Meetup");
                if (!starting && !started){
                    Scoreboards.hubScoreboard(board);
                }
                else if (starting && !started){
                    Scoreboards.CountdownS(board);
                }
                else if (started && !starting){
                    Scoreboards.GameScoreboard(board);
                }

            }
        }, 0, 1);
        World world = Bukkit.getWorld("world");
        WorldBorder border = world.getWorldBorder();
        border.setSize(200);
        border.setCenter(0.0, 0.0);
        world.setGameRule(GameRule.NATURAL_REGENERATION, false);

        getLogger().info("MeetupUHC is enabled");
    }
    @Override
    public void onDisable() {
        getLogger().info("MeetupUHC is disabled");
    }
}
