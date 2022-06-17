package com.luke.minigame.instance;

import com.luke.minigame.GameState;
import com.luke.minigame.MiniGame;
import com.luke.minigame.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {
    private int id;
    private Location spawn;

    private GameState state;
    private List<UUID> players;

    private CountDown countDown;
    private Game game;
    private MiniGame miniGame;


    public Arena(MiniGame miniGame, int id, Location location){
        this.miniGame=miniGame;
        this.id=id;
        this.spawn=location;

        this.state=GameState.RECRUITING;
        this.players=new ArrayList<>();
        countDown=new CountDown(miniGame,this);
        this.game=new Game(this);
    }

    public Arena(MiniGame miniGame,int id, String name, double x, double y, double z, double yaw, double pitch){
        this(miniGame,id, new Location(Bukkit.getWorld(name),x,y,z,(float) yaw,(float)pitch));


    }

    public List<UUID> getPlayers(){
        return players;
    }

    public int getId(){return id;}

    public GameState getState(){ return this.state;}
    public void setState(GameState gameState){
        this.state=gameState;
    }
    public Game getGame(){
        return game;
    }



    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if(state.equals(GameState.RECRUITING)&&players.size()>=ConfigManager.getMinRequiredPlayers()){
            countDown.start();
        }
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("","",10,70,20);

        if(state==GameState.COUNTDOWN && players.size()< ConfigManager.getMinRequiredPlayers()){
            sendMessage(ChatColor.RED+"There is not enough players. Countdown stopped!");
            reset(false);
            return;
        }
        if(state==GameState.LIVE && players.size()< ConfigManager.getMinRequiredPlayers()){
            reset(false);
            sendMessage(ChatColor.RED+"The game has ended as too many players have left!");
        }

    }

    public void start(){
//        state=GameState.LIVE;
        game.start();
    }

    public void reset(boolean kickPlayers){
        if(kickPlayers){
            Location location=ConfigManager.getLobbySpawn();
            for (UUID uuid: players){
                Bukkit.getPlayer(uuid).teleport(location);
            }
            players.clear();
        }

        sendTitle("","");
        state=GameState.RECRUITING;
        countDown.cancel();
        countDown=new CountDown(miniGame,this);
    }

    public void sendMessage(String message){
        for(UUID uuid:players){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title,String subtitle){
        for (UUID uuid: players){
            Bukkit.getPlayer(uuid).sendTitle(title,subtitle,10,70,20);
        }
    }
}
