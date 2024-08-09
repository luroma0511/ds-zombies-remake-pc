package com.zombies.ds.game.player;

import com.jme3.math.Vector3f;
import com.zombies.ds.game.BlackOpsDsRemake;
import com.zombies.ds.game.entity.Entity;
import com.zombies.ds.game.input.Input;
import com.zombies.ds.game.model.EntityManager;
import com.zombies.ds.game.perk.AfterlifeSoda;
import com.zombies.ds.game.perk.Armor;
import com.zombies.ds.game.perk.DoubleTroubleButterbeer;
import com.zombies.ds.game.perk.SpeedySyringe;
import com.zombies.ds.game.weapons.Weapon;

public class Player extends Entity {
    private final Armor armor = new Armor();
    private final AfterlifeSoda afterlifeSoda = new AfterlifeSoda();
    private final DoubleTroubleButterbeer doubleTroubleButterbeer = new DoubleTroubleButterbeer();
    private final SpeedySyringe speedySyringe = new SpeedySyringe();

    private final float standValue = 3.25f;
    private final float crouchValue = 2.25f;
    private final float proneValue = 1.1f;

    public Weapon primaryWeapon;

    private int points;
    private boolean interact;
    private float delayInteract;
    private float health;
    private float healDelay;
    private float runningDelay;

    public boolean running;
    public boolean crouch;
    public boolean prone;
    public boolean proneLock;
    public float switchPositionDelay;
    public float yPosition;
    public float sprintSpeed;
    public float verticalSpeed;
    public float horizontalSpeed;

    public float aimValue;
    public float bobbing;
    public boolean bobbingReverse;
    public float bobValue;
    public int bobWeaponTarget;
    public float bobWeaponDifference;

    public float targetX;
    public float targetY;
    public float rotateXDifference;
    public float rotateYDifference;


    public void reset(){
        interact = false;
        points = 500;
        health = 100;
        healDelay = 0;
        verticalSpeed = 0;
        horizontalSpeed = 0;
        bobbing = 0;
        sprintSpeed = 0;
        bobWeaponTarget = 0;
        bobWeaponDifference = 0;
        aimValue = 1;
        yPosition = standValue;
        bobValue = 0;
        bobbingReverse = false;
        running = false;
        crouch = false;
        prone = false;
        proneLock = false;
        switchPositionDelay = 0;
    }

