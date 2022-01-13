package dev.lolcrunchy.sumo.management;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class Round {

    private final SumoGame sumoGame;
    private final int roundId;
    private final UUID playerOneUniqueId;
    private final UUID playerTwoUniqueId;

    private BukkitTask countDownToRoundBeginThread;

    private boolean hasRoundBegun = false;

    private int countdown;
    private UUID winnerUniqueId;

    public Round(UUID playerOneUniqueId, UUID playerTwoUniqueId, int roundId, SumoGame sumoGame) {
        this.playerOneUniqueId = playerOneUniqueId;
        this.playerTwoUniqueId = playerTwoUniqueId;
        this.roundId = roundId;
        this.sumoGame = sumoGame;
    }

    public void initRound() {
        this.countdown = 3;
        final Player player1 = Bukkit.getPlayer(playerOneUniqueId);
        final Player player2 = Bukkit.getPlayer(playerTwoUniqueId);

        this.sumoGame.getGameManager().teleportToPlayerOneLocation(player1);
        this.sumoGame.getGameManager().teleportToPlayerTwoLocation(player2);
        this.countDownToRoundBeginThread = Bukkit.getScheduler().runTaskTimer(this.sumoGame.getSumo(), this::countDownTimer, 0l, 20l);
    }

    private void countDownTimer(){
        if (countdown <= 0){
            this.sumoGame.messageUsersFromEvent("GO!!");
            this.hasRoundBegun = true;
            this.countDownToRoundBeginThread.cancel();
        }else this.sumoGame.messageUsersFromEvent(String.valueOf(countdown--));
    }

    public UUID getPlayerOneUniqueId() {
        return playerOneUniqueId;
    }

    public UUID getPlayerTwoUniqueId() {
        return playerTwoUniqueId;
    }

    public int getRoundId() {
        return roundId;
    }

    public BukkitTask getCountDownToRoundBeginThread() {
        return countDownToRoundBeginThread;
    }

    public void setCountDownToRoundBeginThread(BukkitTask countDownToRoundBeginThread) {
        this.countDownToRoundBeginThread = countDownToRoundBeginThread;
    }

    public SumoGame getSumoGame() {
        return sumoGame;
    }

    public boolean isHasRoundBegun() {
        return hasRoundBegun;
    }

    public void setHasRoundBegun(boolean hasRoundBegun) {
        this.hasRoundBegun = hasRoundBegun;
    }

    public UUID getWinnerUniqueId() {
        return winnerUniqueId;
    }

    public void setWinnerUniqueId(UUID winnerUniqueId) {
        this.winnerUniqueId = winnerUniqueId;
    }
}
