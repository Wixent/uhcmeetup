package net.enganxe.meetupuhc.events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.commands.setLobbyCommand;
import net.enganxe.meetupuhc.utils.Utils;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static net.enganxe.meetupuhc.Main.config;
import static org.bukkit.Material.*;

public class DeathEvent implements Listener {
    private static Main plugin;
    private Player p;

    public DeathEvent (Main plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent e){
        Player player = e.getEntity();
        player.setGameMode(GameMode.SPECTATOR);
        Location loc = setLobbyCommand.getLobbyLocation();
        player.teleport(loc);
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
        if (!Main.config.getConfig().getBoolean("config.scenarios.timebomb")){
            ItemStack a = new ItemStack(NETHERITE_SCRAP, 2);
            ItemStack u = new ItemStack(GOLD_INGOT, 4);

            ItemStack ghead = new ItemStack(GOLDEN_APPLE, 1);
            ItemMeta gheadMeta = ghead.getItemMeta();
            List<String> loresList = new ArrayList<String>();
            loresList.add("When you eat a golden head,");
            loresList.add("You gain 8 seconds of Regeneration II");
            gheadMeta.setLore(loresList);
            ghead.setItemMeta(gheadMeta);
            String headName = Main.config.getConfig().getString("config.goldenhead");
            assert gheadMeta != null;
            assert headName != null;
            gheadMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                    headName));
            ghead.setItemMeta(gheadMeta);

            if (config.getConfig().getBoolean("config.scenarios.heavypockets")) {
                e.getDrops().add(a);
                e.getDrops().add(u);
            }
            e.getDrops().add(ghead);
        }
        if (Main.PlayersAlive.size() == 1) {
            String palive = Main.PlayersAlive.get(0).getName();
            int wins = config.getConfig().getInt("stats.players." + palive + ".wins");
            config.getConfig().set("stats.players." + palive + ".wins", wins + 1);
            config.saveConfig();
            Bukkit.broadcastMessage(Utils.chat(config.getConfig().getString("messages.win")).replace("%player%", palive));
            Main.finalized = true;
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
    public void sendToLobby() {
        connect(Main.config.getConfig().getString("config.lobby_server"));
    }
    public void connect(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}