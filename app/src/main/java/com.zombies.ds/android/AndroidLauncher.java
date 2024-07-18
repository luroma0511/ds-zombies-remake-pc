package com.zombies.ds.android;

import com.jme3.app.AndroidHarness;
import com.zombies.ds.game.BlackOpsDsRemake;


public class AndroidLauncher extends AndroidHarness {

    public AndroidLauncher() {
        appClass = BlackOpsDsRemake.class.getCanonicalName();
    }
}
