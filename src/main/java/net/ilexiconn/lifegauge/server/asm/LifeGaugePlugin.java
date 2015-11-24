package net.ilexiconn.lifegauge.server.asm;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.Name("LifeGauge")
@IFMLLoadingPlugin.MCVersion("1.8.8")
public class LifeGaugePlugin implements IFMLLoadingPlugin {
    public static boolean isDeobfuscated;

    public String[] getASMTransformerClass() {
        return new String[] {LifeGaugeTransformer.class.getName()};
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
        isDeobfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    public String getAccessTransformerClass() {
        return null;
    }
}
