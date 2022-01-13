package dev.lolcrunchy.sumo.commands.sumoargs;

import dev.lolcrunchy.sumo.Sumo;
import dev.lolcrunchy.sumo.commands.SumoCommandArgument;
import org.bukkit.entity.Player;

public class StartSumoEventArg implements SumoCommandArgument {

    private final Sumo sumo;

    public StartSumoEventArg(Sumo sumo) {
        this.sumo = sumo;
    }

    @Override
    public String argument() {
        return "start";
    }

    @Override
    public void perform(Player whoClicked, String[] args) {
        if (!this.sumo.gameManager.isSumoGameStarted()){
            whoClicked.sendMessage(this.sumo.gameManager.startNewSumoGame());
        }else whoClicked.sendMessage("§cEventet er startet allerede..");
    }
}