    public void input(BlackOpsDsRemake app, float tpf){
        Input input = app.getInput();

        boolean forward = input.isPressed("Forward");
        boolean backward = input.isPressed("Backward");
        boolean strafeLeft = input.isPressed("Strafe Left");
        boolean strafeRight = input.isPressed("Strafe Right");

        boolean moving = forward || backward || strafeLeft || strafeRight;
        boolean diagonal = (strafeLeft || strafeRight) && (forward || backward);

        boolean aiming = input.isPressed("Button Right");

        if (primaryWeapon != null) {
            if (aiming && aimValue > 0) {
                aimValue -= tpf / primaryWeapon.adsSeconds;
                if (aimValue < 0) aimValue = 0;
            } else if (!aiming && aimValue < 1) {
                aimValue += tpf / primaryWeapon.adsSeconds;
                if (aimValue > 1) aimValue = 1;
            }
        }

        if (!crouch && !prone && !aiming && forward && input.isPressed("Run") && runningDelay < 4.375f){
            running = true;
        }

        if (running && !aiming && forward){
            runningDelay += tpf;
            if (runningDelay >= 5) {
                runningDelay = 5;
                running = false;
            }
        } else if (!running){
            runningDelay -= tpf / 2;
            if (runningDelay < 0) runningDelay = 0;
        } else {
            running = false;
        }

        if (input.isJustPressed("Crouch")){
            switchPositionDelay = 0;
            running = false;
            if (prone) {
                proneLock = true;
                crouch = true;
                prone = false;
            } else crouch = !crouch;
        }
        if (input.isPressed("Crouch")){
            switchPositionDelay += tpf;
            if (!proneLock && switchPositionDelay > 0.375f){
                prone = true;
                proneLock = true;
            }

            if (switchPositionDelay > 0.375f) switchPositionDelay = 0;
        } else {
            proneLock = false;
        }

        verticalSpeed = updateMoveSpeed(verticalSpeed, 10, backward, forward, diagonal, tpf);
        horizontalSpeed = updateMoveSpeed(horizontalSpeed, 10, strafeRight, strafeLeft, diagonal, tpf);

        float aimMultiplier = 1 - 0.2f * (1 - aimValue);

        verticalSpeed *= aimMultiplier;
        horizontalSpeed *= aimMultiplier;

        boolean footstep = false;

        if (moving){
            float value = tpf * 0.4f;
            if (running) value *= 2.25f;
            else if (prone) value /= 4;
            else if (crouch) value /= 2;
            if (!bobbingReverse){
                float bobLimit = 0.075f;
                if (running) bobLimit = 0.125f;
                else if (prone) bobLimit = 0.025f;
                else if (crouch) bobLimit = 0.05f;
                bobbing += value;
                if (bobbing > bobLimit) {
                    float difference = bobbing - bobLimit;
                    bobbing -= difference * 2;
                    bobbingReverse = true;
                }
            } else {
                bobbing -= value;
                if (bobbing < 0) {
                    bobbing = -bobbing;
                    footstep = true;
                    bobbingReverse = false;
                }
            }
        } else {
            if (bobbing > 0) {
                bobbing -= bobbing / 5;
                bobbingReverse = false;
            }
        }

        if (prone && yPosition > proneValue){
            float difference = proneValue - yPosition;
            yPosition += difference / 10;
        } else if (!prone && !crouch && yPosition < standValue){
            float difference = standValue - yPosition;
            yPosition += difference / 10;
        } else if (!prone && crouch) {
            float difference = crouchValue - yPosition;
            if (yPosition != crouchValue) {
                yPosition += difference / 10;
            }
        }

        boolean bobWeapon = running || (prone && !aiming);

        if (bobWeapon) {
            float distance = (1 - bobValue) / 3;
            bobValue += distance * tpf * 30;
        } else {
            float distance = bobValue / 3;
            bobValue -= distance * tpf * 30;
        }

        if (bobWeapon && footstep){
            if (bobWeaponTarget == 0) bobWeaponTarget = 1;
            else bobWeaponTarget = 0;
        } else if ((!bobWeapon || !moving) && bobWeaponTarget != 1){
            bobWeaponTarget = 1;
        }

        int target = bobWeaponTarget;
        if (!moving || !bobWeapon) target = 0;
        float distance = (target - bobWeaponDifference) / 3;
        bobWeaponDifference += distance * tpf * 8;

        float limit = 0.5f;
        if (prone) limit = 0.1f;
        else if (crouch) limit = 0.3f;
        if (running && sprintSpeed < 1) {
            sprintSpeed += tpf * 4;
        } else if (!running && sprintSpeed > 0){
            sprintSpeed -= tpf * 4;
        }
        limit += 0.2f * sprintSpeed;
        limit /= 3;

        Vector3f offsetPosition = movePosition(app.getCamera().getLocation(), app.getCamera(), -verticalSpeed * limit, horizontalSpeed * limit, 0);
        app.getCamera().setLocation(offsetPosition);
        app.getCamera().getLocation().y = yPosition + bobbing;

        EntityManager.getCapsule().setLocalTranslation(app.getCamera().getLocation());
        EntityManager.getCapsule().getLocalTranslation().y -= standValue;

        float[] angles = quaternion.toAngles(null);
        Vector3f eulerAngles = new Vector3f(angles[0], angles[1], angles[2]);

        limit = (float) (Math.PI / 2);
        float eulerTarget = eulerAngles.x + input.mouseAxis[1];
        if (eulerTarget >= limit || eulerTarget <= -limit) input.mouseAxis[1] = 0;

        float circle = (float) (Math.PI * 2);

        if (targetX > Math.PI && eulerAngles.y < 0){
            targetX -= circle;
        } else if (targetX < -Math.PI && eulerAngles.y > 0){
            targetX += circle;
        }

        if (aiming) {
            targetX += input.mouseAxis[0];
        } else {
            targetX += input.mouseAxis[0] * 1.75f;
        }
        rotateXDifference = (targetX - eulerAngles.y) * tpf * 15;
        rotate(rotateXDifference, "x");

        targetY += input.mouseAxis[1];
        if (aiming) {
            targetY += input.mouseAxis[1];
        } else {
            targetY += input.mouseAxis[1] * 1.75f;
        }
        rotateYDifference = (targetY - eulerAngles.x) * tpf * 15;
        rotate(rotateYDifference, "y");

        app.getCamera().setRotation(quaternion);

        interact = input.isPressed("Interact");
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

    public float updateMoveSpeed(float moveSpeed, float multiplier, boolean positive, boolean negative, boolean diagonal, float tpf){
        multiplier *= tpf;
        float limit = 1;
        if (diagonal) limit = (float) (Math.sqrt(2) / 2);
        if (positive) {
            moveSpeed += 0.75f * multiplier;
            if (moveSpeed > limit) moveSpeed = limit;
        } else if (negative){
            moveSpeed -= 0.75f * multiplier;
            if (moveSpeed < -limit) moveSpeed = -limit;
        } else {
            if (moveSpeed > 0) {
                moveSpeed -= 0.75f * multiplier;
                if (moveSpeed < 0) moveSpeed = 0;
            } else if (moveSpeed < 0){
                moveSpeed += 0.75f * multiplier;
                if (moveSpeed > 0) moveSpeed = 0;
            }
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
