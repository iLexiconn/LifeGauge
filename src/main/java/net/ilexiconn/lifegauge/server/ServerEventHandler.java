package net.ilexiconn.lifegauge.server;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.ilexiconn.lifegauge.LifeGauge;
import net.ilexiconn.lifegauge.server.config.LifeGaugeConfig;
import net.ilexiconn.lifegauge.server.event.PotionEffectEvent;
import net.ilexiconn.lifegauge.server.message.MessageDisable;
import net.ilexiconn.lifegauge.server.message.MessageUpdatePotions;
import net.minecraft.entity.player.EntityPlayerMP;

public class ServerEventHandler {
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!LifeGaugeConfig.enabled && !event.player.worldObj.isRemote) {
            LifeGauge.networkWrapper.sendTo(new MessageDisable(), (EntityPlayerMP) event.player);
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
