package dev.lolcrunchy.sumo.commands.sumoargs;

import dev.lolcrunchy.sumo.Sumo;
import dev.lolcrunchy.sumo.commands.SumoCommandArgument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Optional;

public class SumoCommand implements CommandExecutor {

    private final Sumo sumo;
    private final SumoCommandArgument[] sumoCommandArguments;

    public SumoCommand(Sumo sumo) {
        this.sumo = sumo;
        this.sumoCommandArguments = new SumoCommandArgument[]{
                new SetEndLocationArg(this.sumo),
                new SetPlayerOneLocationArg(this.sumo),
                new SetPlayerTwoLocationArg(this.sumo),
                new SetSpawnLocationArg(this.sumo),
                new StartSumoEventArg(this.sumo),
                new JoinEventArg(this.sumo),
                new CancelSumoGameArg(this.sumo)
        };
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        final Optional<SumoCommandArgument> firstArgument = Arrays.stream(sumoCommandArguments)
                .filter(sumoCommandArgument -> sumoCommandArgument.argument().equalsIgnoreCase(args[0]))
                .findFirst();
        if (firstArgument.isPresent()){
            firstArgument.get().perform(player, args);
        } else player.sendMessage(this.sumo.configuration.argumentNotFound);
        return true;
    }
}
