package net.enganxe.meetupuhc.scenarios;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Objects;

public class NoClean implements Listener {
    public static int ncleantime;
    private Main plugin;

    public NoClean(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void noClean(EntityDeathEvent e){
        if (e.getEntity().getType() == EntityType.PLAYER && Objects.requireNonNull(e.getEntity().getKiller()).getType() == EntityType.PLAYER){
            e.getEntity().getKiller().setInvulnerable(true);
            e.getEntity().getKiller().sendMessage((ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "You are currently immune for " + Main.config.getConfig().getInt("config.nocleantime") +  " seconds unless you hit someone."));
            ncleantime = Main.config.getConfig().getInt("config.nocleantime");
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    ncleantime = ncleantime - 1;
                    if (ncleantime >= 2){
                        e.getEntity().getKiller().sendMessage(ChatColor.RED + "You are going to lose your invulnerability in " + ncleantime + "s");
                    }
                    if (ncleantime == 1) {
                        e.getEntity().getKiller().setInvulnerable(false);
                        e.getEntity().getKiller().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "You have lost your invulnerability.");
                    }
                }
            }, 0L, 20L);
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
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Player) {
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                Player t = (Player) arrow.getShooter();
                if (t.isInvulnerable()){
                    e.getDamager().setInvulnerable(false);
                    e.getDamager().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "You have lost your invulnerability");
                }
            }
            else if (e.getDamager() instanceof Trident){
                Trident trident = (Trident) e.getDamager();
                Player t = (Player) trident.getShooter();
                if (t.isInvulnerable()){
                    e.getDamager().setInvulnerable(false);
                    e.getDamager().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + "You have lost your invulnerability");
                }
            }
        }
    }
}