package com.luke.minigame.listener;

import com.luke.minigame.GameState;
import com.luke.minigame.MiniGame;
import com.luke.minigame.instance.Arena;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class GameListener implements Listener {
    private MiniGame miniGame;

    public GameListener(MiniGame miniGame){
        this.miniGame=miniGame;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Arena arena=miniGame.getArenaManager().getArena(e.getPlayer());
        if(arena!=null){
            if(e.getBlock().getType().equals(Material.COBBLESTONE)&&arena.getState().equals(GameState.LIVE)){

                arena.getGame().addPoint(e.getPlayer());

                e.setCancelled(false);
            }else{
                e.setCancelled(true);


            }
        }else{
            e.setCancelled(false);
        }


    }
}
