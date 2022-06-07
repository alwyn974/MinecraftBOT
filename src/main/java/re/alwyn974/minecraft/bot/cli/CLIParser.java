package re.alwyn974.minecraft.bot.cli;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundChatPacket;
import org.apache.commons.cli.*;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

import java.util.Scanner;

/**
 * The command line parser
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.13
 * @since 1.0.9
 */
public class CLIParser {

    private static final Options options = new Options();
    private static CommandLine cmd;
    private static EntityBOT bot;
    private static Thread botThread;

    /**
     * Parse the command line arguments
     *
     * @param args the arguments
     * @throws ParseException on parsing error
     */
    public static void parse(String... args) throws ParseException {
        addOptions();
        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);
        ParseResult result = parseResult();
        if (result.shouldPrintHelp())
            printHelp();
        if (result.shouldPrintStatus()) {
            MinecraftBOT.retrieveStatus(result.getHost(), result.getPort(), result.isDebug());
            System.exit(0);
        }
        bot = new EntityBOT(result);
        botThread = new Thread(() -> {
            try {
                MinecraftBOT.getLogger().info("Starting MinecraftBOT...");
                bot.connect();
            } catch (RequestException ex) {
                MinecraftBOT.getLogger().error("Can't authenticate", ex);
                botThread.interrupt();
            }
            try (Scanner scanner = new Scanner(System.in)) {
                while (bot != null && bot.getClient().isConnected()) {
                    String line = scanner.nextLine();
                    if (line.equals("disconnect")) {
                        bot.getClient().disconnect("Disconnected");
                        botThread.interrupt();
                        break;
                    }
                    if (!new CommandHandler().execute(bot, line) && bot != null && bot.getClient().isConnected())
                        bot.getClient().send(new ClientboundChatPacket(line));
                }
                botThread.interrupt();
            }
        });
        botThread.start();
    }

    /**
     * Add the options
     */
    private static void addOptions() {
        options.addOption("h", "host", true, "Setup the host value (Default=127.0.0.1)");
        options.addOption("p", "port", true, "Setup the port value (Default=25565)");
        options.addOption("u", "user", true, "Email/Username of the user");
        options.addOption(null, "premium", false, "If the user need to be logged through Microsoft Authentication");
        options.addOption("d", "debug", false, "Activate debug");
        options.addOption("a", "autoReconnect", false, "Activate auto reconnect");
        options.addOption(null, "reconnectDelay", true, "Delay before reconnection");
        options.addOption("s", "status", false, "Display only the status of the server");
        options.addOption(null, "help", false, "Show this help page");
    }

    private static ParseResult parseResult() {
        ParseResult result = new ParseResult();
        result.setHost(cmd.hasOption("h") ? cmd.getOptionValue("h") : MinecraftBOT.getHost());
        result.setPort(Integer.parseInt(cmd.hasOption("p") ? cmd.getOptionValue("p") : MinecraftBOT.getPort()));
        result.setEmail(cmd.hasOption("u") ? cmd.getOptionValue("u") : MinecraftBOT.getUsername());
        result.setPremium(cmd.hasOption("premium") || Boolean.parseBoolean(MinecraftBOT.getPremium()));
        result.setStatus(cmd.hasOption("s"));
        result.setDebug(cmd.hasOption("d") ? cmd.hasOption("d") : Boolean.parseBoolean(MinecraftBOT.getDebug()));
        result.setAutoReconnect(cmd.hasOption("a") || Boolean.parseBoolean(MinecraftBOT.getAutoReconnect()));
        result.setReconnectDelay(Long.parseLong(cmd.hasOption("reconnectDelay") ? cmd.getOptionValue("reconnectDelay") : MinecraftBOT.getReconnectDelay()));
        result.setHelp(cmd.hasOption("help"));
        return result;
    }

    /**
     * Display the help of arguments
     */
    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(MinecraftBOT.getProjectName(), options);
        System.exit(0);
    }

}
