package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import net.enganxe.meetupuhc.player.KitGiver;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

import static net.enganxe.meetupuhc.Main.*;

public class HubEvents implements Listener {
    private Main plugin;
    public HubEvents(Main plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("scoreboard.title")));
        Main.boards.put(player.getName(), board);
        player.setBedSpawnLocation(new Location(Bukkit.getWorld(config.getConfig().getString("worlds.lobby_world")), 0, 100, 0));
        player.setStatistic(Statistic.PLAYER_KILLS, 0);
        if (!Main.starting && !Main.started) {
            Main.PlayersToStart = config.getConfig().getInt("config.playerstostart");
            int neededPlayers = Main.PlayersToStart - Bukkit.getOnlinePlayers().size();
            player.setGameMode(GameMode.SURVIVAL);
            Location loc = new Location(Bukkit.getWorld(config.getConfig().getString("worlds.lobby_world")), 0, 100, 0);
            player.teleport(loc);
            player.setHealth(20);
            player.setFoodLevel(20);
            Inventory inv = player.getInventory();
            inv.clear();
            String msg = config.getConfig().getString("messages.join");
            String needPlayer = String.valueOf(neededPlayers);
            msg = msg.replace("%player%", player.getName());
            msg = msg.replace("%needplayers%", needPlayer);
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
        else if (Main.starting){
            player.setAllowFlight(false);
            player.setGameMode(GameMode.SURVIVAL);
            player.setStatistic(Statistic.PLAYER_KILLS, 0);
            player.setHealth(20);
            player.setFoodLevel(20);
            PlayerInventory inv =  player.getInventory();
            inv.clear();
            scatter(player);
            event.setJoinMessage("");
            KitGiver.setInv(player);

        }
        else if (Main.started){
            event.setJoinMessage("");
            player.setGameMode(GameMode.SPECTATOR);
            Location loc = new Location(Bukkit.getWorld(config.getConfig().getString("worlds.meetup_world")), 0, 200, 0);
            player.teleport(loc);
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        FastBoard board = Main.boards.remove(player.getName());
        e.getPlayer().eject();

        if (board != null) {
            board.delete();
        }
        if (!Main.starting && !Main.started){
            String msg = Main.config.getConfig().getString("messages.quit");
            msg = msg.replace("%player%", player.getName());
            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
        else if (starting && !started){
            e.setQuitMessage("");
            Main.PlayersAlive.remove(player);
        }
        else if (Main.started){
            Main.PlayersAlive.remove(player);
            if (player.getGameMode() != GameMode.SURVIVAL) {
                e.setQuitMessage("");
            }
            else if (player.getGameMode() == GameMode.SURVIVAL){
                e.setQuitMessage(ChatColor.YELLOW + player.getName() + ChatColor.GOLD + " has left");
            }
            if (Main.PlayersAlive.size() == 1) {
                String palive = Main.PlayersAlive.get(0).getName();
                Bukkit.broadcastMessage("" + ChatColor.YELLOW + palive + ChatColor.GOLD + " won the Meetup!");
                int wins = config.getConfig().getInt("stats.players." + palive + ".wins");
                config.getConfig().set("stats.players." + palive + ".wins", wins + 1);
                config.saveConfig();
                Main.PlayersAlive.clear();
                Bukkit.broadcastMessage(ChatColor.RED + "Server restarting in 30 seconds");
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage(ChatColor.RED + "Stoping Server...");
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String command = "restart";
                        Bukkit.dispatchCommand(console, command);
                    }
                }, 600L);
            }
        }

    }
    @EventHandler
    public void BreakBlock(BlockBreakEvent e){
        if (!Main.started || finalized){
            Player player = e.getPlayer();
            if (!player.hasPermission("meetup.admin")){
                e.setCancelled(true);
            }
        }

    }
    @EventHandler
    public void Hunger(FoodLevelChangeEvent e){
        if (!Main.started || finalized){
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (!Main.started || finalized){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.GOLDEN_APPLE) {
            ItemStack goldenHead = e.getItem();
            if (Objects.requireNonNull(goldenHead.getItemMeta()).hasLore()) {
                Player p = e.getPlayer();
                p.removePotionEffect(PotionEffectType.REGENERATION);
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, 1));
            }
        }
    }

    public void scatter(Player p){
        p.getInventory().clear();
        String world = config.getConfig().getString("worlds.meetup_world");
        int worldborder = Integer.parseInt(Objects.requireNonNull(config.getConfig().getString("config.worldborder")));
        new BukkitRunnable() {
            @Override
            public void run() {
                Random rand = new Random();
                int x = rand.nextInt(worldborder/2);
                int z = rand.nextInt(worldborder/2);
                if (rand.nextInt(100) <= 49) {
                    x = -1 * x;
                }
                if (rand.nextInt(100) <= 49) {
                    z = -1 * z;
                }
                int y = 1;
                assert world != null;
                if (Objects.requireNonNull(Bukkit.getWorld(world)).getHighestBlockAt(x, z).getY() < 120) {
                    y = Objects.requireNonNull(p.getServer().getWorld(world)).getHighestBlockYAt(x, z) + 2;

                    if (Objects.requireNonNull(Bukkit.getWorld(world)).getBlockAt(x, y, z).getType() == Material.WATER) {
                        rand = new Random();
                        x = rand.nextInt(worldborder/2);
                        z = rand.nextInt(worldborder/2);
                        if (rand.nextInt(100) <= 49) {
                            x = -1 * x;
                        }
                        if (rand.nextInt(100) <= 49) {
                            z = -1 * z;
                        }
                        y = Objects.requireNonNull(p.getServer().getWorld(world)).getHighestBlockYAt(x, z) + 2;
                    }
                } else {
                    rand = new Random();
                    x = rand.nextInt(worldborder/2);
                    z = rand.nextInt(worldborder/2);
                    if (rand.nextInt(100) <= 49) {
                        x = -1 * x;
                    }
                    if (rand.nextInt(100) <= 49) {
                        z = -1 * z;
                    }
                }
                p.teleport(new Location(Bukkit.getWorld(world), x, y, z));
                p.setGameMode(GameMode.SURVIVAL);
                Main.PlayersAlive.add(p);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        Location loc = p.getLocation();
                        ArmorStand stand = p.getLocation().getWorld().spawn(loc, ArmorStand.class);
                        stand.setVisible(false);
                        stand.setInvulnerable(true);
                        stand.setCollidable(false);
                        stand.addPassenger(p);
                    }
                }, 3);

            }
        }.runTaskLater(this.plugin, 5);
    }
}