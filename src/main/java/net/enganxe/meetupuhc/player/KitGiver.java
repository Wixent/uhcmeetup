package net.enganxe.meetupuhc.player;

import net.enganxe.meetupuhc.Main;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Material.*;

public class KitGiver {
    public static Integer getRandomInt(Integer max) {
        Random ran = new Random();
        return ran.nextInt(max);
    }

    public static void setInv(Player player) {
        PlayerInventory inv =  player.getInventory();
        ItemStack shield = new ItemStack(SHIELD);
        inv.setItemInOffHand(shield);
        int n = getRandomInt(2);
        if (n == 0){
            ItemStack a = new ItemStack(IRON_SWORD, 1);
            inv.setItem(1, a);
            int l = getRandomInt(2);
            if (l == 0){
                a.addEnchantment(Enchantment.DAMAGE_ALL, 2);
            }
            if (l == 1){
                a.addEnchantment(Enchantment.DAMAGE_ALL, 3);
            }
            inv.setItem(0, a);
        }
        else if (n == 1){
            ItemStack a = new ItemStack(DIAMOND_SWORD, 1);
            int l = getRandomInt(3);
            if (l == 0 || l == 1){
                a.addEnchantment(Enchantment.DAMAGE_ALL, 1);
            }
            if (l == 2){
                a.addEnchantment(Enchantment.DAMAGE_ALL, 2);
            }
            inv.setItem(0, a);
        }
        ItemStack a = new ItemStack(COBBLESTONE, 64);
        inv.setItem(1, a);
        int axee = getRandomInt(2);
        if (axee == 0){
            ItemStack ax = new ItemStack(IRON_AXE, 1);
            int ee = getRandomInt(2);
            if (ee == 0) {
                ax.addEnchantment(Enchantment.DAMAGE_ALL, 1);
            }
            inv.setItem(2, ax);

        }
        else if (axee == 1){
            ItemStack ax = new ItemStack(DIAMOND_AXE, 1);
            int ee = getRandomInt(2);
            if (ee == 0) {
                ax.addEnchantment(Enchantment.DAMAGE_ALL, 1);
            }
            inv.setItem(2, ax);
        }
        int arcc = getRandomInt(4);
        ItemStack bow = new ItemStack(BOW, 1);
        if (arcc == 0 || arcc == 1){
            bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
        }
        else if (arcc == 2){
            bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
        }
        else if (arcc == 3){
            bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
        }
        inv.setItem(3, bow);
        int cob = getRandomInt(3);;
        if (cob == 2){
            ItemStack co = new ItemStack(COBWEB, 24);
            inv.setItem(4, co);
        }

        int low = 5;
        int high = 11;
        Random gaap = new Random();
        int gap = gaap.nextInt(high-low) + low;
        ItemStack gaps = new ItemStack(GOLDEN_APPLE, gap);
        inv.setItem(5, gaps);
        int low1 = 1;
        int high1 = 4;
        Random rand = new Random();
        int ghea = rand.nextInt(high1-low1) + low1;
        ItemStack ghead = new ItemStack(GOLDEN_APPLE, ghea);
        ItemMeta gheadMeta = ghead.getItemMeta();
        String headName = Main.config.getConfig().getString("config.goldenhead");
        assert gheadMeta != null;
        assert headName != null;
        gheadMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                headName));
        List<String> loresList = new ArrayList<String>();
        loresList.add("When you eat a golden head");
        loresList.add("you gain 8 seconds of regeneration II");
        gheadMeta.setLore(loresList);
        ghead.setItemMeta(gheadMeta);
        inv.setItem(6, ghead);
        ItemStack lava = new ItemStack(LAVA_BUCKET, 1);
        inv.setItem(7, lava);
        ItemStack water = new ItemStack(WATER_BUCKET, 1);
        inv.setItem(8, water);
        ItemStack anvil = new ItemStack(ANVIL, 1);
        ItemStack enchants = new ItemStack(ENCHANTING_TABLE, 1);
        ItemStack pickaxe = new ItemStack(DIAMOND_PICKAXE, 1);
        ItemStack botella = new ItemStack(EXPERIENCE_BOTTLE, 64);
        ItemStack mesa = new ItemStack(SMITHING_TABLE, 1);
        ItemStack comida = new ItemStack(COOKED_BEEF, 64);
        ItemStack flechas = new ItemStack(ARROW, 64);

        inv.addItem(a);
        inv.addItem(anvil);
        inv.addItem(enchants);
        inv.addItem(pickaxe);
        inv.addItem(botella);
        inv.addItem(mesa);
        inv.addItem(lava);
        inv.addItem(water);
        inv.addItem(comida);
        inv.addItem(flechas);

        // Armor
        int hel = getRandomInt(1);
        if (hel == 0){
            ItemStack helmet = new ItemStack(IRON_HELMET);
            int en = getRandomInt(2);
            if (en == 0){
                helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            }
            else if (en == 1){
                helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            }
            inv.setHelmet(helmet);
        }
        else if (hel == 1){
            ItemStack helmet = new ItemStack(DIAMOND_HELMET);
            int en = getRandomInt(4);
            if (en == 0 || en == 1){
                helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
            else if (en == 2){
                helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            }
            else if (en == 3){
                helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            }
            inv.setHelmet(helmet);
        }


        int ches = getRandomInt(2);
        if (ches == 0){
            ItemStack chestplate = new ItemStack(IRON_CHESTPLATE);
            int en = getRandomInt(3);
            if (en == 0 || en == 1){
                chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            }
            else if (en == 2){
                chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            }
            inv.setChestplate(chestplate);
        }
        else if (ches == 1){
            ItemStack chestplate = new ItemStack(DIAMOND_CHESTPLATE);
            int en = getRandomInt(4);
            if (en == 0 || en == 1){
                chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
            else if (en == 2){
                chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            }
            else if (en == 3){
                chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            }
            inv.setChestplate(chestplate);
        }
        int leg = getRandomInt(2);
        if (leg == 0){
            ItemStack leggings = new ItemStack(IRON_LEGGINGS);
            int en = getRandomInt(3);
            if (en == 0 || en == 1){
                leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            }
            else if (en == 2){
                leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            }
            inv.setLeggings(leggings);
        }
        else if (leg == 1){
            ItemStack leggings = new ItemStack(DIAMOND_LEGGINGS);
            int en = getRandomInt(4);
            if (en == 0 || en == 1){
                leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
            else if (en == 2){
                leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            }
            else if (en == 3){
                leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            }
            inv.setLeggings(leggings);
        }
        int boots = getRandomInt(2);
        if (boots == 0){
            ItemStack bootss = new ItemStack(IRON_BOOTS);
            int en = getRandomInt(3);
            if (en == 0 || en == 1){
                bootss.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            }
            else if (en == 2){
                bootss.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            }
            inv.setBoots(bootss);
        }
        else if (boots == 1){
            ItemStack bootss = new ItemStack(DIAMOND_BOOTS);
            int en = getRandomInt(4);
            if (en == 0 || en == 1){
                bootss.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
            else if (en == 2){
                bootss.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            }
            else if (en == 3){
                bootss.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
            }
            inv.setBoots(bootss);
        }

    }

}
