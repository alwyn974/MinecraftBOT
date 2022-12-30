package re.alwyn974.minecraft.bot.entity;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.protocol.data.game.ClientCommand;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.*;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.entity.ClientboundMoveEntityPosRotPacket;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.entity.player.ClientboundSetHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundClientCommandPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.ConnectedEvent;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.chat.TranslateChat;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * The session adapter, managing packet and more
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.13
 * @since 1.0.0
 */
public class MCBOTSessionAdapter extends SessionAdapter {

    private final EntityBOT bot;

    /**
     * Instantiate a Sessions Adapter to manage packet
     *
     * @param bot the Entity Bot
     */
    public MCBOTSessionAdapter(EntityBOT bot) {
        this.bot = bot;
    }

    /**
     * <p>Handle the disconnected event</p>
     * <p>If auto reconnect is enabled, it will try to connect unless the disconnection message is "Disconnected" </p>
     * @param event the disconnected event
     */
    @Override
    public void disconnected(DisconnectedEvent event) {
        Component component = MiniMessage.miniMessage().deserialize(event.getReason());
        String reason = TranslateChat.getInstance().translateComponent(component);
        MinecraftBOT.getLogger().info("Disconnected: %s\n%s", reason, event.getCause() != null ? event.getCause() : "");
        if (bot.isAutoReconnect() && !event.getReason().equals("Disconnected")) {
            try {
                TimeUnit.MILLISECONDS.sleep(bot.getReconnectDelay());
                bot.connect();
            } catch (RequestException | InterruptedException e) {
                MinecraftBOT.getLogger().error(e instanceof InterruptedException ? "Can't delay the reconnection" : "Can't authenticate", e);
            }
        }
    }

    @Override
    public void connected(ConnectedEvent event) {
        if (bot.getCommand() == null)
            return;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!bot.getClient().isConnected())
                    return;
                MinecraftBOT.getLogger().info("Sending command: %s", bot.getCommand());
                bot.getClient().send(new ServerboundChatPacket(bot.getCommand()));
            }
        }, bot.getCommandDelay());
    }

    /**
     * Handle all of received packet
     *
     * @param session the session
     * @param packet the packet received
     */
    @Override
    public void packetReceived(Session session, Packet packet) {
        if (packet instanceof ClientboundChatPacket) {
            ClientboundChatPacket chatPacket = (ClientboundChatPacket) packet;
            String translatedMessage = TranslateChat.getInstance().translateComponent(chatPacket.getMessage());
            MinecraftBOT.getLogger().info("%s", translatedMessage);
        }

        if (packet instanceof ClientboundPlayerPositionPacket) {
            ClientboundPlayerPositionPacket positionPacket = (ClientboundPlayerPositionPacket) packet;
            if (bot.getPos() == null) {
                bot.setPos(new EntityPos(positionPacket.getX(), positionPacket.getY(), positionPacket.getZ(), 0, 0));
            } else
                bot.getPos().setX(positionPacket.getX()).setY(positionPacket.getY()).setZ(positionPacket.getZ());
        }

        if (packet instanceof ClientboundMoveEntityPosRotPacket) {
            ClientboundMoveEntityPosRotPacket posPacket = (ClientboundMoveEntityPosRotPacket) packet;
            if (bot.getPos() != null)
                bot.getPos().setPitch(posPacket.getPitch()).setYaw(posPacket.getYaw())
                    .addX(posPacket.getMoveX()).addY(posPacket.getMoveY()).addZ(posPacket.getMoveZ());
        }

        if (packet instanceof ClientboundSetHealthPacket) {
            ClientboundSetHealthPacket clientboundSetHealthPacket = (ClientboundSetHealthPacket) packet;
            boolean healthAndFoodNegative = bot.getFood() == -1 || bot.getHealth() == -1;
            bot.setHealth(clientboundSetHealthPacket.getHealth());
            bot.setFood(clientboundSetHealthPacket.getFood());
            if (clientboundSetHealthPacket.getHealth() <= 0)
                bot.getClient().send(new ServerboundClientCommandPacket(ClientCommand.RESPAWN));
            if (healthAndFoodNegative)
                MinecraftBOT.getLogger().info("Health: %g Food: %g", bot.getHealth(), bot.getFood());
        }

        if (packet instanceof ClientboundChangeDifficultyPacket) {
            ClientboundChangeDifficultyPacket difficultyPacket = (ClientboundChangeDifficultyPacket) packet;
            boolean difficultyNull = bot.getDifficulty() == null;
            bot.setDifficulty(difficultyPacket.getDifficulty());
            if (difficultyNull)
                MinecraftBOT.getLogger().info("Difficulty: %s", bot.getDifficulty().name());
        }

        if (packet instanceof ClientboundPlayerInfoPacket) {
            ClientboundPlayerInfoPacket playerInfoPacket = (ClientboundPlayerInfoPacket) packet;
            bot.setPlayers(Arrays.asList(playerInfoPacket.getEntries()));
        }
    }

}
