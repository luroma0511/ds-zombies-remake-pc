package com.zombies.ds.game.functional;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.zombies.ds.game.entity.Entity;
import com.zombies.ds.game.player.Player;
import com.zombies.ds.game.utils.MathUtils;

public class Functions {
    public static boolean interact(Vector3f machineVector, Camera camera, Player player, float entityRadius){
        return MathUtils.isWithinDistance(camera.getLocation(), machineVector, entityRadius)
                && player.isInteract() && player.getDelayInteract() == 0.5f;
    }
}
