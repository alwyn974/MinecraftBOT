package me.herrphoenix.obamabot.listener;

import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.packet.Packet;
import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.cmd.ObamaCommandHandler;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import re.alwyn974.minecraft.bot.builder.CommandBuilder;
import re.alwyn974.minecraft.bot.chat.TranslateChat;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.entity.EntityBOT;
import re.alwyn974.minecraft.bot.entity.MCBOTSessionAdapter;

import javax.swing.*;


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
        //if (!ObamaBOT.isEnabled()) return;

        if (packet instanceof ClientboundChatPacket) {
            ClientboundChatPacket chatPacket = (ClientboundChatPacket) packet;
            String translatedMessage = TranslateChat.translateComponent(chatPacket.getMessage());

            if (translatedMessage.startsWith("►") || !translatedMessage.contains("►")) {
                //System.out.println("invalid");
                return;
            }
            String[] msg = translatedMessage.split(" ► ");
            if (msg.length < 2) return;

            String command = msg[1];
            if (!command.startsWith(".")) return;

            new ObamaCommandHandler(ObamaBOT.getLogger()).execute(bot, command);
        }
    }
}
