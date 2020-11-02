package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.utils.UI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsCommand implements CommandExecutor {
    private Main plugin;
    public static int playerkills;

    public StatsCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("stats").setExecutor(this);

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            return true;
        }
        Player player = (Player) sender;
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE NAME=?");
            statement.setString(1, player.getName());
            ResultSet results = statement.executeQuery();
            results.next();

            playerkills = results.getInt("KILLS");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        player.openInventory(UI.GUI(player));
        playerkills = 0;


        return false;
    }
}
