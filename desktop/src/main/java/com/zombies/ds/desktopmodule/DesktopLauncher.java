package com.zombies.ds.desktopmodule;

import com.zombies.ds.game.BlackOpsDsRemake;
import com.jme3.system.AppSettings;

import java.awt.*;

/**
 * Used to launch a jme application in desktop environment
 *
 */
public class DesktopLauncher {
    public static void main(String[] args) {
        final AppSettings appSettings = new AppSettings(true);
        appSettings.setWindowSize(1920, 1080);
        appSettings.setFrameRate(60);
        appSettings.setResizable(true);
        appSettings.setVSync(true);
        appSettings.setTitle("Black Ops DS Remake");
        appSettings.setCenterWindow(true);
        appSettings.setGammaCorrection(false);

        final BlackOpsDsRemake game = new BlackOpsDsRemake();
        game.setSettings(appSettings);
        game.setShowSettings(false); //Settings dialog not supported on mac
        game.start();
    }
}
