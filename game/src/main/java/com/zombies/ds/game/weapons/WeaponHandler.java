package com.zombies.ds.game.weapons;

public class WeaponHandler {
    public static final Weapon ppsh = new Weapon("PPSh-41", "The Grinder", 75, 0.05f, 0.15f, 210, 35);
    public static final Weapon m16 = new Weapon("M16A1", "The [M16]", 65, 0.15f, 0.375f, 180, 30);
    public static final Weapon thompson = new Weapon("Thompson", "The Impaler", 50, 0.125f, 0.25f, 200, 20);

    Weapon rayGun = new Weapon("Ray Gun", "Porter 2x Ray Gun", 300, 0.35f, 0.25f, 200, 20);
    Weapon wunderwaffe = new Weapon("Wunderwaffe DG-2", "", 2147483643, 1, 0.375f, 18, 3);

    public static void reset() {
        ppsh.attachment.setAttachments(false, false, true);
        ppsh.attachment.setAmmo(312, 52);

        m16.attachment.setAttachments(true, true, false);

        thompson.attachment.setAttachments(false, false, true);
        thompson.attachment.setAmmo(250, 50);
    }
}
