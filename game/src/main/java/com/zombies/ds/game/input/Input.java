package com.zombies.ds.game.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.*;
import com.jme3.input.event.*;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.zombies.ds.game.BlackOpsDsRemake;
import com.zombies.ds.game.player.Player;

import java.util.*;

public class Input implements ActionListener {
    private final BlackOpsDsRemake app;
    private final InputManager inputManager;
    private final Map<String, Boolean> pressedKeys;
    public final float[] mouseAxis = new float[]{0, 0};
    private final Vector3f initialCameraUp;

    public Input(BlackOpsDsRemake app){
        this.app = app;
        inputManager = app.getInputManager();
        inputManager.addRawInputListener(new RawInputListener() {
            @Override
            public void beginInput() {

            }

            @Override
            public void endInput() {

            }

            @Override
            public void onJoyAxisEvent(JoyAxisEvent joyAxisEvent) {

            }

            @Override
            public void onJoyButtonEvent(JoyButtonEvent joyButtonEvent) {

            }

            @Override
            public void onMouseMotionEvent(MouseMotionEvent mouseMotionEvent) {
                float dx = (float) mouseMotionEvent.getDX() / 75;
                float dy = (float) mouseMotionEvent.getDY() / 75;

                mouseAxis[0] = -dx;
                mouseAxis[1] = -dy;
            }

            @Override
            public void onMouseButtonEvent(MouseButtonEvent mouseButtonEvent) {

            }

            @Override
            public void onKeyEvent(KeyInputEvent keyInputEvent) {

            }

            @Override
            public void onTouchEvent(TouchEvent touchEvent) {

            }
        });
        initialCameraUp = app.getCamera().getUp().clone();
        pressedKeys = new HashMap<>();
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
        addKeyMapping(mapping, "Interact", KeyInput.KEY_E);
        addKeyMapping(mapping, "Crouch", KeyInput.KEY_LCONTROL);
        addKeyMapping(mapping, "Run", KeyInput.KEY_LSHIFT);
        addKeyMapping(mapping, "Melee", KeyInput.KEY_F);

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
            player.input(app, tpf);
        }
        pressedKeys.replaceAll((k, v) -> true);
    }

//    public void rotateCamera(float value, String xyz) {
//        Vector3f axis;
//        if (xyz.equals("x")) axis = initialCameraUp;
//        else axis = app.getCamera().getLeft();
//
//        Matrix3f mat = new Matrix3f();
//        mat.fromAngleNormalAxis(value, axis);
//        Vector3f up = app.getCamera().getUp();
//        Vector3f left = app.getCamera().getLeft();
//        Vector3f dir = app.getCamera().getDirection();
//        mat.mult(up, up);
//        mat.mult(left, left);
//        mat.mult(dir, dir);
//        Quaternion q = app.getCamera().getRotation();
//        q.fromAxes(left, up, dir);
//        q.normalizeLocal();
//    }

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
    public void onAction(String name, boolean pressed, float tpf) {
        if (pressed) pressedKeys.put(name, false);
        else pressedKeys.remove(name);
    }

    public boolean isJustPressed(String key){
        return isPressed(key) && !pressedKeys.get(key);
    }

    public boolean isPressed(String key){
        return pressedKeys.containsKey(key);
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}
