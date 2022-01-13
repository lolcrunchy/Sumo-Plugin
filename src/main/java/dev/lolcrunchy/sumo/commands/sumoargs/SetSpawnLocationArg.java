package dev.lolcrunchy.sumo.commands.sumoargs;

import dev.lolcrunchy.sumo.Sumo;
import dev.lolcrunchy.sumo.commands.SumoCommandArgument;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnLocationArg implements SumoCommandArgument {

    private final Sumo sumo;

    public SetSpawnLocationArg(Sumo sumo){
        this.sumo = sumo;
    }
    @Override
    public String argument() {
        return "setspawnlocation";
    }

    @Override
    public void perform(Player whoClicked, String[] args) {
        final Location location = whoClicked.getLocation();
        final FileConfiguration config = sumo.getConfig();
        config.set("startLocation.X", location.getX());
        config.set("startLocation.Y", location.getY());
        config.set("startLocation.Z", location.getZ());
        sumo.saveConfig();
    }
}
