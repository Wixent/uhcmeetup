package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.config.WorldCreator;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

import static net.enganxe.meetupuhc.Main.config;

public class AutoStartEvent implements Listener {
    public static int time;
    private final Main plugin;
    public static int wtime;
    public static boolean enablewb;
    public AutoStartEvent(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Main.PlayersToStart = config.getConfig().getInt("config.playerstostart");
        if (Bukkit.getOnlinePlayers().size() == Main.PlayersToStart) {
            if (!Main.started && !Main.starting) {
                time = 61;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Main.starting = true;
                        Main.started = false;
                        time = time - 1;
                        if (time == 60){
                            WorldCreator.setWorldBorder();
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                all.setStatistic(Statistic.PLAYER_KILLS, 0);
                                all.setGameMode(GameMode.SURVIVAL);
                                scatter(all);
                            }
                        }
                        if (time == 30) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                        }
                        if (time == 15) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                            }

                        }
                        if (time == 10) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                            }
                        }
                        if (time == 5) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                            }
                        }
                        if (time == 4) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                            }
                        }
                        if (time == 3) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                            }
                        }
                        if (time == 2) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                            }
                        }
                        if (time == 1) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                            }
                        }
                        if (time <= 0) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "The Meetup has been " + ChatColor.LIGHT_PURPLE + "started!");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 10, 1);
                            }
                            Main.started = true;
                            Main.starting = false;

                            WorldBorderSh();
                            this.cancel();
                        }
                    }
                }.runTaskTimer(this.plugin, 0, 20);
            }
        }
    }
    public void WorldBorderSh() {
        if (Main.started && !enablewb) {
            enablewb = true;
            wtime = 61;
        }
        if (Main.started && enablewb) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    wtime = wtime - 1;
                    if (wtime == 60) {
                        String prefix = Main.config.getConfig().getString("config.borderprefix");
                        String msg = Main.config.getConfig().getString("messages.worldborder1");
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
                    }
                    if (wtime == 0) {
                        String prefix = Main.config.getConfig().getString("config.borderprefix");
                        String msg = Main.config.getConfig().getString("messages.worldborder2");
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
                        World world = Bukkit.getWorld(config.getConfig().getString("worlds.meetup_world"));
                        WorldBorder worldBorder = world.getWorldBorder();
                        worldBorder.setSize(100, 180);
                    }

                }
            }.runTaskTimer(this.plugin, 0, 20);
        }
    }
    public void scatter(Player p){
        p.getInventory().clear();
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 500, 200));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 500, 200));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 500, 200));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 500, 200));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 500, 200));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 500, 200));
        String world = config.getConfig().getString("worlds.meetup");
        new BukkitRunnable() {
            @Override
                public void run() {
                    Random rand = new Random();
                    int x = rand.nextInt(251);
                    int z = rand.nextInt(251);
                    if (rand.nextInt(100) <= 49) {
                        x = -1 * x;
                    }
                    if (rand.nextInt(100) <= 49) {
                        z = -1 * z;
                    }
                    int y = 1;
                    if (Objects.requireNonNull(Bukkit.getWorld(world)).getHighestBlockAt(x, z).getY() < 120) {
                        y = Objects.requireNonNull(p.getServer().getWorld(world)).getHighestBlockYAt(x, z) + 2;

                        if (Objects.requireNonNull(Bukkit.getWorld(world)).getBlockAt(x, y, z).getType() == Material.WATER) {
                            rand = new Random();
                            x = rand.nextInt(251);
                            z = rand.nextInt(251);
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
                        x = rand.nextInt(251);
                        z = rand.nextInt(251);
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
                }
        }.runTaskLater(this.plugin, 5);
    }
}
