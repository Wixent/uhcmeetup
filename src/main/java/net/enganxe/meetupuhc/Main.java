package net.enganxe.meetupuhc;

import net.enganxe.meetupuhc.commands.ReloadCommand;
import net.enganxe.meetupuhc.commands.StatsCommand;
import net.enganxe.meetupuhc.commands.worldmeetupCommand;
import net.enganxe.meetupuhc.config.ConfigFile;
import net.enganxe.meetupuhc.config.WorldCreator;
import net.enganxe.meetupuhc.events.*;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import net.enganxe.meetupuhc.player.Scoreboards;
import net.enganxe.meetupuhc.utils.UI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin {

    public static ConfigFile config;
    public static boolean starting;
    public static boolean started;
    public static List<Player> PlayersAlive = new ArrayList<Player>();
    private AutoStartEvent scatterClass;
    public static int PlayersToStart;

    public static Map<String, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable() {
        this.config = new ConfigFile(this);
        getServer().getPluginManager().registerEvents(new HubEvents(this), this);
        getServer().getPluginManager().registerEvents(new AutoStartEvent(this), this);
        new InventoryClick(this);
        new StatsCommand(this);
        new ReloadCommand(this);
        new worldmeetupCommand(this);
        new DeathEvent(this);
        new WorldCreator();
        WorldCreator.createLobby();
        WorldCreator.deleteWorld();
        WorldCreator.createWorld();
        UI.initialize();
        started = false;
        starting = false;
        PlayersToStart = config.getConfig().getInt("config.playerstostart");
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : boards.values()) {
                board.updateTitle(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("scoreboard.title")));
                Scoreboards.update(board);
            }
        }, 0, 20);
        getLogger().info("MeetupUHC is enabled");
    }
    @Override
    public void onDisable() {
        getLogger().info("MeetupUHC is disabled");
    }

    public AutoStartEvent AutoStartEvent(){
        return this.scatterClass;
    }
}