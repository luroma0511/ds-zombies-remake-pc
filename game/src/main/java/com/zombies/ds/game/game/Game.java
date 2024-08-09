package com.zombies.ds.game.game;

import com.jme3.math.Vector3f;
import com.zombies.ds.game.BlackOpsDsRemake;
import com.zombies.ds.game.enemy.EnemyHandler;
import com.zombies.ds.game.model.Model;
import com.zombies.ds.game.player.Player;
import com.zombies.ds.game.utils.Constants;
import com.zombies.ds.game.weapons.WeaponHandler;
import com.zombies.ds.game.weapons.buy.MysteryWardrobe;

public class Game {
    public final Player player;
    public final EnemyHandler enemyHandler;
    public boolean started;

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

    public void update(BlackOpsDsRemake app, float tpf){
        float entityRadius = Constants.entityRadius;

        if (!started){
            player.reset();
            app.getCamera().getLocation().x = 0;
            app.getCamera().getLocation().y = player.yPosition;
            app.getCamera().getLocation().z = 0;

            app.getCamera().setFov(7.5f);
            app.getCamera().setFrustumNear(0.125f);

            player.rotate(2, "x");
            player.targetX = 2;
            started = true;
        }

        if (player.primaryWeapon == null) {
            player.primaryWeapon = WeaponHandler.ppsh;
            player.primaryWeapon.init(app);
            player.primaryWeapon.attach(app);
            player.primaryWeapon.getModel().setLocalScale(2);
            player.primaryWeapon.getModel().setLocalRotation(player.primaryWeapon.getQuaternion());
        }

        float downValueDistance = 0.375f + 0.1775f * (player.aimValue - 1);

        float weaponDownValue = (1 - Math.min(3.25f, player.yPosition) / 3.25f) / 3;
        float bobWeapon = player.bobWeaponDifference * 0.375f;
        Vector3f offsetPosition = player.primaryWeapon.movePosition(app.getCamera().getLocation(), app.getCamera(),
                1f - (float) Math.cos(player.verticalSpeed) / 10,
                player.aimValue * (0.25f - (float) Math.sin(player.horizontalSpeed) / 7.5f - (bobWeapon)),
                downValueDistance + player.aimValue * (player.bobbing / 3 + Math.abs(player.verticalSpeed) / 8.5f + weaponDownValue));

        player.primaryWeapon.getModel().setLocalTranslation(offsetPosition);

        float[] angles = player.getQuaternion().toAngles(null);
        Vector3f playerEulerAngles = new Vector3f(angles[0], angles[1], angles[2]);

        angles = player.primaryWeapon.getQuaternion().toAngles(null);
        Vector3f weaponEulerAngles = new Vector3f(angles[0], angles[1], angles[2]);

        float aimMultiplier = 1 - 0.75f * (1 - player.aimValue);

        float rotationXValue = playerEulerAngles.y - weaponEulerAngles.y + player.rotateXDifference * aimMultiplier;
        float rotationYValue = weaponEulerAngles.x - playerEulerAngles.x - player.rotateYDifference * aimMultiplier;

        player.primaryWeapon.rotate(rotationXValue + player.bobValue, "x");
        player.primaryWeapon.rotate(-rotationYValue, "y");
        player.primaryWeapon.getModel().setLocalRotation(player.primaryWeapon.getQuaternion());

        app.getCamera().setFov(45 + 15 * player.aimValue);

//        player.getAfterlifeSoda().interact(afterlifeMachine, player, entityRadius);
//        player.getDoubleTroubleButterbeer().interact(doubleTroubleMachine, player, entityRadius);
//        player.getSpeedySyringe().interact(speedySyringeMachine, player, entityRadius);
//        player.getArmor().interact(armorMachine, player, entityRadius);

//        player.primaryWeapon.getModel().setLocalTranslation(player.getPosition());
//        player.primaryWeapon.getModel().getLocalRotation()

        player.update(tpf);
    }
}
