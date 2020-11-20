package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class FightEvents implements Listener {

    private static Main plugin;
    public FightEvents (Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void left(PlayerQuitEvent e) {
        if (Main.started && !Main.finalized) {
            Player p = e.getPlayer();
            if (p.getGameMode() == GameMode.SURVIVAL) {
                World w = p.getWorld();
                Location loc = p.getLocation();
                Inventory inv = p.getInventory();
                for (ItemStack item : inv.getContents()) {
                    w.dropItem(loc, item);
                }
            }
        }
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.PLAYER) {
            Player p = (Player) e.getEntity();
            Player a = (Player) e.getDamager();
            if (p.isBlocking()) {
                if (a.getItemInHand().getType().equals(Material.DIAMOND_AXE) || a.getItemInHand().getType().equals(Material.IRON_AXE) || a.getItemInHand().getType().equals(Material.NETHERITE_AXE)){
                    a.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 10, 1);
                }
            }
        }
    }
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity entity = (Entity) e.getEntity();
        if (entity instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                Player t = (Player) arrow.getShooter();
                int h = (int) p.getHealth();
                int a = (int) p.getAbsorptionAmount();
                int heal = h + a;
                String name = p.getName();
                assert t != null;
                t.sendMessage(ChatColor.GOLD + name + ChatColor.YELLOW + " is in " + ChatColor.RED + heal + " ❤");
            }
        }
    }
    @EventHandler
    public void axenerf(EntityDamageByEntityEvent e){
        if (e.getDamager().getType() == EntityType.PLAYER) {
            Player a = (Player) e.getDamager();
            Player t = (Player) e.getEntity();
            if (!t.isBlocking()) {
                if (a.getItemInHand().getType().equals(Material.DIAMOND_AXE) || a.getItemInHand().getType().equals(Material.IRON_AXE)) {
                    int d = (int) e.getDamage();
                    int da = (int) (d * 0.92);
                    e.setDamage(da);
                }
            }
        }
    }
}
