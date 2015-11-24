package net.ilexiconn.lifegauge.client;

import net.ilexiconn.lifegauge.client.render.LifeGaugeRenderer;
import net.ilexiconn.lifegauge.client.render.NameTagRenderer;
import net.ilexiconn.lifegauge.server.config.LifeGaugeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
    public Minecraft mc = Minecraft.getMinecraft();
    public NameTagRenderer nameTagRenderer = new NameTagRenderer();
    public LifeGaugeRenderer lifeGaugeRenderer = new LifeGaugeRenderer();

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedOutEvent event) {
        if (!LifeGaugeConfig.enabled && event.player.worldObj.isRemote) {
            LifeGaugeConfig.enabled = true;
        }
    }

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Specials.Pre event) {
        if (LifeGaugeConfig.enabled && Minecraft.isGuiEnabled()) {
            if (event.entity instanceof EntityLiving) {
                event.setCanceled(true);
                nameTagRenderer.renderNameTag((EntityLiving) event.entity, (float) event.x, (float) event.y, (float) event.z);
            }
        }
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (LifeGaugeConfig.enabled && Minecraft.isGuiEnabled()) {
            if (mc.pointedEntity != null && mc.pointedEntity instanceof EntityLiving) {
                if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
                    lifeGaugeRenderer.renderLifeGauge((EntityLiving) mc.pointedEntity, 0, 0);
                } else if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
                    lifeGaugeRenderer.renderLifeGaugeText((EntityLiving) mc.pointedEntity, 0, 0);
                }
            }
        }
    }
}
