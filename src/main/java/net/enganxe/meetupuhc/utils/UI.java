package net.enganxe.meetupuhc.utils;

import net.enganxe.meetupuhc.commands.StatsCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class UI {

    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 3 * 9;

    public static void initialize(){
        inventory_name = Utils.chat("&dStats");

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI (Player p){
        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
        Utils.createItem(inv, "iron_sword", 1, 13, "&bKills", "&7" + StatsCommand.playerkills);
        Utils.createItem(inv, "rotten_flesh", 1, 14, "&cDeaths", "&7Example");
        Utils.createItem(inv, "golden_apple", 1, 15, "&aGames Played", "&7Example");
        toReturn.setContents(inv.getContents());
        return toReturn;
    }
}
