package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.utils.UI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    private Main plugin;

    public StatsCommand(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("stats").setExecutor(this);

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            return true;
        }
        Player p = (Player) sender;
        p.openInventory(UI.GUI(p));
        return false;
    }
}
