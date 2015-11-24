package net.ilexiconn.lifegauge.client;

import net.ilexiconn.lifegauge.server.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy {
    public void preInit() {
        super.preInit();

        ClientEventHandler eventHandler = new ClientEventHandler();
        FMLCommonHandler.instance().bus().register(eventHandler);
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }
}
