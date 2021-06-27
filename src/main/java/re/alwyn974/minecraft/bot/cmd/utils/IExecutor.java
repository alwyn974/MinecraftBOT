package re.alwyn974.minecraft.bot.cmd.utils;

import re.alwyn974.minecraft.bot.entity.EntityBOT;

/**
 * The executor interface, useful to execute command
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.6
 * @since 1.0.6
 */
public interface IExecutor {

    /**
     * Execute the command
     *
     * @param bot     the bot who send the command
     * @param message the orginal message
     * @param args    the arguments
     */
    void execute(EntityBOT bot, String message, String... args);

}
