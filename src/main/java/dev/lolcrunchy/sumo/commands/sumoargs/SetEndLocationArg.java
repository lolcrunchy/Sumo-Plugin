package dev.lolcrunchy.sumo.commands.sumoargs;

import dev.lolcrunchy.sumo.Sumo;
import dev.lolcrunchy.sumo.commands.SumoCommandArgument;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetEndLocationArg implements SumoCommandArgument {

    private final Sumo sumo;

    public SetEndLocationArg(Sumo sumo) {
        this.sumo = sumo;
    }

    @Override
    public String argument() {
        return "setendlocation";
    }

    @Override
    public void perform(Player whoClicked, String[] args) {
        final FileConfiguration config = this.sumo.getConfig();
        final Location location = whoClicked.getLocation();
        config.set("endLocation.X", location.getX());
        config.set("endLocation.Y", location.getY());
        config.set("endLocation.Z", location.getZ());
        this.sumo.saveConfig();
    }
}
