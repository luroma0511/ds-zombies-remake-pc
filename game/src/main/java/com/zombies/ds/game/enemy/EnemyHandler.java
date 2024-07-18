package com.zombies.ds.game.enemy;

public class EnemyHandler {
    private final ZombieHandler zombieHandler;
    private final DogHandler dogHandler;
    private int enemyLimit;
    private int enemies;
    private int enemiesLeft;

    public EnemyHandler(){
        zombieHandler = new ZombieHandler();
        dogHandler = new DogHandler();
    }

    public void reset(int enemyLimit){
        this.enemyLimit = enemyLimit;
    }

    public void update(float tpf){
        zombieHandler.update(tpf);
        dogHandler.update(tpf);
        boolean canAdd = enemiesLeft > 0 && enemies < enemyLimit;
        if (canAdd && zombieHandler.add()) {
            enemiesLeft--;
            enemies++;
        }
        canAdd = enemiesLeft > 0 && enemies < enemyLimit;
        if (canAdd && dogHandler.add()) {
            enemiesLeft--;
            enemies++;
        }
    }

    public void disposeDog(int enemyIndex){
        dogHandler.dispose(enemyIndex);
        enemies--;
    }

    public void disposeZombie(int enemyIndex){
        zombieHandler.dispose(enemyIndex);
        enemies--;
    }

    public boolean roundOver() {
        return enemiesLeft == 0 && enemies == 0;
    }

    public int getEnemiesLeft() {
        return enemiesLeft;
    }

    public void setEnemiesLeft(int round){
        enemiesLeft = 0;
        int extra10 = extraMultiplier(round, 0, 2);
        int extra20 = extraMultiplier(round, 1, 3);
        int extra30 = extraMultiplier(round, 2, 4);
        int extra40 = extraMultiplier(round, 3, 5);
        int extra50 = extraMultiplier(round, 4, 6);
        enemiesLeft = 6 + extra10 + extra20 + extra30 + extra40 + extra50;
    }

    private int extraMultiplier(int round, int offset, int multiplier){
        round -= offset * 10;
        if (round > 10) round = 10;
        if (offset > 0) round++;
        if (round <= 0) return 0;
        return (round - 1 % 10) * multiplier;
    }
}
