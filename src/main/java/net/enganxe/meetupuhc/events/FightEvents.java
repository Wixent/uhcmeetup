package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class FightEvents implements Listener {

    private static Main plugin;
    public FightEvents (Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        Player p = (Player) e.getEntity();
        Player a = (Player) e.getDamager();
        if (p.isBlocking()){
            if (a.getItemInHand().getType().equals(Material.DIAMOND_AXE) || a.getItemInHand().getType().equals(Material.IRON_AXE)){
                a.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 1);
            }
        }
    }
}
