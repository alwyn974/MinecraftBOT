package re.alwyn974.minecraft.bot;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.data.status.handler.ServerPingTimeHandler;
import com.github.steveice10.packetlib.BuiltinFlags;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import org.apache.commons.cli.ParseException;
import org.reflections.Reflections;
import re.alwyn974.logger.BasicLogger;
import re.alwyn974.logger.LoggerFactory;
import re.alwyn974.minecraft.bot.builder.CommandBuilderException;
import re.alwyn974.minecraft.bot.chat.TranslateChat;
import re.alwyn974.minecraft.bot.cli.CLIParser;
import re.alwyn974.minecraft.bot.cmd.InitSimpleCommand;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.ICommand;
import re.alwyn974.minecraft.bot.gui.MCBOTFrame;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The heart of the MinecraftBOT
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.14
 * @since 1.0.0
 */
public class MinecraftBOT {

    //https://github.com/GeyserMC/MCProtocolLib/blob/master/example/com/github/steveice10/mc/protocol/test/MinecraftProtocolTest.java

    private static final String PROJECT_NAME = "MinecraftBOT";
    private static final BasicLogger LOGGER = LoggerFactory.getLogger(getProjectName());
    private static final String USERNAME = System.getenv("MC_BOT_USERNAME");
    private static final String PREMIUM = System.getenv("MC_BOT_PREMIUM");
    private static final String HOST = System.getenv("MC_BOT_HOST");
    private static final String PORT = System.getenv("MC_BOT_PORT");
    private static final String DEBUG = System.getenv("MC_BOT_DEBUG");
    private static final String PREFIX = System.getenv("MC_BOT_PREFIX");
    private static final String AUTO_RECONNECT = System.getenv("MC_BOT_AUTO_RECONNECT");
    private static final String RECONNECT_DELAY = System.getenv("MC_BOT_RECONNECT_DELAY");
    private static final String LANG_FILE = System.getenv("MC_BOT_LANG_FILE");
    private static final String COMMAND = System.getenv("MC_BOT_COMMAND");
    private static final String COMMAND_DELAY = System.getenv("MC_BOT_COMMAND_DELAY");

