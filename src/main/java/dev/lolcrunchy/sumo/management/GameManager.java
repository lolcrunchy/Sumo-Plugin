package dev.lolcrunchy.sumo.management;

import dev.lolcrunchy.sumo.Sumo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class GameManager {

    public final static Predicate<Integer> SPECIFIC_COUNTDOWN_NUM = (num) -> num == 59 || num == 30 || num == 10 || num==5 || num == 3 || num == 2 || num == 1;
    private final Sumo sumo;
    private SumoGame sumoGame = null;

    public GameManager(Sumo sumo) {
        this.sumo = sumo;
    }

    /**
     *
     * @return message to the user
     */
    public String startNewSumoGame(){
        this.sumoGame = new SumoGame(this.sumo, this);
        this.sumoGame.prepareEvent();
        return "Sumo eventet startes hermed...";
    }

    /**
     *
     * @param player the player to be removed from the event
     */
    public void removePlayerFromEvent(Player player) {
        final UUID uniqueId = player.getUniqueId();
        if (isSumoGameStarted()){
            if (this.sumoGame.getConnectedPlayers().containsKey(uniqueId)){
                this.sumoGame.getConnectedPlayers().remove(uniqueId);
                player.sendMessage("Du er ikke længere en del af Sumo eventet...");
                //teleport to spawn logic
            }
        }
    }


    /**
     *
     * @param whoClicked the player who has to be added to the event
     */
    public void addPlayerToEvent(Player whoClicked) {
        final UUID uniqueId = whoClicked.getUniqueId();
        if (!this.sumoGame.getConnectedPlayers().containsKey(uniqueId)){
            this.sumoGame.getConnectedPlayers().put(uniqueId, new SumoData());
            whoClicked.sendMessage("Du er joinet sumo eventet!");
        }
    }

    /**
     * In case user wants to cancel event
     * @return whether cancelling the event worked
     */
    public String cancelSumoGame(){
        if (!isSumoGameStarted()) return "Der var intet sumo game at aflyse..";
        this.sumoGame.getStartGameThread().cancel();
        this.sumoGame.getCountDownThread().cancel();
        return "Sumo event er aflyst..";
    }

    /**
     *
     * @return the current Sumo game
     */
    public SumoGame getSumoGame() {
        return sumoGame;
    }

    /**
     *
     * @return false if Sumo game is not started, true if Sumo game is started..
     */
    public boolean isSumoGameStarted(){
        return sumoGame != null;
    }

    /**
     *
     * @return the location whereas players are teleported on event start
     */
    public Location getSpawnLocation(){
        final Double x = this.sumo.configuration.startLocation.X;
        final Double y = this.sumo.configuration.startLocation.Y;
        final Double z = this.sumo.configuration.startLocation.Z;
        return new Location(Bukkit.getWorld("world"), x, y, z);
    }

    /**
     *
     * @return the location to where the players end up after dying
     */
    public Location getEndLocation(){
        final Double x = this.sumo.configuration.endLocation.X;
        final Double y = this.sumo.configuration.endLocation.Y;
        final Double z = this.sumo.configuration.endLocation.Z;
        return new Location(Bukkit.getWorld("world"), x, y, z);
    }

    /**
     *
     * @return the location where player 1 gets teleported to on round begin
     */
    public Location getPlayerOneLocation(){
        final Double x = this.sumo.configuration.playerOneLocation.X;
        final Double y = this.sumo.configuration.playerOneLocation.Y;
        final Double z = this.sumo.configuration.playerOneLocation.Z;
        return new Location(Bukkit.getWorld("world"), x, y, z);
    }

    /**
     *
     * @return the location where player 2 gets teleported to on round begin
     */
    public Location getPlayerTwoLocation(){
        final Double x = this.sumo.configuration.playerTwoLocation.X;
        final Double y = this.sumo.configuration.playerTwoLocation.Y;
        final Double z = this.sumo.configuration.playerTwoLocation.Z;
        return new Location(Bukkit.getWorld("world"), x, y, z);
    }

    /**
     *
     * @param p teleports the player to spawn
     */
    public void teleportToSpawnLocation(Player p) {
        p.teleport(getSpawnLocation());
    }

    /**
     *
     * @param p teleports the player to end location
     */
    public void teleportToEndLocation(Player p) {
        p.teleport(getEndLocation());
    }

    /**
     *
     * @param p teleports the player to player one location
     */
    public void teleportToPlayerOneLocation(Player p) {
        p.teleport(getPlayerOneLocation());
    }

    /**
     *
     * @param p teleports the player to player two location
     */
    public void teleportToPlayerTwoLocation(Player p) {
        p.teleport(getPlayerTwoLocation());
    }

    /**
     *
     * @param uniqueId the players uniqueId to check whether player is online or not
     * @return a boolean whether the player is online or not
     */
    public boolean isPlayerStillOnline(UUID uniqueId){
        return Bukkit.getPlayer(uniqueId) != null;
    }

    /**
     *
     * @param sumoData takes a Map.Entry of type UUID and SumoData object to check if player is alive
     * @return if player is alive or dead
     */
    public boolean isPlayerCurrentlyAlive(Map.Entry<UUID, SumoData> sumoData){
        return sumoData.getValue().isStillAlive();
    }
}
