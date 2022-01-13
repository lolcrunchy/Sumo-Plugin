package dev.lolcrunchy.sumo.commands;

import org.bukkit.entity.Player;

public interface SumoCommandArgument {
    String argument();
    void perform(Player whoClicked, String[] args);
}
