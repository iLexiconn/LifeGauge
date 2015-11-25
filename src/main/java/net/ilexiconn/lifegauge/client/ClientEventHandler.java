package net.ilexiconn.lifegauge.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.lifegauge.api.LifeGaugeAPI;
import net.ilexiconn.lifegauge.client.render.LifeGaugeRenderer;
import net.ilexiconn.lifegauge.client.render.NameTagRenderer;
import net.ilexiconn.lifegauge.server.config.LifeGaugeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
    public Minecraft mc = Minecraft.getMinecraft();
    public NameTagRenderer nameTagRenderer = new NameTagRenderer();
    public LifeGaugeRenderer lifeGaugeRenderer = new LifeGaugeRenderer();

    public EntityLiving lastEntity;
    public int offsetGoal = 0;
    public float offset = 0f;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        offset = AnimationHandler.smoothUpdate(offset, offsetGoal);
        AnimationHandler.tick();
    }

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
        FontRenderer fontRenderer = mc.fontRenderer;
        if (LifeGaugeConfig.enabled && Minecraft.isGuiEnabled()) {
            if (mc.pointedEntity != null && mc.pointedEntity instanceof EntityLiving) {
                lastEntity = (EntityLiving) mc.pointedEntity;
                offsetGoal = 0;
            } else if (offsetGoal == 0 && lastEntity != null) {
                Potion[] potionArray = LifeGaugeAPI.getPotionsForEntity(lastEntity);
                int nameWidth = fontRenderer.getStringWidth(lastEntity.getCommandSenderName());
                int healthWidth = fontRenderer.getStringWidth("x" + lastEntity.getMaxHealth() / 2) + 12;
                offsetGoal = Math.max(nameWidth, healthWidth) + potionArray.length * 18 + 64;
            }

            if (lastEntity != null) {
                if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
                    lifeGaugeRenderer.renderLifeGauge(mc, fontRenderer, lastEntity, (int) -offset, 0);
                } else if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
                    lifeGaugeRenderer.renderLifeGaugeText(lastEntity, (int) -offset, 0);
                }
            }
        }
    }
}
