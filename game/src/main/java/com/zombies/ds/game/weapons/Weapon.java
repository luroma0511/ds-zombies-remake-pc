package com.zombies.ds.game.weapons;

public class Weapon {
    public final Attachment attachment = new Attachment();
    public final String name;
    public final String enchantedName;
    public boolean enchanted;
    public final int damage;
    public final float fireRate;
    public final int maxAmmo;
    public final int maxClip;
    public float fireDelay;
    public int ammo;
    public int clip;
    public int level;

    public Weapon(String name, String enchantedName, int damage, float fireRate, int maxAmmo, int maxClip){
        this.name = name;
        this.enchantedName = enchantedName;
        this.damage = damage;
        this.fireRate = fireRate;
        this.maxAmmo = maxAmmo;
        this.maxClip = maxClip;
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
}
