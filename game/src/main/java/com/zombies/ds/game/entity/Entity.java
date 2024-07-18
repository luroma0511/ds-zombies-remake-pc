package com.zombies.ds.game.entity;

import com.jme3.math.Vector3f;

public abstract class Entity {
    private final Vector3f vector;

    protected Entity() {
        vector = new Vector3f();
    }

    public Vector3f getVector() {
        return vector;
    }

    public void setCoords(float x, float y, float z) {
        vector.set(x, y, z);
    }

    public float getX() {
        return vector.x;
    }

    public void setX(int x) {
        vector.x = x;
    }

    public float getY() {
        return vector.y;
    }

    public void setY(int y) {
        vector.y = y;
    }

    public float getZ() {
        return vector.z;
    }

    public void setZ(int z) {
        vector.z = z;
    }
}
