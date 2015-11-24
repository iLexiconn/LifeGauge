package net.ilexiconn.lifegauge.server;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerProxy {
    public void preInit() {
        ServerEventHandler eventHandler = new ServerEventHandler();
        FMLCommonHandler.instance().bus().register(eventHandler);
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }
}
