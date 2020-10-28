package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;;import java.util.HashMap;
import java.util.Map;

import static net.enganxe.meetupuhc.Main.config;

public class DeathEvent implements Listener {
    private static Main plugin;
    public static Map<String, Integer> PlayerKills = new HashMap<>();

    public DeathEvent (Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent e){
        if (e.getEntity().getKiller() instanceof Player){
            String killer = e.getEntity().getKiller().getName();
            String p = e.getEntity().getName();
            String msg = config.getConfig().getString("messages.death_by_player");
            msg = msg.replace("%victim%", p);
            msg = msg.replace("%killer%", killer);
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', msg));
            if (Main.PlayersAlive.contains(p)){
                Main.PlayersAlive.remove(p);
            }
        }
        else if (!(e.getEntity().getKiller() instanceof Player)){
            String p = e.getEntity().getName();
            String msg = config.getConfig().getString("messages.death");
            msg = msg.replace("%victim%", p);
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', msg));
            if (Main.PlayersAlive.contains(p)){
                Main.PlayersAlive.remove(p);
            }
        }
        if (Main.PlayersAlive.size() == 1){
            for (Player p : Main.PlayersAlive) {
                Bukkit.broadcastMessage("" + ChatColor.YELLOW + p + ChatColor.GOLD + " won the Meetup!");
            }
        }
    }
}
