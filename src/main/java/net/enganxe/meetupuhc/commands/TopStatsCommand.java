package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

import static net.enganxe.meetupuhc.Main.*;

public class TopStatsCommand implements CommandExecutor {
    private Main plugin;

    public TopStatsCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("topstats").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        for(String key : config.getConfig().getConfigurationSection("stats.players").getKeys(false)) {
            Collection<Integer> players = config.getConfig().getConfigurationSection("stats.players." + key + ".kills")
                    .getValues(false)     // get the values of the configuration section. will be things like
                    // player0=0, player1=1, player2=2, player3=3, player4=4, player5=5
                    .values()             // get the values of that. This will return a collection with only the integers (as they are the values)
                    .stream()             // make a Stream out of it
                    .map(value -> ((Integer) value))      // cast the Objects to Integer objects
                    .sorted(Collections.reverseOrder())   // sort it in reverse order (biggest to smallest)
                    .limit(3)                             // limit it to five entries
                    .collect(Collectors.toList());        // get all int values remaining
            String a = players.toString();
            p.sendMessage(a);



        }

        return false;

    }

}
