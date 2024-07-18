package com.zombies.ds.game.weapons;

public class Attachment {
    private boolean grenade;
    private boolean scope;
    private boolean drumMag;

    private boolean grenadeEquipped;
    private boolean scopeEquipped;
    private boolean drumMagEquipped;

    private int grenadeAmmo;
    private int grenadeClip;
    private int maxAmmo;
    private int maxClip;

    public void reset(){
        grenadeEquipped = false;
        scopeEquipped = false;
        drumMagEquipped = false;
    }

    public void setAttachments(boolean grenade, boolean scope, boolean drumMag) {
        this.grenade = grenade;
        this.scope = scope;
        this.drumMag = drumMag;
    }

    public void setAmmo(int maxAmmo, int maxClip){
        this.maxAmmo = maxAmmo;
        this.maxClip = maxClip;
    }

    public boolean isGrenade() {
        return grenade;
    }

    public boolean isScope() {
        return scope;
    }

    public boolean isDrumMag() {
        return drumMag;
    }

    public boolean isGrenadeEquipped() {
        return grenadeEquipped;
    }

    public void setGrenadeEquipped(boolean grenadeEquipped) {
        this.grenadeEquipped = grenadeEquipped;
    }

    public boolean isScopeEquipped() {
        return scopeEquipped;
    }

    public void setScopeEquipped(boolean scopeEquipped) {
        this.scopeEquipped = scopeEquipped;
    }

    public boolean isDrumMagEquipped() {
        return drumMagEquipped;
    }

    public void setDrumMagEquipped(boolean drumMagEquipped) {
        this.drumMagEquipped = drumMagEquipped;
    }

    public int getGrenadeAmmo() {
        return grenadeAmmo;
    }

    public int getGrenadeClip() {
        return grenadeClip;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public int getMaxClip() {
        return maxClip;
    }
}
