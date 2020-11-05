package net.enganxe.meetupuhc.guis;

import net.enganxe.meetupuhc.commands.TopStatsCommand;
import net.enganxe.meetupuhc.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class TopUI {

    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 3 * 9;

    public static void initialize(){
        inventory_name = Utils.chat("&6Top Stats");

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI (Player p){
        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
        Utils.createItem(inv, "iron_sword", 1, 1, "&dTop Kills",
                "&b 1: " + TopStatsCommand.maxKillsPlayer1 + "&8: &d" + TopStatsCommand.maxKills1,
                "&6 2: " + TopStatsCommand.maxKillsPlayer2 + "&8: &d" + TopStatsCommand.maxKills2,
                "&7 3: " + TopStatsCommand.maxKillsPlayer3 + "&8: &d" + TopStatsCommand.maxKills3);
        Utils.createItem(inv, "bone", 1, 2, "&cTop Deaths",
                "&b 1: " + TopStatsCommand.maxDeathsPlayer1 + "&8: &d" + TopStatsCommand.maxDeaths1,
                "&6 2: " + TopStatsCommand.maxDeathsPlayer2 + "&8: &d" + TopStatsCommand.maxDeaths2,
                "&7 3: " + TopStatsCommand.maxDeathsPlayer3 + "&8: &d" + TopStatsCommand.maxDeaths3);
        Utils.createItem(inv, "diamond", 1, 3, "&bTop Wins",
                "&b 1: " + TopStatsCommand.maxWinsPlayer1 + "&8: &d" + TopStatsCommand.maxWins1,
                "&6 2: " + TopStatsCommand.maxWinsPlayer2 + "&8: &d" + TopStatsCommand.maxWins2,
                "&7 3: " + TopStatsCommand.maxWinsPlayer3 + "&8: &d" + TopStatsCommand.maxWins3);
        Utils.createItem(inv, "diamond_block", 1, 4, "&6Meetups Played in Total&8:&e " + TopStatsCommand.gamesPlayed);
        toReturn.setContents(inv.getContents());
        return toReturn;
    }
}
