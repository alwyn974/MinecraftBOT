package me.herrphoenix.obamabot.listener;

import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.packet.Packet;
import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.cmd.ObamaCommandHandler;
import me.herrphoenix.obamabot.plot.ObamaPlot;
import me.herrphoenix.obamabot.utils.Utils;
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

            String ign = msg[1].split(" ")[0];

            //some1 paid holy shit omg this is happening stay calm
            if (translatedMessage.startsWith("$")) {
                String[] str = translatedMessage.split(" ");

                String player = str[str.length -1].replace(".", "");

                if (!Utils.isOnline(player) && player.contains("~")) player = player.replace("~", "");

                int amount = Integer.parseInt(str[0].replace("$", ""));

                ObamaPlot.getInstance().onPlayerPayment(player, amount);
                return;
            }

            if (translatedMessage.endsWith(ObamaBOT.PLOT_ENTER)) {
                ObamaPlot.getInstance().onPlayerJoin(ign);
                return;
            }

            if (translatedMessage.endsWith(ObamaBOT.PLOT_LEAVE)) {
                ObamaPlot.getInstance().onPlayerLeave(ign);
                return;
            }

            String command = msg[1];
            if (!command.startsWith(".")) return;

            new ObamaCommandHandler(ObamaBOT.getLogger()).execute(bot, command);
        }
    }
}
