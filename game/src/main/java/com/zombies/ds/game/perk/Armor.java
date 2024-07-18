package com.zombies.ds.game.perk;

public class Armor extends Perk {
    private int durability;

    public void reset(){
        durability = 0;
    }

    @Override
    public void equip(){
        durability = 50;
    }

    public void damage(){
        if (durability > 0) durability--;
    }

    @Override
    public int cost(){
        return durability * 50;
    }

    public int getDurability() {
        return durability;
    }
}
