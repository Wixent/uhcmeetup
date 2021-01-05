package net.enganxe.meetupuhc.events;

import net.enganxe.meetupuhc.Main;
import net.enganxe.meetupuhc.config.WorldCreator;
import net.enganxe.meetupuhc.player.KitGiver;
import net.enganxe.meetupuhc.player.Scoreboards;
import net.enganxe.meetupuhc.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.Objects;
import java.util.Random;

import static net.enganxe.meetupuhc.Main.*;

public class AutoStartEvent implements Listener {
    public static int time;
    public static boolean first;
    public static int wtime;
    public static boolean enablewb;
    private final Main plugin;
    public static String timer;
    public AutoStartEvent(Main plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (starting && !started){
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();
            Objective objective = board.registerNewObjective("Health", "health");
            objective.setDisplayName(ChatColor.RED + "❤");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            p.setScoreboard(board);
            PlayerInventory inv =  p.getInventory();
            inv.clear();
            p.setLevel(0);
            scatter(p);
            World world = p.getWorld();
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            KitGiver.setInv(p);
            return;
        }
        Main.PlayersToStart = config.getConfig().getInt("config.playerstostart");
        String world = config.getConfig().getString("worlds.meetup_world");
        World worldd = Bukkit.getWorld(config.getConfig().getString("worlds.meetup_world"));
        if (Bukkit.getOnlinePlayers().size() == Main.PlayersToStart) {
            if (!Main.started && !Main.starting) {
                time = config.getConfig().getInt("config.countdowntime") + 1;
                first = false;
                Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        time = time - 1;
                        if (!first){
                            first = true;
                            WorldCreator.setWorldBorder();
                            worldd.setGameRule(GameRule.NATURAL_REGENERATION, false);
                            worldd.setGameRule(GameRule.DO_MOB_SPAWNING, false);
                            worldd.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                            worldd.setGameRule(GameRule.DO_INSOMNIA, false);
                            worldd.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
                            worldd.setTime(9000);
                            worldd.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                            Main.starting = true;
                            Main.started = false;
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                scatter(all);
                                KitGiver.setInv(all);
                                ScoreboardManager manager = Bukkit.getScoreboardManager();
                                Scoreboard board = manager.getNewScoreboard();
                                Objective objective = board.registerNewObjective("Health", "health");
                                objective.setDisplayName(ChatColor.RED + "❤");
                                objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                                all.setScoreboard(board);
                            }
                        }
                        if (time == 120){
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }
                        }
                        if (time == 60){
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }
                        }
                        if (time == 30) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }
                        }
                        if (time == 15) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }

                        }
                        if (time == 10) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }
                        }
                        if (time == 5) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }
                        }
                        if (time == 4) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }
                        }
                        if (time == 3) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }
                        }
                        if (time == 2) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }
                        }
                        if (time == 1) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Starting in " + ChatColor.LIGHT_PURPLE + time);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlecountdown").replace("%countdown%", "" + time)), "");
                                }
                            }
                        }
                        if (time == 0) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "The Meetup has been " + ChatColor.LIGHT_PURPLE + "started!");
                            assert world != null;
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (config.getConfig().getBoolean("config.sendcountdowntitle")) {
                                    all.sendTitle(Utils.chat(config.getConfig().getString("messages.titlestart")), Utils.chat(config.getConfig().getString("messages.subtitlestart")), 10, 40, 10);
                                }
                                Main.PlayersAlive.add(all);
                                all.setInvulnerable(false);
                                all.playSound(all.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 10, 1);
                                for(PotionEffect effect : all.getActivePotionEffects()){
                                    all.removePotionEffect(effect.getType());
                                }
                                String pa = all.getName();
                                int gplayed = config.getConfig().getInt("stats.players." + pa + ".gamesplayed");
                                config.getConfig().set("stats.players." + pa + ".gamesplayed", gplayed + 1);
                                config.saveConfig();

                            }
                            int mplayed = config.getConfig().getInt("stats.Played");
                            config.getConfig().set("stats.Played", mplayed + 1);
                            config.saveConfig();
                            Main.starting = false;
                            Main.started = true;
                            WorldBorderSh();
                            time();
                        }
                    }
                },0L, 20L);
            }
        }
    }
    public void WorldBorderSh() {
            wtime = 61;
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    wtime = wtime - 1;
                    if (wtime == 60) {
                        String prefix = Main.config.getConfig().getString("config.borderprefix");
                        String msg = Main.config.getConfig().getString("messages.worldborder1");
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
                    }
                    if (wtime == 0) {
                        String prefix = Main.config.getConfig().getString("config.borderprefix");
                        String msg = Main.config.getConfig().getString("messages.worldborder2");
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
                        World world = Bukkit.getWorld(config.getConfig().getString("worlds.meetup_world"));
                        WorldBorder worldBorder = world.getWorldBorder();
                        worldBorder.setSize(100, 180);
                        sendaction();
                    }
                }
            }, 0L, 20L);
        }
    public void sendaction(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (Main.started && !finalized) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.chat(config.getConfig().getString("messages.actionbarwb")).replace("%wbsize%", Scoreboards.getBorderSize())));
                    }
                }
            }
        },0L, 1L);
    }
    public void scatter(Player p){
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2147483647, 200));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, 200));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 2147483647, 200));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2147483647, 200));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 200));
        String world = config.getConfig().getString("worlds.meetup_world");
        int worldborder = Integer.parseInt(Objects.requireNonNull(config.getConfig().getString("config.worldborder")));
        new BukkitRunnable() {
            @Override
            public void run() {
                Random rand = new Random();
                int x = rand.nextInt(worldborder/2);
                int z = rand.nextInt(worldborder/2);
                if (rand.nextInt(100) <= 49) {
                    x = -1 * x;
                }
                if (rand.nextInt(100) <= 49) {
                    z = -1 * z;
                }
                int y = 1;
                assert world != null;
                if (Objects.requireNonNull(Bukkit.getWorld(world)).getHighestBlockAt(x, z).getY() < 120) {
                    y = Objects.requireNonNull(p.getServer().getWorld(world)).getHighestBlockYAt(x, z) + 2;
                    if (Objects.requireNonNull(Bukkit.getWorld(world)).getBlockAt(x, y, z).getType() == Material.WATER) {
                        rand = new Random();
                        x = rand.nextInt(worldborder/2);
                        z = rand.nextInt(worldborder/2);
                        if (rand.nextInt(100) <= 49) {
                            x = -1 * x;
                        }
                        if (rand.nextInt(100) <= 49) {
                            z = -1 * z;
                        }
                        y = Objects.requireNonNull(p.getServer().getWorld(world)).getHighestBlockYAt(x, z) + 2;
                    }
                } else {
                    rand = new Random();
                    x = rand.nextInt(worldborder/2);
                    z = rand.nextInt(worldborder/2);
                    if (rand.nextInt(100) <= 49) {
                        x = -1 * x;
                    }
                    if (rand.nextInt(100) <= 49) {
                        z = -1 * z;
                    }
                }
                p.setStatistic(Statistic.PLAYER_KILLS, 0);
                p.teleport(new Location(Bukkit.getWorld(world), x, y, z));
                p.setGameMode(GameMode.SURVIVAL);

            }
        }.runTaskLater(this.plugin, 5);
    }
    public void time(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int min = 00;
            int sec = 00;
            int hor = 00;
            String secc;
            String minn;
            String horr;
            @Override
            public void run() {
                if (sec < 10){
                    secc = "0" + sec;
                } else if (sec > 9){
                    secc = "" + sec;
                }
                if (min < 10){
                    minn = "0" + min;
                } else if (min > 9){
                    minn = "" + min;
                }
                if (hor < 10){
                    horr = "0" + hor;
                } else if (sec > 9){
                    horr = "" + hor;
                }
                timer = horr + ":" + minn + ":" + secc;
                sec = sec + 1;
                if (sec == 60){
                    sec = 00;
                    min = min + 1;
                }
                if (min == 60){
                    min = 00;
                    hor = hor + 1;
                }
            }
        },0L, 20L);
    }
}