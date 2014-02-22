package ru.spb.magic2k.field;

/**
 * User: magic2k
 * Date: 26.08.12
 * Time: 3:05
 * Класс, в котором передаются текущие статистические счетчики пользователя.
 */
public class Stats {
    private long fps = 0;
    private int kills = 0;
    private int gold = 0;

    public long getFps() {
        return fps;
    }

    public void setFps(long fps) {
        this.fps = fps;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
