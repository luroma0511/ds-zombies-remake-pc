package com.zombies.ds.game.test;

import com.zombies.ds.game.enemy.EnemyHandler;

public class Test {
    public static void main(String[] args) {
        EnemyHandler enemyHandler = new EnemyHandler();
        for (int round = 1; round <= 50; round++) {
            enemyHandler.setEnemiesLeft(round);
            System.out.println(enemyHandler.getEnemiesLeft());
        }
    }
}
