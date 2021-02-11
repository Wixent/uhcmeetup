package net.enganxe.meetupuhc.scenarios;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

public class AbsorptionLess implements Listener {
    private final Main plugin;
    public AbsorptionLess(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void on (PlayerItemConsumeEvent e) {
        if (Main.config.getConfig().getBoolean("config.scenarios.absorptionless")){
            if (e.getItem().getType() == Material.GOLDEN_APPLE || e.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        e.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION);
                    }
                }, 2L);
            }
        }
    }
}
