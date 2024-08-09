package com.zombies.ds.game.model;

import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;
import com.zombies.ds.game.BlackOpsDsRemake;

public class EntityManager {
    private static Spatial capsule;

    public static void loadCapsule(BlackOpsDsRemake app){
        if (capsule != null) return;
        capsule = app.getAssetManager().loadModel("Models/Capsule/capsule.glb");
//        app.getRootNode().attachChild(capsule);
    }

    public static void loadKraftHaus(BlackOpsDsRemake app){
        loadModel(app, "Krafthaus");

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.05f));
        app.getRootNode().addLight(ambientLight);

// Add directional light for sunlight effect
//        DirectionalLight directionalLight = new DirectionalLight();
//        directionalLight.setDirection(new Vector3f(-50, -50, -50).normalizeLocal());
//        directionalLight.setColor(ColorRGBA.White.mult(0.05f));
//        app.getRootNode().addLight(directionalLight);
//
//        directionalLight = new DirectionalLight();
//        directionalLight.setDirection(new Vector3f(50, -50, 50).normalizeLocal());
//        directionalLight.setColor(ColorRGBA.White.mult(0.05f));
//        app.getRootNode().addLight(directionalLight);
//
//        directionalLight = new DirectionalLight();
//        directionalLight.setDirection(new Vector3f(-50, -50, 50).normalizeLocal());
//        directionalLight.setColor(ColorRGBA.White.mult(0.05f));
//        app.getRootNode().addLight(directionalLight);
//
//        directionalLight = new DirectionalLight();
//        directionalLight.setDirection(new Vector3f(50, -50, -50).normalizeLocal());
//        directionalLight.setColor(ColorRGBA.White.mult(0.05f));
//        app.getRootNode().addLight(directionalLight);
//
        PointLight pointLight = new PointLight();
        pointLight.setFrustumCheckNeeded(true);
        pointLight.setPosition(new Vector3f(0, 5, 0));
        pointLight.setColor(ColorRGBA.fromRGBA255(255, 236, 224, 255));
        pointLight.setRadius(20);
        app.getRootNode().addLight(pointLight);

        pointLight = new PointLight();
        pointLight.setFrustumCheckNeeded(true);
        pointLight.setPosition(new Vector3f(16, 4, -6));
        pointLight.setColor(ColorRGBA.fromRGBA255(255, 236, 224, 255));
        pointLight.setRadius(20);
        app.getRootNode().addLight(pointLight);
    }

    private static void loadModel(BlackOpsDsRemake app, String modelName){
        var model = app.getAssetManager().loadModel("Models/" + modelName + ".obj");
        TangentBinormalGenerator.generate(model);
        app.getRootNode().attachChild(model);
    }

    public static Spatial getCapsule() {
        return capsule;
    }
}
