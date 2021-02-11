package net.enganxe.meetupuhc.scenarios;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.utils.Utils;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class NoClean implements Listener {
    private final Main plugin;
    public NoClean(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public static List<Player> NoCleanList = new ArrayList<>();
    @EventHandler
    public void noClean(PlayerDeathEvent e){
        if (Main.config.getConfig().getBoolean("config.scenarios.noclean")){
            if (e.getEntity().getKiller().getType() == EntityType.PLAYER){
                Player p = e.getEntity();
                Player k = e.getEntity().getKiller();
                assert k != null;
                k.setInvulnerable(true);
                NoCleanList.add(k);
                k.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.noclean1")).replace("%time%", "" + Main.config.getConfig().getInt("config.nocleantime")));
                new BukkitRunnable() {
                    public void run() {
                        if (NoCleanList.contains(k)) {
                            k.setInvulnerable(false);
                            k.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.noclean2")));
                            NoCleanList.remove(k);
                        }else{
                            cancel();
                        }
                    }
                }.runTaskLater(plugin, Main.config.getConfig().getInt("config.nocleantime") *20L);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (Main.config.getConfig().getBoolean("config.scenarios.noclean")){
            if (e.getEntity().getType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.PLAYER){
                if (NoCleanList.contains(e.getDamager())){
                    e.getDamager().setInvulnerable(false);
                    e.getDamager().sendMessage(Utils.chat(Main.config.getConfig().getString("messages.noclean2")));
                    NoCleanList.remove(e.getDamager());
                }
            }
            if (e.getEntity().getType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.ARROW){
                Arrow arrow = (Arrow) e.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player p = (Player) arrow.getShooter();
                    if (NoCleanList.contains(p)){
                        p.setInvulnerable(false);
                        p.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.noclean2")));
                        NoCleanList.remove(p);
                    }
                }
            }
            if (e.getEntity().getType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.TRIDENT){
                Trident trident = (Trident) e.getDamager();
                if (trident.getShooter() instanceof Player) {
                    Player p = (Player) trident.getShooter();
                    if (NoCleanList.contains(p)){
                        p.setInvulnerable(false);
                        p.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.noclean2")));
                        NoCleanList.remove(p);
                    }
                }
            }
            if (e.getEntity().getType() == EntityType.PLAYER && e.getDamager().getType() == EntityType.SPECTRAL_ARROW){
                SpectralArrow arrow = (SpectralArrow) e.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player p = (Player) arrow.getShooter();
                    if (NoCleanList.contains(p)){
                        p.setInvulnerable(false);
                        p.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.noclean2")));
                        NoCleanList.remove(p);
                    }
                }
            }
        }
    }
}
