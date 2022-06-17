package com.luke.minigame.command;

import com.luke.minigame.GameState;
import com.luke.minigame.MiniGame;
import com.luke.minigame.instance.Arena;
import com.luke.minigame.manager.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

public class AreaCommand implements CommandExecutor {

    private MiniGame miniGame;

    public AreaCommand(MiniGame miniGame){
        this.miniGame=miniGame;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player=(Player) sender;

            if(args.length==1&& args[0].equalsIgnoreCase("list")){
                player.sendMessage(ChatColor.GREEN+"These are the available arenas:");
                for (Arena arena: miniGame.getArenaManager().getArenas()){
                    player.sendMessage(ChatColor.GREEN+ " - "+ arena.getId()+ " ("+arena.getState().name()+")"
                    + " ("+arena.getPlayers()+"/"+ ConfigManager.getMinRequiredPlayers() +")");
                }

            }else if(args.length==1 && args[0].equalsIgnoreCase("quit")){
                Arena arena=miniGame.getArenaManager().getArena(player);
                if(arena!=null){
                    arena.removePlayer(player);
                    player.sendMessage(ChatColor.RED+"You left the arena");
                }else{
                    player.sendMessage(ChatColor.RED+"You are not in an arena");
                }

            }else if(args.length==2 && args[0].equalsIgnoreCase("join")){
                Arena arena=miniGame.getArenaManager().getArena(player);
                if(arena!=null){
                    player.sendMessage(ChatColor.RED+"You are already in an arena");
                }else{
                    int id = 0;
                    try {
                        id=Integer.parseInt(args[1]);
                    }
                    catch (NullPointerException e){
                        player.sendMessage(ChatColor.RED+"You used an invalid ID!");
                    }
                    arena=miniGame.getArenaManager().getArena(id);
                    if(arena!=null){
                        if(arena.getState().equals(GameState.LIVE)){
                            player.sendMessage(ChatColor.RED+"This arena is in live!");
                        }else{
                            arena.addPlayer(player);
                            player.sendMessage(ChatColor.GREEN+"You are now playing in Arena "+id+".");
                        }
                    }
                    else{
                        player.sendMessage(ChatColor.RED+"This arena id does not exist!");
                    }


                }

            }else{
                player.sendMessage(ChatColor.RED+"Invalid usage! These are the options:");
                player.sendMessage(ChatColor.RED+" - /arena list");
                player.sendMessage(ChatColor.RED+" - /arena quit");
                player.sendMessage(ChatColor.RED+" - /arena join <id>");
            }

        }

        return false;
    }
}
