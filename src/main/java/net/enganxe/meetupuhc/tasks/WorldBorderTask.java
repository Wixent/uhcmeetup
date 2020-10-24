package net.enganxe.meetupuhc.tasks;

import net.enganxe.meetupuhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldBorderTask {
    public static int wtime;
    private final Main plugin;
    public static boolean enablewb;
    public WorldBorderTask(Main plugin){
        this.plugin = plugin;
    }
    public void WorldBorder() {
        if (Main.started && !enablewb){
            enablewb = true;
            wtime = 61;
        }
        if (Main.started && enablewb) {
            new BukkitRunnable(){
                @Override
                public void run() {
                    wtime = wtime -1;
                    if (wtime == 60){
                        Bukkit.broadcastMessage(ChatColor.AQUA + "Worldborder is shrinking in one minute");
                    }
                    if (wtime == 1){
                        Bukkit.broadcastMessage(ChatColor.AQUA + "Worldborder to 100x100 for 3 minutes");
                        World world = Bukkit.getWorld("world");
                        WorldBorder worldBorder = world.getWorldBorder();
                        worldBorder.setSize(100, 180);
                    }

                }
            }.runTaskTimer(this.plugin, 0, 20);
        }
    }
}
