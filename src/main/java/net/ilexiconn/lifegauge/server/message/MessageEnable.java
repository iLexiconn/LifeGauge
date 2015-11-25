package net.ilexiconn.lifegauge.server.message;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.lifegauge.server.config.LifeGaugeConfig;
import net.ilexiconn.llibrary.common.message.AbstractMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageEnable extends AbstractMessage<MessageEnable> {
    public boolean enabled;

    public MessageEnable() {

    }

    public MessageEnable(boolean enabled) {
        this.enabled = enabled;
    }

    @SideOnly(Side.CLIENT)
    public void handleClientMessage(MessageEnable message, EntityPlayer player) {
        LifeGaugeConfig.enabled = message.enabled;
    }

    public void handleServerMessage(MessageEnable message, EntityPlayer player) {

    }

    public void fromBytes(ByteBuf buf) {
        enabled = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(enabled);
    }
}
