package net.ilexiconn.lifegauge.server.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PotionEffectEvent extends Event {
    public final PotionEffect potion;
    public final EntityLivingBase entity;

    public PotionEffectEvent(PotionEffect potion, EntityLivingBase entity) {
        this.potion = potion;
        this.entity = entity;
    }

    public static class PotionEffectStartEvent extends PotionEffectEvent {
        public PotionEffectStartEvent(PotionEffect potion, EntityLivingBase entity) {
            super(potion, entity);
        }
    }

    public static class PotionEffectChangeEvent extends PotionEffectEvent {
        public PotionEffectChangeEvent(PotionEffect potion, EntityLivingBase entity) {
            super(potion, entity);
        }
    }

    public static class PotionEffectFinishEvent extends PotionEffectEvent {
        public PotionEffectFinishEvent(PotionEffect potion, EntityLivingBase entity) {
            super(potion, entity);
        }
    }
}
