package net.ilexiconn.lifegauge.server;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy {
    public void preInit() {
        ServerEventHandler eventHandler = new ServerEventHandler();
        FMLCommonHandler.instance().bus().register(eventHandler);
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }
}
