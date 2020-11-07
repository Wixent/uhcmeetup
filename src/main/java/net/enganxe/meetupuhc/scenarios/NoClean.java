package net.enganxe.meetupuhc.scenarios;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class NoClean implements Listener {
    private final Main plugin;
    int time;
    public NoClean(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void noClean(EntityDeathEvent e){
            if (e.getEntity().getType() == EntityType.PLAYER && e.getEntity().getKiller().getType() == EntityType.PLAYER){
                e.getEntity().getKiller().setInvulnerable(true);
                e.getEntity().getKiller().sendMessage((ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "You are currently immune for " + Main.config.getConfig().getInt("config.nocleantime") +  " seconds unless you hit someone."));
                time = Main.config.getConfig().getInt("config.nocleantime");
                Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
                    @Override
                    public void run() {
                        time--;
                        if (time >= 2){
                            e.getEntity().getKiller().sendMessage(ChatColor.RED + "You are going to lose your invulnerability in " + time + "s");
                        }
                        if (time == 1) {
                            e.getEntity().getKiller().setInvulnerable(false);
                            e.getEntity().getKiller().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "You have lost your invulnerability.");
                        }
                    }
                },0L, 20L);
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
