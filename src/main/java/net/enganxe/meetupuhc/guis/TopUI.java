package net.enganxe.meetupuhc.guis;

import net.enganxe.meetupuhc.commands.StatsCommand;
import net.enganxe.meetupuhc.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TopUI {    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 3 * 9;

    public static void initialize(){
        inventory_name = Utils.chat("&6Top Stats");

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI (Player p){
        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
        Utils.createItem(inv, "iron_sword", 1, 1, "&bKills&8");
        Utils.createItem(inv, "bone", 1, 2, "&cDeaths&7");
        Utils.createItem(inv, "diamond", 1, 3, "&aTop Wins&8");
        toReturn.setContents(inv.getContents());
        return toReturn;
    }

}
