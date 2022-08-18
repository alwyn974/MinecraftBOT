package me.herrphoenix.obamabot.cmd;

import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.cmd.builder.ICommandObama;
import re.alwyn974.logger.BasicLogger;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.ICommand;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author HerrPhoenix
 */
public class ObamaCommandHandler extends CommandHandler {
    private static String prefix = ObamaBOT.getPrefix();
    private static final HashMap<String, ICommandObama> COMMANDS = new HashMap<>();
    private static final List<ICommandObama> COMMANDS_LIST = new ArrayList<>();

    private static EntityBOT client;

    public ObamaCommandHandler(BasicLogger logger) {
        super(logger);
    }

    public static void setClient(EntityBOT bot) {
        client = bot;
    }

    public static void registerCommand(ICommandObama command) {
        ICommandObama cmd = COMMANDS.put(command.getName(), command);
        COMMANDS_LIST.add(command);
        ObamaBOT.getLogger().info("Register command: %s", command.getName());
        if (cmd != null)
            ObamaBOT.getLogger().error("Duplicated command %s", cmd.getName());
    }

    public boolean execute(EntityBOT bot, String message) {
        final String[] args = message.split(" ");

        if (args[0].startsWith(getPrefix())) {
            String cmd = args[0].substring(1);

            final ICommandObama command = getCommand(cmd);

            if (command != null) {
                final String[] cmdArgs = new String[args.length - 1];

                System.arraycopy(args, 1, cmdArgs, 0, cmdArgs.length);
                if (command.needToBeConnected() && bot == null)
                    logger.error("You need to be connected to a server to do this !");
                else
                    command.executor().execute(bot, message, cmdArgs);
                return true;
            } else if (!cmd.isEmpty())
                logger.error("Command doesn't exist [%s]", cmd);
        }
        return false;
    }

    private ICommandObama getCommand(String name) {
        if (COMMANDS.get(name) != null)
            return (COMMANDS.get(name));
        return null;
    }

    public static EntityBOT getClient() {
        return client;
    }

    public static String getPrefix() {
        return prefix;
    }
}
