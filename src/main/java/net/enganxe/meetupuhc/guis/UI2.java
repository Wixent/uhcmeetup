package net.enganxe.meetupuhc.guis;

import net.enganxe.meetupuhc.commands.StatsCommand;
import net.enganxe.meetupuhc.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class UI2 {

    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 3 * 9;

    public static void initialize(){
        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI (Player p){
        inventory_name = Utils.chat("&d" + StatsCommand.targetname + "'s Stats");
        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
        Utils.createItem(inv, "iron_sword", 1, 13, "&bKills&8:&7 " + StatsCommand.playerkills);
        Utils.createItem(inv, "bone", 1, 14, "&cDeaths&7: " + StatsCommand.playerdeaths);
        Utils.createItem(inv, "diamond", 1, 15, "&aWins&8:&7 " + StatsCommand.playerwins);
        toReturn.setContents(inv.getContents());
        return toReturn;
    }
}
