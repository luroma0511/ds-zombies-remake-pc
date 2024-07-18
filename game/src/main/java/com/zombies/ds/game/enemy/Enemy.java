package com.zombies.ds.game.enemy;

import com.zombies.ds.game.entity.Entity;

public abstract class Enemy extends Entity {
    protected final String name;
    protected int health;
    protected int maxHealth;
    protected float disposeCooldown;
    protected boolean active;
    protected float moveSpeed;

    protected Enemy(String name){
        this.name = name;
    }

    protected abstract void reset();

    protected abstract void moveSpeedInit(int round);

    protected abstract void healthInit(int round);

    public void damage(int damage){
        health -= damage;
        if (health < 0) health = 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
