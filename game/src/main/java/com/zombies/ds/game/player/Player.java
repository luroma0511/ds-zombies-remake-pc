package com.zombies.ds.game.player;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.zombies.ds.game.entity.Entity;
import com.zombies.ds.game.perk.AfterlifeSoda;
import com.zombies.ds.game.perk.Armor;
import com.zombies.ds.game.perk.DoubleTroubleButterbeer;
import com.zombies.ds.game.perk.SpeedySyringe;

public class Player extends Entity {
    public final Vector3f rotation = new Vector3f(0, 0, 0);
    public final Quaternion quaternion = new Quaternion();

    private final Armor armor = new Armor();
    private final AfterlifeSoda afterlifeSoda = new AfterlifeSoda();
    private final DoubleTroubleButterbeer doubleTroubleButterbeer = new DoubleTroubleButterbeer();
    private final SpeedySyringe speedySyringe = new SpeedySyringe();

    private int points;
    private boolean interact;
    private float delayInteract;
    private float health;
    private float healDelay;

    public float upSpeed;
    public float downSpeed;
    public float leftSpeed;
    public float rightSpeed;

    public void reset(){
        interact = false;
        points = 500;
        health = 100;
        healDelay = 0;
        upSpeed = 0;
        downSpeed = 0;
        leftSpeed = 0;
        rightSpeed = 0;
    }

    public void update(float tpf){
        afterlifeSoda.update(tpf);
        doubleTroubleButterbeer.update(tpf);
        speedySyringe.update(tpf);

        if (!interact) delayInteract = 0;
        else if (delayInteract < 0.5f) {
            delayInteract += tpf;
            if (delayInteract > 0.5f) delayInteract = 0.5f;
            if (delayInteract == 0.5f){
                System.out.println("Interacting");
            }
        }

        healDelay -= tpf;
        if (healDelay < 0) healDelay = 0;

        if (healDelay == 0) health += tpf * 60;
        if (health > 100) health = 100;
    }

    public float updateMoveSpeed(float moveSpeed, boolean pressed, float tpf){
        if (pressed) {
            moveSpeed += 8 * tpf;
            if (moveSpeed > 1) moveSpeed = 1;
        } else {
            moveSpeed -= 8 * tpf;
            if (moveSpeed < 0) moveSpeed = 0;
        }
        return moveSpeed;
    }

    public void damage(int damage){
        if (armor.getDurability() > 0) health -= (float) (damage / 2);
        else health -= damage;
        armor.damage();
        healDelay = 2.5f;
        if (health <= 25) healDelay *= 2;
    }

    public float getDelayInteract() {
        return delayInteract;
    }

    public boolean isInteract() {
        return interact;
    }

    public void setInteract(boolean interact) {
        this.interact = interact;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public float getHealth() {
        return health;
    }

    public Armor getArmor() {
        return armor;
    }

    public AfterlifeSoda getAfterlifeSoda() {
        return afterlifeSoda;
    }

    public DoubleTroubleButterbeer getDoubleTroubleButterbeer() {
        return doubleTroubleButterbeer;
    }

    public SpeedySyringe getSpeedySyringe() {
        return speedySyringe;
    }
}
