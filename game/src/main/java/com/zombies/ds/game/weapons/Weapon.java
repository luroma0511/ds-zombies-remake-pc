package com.zombies.ds.game.weapons;

import com.jme3.anim.AnimComposer;
import com.jme3.bullet.animation.DynamicAnimControl;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.anim.Animation;
import com.zombies.ds.game.BlackOpsDsRemake;
import com.zombies.ds.game.entity.Entity;

public class Weapon extends Entity {
    private Spatial model;
    private AnimComposer control;

    public final Attachment attachment = new Attachment();
    public final String name;
    public final String enchantedName;
    public boolean enchanted;
    public final float adsSeconds;
    public final int damage;
    public final float fireRate;
    public final int maxAmmo;
    public final int maxClip;
    public float fireDelay;
    public int ammo;
    public int clip;
    public int level;

    public Weapon(String name, String enchantedName, int damage, float fireRate, float adsSeconds, int maxAmmo, int maxClip){
        this.name = name;
        this.enchantedName = enchantedName;
        this.damage = damage;
        this.fireRate = fireRate;
        this.maxAmmo = maxAmmo;
        this.maxClip = maxClip;
        this.adsSeconds = adsSeconds;
    }

    public void init(BlackOpsDsRemake app){
        model = app.getAssetManager().loadModel("Models/Weapons/" + name + ".obj");
        control = model.getControl(AnimComposer.class);
    }

    public void attach(BlackOpsDsRemake app){
        app.getRootNode().attachChild(model);
    }

    public void detach(BlackOpsDsRemake app){
        app.getRootNode().detachChild(model);
    }

    public void reset(){
        ammo = maxAmmo;
        clip = maxClip;
        fireDelay = 0;
        level = 1;
    }

    public void resetAmmo(){
        if (attachment.isDrumMag() && attachment.isDrumMagEquipped()){
            ammo = attachment.getMaxAmmo();
            clip = attachment.getMaxClip();
        } else {
            ammo = maxAmmo;
            clip = maxClip;
        }
    }

    public void reloadAmmo(){
        if (attachment.isDrumMag() && attachment.isDrumMagEquipped()) {
            remainderAmmo(attachment.getMaxClip());
        } else {
            remainderAmmo(maxClip);
        }
    }

    private void remainderAmmo(int maxClip){
        int remainder = maxClip - clip;
        if (remainder == 0 || ammo == 0) return;
        if (ammo < remainder) {
            clip += ammo;
            ammo = 0;
        } else {
            ammo -= remainder;
            clip += remainder;
        }
    }

    public Spatial getModel() {
        return model;
    }
}
