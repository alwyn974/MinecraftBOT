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

            //actual message sent by the player
            String playerMessage = translatedMessage.split(":", 2)[1];

            //commands
            if (playerMessage.startsWith("obama")) {
                String[] args = playerMessage.split(" ", 2)[1].split(" ");

                if (args.length >= 2) {
                    if (args[0].equals("exempt")) {
                        String ign = args[1];

                        ObamaRegistry.getRegistry().addLifetime(ign);

                        bot.getClient().send(new ServerboundChatPacket(ign + " will not be taxed"));
                    }
                    if (args[0].equals("triggerEntry")) {
                        String ign = args[1];

                        try {
                            bot.getClient().send(new ServerboundChatPacket("Triggering plot entry for " + ign + " in 5 seconds."));
                            Thread.sleep(5000);
                            bot.getObama().handlePlayer(ign);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    if (args[0].equals("check")) {
                        String ign = args[1];

                        if (ObamaRegistry.getRegistry().hasLifetime(ign)) {
                            bot.getClient().send(new ServerboundChatPacket(ign + " has lifetime"));
                            return;
                        }
                        if (ObamaRegistry.getRegistry().hasHourly(ign)) {
                            bot.getClient().send(new ServerboundChatPacket(ign + " has paid a hourly tax and it will expire on " + ObamaRegistry.getRegistry().getExpire(translatedMessage.split(" ")[6])));
                            return;
                        }
                        bot.getClient().send(new ServerboundChatPacket(ign + " has not paid anything."));
                    }
                }
            }

            //someone entered the plot
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
