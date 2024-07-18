package com.zombies.ds.game.utils;

import com.zombies.ds.game.entity.Entity;

public class MathUtils {
    public static float distance(Entity entity1, Entity entity2, float radius){
        float distanceX = (float) Math.pow(entity2.getX() - entity1.getX(), 2);
        float distanceY = (float) Math.pow(entity2.getY() - entity1.getY(), 2);
        float distanceZ = (float) Math.pow(entity2.getZ() - entity1.getZ(), 2);
        return (float) Math.sqrt(distanceX + distanceY + distanceZ);
    }

    public static boolean isWithinDistance(Entity entity1, Entity entity2, float radius){
        float distance = distance(entity1, entity2, radius);
        return distance <= radius;
    }
}
