package dev.lolcrunchy.sumo.management;

public class SumoData {
    private boolean isStillAlive;
    private int knockouts;
    private int hits;
    private int deaths;

    public SumoData() {
        this.isStillAlive = true;
        this.knockouts = 0;
        this.hits = 0;
        this.deaths = 0;
    }

    public boolean isStillAlive() {
        return isStillAlive;
    }

    public void setStillAlive(boolean stillAlive) {
        isStillAlive = stillAlive;
    }

    public int getKnockouts() {
        return knockouts;
    }

    public void setKnockouts(int knockouts) {
        this.knockouts = knockouts;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
