package com.luke.minigame.manager;

import com.luke.minigame.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static FileConfiguration config;
    public static void setUpConfig(MiniGame miniGame){
        ConfigManager.config= miniGame.getConfig();
        miniGame.saveDefaultConfig();
    }

    public static int getMinRequiredPlayers(){
        return config.getInt("min-required-players");
    }

    public static int getCountdownSeconds(){
        return config.getInt("countdown-seconds");
    }
    public static Location getLobbySpawn(){
        return new Location(
                Bukkit.getWorld(config.getString("lobby-spawn.world")),
                config.getDouble("lobby-spawn.x"),
                config.getDouble("lobby-spawn.y"),
                config.getDouble("lobby-spawn.z"),
                (float) config.getDouble("lobby-spawn.yaw"),
                (float) config.getDouble("lobby-spawn.pitch")
        );
    }

}
