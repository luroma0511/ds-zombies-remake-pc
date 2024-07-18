package com.zombies.ds.game.enemy;

public class Dog extends Enemy {
    public Dog() {
        super("Hellhound");
    }

    @Override
    public void reset() {
        active = true;
        disposeCooldown = 1.75f;
    }

    @Override
    public void moveSpeedInit(int round){

    }

    @Override
    public void healthInit(int round){
        if (round > 50) round = 50;
        health = 350 + (100 * (round - 1));
    }
}
