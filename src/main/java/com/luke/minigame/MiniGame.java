package com.luke.minigame;

import com.luke.minigame.instance.Arena;
import com.luke.minigame.manager.ArenaManager;
import com.luke.minigame.manager.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MiniGame extends JavaPlugin {

    private ArenaManager arenaManager;
    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager.setUpConfig(this);
        arenaManager=new ArenaManager(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ArenaManager getArenaManager(){
        return arenaManager;
    }

}
