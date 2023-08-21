package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.enganxe.meetupuhc.Main.*;

public class StatsEvents implements Listener {
    private static Main plugin;

    public StatsEvents (Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void join(PlayerJoinEvent e) {
        String player = e.getPlayer().getName();
        if (!config.getConfig().getBoolean("stats.mysql.enabled")) {
            if (!config.getConfig().contains("stats.players." + player)) {
                config.getConfig().set("stats.players." + player + ".name", player);
                config.getConfig().set("stats.players." + player + ".kills", 0);
                config.getConfig().set("stats.players." + player + ".deaths", 0);
                config.getConfig().set("stats.players." + player + ".wins", 0);
                config.getConfig().set("stats.players." + player + ".gamesplayed", 0);
                config.saveConfig();
            }
        }
    }
    @EventHandler
    public void death(PlayerDeathEvent e){
        Player p = e.getEntity();
        if (!config.getConfig().getBoolean("stats.mysql.enabled")) {
            if (p.getKiller() instanceof Player) {
                String player = p.getName();
                String killer = p.getKiller().getName();
                int kills = config.getConfig().getInt("stats.players." + killer + ".kills");
                int deaths = config.getConfig().getInt("stats.players." + player + ".deaths");

                config.getConfig().set("stats.players." + killer + ".kills", kills + 1);
                config.getConfig().set("stats.players." + player + ".deaths", deaths + 1);
                config.saveConfig();
            }
        }
    }
    @EventHandler
    public void quit(PlayerQuitEvent e){
        String player = e.getPlayer().getName();
        Player p = e.getPlayer();
        if (!config.getConfig().getBoolean("stats.mysql.enabled")) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                if (started && !finalized) {
                    int deaths = config.getConfig().getInt("stats.players." + player + ".deaths");
                    config.getConfig().set("stats.players." + player + ".deaths", deaths + 1);
                    config.saveConfig();
                }
            }
        }
    }

}
