package re.alwyn974.minecraft.bot.entity;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.packetlib.event.session.*;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.chat.TranslateChat;

public class MCBOTSessionAdapter extends SessionAdapter {

    private final EntityBOT bot;

    public MCBOTSessionAdapter(EntityBOT bot) {
        this.bot = bot;
    }

    @Override
    public void connected(ConnectedEvent event) {
        super.connected(event);
    }

    @Override
    public void disconnected(DisconnectedEvent event) {
        MinecraftBOT.getLogger().info("Disconnected: %s", event.getReason());
    }

    @Override
    public void disconnecting(DisconnectingEvent event) {
        super.disconnecting(event);
    }

    @Override
    public void packetError(PacketErrorEvent event) {
        super.packetError(event);
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if (event.getPacket() instanceof ServerChatPacket)
            MinecraftBOT.getLogger().info(TranslateChat.translateComponent(event.<ServerChatPacket>getPacket().getMessage()));
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
