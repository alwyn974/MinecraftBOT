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
 * @version 1.0.8
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
        ICommand lifeCommand = new CommandBuilder().withName("health")
                .withDescription("Get the health of the player")
                .withUsage(CommandHandler.getPrefix() + "health").withNeedToBeConnected(true)
                .withExecutor((bot, message, args) -> MinecraftBOT.getLogger().info("Health: %g", bot.getHealth()))
                .build();
        CommandHandler.registerCommand(lifeCommand);
        ICommand foodCommand = new CommandBuilder().withName("food")
                .withDescription("Get the food of the player")
                .withUsage(CommandHandler.getPrefix() + "food").withNeedToBeConnected(true)
                .withExecutor((bot, message, args) -> MinecraftBOT.getLogger().info("Food: %g", bot.getFood()))
                .build();
        CommandHandler.registerCommand(foodCommand);
        ICommand posCommand = new CommandBuilder().withName("pos")
                .withDescription("Get the position of the player")
                .withUsage(CommandHandler.getPrefix() + "pos").withNeedToBeConnected(true)
                .withExecutor((bot, message, args) -> MinecraftBOT.getLogger().info("Position: %s", bot.getPos()))
                .build();
        CommandHandler.registerCommand(posCommand);
    }

}
