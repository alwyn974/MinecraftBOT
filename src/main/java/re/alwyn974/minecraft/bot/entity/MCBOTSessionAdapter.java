package re.alwyn974.minecraft.bot.entity;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerDifficultyPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.chat.TranslateChat;

import java.util.Arrays;
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
     * Instanciate a Sessions Adapter to manage packet
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
        MinecraftBOT.getLogger().info("Disconnected: %s\n%s", event.getReason(), event.getCause() != null ? event.getCause() : "");
        if (bot.isAutoReconnect() && !event.getReason().equals("Disconnected")) {
            try {
                TimeUnit.MILLISECONDS.sleep(bot.getReconnectDelay());
                bot.connect();
            } catch (RequestException | InterruptedException e) {
                MinecraftBOT.getLogger().error(e instanceof InterruptedException ? "Can't delay the reconnection" : "Can't authenticate", e);
            }
        }
    }

    /**
     * Handle all of received packet
     *
     * @param event the packet received event
     */
    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if (event.getPacket() instanceof ServerChatPacket)
            MinecraftBOT.getLogger().info("%s", TranslateChat.translateComponent(event.<ServerChatPacket>getPacket().getMessage()));
        if (event.getPacket() instanceof ServerPlayerPositionRotationPacket) {
            ServerPlayerPositionRotationPacket pos = event.getPacket();
            boolean posNull = bot.getPos() == null;
            bot.setPos(new EntityPos(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch()));
            if (posNull)
                MinecraftBOT.getLogger().info("Position: %s", bot.getPos());
        }
        if (event.getPacket() instanceof ServerPlayerHealthPacket) {
            ServerPlayerHealthPacket serverPlayerHealthPacket = event.getPacket();
            boolean healthAndFoodNegative = bot.getFood() == -1 || bot.getHealth() == -1;
            bot.setHealth(serverPlayerHealthPacket.getHealth());
            bot.setFood(serverPlayerHealthPacket.getFood());
            if (serverPlayerHealthPacket.getHealth() <= 0)
                bot.getClient().send(new ClientRequestPacket(ClientRequest.RESPAWN));
            if (healthAndFoodNegative)
                MinecraftBOT.getLogger().info("Health: %g Food: %g", bot.getHealth(), bot.getFood());
        }
        if (event.getPacket() instanceof ServerDifficultyPacket) {
            ServerDifficultyPacket difficultyPacket = event.getPacket();
            boolean difficultyNull = bot.getDifficulty() == null;
            bot.setDifficulty(difficultyPacket.getDifficulty());
            if (difficultyNull)
                MinecraftBOT.getLogger().info("Difficulty: %s", bot.getDifficulty().name());
        }
        if (event.getPacket() instanceof ServerPlayerListEntryPacket) {
            ServerPlayerListEntryPacket serverPlayerListEntryPacket = event.getPacket();
            bot.setPlayers(Arrays.asList(serverPlayerListEntryPacket.getEntries()));
        }
    }

}
