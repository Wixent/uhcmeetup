package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

public class AutoStartEvent implements Listener {
    public static int time;
    private final Main plugin;
    public AutoStartEvent(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (Bukkit.getOnlinePlayers().size() == Main.PlayersToStart) {
            if (!Main.started && !Main.starting) {
                time = 62;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        time = time - 1;
                        if (time == 60){
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            Main.starting = true;
                            Main.started = false;
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.setGameMode(GameMode.SURVIVAL);
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                all.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 50));
                                all.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000, 128));
                                Random rand = new Random();
                                all.setStatistic(Statistic.PLAYER_KILLS, 0);
                                int count = Bukkit.getOnlinePlayers().size();
                                int upperbound = 200;
                                int x = rand.nextInt(upperbound);
                                int z = rand.nextInt(upperbound);
                                double randDouble = Math.random();
                                if (randDouble <= 0.5D) {
                                    x = -1 * x;
                                }
                                double randDouble2 = Math.random();
                                if (randDouble2 <= 0.5D) {
                                    z = -1 * z;
                                }
                                int y = 1;
                                if (Objects.requireNonNull(Bukkit.getWorld("world")).getHighestBlockAt(x, z).getY() < 120) {
                                    y = Objects.requireNonNull(all.getServer().getWorld("world")).getHighestBlockYAt(x, z) + 2;
                                    if (Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(x, y, z).getType() == Material.WATER) {
                                        rand = new Random();
                                        upperbound = 200 / 2;
                                        x = rand.nextInt(upperbound);
                                        z = rand.nextInt(upperbound);
                                        randDouble = Math.random();
                                        if (randDouble <= 0.5D) {
                                            x = -1 * x;
                                        }
                                        randDouble2 = Math.random();
                                        if (randDouble2 <= 0.5D) {
                                            z = -1 * z;
                                        }
                                        y = Objects.requireNonNull(all.getServer().getWorld("world")).getHighestBlockYAt(x, z) + 2;
                                    }
                                } else {
                                    rand = new Random();
                                    upperbound = 200 / 2;
                                    x = rand.nextInt(upperbound);
                                    z = rand.nextInt(upperbound);
                                    randDouble = Math.random();
                                    if (randDouble <= 0.5D) {
                                        x = -1 * x;
                                    }
                                    randDouble2 = Math.random();
                                    if (randDouble2 <= 0.5D) {
                                        z = -1 * z;
                                    }

                                }
                                all.teleport(new Location(Bukkit.getWorld("world"), x, y, z), PlayerTeleportEvent.TeleportCause.PLUGIN);
                                Bukkit.broadcastMessage(ChatColor.DARK_GREEN + "[Scatter]: " + ChatColor.GRAY + all.getName());
                                all.setGameMode(GameMode.SURVIVAL);
                                count--;
                                if (count == 0) cancel();
                                Main.PlayersAlive.add(all);
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
                        if (time == 0) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "The Meetup has been " + ChatColor.LIGHT_PURPLE + "started!");
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 10, 1);
                                for (PotionEffect effect : all.getActivePotionEffects()) {
                                    all.removePotionEffect(effect.getType());
                                }
                            }
                            Main.started = true;
                            Main.starting = false;
                            return;
                        }
                    }
                }.runTaskTimer(this.plugin, 0, 20);
            }
        }
    }
}
