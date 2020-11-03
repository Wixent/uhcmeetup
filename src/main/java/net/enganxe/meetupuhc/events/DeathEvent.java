package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import static net.enganxe.meetupuhc.Main.config;

;

public class DeathEvent implements Listener {
    private static Main plugin;

    public DeathEvent (Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent e){
        Player player = e.getEntity();
        player.setGameMode(GameMode.SPECTATOR);
        if (e.getEntity().getKiller() instanceof Player){
            String killer = e.getEntity().getKiller().getName();
            String p = e.getEntity().getName();
            String msg = config.getConfig().getString("messages.death_by_player");
            msg = msg.replace("%victim%", p);
            msg = msg.replace("%killer%", killer);
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', msg));
            Main.PlayersAlive.remove(player);

        }
        else if (!(e.getEntity().getKiller() instanceof Player)) {
            String msg = config.getConfig().getString("messages.death");
            String p = e.getEntity().getName();
            msg = msg.replace("%victim%", p);
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', msg));
            Main.PlayersAlive.remove(player);
        }
        if (Main.PlayersAlive.size() == 1) {
            String palive = Main.PlayersAlive.get(0).getName();
            int wins = config.getConfig().getInt("stats.players." + palive + ".wins");
            config.getConfig().set("stats.players." + palive + ".wins", wins + 1);
            config.saveConfig();
            Bukkit.broadcastMessage("" + ChatColor.YELLOW + palive + ChatColor.GOLD + " won the Meetup!");
            Main.PlayersAlive.clear();
            Bukkit.broadcastMessage(ChatColor.RED + "Server restarting in 20 seconds");
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(ChatColor.RED + "Stoping Server...");
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    String command = "restart";
                    Bukkit.dispatchCommand(console, command);
                }
            }, 400L);
        }
    }
}