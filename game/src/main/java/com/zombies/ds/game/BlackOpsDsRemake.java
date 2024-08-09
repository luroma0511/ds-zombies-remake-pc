package com.zombies.ds.game;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.app.state.AppState;
import com.zombies.ds.game.input.Input;
import com.zombies.ds.game.model.EntityManager;
import com.zombies.ds.game.states.AppManager;
import com.zombies.ds.game.weapons.Weapon;
import com.zombies.ds.game.weapons.WeaponHandler;

/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class BlackOpsDsRemake extends SimpleApplication {
    private Input input;
    private AppManager appManager;
    private float tpf;

    public BlackOpsDsRemake() {
    }

    public BlackOpsDsRemake(AppState... initialStates) {
        super(initialStates);
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        input = new Input(this);
        appManager = new AppManager(this);

        assetManager.registerLocator("./assets", FileLocator.class);

        EntityManager.loadKraftHaus(this);
        EntityManager.loadCapsule(this);

        WeaponHandler.reset();
    }

    @Override
    public void simpleUpdate(float tpf) {
        this.tpf = tpf;
        appManager.change();
        input.update();
        appManager.update();
        input.mouseAxis[0] = 0;
        input.mouseAxis[1] = 0;
    }

    public AppManager getAppManager() {
        return appManager;
    }

    public Input getInput() {
        return input;
    }

    public float getTpf() {
        return tpf;
    }
}
