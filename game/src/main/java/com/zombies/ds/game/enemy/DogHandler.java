package com.zombies.ds.game.enemy;

public class DogHandler {
    private final Dog[] dogs;
    private float spawnRate;
    private int currentArrayIndex;

    public DogHandler() {
        dogs = new Dog[64];
    }

    public void update(float tpf){
        spawnRate -= tpf;
        if (spawnRate < 0) spawnRate = 0;
        for (Dog dog: dogs) {
            if (dog == null) continue;
            if (dog.active){

            } else {
                dog.disposeCooldown -= tpf;
                if (dog.disposeCooldown > 0) dog.disposeCooldown = 0;
            }
        }
    }

    public boolean add(){
        if (spawnRate > 0) return false;
        if (dogs[currentArrayIndex] == null) dogs[currentArrayIndex] = new Dog();
        dogs[currentArrayIndex].reset();
        currentArrayIndex++;
        if (currentArrayIndex >= dogs.length) currentArrayIndex = 0;
        spawnRate = 1;
        return true;
    }

    public void dispose(int enemyIndex){
        dogs[enemyIndex].setActive(false);
    }
}
