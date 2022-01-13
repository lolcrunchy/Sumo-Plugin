package dev.lolcrunchy.sumo;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.function.Predicate;

public class Util {

    public static int[] generateTwoSeparateNumbersUsingReflection(int userSize){
        final int o = new Random().nextInt(userSize);
        final int t = new Random().nextInt(userSize);
        return o != t ? new int[]{o,t} : generateTwoSeparateNumbersUsingReflection(userSize);
    }

    public static Predicate<Player> IS_WITHIN_WATER = (player) -> player.getLocation().getBlock().getType() == Material.WATER || player.getLocation().getBlock().getType() == Material.STATIONARY_WATER;

}
