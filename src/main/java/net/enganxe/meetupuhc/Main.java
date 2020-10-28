package net.enganxe.meetupuhc;

import net.enganxe.meetupuhc.commands.ReloadCommand;
import net.enganxe.meetupuhc.commands.StatsCommand;
import net.enganxe.meetupuhc.config.ConfigFile;
import net.enganxe.meetupuhc.events.AutoStartEvent;
import net.enganxe.meetupuhc.events.DeathEvent;
import net.enganxe.meetupuhc.events.HubEvents;
import net.enganxe.meetupuhc.events.InventoryClick;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import net.enganxe.meetupuhc.player.Scoreboards;
import net.enganxe.meetupuhc.utils.UI;
import org.bukkit.*;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    public static ConfigFile config;
    public static boolean starting;
    public static boolean started;
    public static ArrayList<Player> PlayersAlive;
    public static int PlayersToStart;
    public static int kills;

    public static Map<String, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable() {
        this.config = new ConfigFile(this);
        getServer().getPluginManager().registerEvents(new HubEvents(), this);
        getServer().getPluginManager().registerEvents(new AutoStartEvent(this), this);
        new InventoryClick(this);
        new StatsCommand(this);
        new ReloadCommand(this);
        new DeathEvent(this);
        UI.initialize();
        started = false;
        starting = false;
        PlayersToStart = config.getConfig().getInt("config.playerstostart");
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : boards.values()) {
                board.updateTitle(ChatColor.GOLD + "Enganxe Meetup");
                Scoreboards.update(board);

            }
        }, 0, 1);
        getLogger().info("MeetupUHC is enabled");
    }
    @Override
    public void onDisable() {
        getLogger().info("MeetupUHC is disabled");
    }
}
