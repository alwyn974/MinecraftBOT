package me.herrphoenix.obamabot;

import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import me.herrphoenix.obamabot.cmd.ObamaCommandHandler;
import me.herrphoenix.obamabot.cmd.builder.ICommandObama;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import org.reflections.Reflections;
import re.alwyn974.logger.BasicLogger;
import re.alwyn974.logger.LoggerFactory;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.builder.CommandBuilderException;
import re.alwyn974.minecraft.bot.cmd.InitSimpleCommand;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.ICommand;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

import java.util.Set;
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
        ObamaCommandHandler.setClient(bot);

        registerCommands();
    }

    private static final BasicLogger LOGGER = LoggerFactory.getLogger("ObamaBOT");
    private static boolean enable = false;
    public static String getPrefix() { return "."; }
    public static boolean isEnabled() {
        return enable;
    }
    public static void toggleObama() {
        enable = !enable;
    }
    public static final String PLOT_ENTER = "entered your plot (-6850;-249999)";

    public static void registerCommands() {
        Reflections reflections = new Reflections("me.herrphoenix.obamabot.cmd.impl.");
        Set<Class<? extends ICommandObama>> classes = reflections.getSubTypesOf(ICommandObama.class);

        classes.forEach(cmd -> {
            try {
                ObamaCommandHandler.registerCommand(cmd.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                getLogger().error("Cannot instantiate the command", e);
            }
        });
    }

    public void handlePlayer(String ign) throws InterruptedException {
        if (ObamaRegistry.getRegistry().hasLifetime(ign)) {
            chat("Hello, " + ign + ". Enjoy your stay at Obama's!");
            return;
        }
        if (ObamaRegistry.getRegistry().hasHourly(ign)) {
            chat("Hello, " + ign + ". Remember to renew your payment at " + ObamaRegistry.getRegistry().getExpire(ign));
            return;
        }

        chat("Hello, " + ign + ". Welcome to Obama Land!");
        Thread.sleep(1000);
        chat("This is Obama's sacred soil. So we tax people that enter our plot.");
        Thread.sleep(1000);
        chat("You can pay 50$, and be able to stay here for an hour.");
        Thread.sleep(1000);
        chat("You can pay 500$, and be able to stay whenever and how long you want.");
        Thread.sleep(1000);
        chat("Payment goes to TheORS. Use /pay TheORS <amount>.");
        Thread.sleep(1000);
        chat("You have 1 minute and 30 seconds. If you do not pay in this amount of time, you will be kicked. This is the first of 2 chances we give you.");
        Thread.sleep(1000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    chat("1 minute and 30 seconds have passed.");
                    Thread.sleep(1000);
                    chat("You will be kicked from Obama's.");
                    Thread.sleep(1000);
                    chat("You are free to return, but if you refuse to pay tax again, you will be denied.");
                    Thread.sleep(1000);
                    chat("/p kick " + ign);
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 90000);
    }

    public void chat(String message) {
        bot.getClient().send(new ServerboundChatPacket(message));
    }

    public static BasicLogger getLogger() {
        return LOGGER;
    }
}
