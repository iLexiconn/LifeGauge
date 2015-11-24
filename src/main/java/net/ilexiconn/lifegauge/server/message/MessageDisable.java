package net.ilexiconn.lifegauge.server.message;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.lifegauge.server.config.LifeGaugeConfig;
import net.ilexiconn.llibrary.common.message.AbstractMessage;
import net.minecraft.entity.player.EntityPlayer;

public class MessageDisable extends AbstractMessage<MessageDisable> {
    public void handleClientMessage(MessageDisable message, EntityPlayer player) {
        LifeGaugeConfig.enabled = false;
    }

    public void handleServerMessage(MessageDisable message, EntityPlayer player) {

    }

    public void fromBytes(ByteBuf buf) {

    }

    public void toBytes(ByteBuf buf) {

    }
}
