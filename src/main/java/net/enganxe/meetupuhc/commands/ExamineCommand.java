package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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
                        openInventory(p, target);
                    }
                }
            }
        }
        return false;
    }
    private void openInventory(Player player, Player target) {
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
    }
}
