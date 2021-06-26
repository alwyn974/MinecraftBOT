package re.alwyn974.minecraft.bot.entity;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerDifficultyPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.event.session.*;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.chat.TranslateChat;

/**
 * The session adapter, managing packet and more
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @since 1.0.0
 * @version 1.0.5
 */
public class MCBOTSessionAdapter extends SessionAdapter {

    private final EntityBOT bot;

    /**
     * Instanciate a Sessions Adapter to manage packet
     * @param bot the Entity Bot
     */
    public MCBOTSessionAdapter(EntityBOT bot) {
        this.bot = bot;
    }

    /**
     * Handle the disconnected event
     * @param event the disconnected event
     */
    @Override
    public void disconnected(DisconnectedEvent event) {
        MinecraftBOT.getLogger().info("Disconnected: %s\n%s", event.getReason(), event.getCause() != null ? event.getCause() : "");
    }

    /**
     * Handle all of received packet
     * @param event the packet received event
     */
    @Override
    public void packetReceived(PacketReceivedEvent event) {
        //System.out.println(event.getPacket().getClass().getName());
        if (event.getPacket() instanceof ServerChatPacket)
            MinecraftBOT.getLogger().info(TranslateChat.translateComponent(event.<ServerChatPacket>getPacket().getMessage()));
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
    }

}
