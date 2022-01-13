package dev.lolcrunchy.sumo.commands.sumoargs;

import dev.lolcrunchy.sumo.Sumo;
import dev.lolcrunchy.sumo.commands.SumoCommandArgument;
import org.bukkit.entity.Player;

import java.util.UUID;

public class JoinEventArg implements SumoCommandArgument {

    private final Sumo sumo;

    public JoinEventArg(Sumo sumo) {
        this.sumo = sumo;
    }

    @Override
    public String argument() {
        return "join";
    }

    @Override
    public void perform(Player whoClicked, String[] args) {
        if (this.sumo.gameManager.isSumoGameStarted()){
            final UUID uniqueId = whoClicked.getUniqueId();
            if (this.sumo.gameManager.getSumoGame().getConnectedPlayers().containsKey(uniqueId)){
                this.sumo.gameManager.removePlayerFromEvent(whoClicked);
            }else {
                this.sumo.gameManager.addPlayerToEvent(whoClicked);
            }
        }else whoClicked.sendMessage("Der er intet Sumo event lige i øjeblikket.");
    }
}
