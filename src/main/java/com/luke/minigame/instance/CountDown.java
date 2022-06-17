package com.luke.minigame.instance;

import com.luke.minigame.GameState;
import com.luke.minigame.MiniGame;
import com.luke.minigame.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDown extends BukkitRunnable {

    private MiniGame miniGame;
    private Arena arena;
    private int countDownSeconds;

    public CountDown(MiniGame miniGame,Arena arena){
        this.miniGame=miniGame;
        this.arena=arena;
        this.countDownSeconds= ConfigManager.getCountdownSeconds();
    }
    public void start(){
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(miniGame,0,20);
    }

    @Override
    public void run() {
        if(countDownSeconds==0){
            cancel();

            arena.start();
            return;
        }

        if(countDownSeconds<=10||countDownSeconds%10==0){
            arena.sendMessage(
                    ChatColor.GREEN+"Game will start in "+countDownSeconds+" second"+(countDownSeconds>1 ?"s":"")+"."
            );

        }
        arena.sendTitle(ChatColor.GREEN.toString()+countDownSeconds+" second"+(countDownSeconds>1 ?"s":""),
                            ChatColor.GRAY.toString()+arena.getPlayers().size()+"/"+ConfigManager.getMinRequiredPlayers()
                            );

        countDownSeconds--;

    }
}
