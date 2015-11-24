package net.ilexiconn.lifegauge.server.asm;

import net.ilexiconn.lifegauge.server.event.PotionEffectEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;

public class LifeGaugeHooks {
    public static void onNewPotionEffect(PotionEffect potion, EntityLivingBase entity) {
        MinecraftForge.EVENT_BUS.post(new PotionEffectEvent.PotionEffectStartEvent(potion, entity));
    }

    public static void onChangedPotionEffect(PotionEffect potion, EntityLivingBase entity) {
        MinecraftForge.EVENT_BUS.post(new PotionEffectEvent.PotionEffectChangeEvent(potion, entity));
    }

    public static void onFinishedPotionEffect(PotionEffect potion, EntityLivingBase entity) {
        MinecraftForge.EVENT_BUS.post(new PotionEffectEvent.PotionEffectFinishEvent(potion, entity));
    }
}
