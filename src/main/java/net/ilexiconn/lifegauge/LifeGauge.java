package net.ilexiconn.lifegauge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.ilexiconn.lifegauge.server.ServerProxy;
import net.ilexiconn.lifegauge.server.config.LifeGaugeConfig;
import net.ilexiconn.lifegauge.server.message.MessageEnable;
import net.ilexiconn.lifegauge.server.message.MessageUpdatePotions;
import net.ilexiconn.llibrary.common.config.ConfigHelper;
import net.ilexiconn.llibrary.common.message.AbstractMessage;
import net.minecraft.client.gui.Gui;

@Mod(modid = "lifegauge", name = "Life Gauge", version = LifeGauge.VERSION, dependencies = "required-after:llibrary@[0.6.0,)")
public class LifeGauge extends Gui {
    @SidedProxy(serverSide = "net.ilexiconn.lifegauge.server.ServerProxy", clientSide = "net.ilexiconn.lifegauge.client.ClientProxy")
    public static ServerProxy proxy;
    public static SimpleNetworkWrapper networkWrapper;

    public static final String VERSION = "0.1.3";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHelper.registerConfigHandler("lifegauge", event.getSuggestedConfigurationFile(), new LifeGaugeConfig());
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("lifegauge");
        AbstractMessage.registerMessage(networkWrapper, MessageEnable.class, 0, Side.CLIENT);
        AbstractMessage.registerMessage(networkWrapper, MessageUpdatePotions.class, 1, Side.CLIENT);

        FMLInterModComms.sendMessage("llibrary", "update-checker", "https://github.com/iLexiconn/LifeGauge/raw/version/versions.json");

        proxy.preInit();
    }
}
