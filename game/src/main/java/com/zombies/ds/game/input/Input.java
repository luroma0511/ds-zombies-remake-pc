package com.zombies.ds.game.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.*;
import com.zombies.ds.game.BlackOpsDsRemake;
import com.zombies.ds.game.player.Player;

import java.util.*;

public class Input implements ActionListener, AnalogListener {
    private final BlackOpsDsRemake app;
    private final InputManager inputManager;
    private final Set<String> pressedKeys;

    public Input(BlackOpsDsRemake app){
        this.app = app;
        inputManager = app.getInputManager();
        pressedKeys = new HashSet<>();
        initialize();
    }

    private void initialize(){
        Stack<String> mapping = new Stack<>();

        addKeyMapping(mapping, "Forward", KeyInput.KEY_W);
        addKeyMapping(mapping, "Backward", KeyInput.KEY_S);
        addKeyMapping(mapping, "Strafe Left", KeyInput.KEY_A);
        addKeyMapping(mapping, "Strafe Right", KeyInput.KEY_D);
        addKeyMapping(mapping, "Up", KeyInput.KEY_UP);
        addKeyMapping(mapping, "Down", KeyInput.KEY_DOWN);
        addKeyMapping(mapping, "Left", KeyInput.KEY_LEFT);
        addKeyMapping(mapping, "Right", KeyInput.KEY_RIGHT);
        addKeyMapping(mapping, "Interact", KeyInput.KEY_F);

        addMouseAxisMapping(mapping, "Rotate Left", MouseInput.AXIS_X, true);
        addMouseAxisMapping(mapping, "Rotate Right", MouseInput.AXIS_X, false);
        addMouseAxisMapping(mapping, "Rotate Up", MouseInput.AXIS_Y, false);
        addMouseAxisMapping(mapping, "Rotate Down", MouseInput.AXIS_Y, true);

        addClickMapping(mapping, "Button Left", MouseInput.BUTTON_LEFT);
        addClickMapping(mapping, "Button Right", MouseInput.BUTTON_RIGHT);

        mapping.forEach(keys -> inputManager.addListener(this, keys));
    }

    public void update(){
        float tpf = app.getTpf();
        Player player = app.getAppManager().getGame().player;

        if (player != null && app.getAppManager().getState().equals("Game")) {
            boolean forward = isPressed("Forward");
            player.upSpeed = player.updateMoveSpeed(player.upSpeed, forward, tpf);

            boolean backward = isPressed("Backward");
            player.downSpeed = player.updateMoveSpeed(player.downSpeed, backward, tpf);

            boolean strafeLeft = isPressed("Strafe Left");
            player.leftSpeed = player.updateMoveSpeed(player.leftSpeed, strafeLeft, tpf);

            boolean strafeRight = isPressed("Strafe Right");
            player.rightSpeed = player.updateMoveSpeed(player.rightSpeed, strafeRight, tpf);

            player.setCoords(player.getX() + (player.rightSpeed - player.leftSpeed) * 0.05f, 0, player.getZ() + (player.downSpeed - player.upSpeed) * 0.05f);



            player.setInteract(isPressed("Interact"));
        }
    }

    public void updateKeyMapping(String name, int key){
        inputManager.deleteMapping(name);
        addKeyMapping(name, key);
        inputManager.addListener(this, name);
    }

    public void updateClickMapping(String name, int key){
        inputManager.deleteMapping(name);
        addClickMapping(name, key);
        inputManager.addListener(this, name);
    }

    private void addKeyMapping(String name, int key){
        inputManager.addMapping(name, new KeyTrigger(key));
    }

    private void addKeyMapping(Stack<String> mapping, String name, int key){
        addKeyMapping(name, key);
        mapping.add(name);
    }

    private void addClickMapping(String name, int key){
        inputManager.addMapping(name, new MouseButtonTrigger(key));
    }

    private void addClickMapping(Stack<String> mapping, String name, int key){
        addClickMapping(name, key);
        mapping.add(name);
    }

    private void addMouseAxisMapping(Stack<String> mapping, String name, int axis, boolean invert){
        inputManager.addMapping(name, new MouseAxisTrigger(axis, invert));
        mapping.add(name);
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
//        System.out.println(name);
    }

    @Override
    public void onAction(String name, boolean pressed, float tpf) {
        if (pressed) pressedKeys.add(name);
        else pressedKeys.remove(name);
    }

    public boolean isPressed(String key){
        return pressedKeys.contains(key);
    }
}
