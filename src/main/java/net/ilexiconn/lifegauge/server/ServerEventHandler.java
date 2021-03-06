package net.ilexiconn.lifegauge.server;

import net.ilexiconn.lifegauge.LifeGauge;
import net.ilexiconn.lifegauge.server.config.LifeGaugeConfig;
import net.ilexiconn.lifegauge.server.event.PotionEffectEvent;
import net.ilexiconn.lifegauge.server.message.MessageEnable;
import net.ilexiconn.lifegauge.server.message.MessageUpdatePotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ServerEventHandler {
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.worldObj.isRemote && event.player instanceof EntityPlayerMP) {
            LifeGauge.networkWrapper.sendTo(new MessageEnable(LifeGaugeConfig.enabled), (EntityPlayerMP) event.player);
            for (Entity entity : event.player.worldObj.getLoadedEntityList()) {
                if (entity instanceof EntityLiving) {
                    EntityLiving living = (EntityLiving) entity;
                    for (PotionEffect potion : living.getActivePotionEffects()) {
                        LifeGauge.networkWrapper.sendTo(new MessageUpdatePotions(living.getEntityId(), potion.getPotionID(), true), (EntityPlayerMP) event.player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPotionStart(PotionEffectEvent.PotionEffectStartEvent event) {
        LifeGauge.networkWrapper.sendToAll(new MessageUpdatePotions(event.entity.getEntityId(), event.potion.getPotionID(), true));
    }

    @SubscribeEvent
    public void onPotionFinish(PotionEffectEvent.PotionEffectFinishEvent event) {
        LifeGauge.networkWrapper.sendToAll(new MessageUpdatePotions(event.entity.getEntityId(), event.potion.getPotionID(), false));
    }
}
