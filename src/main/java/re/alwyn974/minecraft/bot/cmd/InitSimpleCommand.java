package re.alwyn974.minecraft.bot.cmd;

import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.builder.CommandBuilder;
import re.alwyn974.minecraft.bot.builder.CommandBuilderException;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.ICommand;

/**
 * Init all the simple command, created by the {@link CommandBuilder}
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.7
 * @since 1.0.7
 */
public class InitSimpleCommand {

    public void initSimpleCommand() throws CommandBuilderException {
        ICommand difficultyCommand = new CommandBuilder().withName("difficulty")
                .withDescription("Get the difficulty of the server")
                .withUsage(CommandHandler.getPrefix() + "difficulty").withNeedToBeConnected(true)
                .withExecutor((bot, message, args) -> MinecraftBOT.getLogger().info("Difficulty: %s", bot.getDifficulty().name()))
                .build();
        CommandHandler.registerCommand(difficultyCommand);
    }

}
