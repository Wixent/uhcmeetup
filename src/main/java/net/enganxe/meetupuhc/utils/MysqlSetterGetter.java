package net.enganxe.meetupuhc.utils;

import net.enganxe.meetupuhc.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MysqlSetterGetter implements Listener {
    Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void OnJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        createPlayer(player);
    }
    @EventHandler
    public void OnDeath(PlayerDeathEvent event){
        if (event.getEntity().getKiller() != null) {
            Player killer = event.getEntity().getKiller();
            updateKills(killer);
        }
    }

    public boolean playerExists(Player player){
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
            statement.setString(1, player.getName());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                plugin.getServer().getLogger().info(ChatColor.YELLOW + "Player Found");
                return true;
            }
            plugin.getServer().getLogger().info(ChatColor.RED + "Player NOT Found");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void createPlayer(final Player player) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
            statement.setString(1, player.getName());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            if (!playerExists(player)) {
                PreparedStatement insert = plugin.getConnection()
                        .prepareStatement("INSERT INTO " + plugin.table + " (NAME,KILLS) VALUES (?,?)");
                insert.setString(1, player.getName());
                insert.setInt(2, 0);
                insert.executeUpdate();
                plugin.getServer().getLogger().info(ChatColor.GREEN + "Player Inserted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateKills(Player player) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("UPDATE " + plugin.table + " SET KILLS=? WHERE NAME=?");
            statement.setInt(1, +1);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
