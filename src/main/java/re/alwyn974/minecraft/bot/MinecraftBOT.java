package re.alwyn974.minecraft.bot;

import org.reflections.Reflections;
import re.alwyn974.logger.BasicLogger;
import re.alwyn974.logger.LoggerFactory;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.ICommand;
import re.alwyn974.minecraft.bot.gui.MCBOTFrame;

import java.awt.GraphicsEnvironment;
import java.util.Set;

/**
 * The heart of the MinecraftBOT
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.5
 * @since 1.0.0
 */
public class MinecraftBOT {

    private static final String PROJECT_NAME = "MinecraftBOT";
    private static final BasicLogger logger = LoggerFactory.getLogger(getProjectName());

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
        Reflections reflections = new Reflections("re.alwyn974.minecraft.bot.cmd");
        Set<Class<? extends ICommand>> classes = reflections.getSubTypesOf(ICommand.class);

        classes.forEach(cmd -> {
            try {
                CommandHandler.registerCommand(cmd.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                MinecraftBOT.getLogger().error("Cannot instantiate the command : %s", e);
            }
        });
    }

    /**
     * Run the programm in command line
     *
     * @param args the arguments of the program
     */
    private static void runHeadless(String... args) {
        //TODO: command line bot
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


}
