package com.luke.minigame.manager;

import com.luke.minigame.MiniGame;
import com.luke.minigame.instance.Arena;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.List;

public class ArenaManager {
    private MiniGame miniGame;
    private List<Arena> arenas=new ArrayList<>();

    public ArenaManager(MiniGame miniGame){
        this.miniGame=miniGame;
        FileConfiguration config= miniGame.getConfig();
        for (String key: config.getConfigurationSection("arenas").getKeys(false)){
            arenas.add(new Arena(
                    miniGame,
                    Integer.parseInt(key),
                config.getString("arenas."+key+".world"),
                config.getDouble("arenas."+key+".x"),
                config.getDouble("arenas."+key+".y"),
                config.getDouble("arenas."+key+".z"),
                config.getDouble("arenas."+key+".yaw"),
                config.getDouble("arenas."+key+".pitch")

            ));
        }

    }

    public List<Arena> getArenas(){
        return arenas;
    }

    public Arena getArena(Player player){
        for(Arena arena:arenas){
            if(arena.getPlayers().contains(player.getUniqueId())){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(int id){
        for(Arena arena:arenas){
            if(arena.getId()==id){
                return arena;
            }
        }
        return null;
    }
}
