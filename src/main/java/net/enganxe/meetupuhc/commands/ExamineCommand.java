package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ExamineCommand implements CommandExecutor {
    private Main plugin;

    public ExamineCommand(Main plugin) {
        this.plugin = plugin;

        plugin.getCommand("examine").setExecutor(this);

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.getGameMode() == GameMode.SPECTATOR){
                if (args.length == 0){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCorrect Ussage: /examine <player>"));
                }
                else if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null){
                        p.sendMessage(ChatColor.RED+ "The player must be online!");
                        return true;
                    } else {
                        openInv(p, target);
                    }
                }
            }
        }
        return false;
    }

/*    private void openInventory(Player player, Player target) {
        String tname = target.getName();
        String iname = "" + ChatColor.GOLD + tname + "'s Inventory";
        Inventory inv = Bukkit.createInventory(null, 6 * 9, iname);
        for (ItemStack item : target.getInventory().getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }
            inv.addItem(item);
        }
        player.openInventory(inv);
    }*/

    public static void openInv(Player player, Player target) {
        String tname = target.getName();
        String iname = "" + ChatColor.GOLD + tname + "'s Inventory";
        Inventory inv = Bukkit.createInventory(null, 6 * 9, iname);

        ItemStack pane = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName(" ")
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .build();
        pane.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        inv.setItem(0, pane);
        inv.setItem(1, pane);
        inv.setItem(2, target.getInventory().getHelmet() == null ? new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cHelmet").build() : target.getInventory().getHelmet());
        inv.setItem(3, target.getInventory().getChestplate() == null ? new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cChestplate").build() : target.getInventory().getChestplate());
        inv.setItem(4, target.getInventory().getItemInOffHand().getType() == Material.AIR ? new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cOff Hand").build() : target.getInventory().getItemInOffHand());
        inv.setItem(5, target.getInventory().getLeggings() == null ? new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cLeggings").build() : target.getInventory().getLeggings());
        inv.setItem(6, target.getInventory().getBoots() == null ? new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cBoots").build() : target.getInventory().getBoots());
        inv.setItem(7, pane);
        inv.setItem(8, pane);

        for (int i = 9; i <= 17; i++)
            inv.setItem(i, pane);

        inv.setItem(18, target.getInventory().getItem(0));
        inv.setItem(19, target.getInventory().getItem(1));
        inv.setItem(20, target.getInventory().getItem(2));
        inv.setItem(21, target.getInventory().getItem(3));
        inv.setItem(22, target.getInventory().getItem(4));
        inv.setItem(23, target.getInventory().getItem(5));
        inv.setItem(24, target.getInventory().getItem(6));
        inv.setItem(25, target.getInventory().getItem(7));
        inv.setItem(26, target.getInventory().getItem(8));
        inv.setItem(27, target.getInventory().getItem(27));
        inv.setItem(28, target.getInventory().getItem(28));
        inv.setItem(29, target.getInventory().getItem(29));
        inv.setItem(30, target.getInventory().getItem(30));
        inv.setItem(31, target.getInventory().getItem(31));
        inv.setItem(32, target.getInventory().getItem(32));
        inv.setItem(33, target.getInventory().getItem(33));
        inv.setItem(34, target.getInventory().getItem(34));
        inv.setItem(35, target.getInventory().getItem(35));
        inv.setItem(36, target.getInventory().getItem(18));
        inv.setItem(37, target.getInventory().getItem(19));
        inv.setItem(38, target.getInventory().getItem(20));
        inv.setItem(39, target.getInventory().getItem(21));
        inv.setItem(40, target.getInventory().getItem(22));
        inv.setItem(41, target.getInventory().getItem(23));
        inv.setItem(42, target.getInventory().getItem(24));
        inv.setItem(43, target.getInventory().getItem(25));
        inv.setItem(44, target.getInventory().getItem(26));
        inv.setItem(45, target.getInventory().getItem(9));
        inv.setItem(46, target.getInventory().getItem(10));
        inv.setItem(47, target.getInventory().getItem(11));
        inv.setItem(48, target.getInventory().getItem(12));
        inv.setItem(49, target.getInventory().getItem(13));
        inv.setItem(50, target.getInventory().getItem(14));
        inv.setItem(51, target.getInventory().getItem(15));
        inv.setItem(52, target.getInventory().getItem(16));
        inv.setItem(53, target.getInventory().getItem(17));

        player.openInventory(inv);
    }
}
