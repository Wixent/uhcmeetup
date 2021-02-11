package net.enganxe.meetupuhc.commands;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ScenarioCommands implements CommandExecutor {
    private Main plugin;

    public ScenarioCommands(Main plugin){
        this.plugin = plugin;

        plugin.getCommand("timebomb").setExecutor(this);
        plugin.getCommand("heavypockets").setExecutor(this);
        plugin.getCommand("noclean").setExecutor(this);
        plugin.getCommand("absorptionless").setExecutor(this);
        plugin.getCommand("scen").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("meetup.admin") && !cmd.getName().equalsIgnoreCase("scen")){
            sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("timebomb")){
            if (Main.config.getConfig().getBoolean("config.scenarios.timebomb")){
                Main.config.getConfig().set("config.scenarios.timebomb", false);
                Main.config.saveConfig();
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scendisabled")).replace("%scenario%", "TimeBomb"));
            }
            else if (!Main.config.getConfig().getBoolean("config.scenarios.timebomb")){
                Main.config.getConfig().set("config.scenarios.timebomb", true);
                Main.config.saveConfig();
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scenenabled")).replace("%scenario%", "TimeBomb"));
            }
        } else if (cmd.getName().equalsIgnoreCase("heavypockets")){
            if (Main.config.getConfig().getBoolean("config.scenarios.heavypockets")){
                Main.config.getConfig().set("config.scenarios.heavypockets", false);
                Main.config.saveConfig();
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scendisabled")).replace("%scenario%", "HeavyPockets"));
            }
            else if (!Main.config.getConfig().getBoolean("config.scenarios.heavypockets")){
                Main.config.getConfig().set("config.scenarios.heavypockets", true);
                Main.config.saveConfig();
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scenenabled")).replace("%scenario%", "HeavyPockets"));
            }
        } else if (cmd.getName().equalsIgnoreCase("noclean")){
            if (Main.config.getConfig().getBoolean("config.scenarios.noclean")){
                Main.config.getConfig().set("config.scenarios.noclean", false);
                Main.config.saveConfig();
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scendisabled")).replace("%scenario%", "NoClean"));
            }
            else if (!Main.config.getConfig().getBoolean("config.scenarios.noclean")){
                Main.config.getConfig().set("config.scenarios.noclean", true);
                Main.config.saveConfig();
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scenenabled")).replace("%scenario%", "NoClean"));
            }
        } else if (cmd.getName().equalsIgnoreCase("absorptionless")){
            if (Main.config.getConfig().getBoolean("config.scenarios.absorptionless")){
                Main.config.getConfig().set("config.scenarios.absorptionless", false);
                Main.config.saveConfig();
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scendisabled")).replace("%scenario%", "AbsorptionLess"));
            }
            else if (!Main.config.getConfig().getBoolean("config.scenarios.absorptionless")){
                Main.config.getConfig().set("config.scenarios.absorptionless", true);
                Main.config.saveConfig();
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scenenabled")).replace("%scenario%", "AbsorptionLess"));
            }
        } else if (cmd.getName().equalsIgnoreCase("scen")) {
            sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scen1")));
            if (Main.config.getConfig().getBoolean("config.scenarios.absorptionless")){
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scen2")).replace("%scenario%", "AbsorptionLess"));
            }
            if (Main.config.getConfig().getBoolean("config.scenarios.noclean")){
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scen2")).replace("%scenario%", "NoClean"));
            }
            if (Main.config.getConfig().getBoolean("config.scenarios.timebomb")){
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scen2")).replace("%scenario%", "TimeBomb"));
            }
            if (Main.config.getConfig().getBoolean("config.scenarios.heavypockets")){
                sender.sendMessage(Utils.chat(Main.config.getConfig().getString("messages.scen2")).replace("%scenario%", "HeavyPockets"));
            }
        }

        return false;
    }
}
