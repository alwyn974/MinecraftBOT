package re.alwyn974.minecraft.bot.cmd;

import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.cmd.utils.ICommand;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

/**
 * Difficulty Command, retrieve the difficulty of the server
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @since 1.0.5
 * @version 1.0.5
 */
public class DifficultyCommand implements ICommand {

    @Override
    public String getName() {
        return "difficulty";
    }

    @Override
    public String getDescription() {
        return "Get the difficulty of the server";
    }

    @Override
    public String getUsage() {
        return String.format("%s%s", getPrefix(), getName());
    }

    @Override
    public boolean needToBeConnected() {
        return true;
    }

    @Override
    public void execute(EntityBOT bot, String message, String... args) {
        MinecraftBOT.getLogger().info("Difficulty: %s", bot.getDifficulty().name());
    }

}
