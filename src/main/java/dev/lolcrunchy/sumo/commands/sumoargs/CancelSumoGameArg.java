package dev.lolcrunchy.sumo.commands.sumoargs;

import dev.lolcrunchy.sumo.Sumo;
import dev.lolcrunchy.sumo.commands.SumoCommandArgument;
import org.bukkit.entity.Player;

public class CancelSumoGameArg implements SumoCommandArgument {

    private final Sumo sumo;

    public CancelSumoGameArg(Sumo sumo) {
        this.sumo = sumo;
    }

    @Override
    public String argument() {
        return "cancel";
    }

    @Override
    public void perform(Player whoClicked, String[] args) {
        whoClicked.sendMessage(this.sumo.gameManager.cancelSumoGame());
    }
}
