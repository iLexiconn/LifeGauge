package net.ilexiconn.lifegauge.server.config;

import net.ilexiconn.llibrary.common.config.IConfigHandler;
import net.minecraftforge.common.config.Configuration;

public class LifeGaugeConfig implements IConfigHandler {
    public static boolean enabled;

    public void loadConfig(Configuration config) {
        enabled = config.getBoolean("Enabled", Configuration.CATEGORY_GENERAL, true, "Enable or disable the mod. If the server disables the mod, it will be disabled on all clients connected to the server.");
    }
}
