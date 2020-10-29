package net.enganxe.meetupuhc.config;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldType;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static net.enganxe.meetupuhc.Main.config;

public class WorldCreator {
    public static void createLobby(){
        World lobby = Bukkit.getWorld(config.getConfig().getString("worlds.lobby_world"));
        if (lobby == null) {
            org.bukkit.WorldCreator c = new org.bukkit.WorldCreator(config.getConfig().getString("worlds.lobby_world"));
            c.type(WorldType.FLAT);
            c.generateStructures(false);
            c.createWorld();
        }
    }
    public static void createWorld(){
        org.bukkit.WorldCreator c = new org.bukkit.WorldCreator(Objects.requireNonNull(config.getConfig().getString("worlds.meetup_world")));
        c.generateStructures(false);
        c.generatorSettings();
        Random rand = new Random();
        int seedn = rand.nextInt(20);
        if (seedn == 0) {
            c.seed(11071079617937L);
        }
        if (seedn == 1) {
            c.seed(144845014575172L);
        }
        if (seedn == 2) {
            c.seed(-271973583926241L);
        }
        if (seedn == 3) {
            c.seed(-632446011456927L);
        }
        if (seedn == 4) {
            c.seed(828786898008397L);
        }
        if (seedn == 5) {
            c.seed(912216414204313L);
        }
        if (seedn == 6) {
            c.seed(149829148888427L);
        }
        if (seedn == 7) {
            c.seed(706625171064917L);
        }
        if (seedn == 8) {
            c.seed(-471484538450128L);
        }
        if (seedn == 9) {
            c.seed(196773753078935L);
        }
        if (seedn == 10) {
            c.seed(-130040142013482L);
        }
        if (seedn == 11) {
            c.seed(324262641544591L);
        }
        if (seedn == 12) {
            c.seed(6819149838210L);
        }
        if (seedn == 13) {
            c.seed(520522898501028L);
        }
        if (seedn == 14) {
            c.seed(-186326304768951L);
        }
        if (seedn == 15) {
            c.seed(651407197701048L);
        }
        if (seedn == 16) {
            c.seed(-226065359796077L);
        }
        if (seedn == 17) {
            c.seed(327832372465923L);
        }
        if (seedn == 18) {
            c.seed(728489980140614L);
        }
        if (seedn == 19) {
            c.seed(-575037565325895L);
        }
        c.createWorld();
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
    }
}