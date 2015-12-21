package net.ilexiconn.lifegauge.server.message;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.lifegauge.api.LifeGaugeAPI;
import net.ilexiconn.llibrary.common.message.AbstractMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageUpdatePotions extends AbstractMessage<MessageUpdatePotions> {
    public int entityId;
    public int potionId;
    public boolean add;

    public MessageUpdatePotions() {

    }

    public MessageUpdatePotions(int entityId, int potionId, boolean add) {
        this.entityId = entityId;
        this.potionId = potionId;
        this.add = add;
    }

    @SideOnly(Side.CLIENT)
    public void handleClientMessage(MessageUpdatePotions message, EntityPlayer player) {
        Entity entity = player.worldObj.getEntityByID(message.entityId);
        if (entity instanceof EntityLiving) {
            EntityLiving living = (EntityLiving) entity;
            if (message.add) {
                LifeGaugeAPI.addPotionToEntity(living, Potion.potionTypes[message.potionId]);
            } else {
                LifeGaugeAPI.removePotionFromEntity(living, Potion.potionTypes[message.potionId]);
            }
        }
    }

    public void handleServerMessage(MessageUpdatePotions message, EntityPlayer player) {

    }

    public void fromBytes(ByteBuf buf) {
        entityId = buf.readInt();
        potionId = buf.readInt();
        add = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(potionId);
        buf.writeBoolean(add);
    }
}
