package me.herrphoenix.obamabot.listener;

import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.packet.Packet;
import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import re.alwyn974.minecraft.bot.chat.TranslateChat;
import re.alwyn974.minecraft.bot.entity.EntityBOT;
import re.alwyn974.minecraft.bot.entity.MCBOTSessionAdapter;


/**
 * @author HerrPhoenix
 */
public class ObamaListener extends MCBOTSessionAdapter {
    private final EntityBOT bot;

    /**
     * Instantiate a Sessions Adapter to manage packet
     *
     * @param bot the Entity Bot
     */
    public ObamaListener(EntityBOT bot) {
        super(bot);

        this.bot = bot;
    }

    @Override
    public void packetReceived(Session session, Packet packet) {
        super.packetReceived(session, packet);

        if (packet instanceof ClientboundChatPacket) {
            ClientboundChatPacket chatPacket = (ClientboundChatPacket) packet;
            String translatedMessage = TranslateChat.translateComponent(chatPacket.getMessage());
            if (translatedMessage.isEmpty())
                translatedMessage = "Failed to translate message: " + chatPacket.getMessage();

            if (translatedMessage.contains(".exempt")) {
                String ign = translatedMessage.split(" ")[translatedMessage.split(" ").length -1];

                ObamaRegistry.getRegistry().addLifetime(ign);

                bot.getClient().send(new ServerboundChatPacket(ign + " will not be taxed"));
            }
            if (translatedMessage.contains(".check")) {
                String ign = translatedMessage.split(" ")[translatedMessage.split(" ").length -1];

                boolean lifetime = ObamaRegistry.getRegistry().hasLifetime(ign);
                boolean hourly = ObamaRegistry.getRegistry().hasHourly(ign);

                if (lifetime) { bot.getClient().send(new ServerboundChatPacket(ign + " has lifetime")); return; }
                if (hourly) { bot.getClient().send(new ServerboundChatPacket(ign + " has paid a hourly tax and it will expire on " + ObamaRegistry.getRegistry().getExpire(translatedMessage.split(" ")[6]))); return; }
                bot.getClient().send(new ServerboundChatPacket(ign + " has not paid anything."));
            }

            if (translatedMessage.endsWith(ObamaBOT.PLOT_ENTER)) {
                String ign = translatedMessage.split(" ")[2];
                if (ign == null) ign = translatedMessage.split(" ")[4];

                try {
                    bot.getObama().handlePlayer(ign);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
