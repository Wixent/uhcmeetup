package net.enganxe.meetupuhc.scenarios;

import net.enganxe.meetupuhc.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class NoClean implements Listener {
    private final Main plugin;
    public NoClean(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void noClean(EntityDeathEvent e){
            if (e.getEntity().getType() == EntityType.PLAYER && e.getEntity().getKiller().getType() == EntityType.PLAYER){
                e.getEntity().getKiller().setInvulnerable(true);
                e.getEntity().getKiller().sendMessage((ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "You are currently immune for 20 seconds unless you hit someone."));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        e.getEntity().getKiller().setInvulnerable(false);
                        e.getEntity().getKiller().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "You have lost your invulnerability.");
                    }
                }.runTaskLater(plugin, 400L);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
            if (e.getEntity().getType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.PLAYER){
                if (e.getEntity().isInvulnerable()){
                    e.getDamager().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "The Player has immunity because of NoClean.");
                }
                if (e.getDamager().isInvulnerable()){
                    e.getDamager().setInvulnerable(false);
                    e.getDamager().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "You have lost your invulnerability");
                }
            }
    }
}
