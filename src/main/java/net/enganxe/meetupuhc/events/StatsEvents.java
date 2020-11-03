package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.enganxe.meetupuhc.Main.config;

public class StatsEvents implements Listener {
    private static Main plugin;

    public StatsEvents (Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void join(PlayerJoinEvent e) {
        String player = e.getPlayer().getName();
        if (!config.getConfig().contains("stats.players." + player)){
            config.getConfig().set("stats.players." + player + ".kills", 0);
            config.getConfig().set("stats.players." + player + ".deaths", 0);
            config.getConfig().set("stats.players." + player + ".wins", 0);
            config.saveConfig();
        }
    }
    @EventHandler
    public void death(PlayerDeathEvent e){
        Player p = e.getEntity();
        if (p.getKiller() instanceof Player){
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
