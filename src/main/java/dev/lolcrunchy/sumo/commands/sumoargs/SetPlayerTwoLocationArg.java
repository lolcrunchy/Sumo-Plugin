package dev.lolcrunchy.sumo.commands.sumoargs;

import dev.lolcrunchy.sumo.Sumo;
import dev.lolcrunchy.sumo.commands.SumoCommandArgument;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetPlayerTwoLocationArg implements SumoCommandArgument {
    private final Sumo sumo;

    public SetPlayerTwoLocationArg(Sumo sumo) {
        this.sumo = sumo;
    }


    @Override
    public String argument() {
        return "setplayertwolocation";
    }

    @Override
    public void perform(Player whoClicked, String[] args) {
        final FileConfiguration config = this.sumo.getConfig();
        final Location location = whoClicked.getLocation();
        config.set("playerTwoLocation.X", location.getX());
        config.set("playerTwoLocation.Y", location.getY());
        config.set("playerTwoLocation.Z", location.getZ());
        this.sumo.saveConfig();
    }
}
