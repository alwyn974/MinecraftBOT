package re.alwyn974.minecraft.bot.entity;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.event.session.*;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.chat.TranslateChat;

public class MCBOTSessionAdapter extends SessionAdapter {

    private final EntityBOT bot;

    public MCBOTSessionAdapter(EntityBOT bot) {
        this.bot = bot;
    }

    @Override
    public void disconnected(DisconnectedEvent event) {
        MinecraftBOT.getLogger().info("Disconnected: %s\n%s", event.getReason(), event.getCause() != null ? event.getCause() : "");
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if (event.getPacket() instanceof ServerChatPacket)
            MinecraftBOT.getLogger().info(TranslateChat.translateComponent(event.<ServerChatPacket>getPacket().getMessage()));
        if (event.getPacket() instanceof ServerPlayerPositionRotationPacket) {
            ServerPlayerPositionRotationPacket pos = event.<ServerPlayerPositionRotationPacket>getPacket();
            bot.setPos(new EntityPos(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch()));
        }
    }

    @Override
    public void packetSending(PacketSendingEvent event) {
        super.packetSending(event);
    }

    @Override
    public void packetSent(PacketSentEvent event) {
        super.packetSent(event);
    }


}