    /**
     * The main
     *
     * @param args the arguments of the program
     */
    public static void main(String... args) {
        registerCommands();
        try {
            setupDefaultLangFile();
        } catch (IOException e) {
            MinecraftBOT.getLogger().error("Error while creating default lang file", e);
        }
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            throwable.printStackTrace();
            MinecraftBOT.getLogger().error("Error: %s", throwable.toString());
        });
        if (GraphicsEnvironment.isHeadless() || args.length > 0)
            runHeadless(args);
        else
            new MCBOTFrame();
    }

    private static void setupDefaultLangFile() throws IOException {
        File langDirectory = new File("lang");
        if (!langDirectory.exists() && !langDirectory.mkdirs()) {
            MinecraftBOT.getLogger().error("Can't create lang directory");
            return;
        }
        File defaultLang = new File(langDirectory, "en_us.json");
        if (!defaultLang.exists()) {
            MinecraftBOT.getLogger().info("Creating default lang file");
            Files.copy(Objects.requireNonNull(MinecraftBOT.class.getResourceAsStream("/lang/en_us.json")), Paths.get("lang", "en_us.json"));
        }
    }

    /**
     * Register all the command located in re.alwyn974.minecraft.bot.cmd
     */
    private static void registerCommands() {
        Reflections reflections = new Reflections("re.alwyn974.minecraft.bot.cmd.");
        Set<Class<? extends ICommand>> classes = reflections.getSubTypesOf(ICommand.class);

        classes.forEach(cmd -> {
            try {
                CommandHandler.registerCommand(cmd.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                MinecraftBOT.getLogger().error("Cannot instantiate the command", e);
            }
        });
        try {
            new InitSimpleCommand().initSimpleCommand();
        } catch (CommandBuilderException e) {
            MinecraftBOT.getLogger().error("Cannot init the simple command", e);
        }
    }

    /**
     * Run the program in command line
     *
     * @param args the arguments of the program
     */
    private static void runHeadless(String... args) {
        try {
            CLIParser.parse(args);
        } catch (ParseException e) {
            MinecraftBOT.getLogger().error("Cannot parse the arguments correctly", e);
        }
    }

    /**
     * Get logger of MinecraftBOT
     *
     * @return the logger {@link BasicLogger}
     */
    public static BasicLogger getLogger() {
        return LOGGER;
    }

    /**
     * Get the project name
     *
     * @return the project name
     */
    public static String getProjectName() {
        return PROJECT_NAME;
    }


    /**
     * Get the username
     *
     * @return the username
     */
    public static String getUsername() {
        return USERNAME != null ? USERNAME : "";
    }

    /**
     * Check if the user is premium
     *
     * @return "true" if the user is premium
     */
    public static String getPremium() {
        return PREMIUM;
    }

    /**
     * Get the host
     *
     * @return the host
     */
    public static String getHost() {
        return HOST != null ? HOST : "127.0.0.1";
    }

    /**
     * Get the port
     *
     * @return the port
     */
    public static String getPort() {
        return PORT != null ? PORT : "25565";
    }

    /**
     * Get the debug value
     *
     * @return the debug value
     */
    public static String getDebug() {
        return DEBUG;
    }

    /**
     * Get the auto reconnect value
     *
     * @return the auto reconnect value
     */
    public static String getAutoReconnect() {
        return AUTO_RECONNECT;
    }

    /**
     * Get reconnect delay
     *
     * @return the delay
     */
    public static String getReconnectDelay() {
        return RECONNECT_DELAY != null ? RECONNECT_DELAY : "1000";
    }

    /**
     * Get the language
     *
     * @return the language
     */
    public static String getLangFile() {
        return LANG_FILE != null ? LANG_FILE : "en_us.json";
    }

    /**
     * Get the command to execute when the bot is connected
     * @return the command
     */
    public static String getCommand() {
        return COMMAND;
    }

    /**
     * Get the command delay
     * @return the command delay
     */
    public static String getCommandDelay() {
        return COMMAND_DELAY != null ? COMMAND_DELAY : "750";
    }

    /**
     * Get the prefix of commands
     *
     * @return the prefix
     */
    public static String getPrefix() {
        return PREFIX != null ? PREFIX : ".";
    }

    /**
     * Retrieve the status of a server
     *
     * @param host  the host
     * @param port  the port
     * @param debug debug value to print some useful things
     */
    public static void retrieveStatus(String host, int port, boolean debug) {
        new Thread(() -> {
            SessionService sessionService = new SessionService();
            sessionService.setProxy(Proxy.NO_PROXY);

            MinecraftProtocol protocol = new MinecraftProtocol();
            Session client = new TcpClientSession(host, port, protocol);
            client.setFlag(BuiltinFlags.PRINT_DEBUG, debug);
            client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
            client.setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, (ServerInfoHandler) (session, info) -> {
                MinecraftBOT.getLogger().info("Version: %s, Protocol Version: %d", info.getVersionInfo().getVersionName(), info.getVersionInfo().getProtocolVersion());
                MinecraftBOT.getLogger().info("Player Count: %d/%d", info.getPlayerInfo().getOnlinePlayers(), info.getPlayerInfo().getMaxPlayers());
                List<String> players = new ArrayList<>();
                for (GameProfile player : info.getPlayerInfo().getPlayers())
                    players.add(player.getName());
                MinecraftBOT.getLogger().info("Players: %s", players.toString());
                MinecraftBOT.getLogger().info("Description: %s", TranslateChat.getInstance().translateComponent(info.getDescription()));
            });
            client.setFlag(MinecraftConstants.SERVER_PING_TIME_HANDLER_KEY, (ServerPingTimeHandler) (session, pingTime) -> MinecraftBOT.getLogger().info("Server ping took %dms", pingTime));
            client.addListener(new SessionAdapter() {
                @Override
                public void disconnected(DisconnectedEvent event) {
                    MinecraftBOT.getLogger().info("Disconnected: %s\n%s", event.getReason(), event.getCause() != null ? event.getCause() : "");
                }
            });

            if (debug)
                MinecraftBOT.getLogger().debug("Connecting to Minecraft server: %s:%s", host, port);
            client.connect();

            try {
                Thread.sleep(2000L);
                client.disconnect("Finished");
            } catch (InterruptedException ex) {
                MinecraftBOT.getLogger().error("Thread interrupt", ex);
                client.disconnect("Finished");
            }
        }).start();
    }
}
