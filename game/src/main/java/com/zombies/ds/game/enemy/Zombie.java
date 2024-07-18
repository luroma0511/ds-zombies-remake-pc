package com.zombies.ds.game.enemy;

public class Zombie extends Enemy {
    public Zombie() {
        super("Zombie");
    }

    @Override
    public void reset(){
        disposeCooldown = 15;
        active = true;
    }

    @Override
    public void moveSpeedInit(int round){

    }

    @Override
    public void healthInit(int round){
        if (round > 50) round = 50;
        if (round <= 10) health = 150 + (round - 1) * 100;
        else health = (int) (1050 * (Math.pow(1.125f, round - 10)));
        maxHealth = health;
    }
}
