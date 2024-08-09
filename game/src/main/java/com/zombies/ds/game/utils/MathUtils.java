package com.zombies.ds.game.utils;

import com.jme3.math.Vector3f;
import com.zombies.ds.game.entity.Entity;

public class MathUtils {
    public static float distance(Vector3f entityV1, Vector3f entityV2){
        float distanceX = (float) Math.pow(entityV2.x - entityV1.x, 2);
        float distanceY = (float) Math.pow(entityV2.y - entityV1.y, 2);
        float distanceZ = (float) Math.pow(entityV2.z - entityV1.z, 2);
        return (float) Math.sqrt(distanceX + distanceY + distanceZ);
    }

    public static boolean isWithinDistance(Vector3f entityV1, Vector3f entityV2, float radius){
        float distance = distance(entityV1, entityV2);
        return distance <= radius;
    }
}
