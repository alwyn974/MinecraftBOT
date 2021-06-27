package re.alwyn974.minecraft.bot.cmd.utils;

import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * The session adapter, managing packet and more
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.5
 * @since 1.0.4
 */
public class CommandHandler {

    private static String prefix = ".";
    private static final HashMap<String, ICommand> COMMANDS = new HashMap<>();
    private static final List<ICommand> COMMANDS_LIST = new ArrayList<>();

    /**
     * register a command
     *
     * @param command the command
     */
    public static void registerCommand(ICommand command) {
        ICommand cmd = COMMANDS.put(command.getName(), command);
        COMMANDS_LIST.add(command);
        MinecraftBOT.getLogger().info("Register command: %s", command.getName());
        if (cmd != null)
            MinecraftBOT.getLogger().error("Duplicated command %s", cmd.getName());
    }

    /**
     * Execute the command
     *
     * @param bot     the bot {@link EntityBOT}
     * @param message the message
     * @return if a command as been executed
     */
    public boolean execute(EntityBOT bot, String message) {
        final String[] args = message.split(" ");

        if (args[0].startsWith(CommandHandler.getPrefix())) {
            String cmd = args[0].substring(1);

            final ICommand command = getCommand(cmd);

            if (command != null) {
                final String[] cmdArgs = new String[args.length - 1];

                System.arraycopy(args, 1, cmdArgs, 0, cmdArgs.length);
                if (command.needToBeConnected() && bot == null)
                    MinecraftBOT.getLogger().error("You need to be connected to a server to do this !");
                else
                    command.executor().execute(bot, message, cmdArgs);
                return true;
            } else if (!cmd.isEmpty())
                MinecraftBOT.getLogger().error("Command doesn't exist [%s]", cmd);
        }
        return false;
    }

    /**
     * Get the command
     *
     * @param name the command name
     * @return the command {@link ICommand}
     */
    private ICommand getCommand(String name) {
        if (COMMANDS.get(name) != null)
            return (COMMANDS.get(name));
        return null;
    }

    /**
     * Get the prefix of all commands
     *
     * @return the prefix of the all commands
     */
    public static String getPrefix() {
        return prefix;
    }

    /**
     * Change the prefix of the commands
     *
     * @param prefix the new prefix
     */
    public static void setPrefix(String prefix) {
        CommandHandler.prefix = prefix;
    }

    /**
     * Get the list of all commands
     *
     * @return the list of all commands
     */
    public static List<ICommand> getCommands() {
        Collections.sort(COMMANDS_LIST);
        return COMMANDS_LIST;
    }

}
