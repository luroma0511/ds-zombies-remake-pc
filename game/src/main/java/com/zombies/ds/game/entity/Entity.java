package com.zombies.ds.game.entity;

import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

public abstract class Entity {
    protected final Quaternion quaternion = new Quaternion();
    protected final Vector3f initialUp = quaternion.getRotationColumn(1).clone();

    public Vector3f movePosition(Vector3f position, Camera camera, float directionValue, float rightValue, float downValue){
        Vector3f cameraDirection = camera.getDirection();
        Vector3f cameraRight = camera.getLeft().negate();
        Vector3f cameraDown = camera.getUp().negate();

        return position
                .add(cameraDirection.mult(directionValue))
                .add(cameraRight.mult(rightValue))
                .add(cameraDown.mult(downValue));
    }

    public void rotate(float value, String xyz) {
        Vector3f axis;
        if (xyz.equals("x")) axis = initialUp;
        else if (xyz.equals("y")) axis = quaternion.getRotationColumn(0);
        else axis = quaternion.getRotationColumn(2);

        Matrix3f mat = new Matrix3f();
        mat.fromAngleNormalAxis(value, axis);
        Vector3f up = quaternion.getRotationColumn(1);
        Vector3f left = quaternion.getRotationColumn(0);
        Vector3f dir = quaternion.getRotationColumn(2);
        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);
        quaternion.fromAxes(left, up, dir);
        quaternion.normalizeLocal();
    }

    public Quaternion getQuaternion() {
        return quaternion;
    }
}
