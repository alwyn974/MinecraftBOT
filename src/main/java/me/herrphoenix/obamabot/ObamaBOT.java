package me.herrphoenix.obamabot;

import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import me.herrphoenix.obamabot.cmd.ObamaCommandHandler;
import me.herrphoenix.obamabot.cmd.builder.ICommandObama;
import me.herrphoenix.obamabot.plot.ObamaPlot;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import org.reflections.Reflections;
import re.alwyn974.logger.BasicLogger;
import re.alwyn974.logger.LoggerFactory;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author HerrPhoenix
 */
public class ObamaBOT {
    private static EntityBOT bot;

    public ObamaBOT(EntityBOT bot) {
        ObamaBOT.bot = bot;

        ObamaRegistry.getRegistry().loadLifetimes();
        ObamaRegistry.getRegistry().loadPoints();

        ObamaCommandHandler.setClient(bot);

        registerCommands();

        ObamaPlot.getInstance().startCounting();
    }

    public static EntityBOT getBot() {
        return bot;
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
    public static final String PLOT_LEAVE = "left your plot (-6850;-249999)";
    public static final String[] EXECUTIVES = {"HerrPhoenix", "Rommel", "Betise", "YaBoiBet", "Betise", "naranbaatr", "naranbaatar"};
    public static final String[] IGNORE = {"UpperGround", "JesusKun", "TurboMaxe", "turbuu", "turbo", "turbu", "Dmitri"};

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

    public static final long CHAT_COOLDOWN = 2500L;

    public static void handlePlayerJoin(String ign) {
        if (ObamaRegistry.getRegistry().hasLifetime(ign) || ObamaRegistry.getRegistry().hasHourly(ign)) {
            return;
        }

        try {
            chat("Hello, " + ign + ". Welcome to Obama's");
            Thread.sleep(CHAT_COOLDOWN);
            chat("This is Obama's sacred soil, so we tax people that enter our plot");
            Thread.sleep(CHAT_COOLDOWN);
            chat("You can pay 50$, and be able to stay here for an hour");
            Thread.sleep(CHAT_COOLDOWN);
            chat("You can pay 500$, and be able to stay whenever and how long you want");
            Thread.sleep(CHAT_COOLDOWN);
            chat("Payment goes to TheORS, use /pay TheORS <amount>");
            Thread.sleep(CHAT_COOLDOWN);
            chat("You have 1 minute and 30 seconds, if you do not pay in this amount of time, you will be denied");
            Thread.sleep(CHAT_COOLDOWN);
            chat("If you do not wish to pay, please leave.");

            ObamaRegistry.getRegistry().addPending(ign);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (ObamaRegistry.getRegistry().hasLifetime(ign) || ObamaRegistry.getRegistry().hasHourly(ign)) return;

                        if (ObamaPlot.getInstance().isInPlot(ign)) {
                            chat(ign + ", 1 minute and 30 seconds have passed.");
                            Thread.sleep(CHAT_COOLDOWN);
                            chat("You will be denied from Obama's.");
                            Thread.sleep(CHAT_COOLDOWN);
                            chat("/p kick " + ign);
                            Thread.sleep(CHAT_COOLDOWN);
                            chat("/p deny " + ign);
                        }

                        ObamaRegistry.getRegistry().removePending(ign);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 90000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void chat(String message) {
        bot.getClient().send(new ServerboundChatPacket(message));
    }

    public static BasicLogger getLogger() {
        return LOGGER;
    }
}
