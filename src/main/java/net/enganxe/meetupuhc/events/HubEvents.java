package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.commands.ExamineCommand;
import net.enganxe.meetupuhc.commands.setLobbyCommand;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import net.enganxe.meetupuhc.player.KitGiver;
import net.enganxe.meetupuhc.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Objects;

import static net.enganxe.meetupuhc.Main.*;

public class HubEvents implements Listener {
    private Main plugin;

    public HubEvents(Main plugin) {
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
            World wmeetup = Bukkit.getWorld(config.getConfig().getString("worlds.lobby_world"));
            wmeetup.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            wmeetup.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            wmeetup.setTime(9000);
            wmeetup.setStorm(false);
            Main.PlayersToStart = config.getConfig().getInt("config.playerstostart");
            int neededPlayers = Main.PlayersToStart - Bukkit.getOnlinePlayers().size();
            player.setGameMode(GameMode.SURVIVAL);
            Location loc = setLobbyCommand.getLobbyLocation();
            assert loc != null;
            player.teleport(loc);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            player.setExp(0.0f);
            for(PotionEffect effect : player.getActivePotionEffects()){
                player.removePotionEffect(effect.getType());
            }
            Inventory inv = player.getInventory();
            inv.clear();
            String msg = config.getConfig().getString("messages.join");
            String needPlayer = String.valueOf(neededPlayers);
            msg = msg.replace("%player%", player.getName());
            msg = msg.replace("%needplayers%", needPlayer);
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', msg));
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (!Main.starting && !Main.started) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.chat(config.getConfig().getString("messages.actionbarwaiting")).replace("%online%", "" + Bukkit.getOnlinePlayers().size()).replace("%mintostart%", "" + PlayersToStart)));
                    }
                }
            },0L, 1L);
        } else if (Main.starting) {
            player.setAllowFlight(false);
            player.setGameMode(GameMode.SURVIVAL);
            player.setStatistic(Statistic.PLAYER_KILLS, 0);
            player.setHealth(20);
            player.setExp(0.0f);
            player.setFoodLevel(20);
            event.setJoinMessage("");

        } else if (Main.started) {
            event.setJoinMessage("");
            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
            player.setHealth(20);
            player.setExp(0.0f);
            player.setFoodLevel(20);
            for(PotionEffect effect : player.getActivePotionEffects()){
                player.removePotionEffect(effect.getType());
            }
            Location loc = new Location(Bukkit.getWorld(config.getConfig().getString("worlds.meetup_world")), 0, 200, 0);
            player.teleport(loc);
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard boardd = manager.getNewScoreboard();
            Objective objective = boardd.registerNewObjective("Health", "health");
            objective.setDisplayName(ChatColor.RED + "❤");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            player.setScoreboard(boardd);
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
        if (!Main.starting && !Main.started) {
            String msg = Main.config.getConfig().getString("messages.quit");
            msg = msg.replace("%player%", player.getName());
            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', msg));
        } else if (starting && !started) {
            e.setQuitMessage("");
        } else if (Main.started) {
            Main.PlayersAlive.remove(player);
            if (player.getGameMode() != GameMode.SURVIVAL) {
                e.setQuitMessage("");
            } else if (player.getGameMode() == GameMode.SURVIVAL) {
                e.setQuitMessage(ChatColor.YELLOW + player.getName() + ChatColor.GOLD + " has left");
            }
            if (Main.PlayersAlive.size() == 1) {
                String palive = Main.PlayersAlive.get(0).getName();
                Bukkit.broadcastMessage(Utils.chat(config.getConfig().getString("messages.win")).replace("%player%", palive));
                int wins = config.getConfig().getInt("stats.players." + palive + ".wins");
                config.getConfig().set("stats.players." + palive + ".wins", wins + 1);
                config.saveConfig();
                Main.PlayersAlive.clear();
                if (config.getConfig().getBoolean("config.finishrestart")) {
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

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (starting && (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BreakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!Main.started || finalized) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BreakBlock(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        if (!Main.started || finalized) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlaceBlock(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!Main.started || finalized) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void Hunger(FoodLevelChangeEvent e) {
        if (!Main.started || finalized) {
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!Main.started || finalized) {
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

    @EventHandler
    public void EntityInteractEvent(PlayerInteractAtEntityEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.SPECTATOR && e.getRightClicked().getType() == EntityType.PLAYER &&
                e.getHand() == EquipmentSlot.HAND) {
            Player p = e.getPlayer();
            String t = e.getRightClicked().getName();
            p.chat("/examine " + t);
        }
    }

    @EventHandler
    public void onInventory(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        for (Player spec : Bukkit.getOnlinePlayers()) {
            if (spec.getGameMode() == GameMode.SPECTATOR && spec.getOpenInventory().getTitle().contains(p.getName())) {
                ExamineCommand.openInv(spec, p);
            }
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();

        for (Player spec : Bukkit.getOnlinePlayers()) {
            if (spec.getGameMode() == GameMode.SPECTATOR && spec.getOpenInventory().getTitle().contains(p.getName())) {
                ExamineCommand.openInv(spec, p);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        for (Player spec : Bukkit.getOnlinePlayers()) {
            if (spec.getGameMode() == GameMode.SPECTATOR && spec.getOpenInventory().getTitle().contains(p.getName())) {
                ExamineCommand.openInv(spec, p);
            }
        }
    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            for (Player spec : Bukkit.getOnlinePlayers()) {
                if (spec.getGameMode() == GameMode.SPECTATOR && spec.getOpenInventory().getTitle().contains(p.getName())) {
                    ExamineCommand.openInv(spec, p);
                }
            }
        }
    }

    @EventHandler
    public void onThrow(InventoryDragEvent e) {
        Player p = (Player) e.getWhoClicked();

        for (Player spec : Bukkit.getOnlinePlayers()) {
            if (spec.getGameMode() == GameMode.SPECTATOR && spec.getOpenInventory().getTitle().contains(p.getName())) {
                ExamineCommand.openInv(spec, p);
            }
        }
    }

}