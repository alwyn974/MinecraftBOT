package re.alwyn974.minecraft.bot;

import org.apache.commons.cli.ParseException;
import org.reflections.Reflections;
import re.alwyn974.logger.BasicLogger;
import re.alwyn974.logger.LoggerFactory;
import re.alwyn974.minecraft.bot.cli.CLIParser;
import re.alwyn974.minecraft.bot.cmd.InitSimpleCommand;
import re.alwyn974.minecraft.bot.builder.CommandBuilderException;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.ICommand;
import re.alwyn974.minecraft.bot.gui.MCBOTFrame;

import java.awt.GraphicsEnvironment;
import java.util.Set;

/**
 * The heart of the MinecraftBOT
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.7
 * @since 1.0.0
 */
public class MinecraftBOT {

    private static final String PROJECT_NAME = "MinecraftBOT";
    private static final BasicLogger logger = LoggerFactory.getLogger(getProjectName());
    private static final String username = System.getenv("MC_BOT_USERNAME");
    private static final String password = System.getenv("MC_BOT_PASSWORD");
    private static final String host = System.getenv("MC_BOT_HOST");
    private static final String port = System.getenv("MC_BOT_PORT");
    private static final String debug = System.getenv("MC_BOT_DEBUG");

    /**
     * The main
     *
     * @param args the arguments of the program
     */
    public static void main(String... args) {
        registerCommands();
        if (GraphicsEnvironment.isHeadless() || args.length > 0)
            runHeadless(args);
        else
            new MCBOTFrame();
    }

    /**
     * Register all of the command located in re.alwyn974.minecraft.bot.cmd
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
     * Run the programm in command line
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
     * Get the logger of MinecraftBOT
     *
     * @return the logger {@link BasicLogger}
     */
    public static BasicLogger getLogger() {
        return logger;
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
        return username != null ? username : "";
    }

    /**
     * Get the password
     *
     * @return the password
     */
    public static String getPassword() {
        return password != null ? password : "";
    }

    /**
     * Get the host
     *
     * @return the host
     */
    public static String getHost() {
        return host != null ? host : "127.0.0.1";
    }

    /**
     * Get the port
     *
     * @return the port
     */
    public static String getPort() {
        return port != null ? port : "25565";
    }

    /**
     * Get the debug value
     *
     * @return the debug value
     */
    public static String getDebug() {
        return debug;
    }

}
