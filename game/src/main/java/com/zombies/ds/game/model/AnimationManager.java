package com.zombies.ds.game.model;

import com.jme3.anim.AnimComposer;
import com.jme3.scene.Spatial;

public class AnimationManager {

    public static void playAnimation(AnimComposer control, String animationName){
        control.setCurrentAction(animationName);
    }
}
