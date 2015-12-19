package net.ilexiconn.lifegauge.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;

import java.util.List;
import java.util.Map;

public class LifeGaugeAPI {
    private static Map<EntityLiving, List<Potion>> potionMap = Maps.newHashMap();

    public static void addPotionToEntity(EntityLiving entity, Potion potion) {
        if (potionMap.containsKey(entity)) {
            potionMap.get(entity).add(potion);
        } else {
            potionMap.put(entity, Lists.newArrayList(potion));
        }
    }

    public static void removePotionFromEntity(EntityLiving entity, Potion potion) {
        if (potionMap.containsKey(entity)) {
            if (potionMap.get(entity).contains(potion)) {
                potionMap.get(entity).remove(potion);
            }
        }
    }

    public static Potion[] getPotionsForEntity(EntityLiving entity) {
        if (potionMap.containsKey(entity)) {
            List<Potion> potionList = potionMap.get(entity);
            return potionList.toArray(new Potion[potionList.size()]);
        } else {
            return new Potion[]{};
        }
    }
}
