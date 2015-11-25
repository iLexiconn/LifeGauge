package net.ilexiconn.lifegauge.server.message;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.ilexiconn.lifegauge.server.config.LifeGaugeConfig;
import net.ilexiconn.llibrary.common.message.AbstractMessage;
import net.minecraft.entity.player.EntityPlayer;

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
