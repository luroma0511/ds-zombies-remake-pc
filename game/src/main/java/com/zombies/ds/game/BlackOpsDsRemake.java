package com.zombies.ds.game;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.app.state.AppState;
import com.zombies.ds.game.input.Input;
import com.zombies.ds.game.states.AppManager;

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
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        Box floor = new Box(4, 0, 4);
        Geometry floorGeom = new Geometry("Box", floor);
        floorGeom.setLocalTranslation(0, -1, 0);
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        mat.setColor("Color", ColorRGBA.Red);
        floorGeom.setMaterial(mat);

        rootNode.attachChild(geom);
        rootNode.attachChild(floorGeom);
        flyCam.setEnabled(false);

        input = new Input(this);
        appManager = new AppManager(this);
    }

    @Override
    public void simpleUpdate(float tpf) {
        this.tpf = tpf;
        appManager.change();
        input.update();
        appManager.update();
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
