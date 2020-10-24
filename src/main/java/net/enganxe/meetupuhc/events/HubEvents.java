package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.Scoreboards;
import net.enganxe.meetupuhc.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HubEvents implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);
        Main.boards.put(player.getName(), board);
        board.updateTitle(ChatColor.GOLD + "Enganxe Meetup");
        if (!Main.starting && !Main.started) {
            int needPlayers = Main.PlayersToStart - Bukkit.getOnlinePlayers().size();
            Scoreboards.hubScoreboard(board);
            player.setGameMode(GameMode.SURVIVAL);
            event.setJoinMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " has joined (" + needPlayers + " needed players to start)");
            event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation().add(0.5, 240, 0.5));
        }
        else if (Main.starting){
            Scoreboards.CountdownS(board);
            player.setGameMode(GameMode.SURVIVAL);
            if (Main.PlayersAlive.contains(player)){
                return;
            }
            if (!Main.PlayersAlive.contains(player)){
                Main.PlayersAlive.add(player);
            }
        }
        else if (Main.started){
            Scoreboards.GameScoreboard(board);
            event.setJoinMessage("");
            player.setGameMode(GameMode.SPECTATOR);
            Scoreboards.GameScoreboard(board);
            event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation().add(0.5, 240, 0.5));
            if (Main.PlayersAlive.contains(player)){
                Main.PlayersAlive.remove(player);
            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (!Main.starting && !Main.started){
            e.setQuitMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.GOLD + " has left");
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


        FastBoard board = Main.boards.remove(player.getName());

        if (board != null) {
            board.delete();
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
