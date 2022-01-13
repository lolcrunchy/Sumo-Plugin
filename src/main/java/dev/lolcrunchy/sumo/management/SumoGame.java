package dev.lolcrunchy.sumo.management;

import dev.lolcrunchy.sumo.Sumo;
import dev.lolcrunchy.sumo.Util;
import dev.lolcrunchy.sumo.commands.UserIndex;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SumoGame {

    private final Sumo sumo;
    private final Map<UUID, SumoData> connectedPlayers;
    private final GameManager gameManager;
    private List<Round> rounds;

    private LocalDateTime eventStartedAt;
    private BukkitTask startGameThread;
    private BukkitTask countDownThread;

    public SumoGame(Sumo sumo, GameManager gameManager) {
        this.sumo = sumo;
        this.gameManager = gameManager;
        this.connectedPlayers = new HashMap<>();
    }

    public void countDownFinishTeleportUsers() {
        this.sumo.notifyAllUsers(this.sumo.configuration.eventStarted);
        this.rounds = new ArrayList<>();
        this.connectedPlayers.entrySet().stream()
                .map(Map.Entry::getKey)
                .filter(this.gameManager::isPlayerStillOnline)
                .map(Bukkit::getPlayer)
                .forEach(this.gameManager::teleportToSpawnLocation);

        new BukkitRunnable() {
            @Override
            public void run() {
                startGame();
                cancel();
            }
        }.runTaskLater(this.sumo, 20*5);
    }

    private void startGame() {
        startGameThread = Bukkit.getScheduler().runTaskTimer(this.sumo, this::runStartGameLogic, 0l, 20l);
    }

    private void runStartGameLogic() {
        if (connectedPlayers.size() == 1) {
            //Winner logic
        }
        //All game logic placed in here
        Round round = rounds.isEmpty() ? createNextRound() : rounds.get(rounds.size() - 1);
        if (rounds.isEmpty()){
            rounds.add(round);
            round.initRound();
            return;
        }
        Player playerOne = Bukkit.getPlayer(round.getPlayerOneUniqueId());
        Player playerTwo = Bukkit.getPlayer(round.getPlayerTwoUniqueId());

        final boolean testPlayerOne = Util.IS_WITHIN_WATER.test(playerOne);
        final boolean testPlayerTwo = Util.IS_WITHIN_WATER.test(playerTwo);

        if (testPlayerOne || testPlayerTwo) {
            if (testPlayerOne) setWinnerOfRound(playerOne, round);
            else setWinnerOfRound(playerTwo, round);
        }
    }

    private void setWinnerOfRound(Player player, Round round) {
        //Winner logic
    }
    
    /**
     *
     * @return UUID array containing two alive random players
     */
    public UUID[] pickTwoRandomAlivePlayerUniqueIds(){
        final UUID[] aliveUuids = this.connectedPlayers.entrySet().stream()
                    .filter(this.gameManager::isPlayerCurrentlyAlive)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList())
                .toArray(new UUID[]{});
        int[] randomUsersIndex = Util.generateTwoSeparateNumbersUsingReflection(aliveUuids.length);
        return new UUID[]{aliveUuids[randomUsersIndex[UserIndex.PLAYER_ONE.ordinal()]], aliveUuids[randomUsersIndex[UserIndex.PLAYER_TWO.ordinal()]]};
    }

    /**
     * Logic for creating next round
     * @return a new Round object and initialise the round
     */
    private Round createNextRound() {
        UUID[] uuids = pickTwoRandomAlivePlayerUniqueIds();
        final Round round = new Round(uuids[UserIndex.PLAYER_ONE.ordinal()], uuids[UserIndex.PLAYER_TWO.ordinal()], rounds.size(), this);
        return round;
    }

    public void prepareEvent() {
        this.eventStartedAt = LocalDateTime.now();
        this.sumo.notifyAllUsers(this.sumo.configuration.joinEventFirstMessage);
        countDownThread = Bukkit.getScheduler().runTaskTimer(this.sumo, this::countDownToEvent, 0l, 20l);
    }

    public void countDownToEvent() {
        final LocalDateTime now = LocalDateTime.now();
        final int second = eventStartedAt.minusSeconds(now.getSecond()).getSecond();
        if (now.isAfter(eventStartedAt.plusSeconds(59))) {
            this.countDownThread.cancel();
            if (connectedPlayers.size() < sumo.configuration.amountOfPlayersNeedToStartEvent) {
                this.sumo.notifyAllUsers("Sumo havde ikke nok spillere til at starte...");
                return;
            }
            countDownFinishTeleportUsers();
        }
        if (this.gameManager.SPECIFIC_COUNTDOWN_NUM.test(second)) {
            this.sumo.notifyAllUsers(this.sumo.configuration.joinEventCountdownMessage.replace("{tid}", String.valueOf(second)));
        }
    }

    public void messageUsersFromEvent(String message) {
        this.connectedPlayers
                .entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .filter(this.gameManager::isPlayerStillOnline)
                .map(Bukkit::getPlayer)
                .forEach(player -> player.sendMessage(message));
    }

    public Map<UUID, SumoData> getConnectedPlayers() {
        return connectedPlayers;
    }

    public BukkitTask getCountDownThread() {
        return countDownThread;
    }

    public BukkitTask getStartGameThread() {
        return startGameThread;
    }

    public Sumo getSumo() {
        return sumo;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public List<Round> getRounds() {
        return rounds;
    }

}
