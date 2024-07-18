package com.zombies.ds.game.enemy;

public class ZombieHandler {
    private final Zombie[] zombies;
    private float spawnRate;
    private int currentArrayIndex;

    public ZombieHandler(){
        zombies = new Zombie[128];
    }

    public void update(float tpf){
        spawnRate -= tpf;
        if (spawnRate < 0) spawnRate = 0;
        for (Zombie zombie: zombies) {
            if (zombie == null) continue;
            if (zombie.active){

            } else {
                zombie.disposeCooldown -= tpf;
                if (zombie.disposeCooldown > 0) zombie.disposeCooldown = 0;
            }
        }
    }

    public boolean add(){
        if (spawnRate > 0) return false;
        if (zombies[currentArrayIndex] == null) zombies[currentArrayIndex] = new Zombie();
        zombies[currentArrayIndex].reset();
        currentArrayIndex++;
        if (currentArrayIndex >= zombies.length) currentArrayIndex = 0;
        spawnRate = 1;
        return true;
    }

    public void dispose(int enemyIndex){
        zombies[enemyIndex].setActive(false);
    }
}
