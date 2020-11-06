package net.enganxe.meetupuhc.scenarios;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Material.*;

public class TimeBomb implements Listener {
    private final Main plugin;
    public TimeBomb(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final Location loc = player.getLocation().clone();

        Block leftSide = loc.getBlock();
        Block rightSide = loc.clone().add(-1, 0, 0).getBlock();

        leftSide.setType(Material.CHEST);
        rightSide.setType(Material.CHEST);

        BlockData leftData = leftSide.getBlockData();
        ((Directional) leftData).setFacing(BlockFace.NORTH);
        leftSide.setBlockData(leftData);

        org.bukkit.block.data.type.Chest chestDataLeft = (org.bukkit.block.data.type.Chest) leftData;
        chestDataLeft.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);
        leftSide.setBlockData(chestDataLeft);

        Chest chest = (Chest) leftSide.getState();

        BlockData rightData = rightSide.getBlockData();
        ((Directional) rightData).setFacing(BlockFace.NORTH);
        rightSide.setBlockData(rightData);

        org.bukkit.block.data.type.Chest chestDataRight = (org.bukkit.block.data.type.Chest) rightData;
        chestDataRight.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
        rightSide.setBlockData(chestDataRight);

        for (ItemStack item : event.getDrops()) {
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }
            chest.getInventory().addItem(item);
        }
        ItemStack a = new ItemStack(Material.NETHERITE_SCRAP, 2);
        ItemStack e = new ItemStack(Material.GOLD_INGOT, 4);
        ItemStack ghead = new ItemStack(GOLDEN_APPLE, 1);
        ItemMeta gheadMeta = ghead.getItemMeta();
        String headName = Main.config.getConfig().getString("config.goldenhead");
        assert gheadMeta != null;
        assert headName != null;
        gheadMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                headName));
        ghead.setItemMeta(gheadMeta);

        chest.getInventory().addItem(ghead);
        chest.getInventory().addItem(a);
        chest.getInventory().addItem(e);

        event.getDrops().clear();

        final ArmorStand stand = player.getWorld().spawn(chest.getLocation().clone().add(0.5, 1, 0), ArmorStand.class);

        stand.setCustomNameVisible(true);
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setVisible(false);

        stand.setMarker(true);

        new BukkitRunnable() {
            private int time = 30; // add one for countdown.

            public void run() {
                time--;

                if (time == 0) {
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + "§a" + player.getName() + "'s §fcorpse has exploded!");
                    loc.getBlock().setType(Material.AIR);
                    loc.getWorld().createExplosion(loc.getBlockX() + 0.5, loc.getBlockY() + 0.5, loc.getBlockZ() + 0.5, 6, false, true);
                    loc.getWorld().strikeLightning(loc);
                    stand.remove();
                    cancel();
                    return;
                } else if (time == 1) {
                    stand.setCustomName("§4" + time + "s");
                } else if (time == 2) {
                    stand.setCustomName("§c" + time + "s");
                } else if (time == 3) {
                    stand.setCustomName("§6" + time + "s");
                } else if (time <= 15) {
                    stand.setCustomName("§e" + time + "s");
                } else {
                    stand.setCustomName("§a" + time + "s");
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
