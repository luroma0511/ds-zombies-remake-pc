package com.zombies.ds.game.states;

import com.zombies.ds.game.BlackOpsDsRemake;
import com.zombies.ds.game.game.Game;

public class AppManager {
    BlackOpsDsRemake app;
    String state = "Game";
    String prevState = "Game";
    private final Game game;

    public AppManager(BlackOpsDsRemake app) {
        this.app = app;
        game = new Game();
    }

    public void change(){
        if (prevState.equals(state)) return;
        prevState = state;
    }

    public void update(){
        if (state.equals("Game")) {
            app.getCamera().setLocation(app.getAppManager().getGame().player.getVector());
            game.update(app.getTpf());
        }
    }

    public String getState() {
        return state;
    }

    public Game getGame() {
        return game;
    }
}
