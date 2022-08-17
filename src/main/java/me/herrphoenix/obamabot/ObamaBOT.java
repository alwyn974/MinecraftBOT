package me.herrphoenix.obamabot;

import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

/**
 * @author HerrPhoenix
 */
public class ObamaBOT {
    private final EntityBOT bot;

    public ObamaBOT(EntityBOT bot) {
        this.bot = bot;
        ObamaRegistry.getRegistry().load();
    }

    public static final String PLOT_ENTER = "entered your plot (-6850;-249999)";

    public void handlePlayer(String ign) throws InterruptedException {
        if (ObamaRegistry.getRegistry().hasLifetime(ign)) {
            bot.getClient().send(new ServerboundChatPacket("Hello, " + ign + ". Enjoy your stay at Obama's!"));
            return;
        }
        if (ObamaRegistry.getRegistry().hasHourly(ign)) {
            bot.getClient().send(new ServerboundChatPacket("Hello, " + ign + ". Remember to renew your payment at " + ObamaRegistry.getRegistry().getExpire(ign)));
            return;
        }

        bot.getClient().send(new ServerboundChatPacket("Hello, " + ign + ". Welcome to Obama Land!"));
        bot.getClient().send(new ServerboundChatPacket("This is Obama's sacred soil. So we tax people that enter our plot."));
        bot.getClient().send(new ServerboundChatPacket("You can pay 50$, and be able to stay here for an hour."));
        bot.getClient().send(new ServerboundChatPacket("You can pay 500$, and be able to stay whenever and how long you want."));
        bot.getClient().send(new ServerboundChatPacket("Payment goes to TheORS. Use /pay TheORS <amount>."));
        bot.getClient().send(new ServerboundChatPacket("You have 30 seconds. If you do not pay, you will be kicked. This is the first of 2 chances we give you."));

        //maybe use a timer here idk im lazy
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bot.getClient().send(new ServerboundChatPacket("30 Seconds have passed."));
        bot.getClient().send(new ServerboundChatPacket("You will be kicked from Obama's."));
        bot.getClient().send(new ServerboundChatPacket("You are free to return, but if you refuse to pay tax again, you will be denied."));
        bot.getClient().send(new ServerboundChatPacket("/p kick " + ign));
    }
}
