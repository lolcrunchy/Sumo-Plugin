package dev.lolcrunchy.sumo.commands.sumoargs;

import dev.lolcrunchy.sumo.Sumo;
import dev.lolcrunchy.sumo.commands.SumoCommandArgument;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetPlayerOneLocationArg implements SumoCommandArgument {
    private final Sumo sumo;

    public SetPlayerOneLocationArg(Sumo sumo) {
        this.sumo = sumo;
    }

    @Override
    public String argument() {
        return "setplayeronelocation";
    }

    @Override
    public void perform(Player whoClicked, String[] args) {
        final FileConfiguration config = this.sumo.getConfig();
        final Location location = whoClicked.getLocation();
        config.set("playerOneLocation.X", location.getX());
        config.set("playerOneLocation.Y", location.getY());
        config.set("playerOneLocation.Z", location.getZ());
        this.sumo.saveConfig();
    }
}
