package net.ilexiconn.lifegauge.server.config;

import net.ilexiconn.llibrary.common.config.IConfigHandler;
import net.minecraftforge.common.config.Configuration;

public class LifeGaugeConfig implements IConfigHandler {
    public static boolean enabled;
    public static int renderDistance;

    public void loadConfig(Configuration config) {
        enabled = config.getBoolean("Enabled", Configuration.CATEGORY_GENERAL, true, "Enable or disable the mod. If the server disables the mod, it will be disabled on all clients connected to the server.");
        renderDistance = config.getInt("Render distance", Configuration.CATEGORY_GENERAL, -1, -1, 64, "Set the max render distance for the special name tags in blocks. -1 means it gets rendered at every distance. Will not stop the default name tag rendering if the distance is shorter than the default name t5ag distance.");
    }
}
