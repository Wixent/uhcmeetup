package net.enganxe.meetupuhc;

import net.enganxe.meetupuhc.commands.ReloadCommand;
import net.enganxe.meetupuhc.commands.StatsCommand;
import net.enganxe.meetupuhc.commands.worldmeetupCommand;
import net.enganxe.meetupuhc.config.ConfigFile;
import net.enganxe.meetupuhc.config.WorldCreator;
import net.enganxe.meetupuhc.events.AutoStartEvent;
import net.enganxe.meetupuhc.events.DeathEvent;
import net.enganxe.meetupuhc.events.HubEvents;
import net.enganxe.meetupuhc.events.InventoryClick;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import net.enganxe.meetupuhc.player.Scoreboards;
import net.enganxe.meetupuhc.scenarios.TimeBomb;
import net.enganxe.meetupuhc.utils.UI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin implements Listener {

    public static ConfigFile config;
    public static boolean starting;
    public static boolean started;
    public static List<Player> PlayersAlive = new ArrayList<Player>();
    private AutoStartEvent scatterClass;
    public static int PlayersToStart;
    private Connection connection;
    public String host, database, username, password, table;
    public int port;

    public static Map<String, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable() {
        this.config = new ConfigFile(this);
        getServer().getPluginManager().registerEvents(new HubEvents(this), this);
        new TimeBomb(this);
        new AutoStartEvent(this);
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
        mysqlSetup();
        started = false;
        starting = false;
        StatsCommand.playerkills = 0;
        PlayersAlive.clear();
        PlayersToStart = config.getConfig().getInt("config.playerstostart");
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : boards.values()) {
                board.updateTitle(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("scoreboard.title")));
                Scoreboards.update(board);
            }
        }, 0, 20);
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("MeetupUHC is enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("MeetupUHC is disabled");
    }

    public void mysqlSetup() {
        host = config.getConfig().getString("mysql.host");
        port = config.getConfig().getInt("mysql.port");
        database = config.getConfig().getString("mysql.database");
        username = config.getConfig().getString("mysql.username");
        password = config.getConfig().getString("mysql.password");
        table = config.getConfig().getString("mysql.table");

        try {

            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":"
                        + this.port + "/" + this.database, this.username, this.password));

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}