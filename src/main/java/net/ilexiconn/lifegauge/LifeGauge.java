package net.ilexiconn.lifegauge;

import net.ilexiconn.lifegauge.server.ServerProxy;
import net.ilexiconn.lifegauge.server.config.LifeGaugeConfig;
import net.ilexiconn.lifegauge.server.message.MessageEnable;
import net.ilexiconn.lifegauge.server.message.MessageUpdatePotions;
import net.ilexiconn.llibrary.common.config.ConfigHelper;
import net.ilexiconn.llibrary.common.message.AbstractMessage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "lifegauge", name = "Life Gauge", version = LifeGauge.VERSION, dependencies = "required-after:llibrary@[0.6.0,)")
public class LifeGauge {
    public static final String VERSION = "0.1.3";
    @SidedProxy(serverSide = "net.ilexiconn.lifegauge.server.ServerProxy", clientSide = "net.ilexiconn.lifegauge.client.ClientProxy")
    public static ServerProxy proxy;
    public static SimpleNetworkWrapper networkWrapper;

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
