package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;
import java.util.Random;

import static net.enganxe.meetupuhc.Main.config;

public class HubEvents implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("scoreboard.title")));
        Main.boards.put(player.getName(), board);
        String p = player.getPlayer().getName();
        player.setStatistic(Statistic.PLAYER_KILLS, 0);
        if (!Main.starting && !Main.started) {
            Main.PlayersToStart = config.getConfig().getInt("config.playerstostart");
            int neededPlayers = Main.PlayersToStart - Bukkit.getOnlinePlayers().size();
            player.setGameMode(GameMode.SURVIVAL);
            Location loc = new Location(Bukkit.getWorld(config.getConfig().getString("worlds.lobby_world")), 0, 100, 0);
            player.teleport(loc);
            String msg = config.getConfig().getString("messages.join");
            String needPlayer = String.valueOf(neededPlayers);
            msg = msg.replace("%player%", player.getName());
            msg = msg.replace("%needplayers%", needPlayer);
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
        else if (Main.starting){
            player.setAllowFlight(false);
            player.setGameMode(GameMode.SURVIVAL);
            Location loc = new Location(Bukkit.getWorld(config.getConfig().getString("worlds.meetup_world")), 0, 100, 0);
            player.teleport(loc);
            player.setGameMode(GameMode.SURVIVAL);
            player.setStatistic(Statistic.PLAYER_KILLS, 0);
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
                if (Main.PlayersAlive.contains(player)){
                    Main.PlayersAlive.remove(player);
                }
            }
            else if (player.getGameMode() == GameMode.SURVIVAL){
                e.setQuitMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " has left");
                if (Main.PlayersAlive.contains(player)){
                    Main.PlayersAlive.remove(player);
                }
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
}
