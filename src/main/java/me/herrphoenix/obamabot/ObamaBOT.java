package me.herrphoenix.obamabot;

import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

import java.util.Timer;
import java.util.TimerTask;

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
        Thread.sleep(1000);
        bot.getClient().send(new ServerboundChatPacket("This is Obama's sacred soil. So we tax people that enter our plot."));
        Thread.sleep(1000);
        bot.getClient().send(new ServerboundChatPacket("You can pay 50$, and be able to stay here for an hour."));
        Thread.sleep(1000);
        bot.getClient().send(new ServerboundChatPacket("You can pay 500$, and be able to stay whenever and how long you want."));
        Thread.sleep(1000);
        bot.getClient().send(new ServerboundChatPacket("Payment goes to TheORS. Use /pay TheORS <amount>."));
        Thread.sleep(1000);
        bot.getClient().send(new ServerboundChatPacket("You have 1 minute and 30 seconds. If you do not pay in this amount of time, you will be kicked. This is the first of 2 chances we give you."));
        Thread.sleep(1000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    bot.getClient().send(new ServerboundChatPacket("1 minute and 30 seconds have passed."));
                    Thread.sleep(1000);
                    bot.getClient().send(new ServerboundChatPacket("You will be kicked from Obama's."));
                    Thread.sleep(1000);
                    bot.getClient().send(new ServerboundChatPacket("You are free to return, but if you refuse to pay tax again, you will be denied."));
                    Thread.sleep(1000);
                    bot.getClient().send(new ServerboundChatPacket("/p kick " + ign));
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 90000);
    }
}
