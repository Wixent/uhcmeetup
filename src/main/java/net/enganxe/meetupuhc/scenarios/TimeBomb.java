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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Material.*;

public class TimeBomb implements Listener {
    private final Main plugin;
    public TimeBomb(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerDeathEvent e) {
        if (Main.config.getConfig().getBoolean("config.scenarios.timebomb")) {
            final Player player = e.getEntity();
            final Location loc = player.getLocation().clone();

            if (Math.signum(e.getEntity().getLocation().getBlockY()) == -1.0 || Math.signum(e.getEntity().getLocation().getBlockY()) == 0.0)
                return;

            Block leftSide = loc.getBlock();
            Block rightSide = loc.clone().add(-1, 0, 0).getBlock();

            if (rightSide.getRelative(BlockFace.UP).getType() == Material.BEDROCK) {
                rightSide.getRelative(BlockFace.UP).setType(Material.AIR);
            }
            if (leftSide.getRelative(BlockFace.UP).getType() == Material.BEDROCK) {
                leftSide.getRelative(BlockFace.UP).setType(Material.AIR);
            }

            leftSide.setType(Material.CHEST);
            rightSide.setType(Material.CHEST);

            BlockData leftData = leftSide.getBlockData();
            ((Directional) leftData).setFacing(BlockFace.NORTH);
            leftSide.setBlockData(leftData);

            org.bukkit.block.data.type.Chest chestDataLeft = (org.bukkit.block.data.type.Chest) leftData;
            chestDataLeft.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);
            leftSide.setBlockData(chestDataLeft);

            Chest leftChest = (Chest) leftSide.getState();

            BlockData rightData = rightSide.getBlockData();
            ((Directional) rightData).setFacing(BlockFace.NORTH);
            rightSide.setBlockData(rightData);

            org.bukkit.block.data.type.Chest chestDataRight = (org.bukkit.block.data.type.Chest) rightData;
            chestDataRight.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
            rightSide.setBlockData(chestDataRight);

            Chest rightChest = (Chest) rightSide.getState();

            for (int i = 0; i < e.getDrops().size(); i++) {
                ItemStack item = e.getDrops().get(i);
                if (item == null || item.getType() == Material.AIR) {
                    continue;
                }

                if (i < 27) {
                    leftChest.getInventory().addItem(item);
                } else {
                    rightChest.getInventory().addItem(item);
                }
            }

            ItemStack ghead = new ItemStack(GOLDEN_APPLE, 1);
            ItemMeta gheadMeta = ghead.getItemMeta();

            List<String> loresList = new ArrayList<String>();
            loresList.add("When you eat a golden head,");
            loresList.add("You gain 8 seconds of Regeneration II");

            assert gheadMeta != null;
            gheadMeta.setLore(loresList);
            ghead.setItemMeta(gheadMeta);

            String headName = Main.config.getConfig().getString("config.goldenhead");
            assert headName != null;
            gheadMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', headName));
            ghead.setItemMeta(gheadMeta);

            if (e.getDrops().size() + 1 <= 27) {
                leftChest.getInventory().addItem(ghead);
            } else {
                rightChest.getInventory().addItem(ghead);
            }

            if (Main.config.getConfig().getBoolean("config.scenarios.heavypockets")) {
                ItemStack a = new ItemStack(Material.NETHERITE_SCRAP, 2);
                ItemStack b = new ItemStack(Material.GOLD_INGOT, 4);

                if (e.getDrops().size() + 2 <= 27) {
                    leftChest.getInventory().addItem(a);
                    leftChest.getInventory().addItem(b);
                } else {
                    rightChest.getInventory().addItem(a);
                    rightChest.getInventory().addItem(b);
                }
            }

            e.getDrops().clear();

            final ArmorStand stand = player.getWorld().spawn(leftChest.getLocation().clone().add(0, 1, 0.5), ArmorStand.class);

            stand.setCustomNameVisible(true);
            stand.setSmall(true);
            stand.setGravity(false);
            stand.setVisible(false);

            stand.setMarker(true);

            new BukkitRunnable() {
                private int time = 30;

                public void run() {
                    time--;

                    if (time == 0) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Meetup" + ChatColor.DARK_GRAY + "] " + "§a" + player.getName() + "'s §fcorpse has exploded!");
                        leftChest.getInventory().clear();
                        leftSide.setType(Material.AIR);
                        rightSide.setType(Material.AIR);
                        loc.getWorld().createExplosion(loc.getBlockX() + 0.5, loc.getBlockY() + 0.5, loc.getBlockZ() + 0.5, 4, false, true);
                        loc.getWorld().strikeLightning(loc); //actually kill the items with the lightning LOLLL!!!!!
                        stand.remove();
                        cancel();
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
}
