package dev.lolcrunchy.sumo.management;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class GameListener implements Listener {

    private final GameManager gameManager;

    public GameListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        final Player player = e.getPlayer();
        final UUID uniqueId = player.getUniqueId();
        if (this.gameManager.isSumoGameStarted() && this.gameManager.getSumoGame().getConnectedPlayers().containsKey(uniqueId)){
            //Check if user is in the current round,
            // if not then just remove the player from map, if he is in the current round you need to specify the winner..
        }
    }

}
