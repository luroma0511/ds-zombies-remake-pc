package com.zombies.ds.game.perk;

import com.zombies.ds.game.entity.Entity;
import com.zombies.ds.game.functional.Functions;
import com.zombies.ds.game.player.Player;
import com.zombies.ds.game.utils.MathUtils;

public abstract class Perk {
    protected boolean equipped;

    //30 minute timer
    private final int timer = 60 * 30;

    private float timeLimit;
    private float delay;

    public void reset(){
        timeLimit = 0;
        delay = 0;
        equipped = false;
    }

    public void update(float tpf){
        delay -= tpf;
        if (delay < 0) delay = 0;
        if (delay > 0) return;

        timeLimit -= tpf;
        if (timeLimit < 0) timeLimit = 0;
    }

    public void equip(){
        delay = 3;
        timeLimit = timer;
        equipped = true;
    }

    public int cost(){
        int timeValue = (int) ((float) (timer - getTimeLimit()) / 60);
        if (timeValue < 20) return 0;
        equipped = false;
        return timeValue * 75;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public int getTimer() {
        return timer;
    }

    public float getTimeLimit() {
        return timeLimit;
    }

    public float getDelay() {
        return delay;
    }

    public void interact(Entity machine, Player player, float entityRadius){
        if (!equipped && Functions.interact(machine, player, entityRadius)){
            if (cost() == 0 || player.getPoints() < cost()) return;
            equip();
        }
    }
}
