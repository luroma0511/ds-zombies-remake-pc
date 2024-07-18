package com.zombies.ds.game.game;

import com.zombies.ds.game.enemy.EnemyHandler;
import com.zombies.ds.game.model.Model;
import com.zombies.ds.game.player.Player;
import com.zombies.ds.game.utils.Constants;
import com.zombies.ds.game.weapons.buy.MysteryWardrobe;

public class Game {
    public final Player player;
    public final EnemyHandler enemyHandler;

    //Objects
    public final MysteryWardrobe mysteryWardrobe;

    public final Model afterlifeMachine;
    public final Model armorMachine;
    public final Model doubleTroubleMachine;
    public final Model speedySyringeMachine;

    public Game(){
        player = new Player();
        enemyHandler = new EnemyHandler();

        mysteryWardrobe = new MysteryWardrobe();

        afterlifeMachine = new Model();
        armorMachine = new Model();
        doubleTroubleMachine = new Model();
        speedySyringeMachine = new Model();
    }

    public void update(float tpf){
        float entityRadius = Constants.entityRadius;

        player.getAfterlifeSoda().interact(afterlifeMachine, player, entityRadius);
        player.getDoubleTroubleButterbeer().interact(doubleTroubleMachine, player, entityRadius);
        player.getSpeedySyringe().interact(speedySyringeMachine, player, entityRadius);
        player.getArmor().interact(armorMachine, player, entityRadius);

        player.update(tpf);
    }
}
