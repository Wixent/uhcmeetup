package net.enganxe.meetupuhc.config;

import net.enganxe.meetupuhc.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static net.enganxe.meetupuhc.Main.config;
import static net.enganxe.meetupuhc.utils.Utils.getRandomInt;

public class WorldCreator {
    public static void createLobby(){
        World lobby = Bukkit.getWorld(config.getConfig().getString("worlds.lobby_world"));
        if (lobby == null) {
            org.bukkit.WorldCreator c = new org.bukkit.WorldCreator(config.getConfig().getString("worlds.lobby_world"));
            c.type(WorldType.FLAT);
            c.generateStructures(false);
            c.createWorld();
            World world = Bukkit.getWorld(Objects.requireNonNull(config.getConfig().getString("worlds.lobby_world")));
            assert world != null;
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);

        }
    }
    public static void createWorld(){
        org.bukkit.WorldCreator c = new org.bukkit.WorldCreator(Objects.requireNonNull(config.getConfig().getString("worlds.meetup_world")));
        c.generateStructures(false);
        c.generatorSettings();

        int total = config.getConfig().getInt("config.seeds.totalseeds");
        int number = getRandomInt(total) + 1;
        c.seed(config.getConfig().getLong("config.seeds." + number));
        Bukkit.getLogger().info("seed:" + config.getConfig().getLong("config.seeds." + number));
        c.createWorld();
        World world = Bukkit.getWorld(Objects.requireNonNull(config.getConfig().getString("worlds.meetup_world")));
        assert world != null;
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
    }
    public static void deleteWorld(){
        File worldFolder = new File(Objects.requireNonNull(config.getConfig().getString("worlds.meetup_world")));
        try {
            FileUtils.deleteDirectory(worldFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void setWorldBorder(){
        World world = Bukkit.getWorld(Objects.requireNonNull(config.getConfig().getString("worlds.meetup_world")));
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setSize(config.getConfig().getInt("config.worldborder"));
        worldBorder.setDamageBuffer(0);
    }
}