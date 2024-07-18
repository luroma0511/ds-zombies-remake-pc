package com.zombies.ds.game.functional;

import com.zombies.ds.game.entity.Entity;
import com.zombies.ds.game.player.Player;
import com.zombies.ds.game.utils.MathUtils;

public class Functions {
    public static boolean interact(Entity entity, Player player, float entityRadius){
        return MathUtils.isWithinDistance(player, entity, entityRadius)
                && player.isInteract() && player.getDelayInteract() == 0.5f;
    }
}
