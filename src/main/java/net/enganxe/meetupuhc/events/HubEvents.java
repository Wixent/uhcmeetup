package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import org.bukkit.*;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

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
        String p = player.getPlayer().getName();
        player.setStatistic(Statistic.PLAYER_KILLS, 0);
        if (!Main.starting && !Main.started) {
            Main.PlayersToStart = config.getConfig().getInt("config.playerstostart");
            int neededPlayers = Main.PlayersToStart - Bukkit.getOnlinePlayers().size();
            player.setGameMode(GameMode.SURVIVAL);
            Location loc = new Location(Bukkit.getWorld(config.getConfig().getString("worlds.lobby_world")), 0, 100, 0);
            player.teleport(loc);
            player.setHealth(20);
            player.setFoodLevel(20);
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
            if (PlayersAlive.contains(player)){
                PlayersAlive.remove(player);
            }
            PlayersAlive.add(player);
            event.setJoinMessage("");
            scatter(player);
        }
        else if (Main.started){
            event.setJoinMessage("");
            player.setGameMode(GameMode.SPECTATOR);
            Location loc = new Location(Bukkit.getWorld(config.getConfig().getString("worlds.meetup_world")), 0, 200, 0);
            player.teleport(loc);
            if (Main.PlayersAlive.contains(p)){
                Main.PlayersAlive.remove(p);
            }
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
        else if (Main.started){
            if (player.getGameMode() != GameMode.SURVIVAL) {
                e.setQuitMessage("");
                Main.PlayersAlive.remove(player);
            }
            else if (player.getGameMode() == GameMode.SURVIVAL){
                e.setQuitMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " has left");
                Main.PlayersAlive.remove(player);
            }
            if (Main.PlayersAlive.size() == 1) {
                String palive = Main.PlayersAlive.get(0).getName();
                Bukkit.broadcastMessage("" + ChatColor.YELLOW + palive + ChatColor.GOLD + " won the Meetup!");
                Main.PlayersAlive.clear();
                Bukkit.broadcastMessage(ChatColor.RED + "Server restarting in 20 seconds");
                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask((Plugin) this, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage(ChatColor.RED + "Stoping Server...");
                        Bukkit.spigot().restart();
                    }
                }, 400);
            }
        }

    }
    @EventHandler
    public void BreakBlock(BlockBreakEvent e){
        if (!Main.started){
            Player player = e.getPlayer();
            if (!player.hasPermission("meetup.admin")){
                e.setCancelled(true);
            }
        }

    }
    @EventHandler
    public void Hunger(FoodLevelChangeEvent e){
        if (!Main.started){
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (!Main.started){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onEat(PlayerItemConsumeEvent e){
        if (e.getItem().getType() == Material.GOLDEN_APPLE && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(config.getConfig().getString("config.goldenhead"))){
            Player p = e.getPlayer();
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 9, 2));
        }
    }

    public void scatter(Player p){
        p.getInventory().clear();
        String world = config.getConfig().getString("worlds.meetup_world");
        int worldborder = Integer.parseInt(Objects.requireNonNull(config.getConfig().getString("config.worldborder")));
        KitGiver.setInv(p);
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
                p.setStatistic(Statistic.PLAYER_KILLS, 0);
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