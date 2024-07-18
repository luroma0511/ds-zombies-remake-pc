package com.zombies.ds.game.perk;

public class AfterlifeSoda extends Perk {
    private int usages;
    private float cooldown;
    private int kills;
    private boolean savePerk;

    public void reset(){
        usages = 0;
        equipped = false;
        cooldown = 0;
        kills = 0;
        savePerk = false;
    }

    @Override
    public void update(float tpf){
        if (cooldown > 0){
            cooldown -= tpf;
            if (cooldown > 0) return;
            cooldown = 0;
            if (savePerk) equipped = false;
            savePerk = false;
        }
    }

    public void kill(){
        if (isActive()) kills++;
        if (kills >= 15) savePerk = true;
    }

    @Override
    public void equip(){
        usages++;
        equipped = true;
    }

    @Override
    public int cost(){
        return 500 + usages * 1000;
    }

    public void down(){
        cooldown = 10;
        kills = 0;
    }

    public boolean isActive(){
        return equipped && cooldown > 0;
    }

    public int getUsages() {
        return usages;
    }
}
