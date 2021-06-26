package re.alwyn974.minecraft.bot.cmd.utils;

import org.jetbrains.annotations.NotNull;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

/**
 * The interface for the Command
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @since 1.0.4
 * @version 1.0.4
 */
public interface ICommand extends Comparable<ICommand> {

    /**
     * Get the command name
     * @return the command name
     */
    String getName();

    /**
     * Get the command prefix
     * @return the command prefix
     */
    default String getPrefix() {
        return CommandHandler.getPrefix();
    }

    String getDescription();

    /**
     * Get the usage of the command
     * @return the usage of the command
     */
    String getUsage();

    /**
     * Execute the command
     * @param bot the bot who send the command
     * @param message the orginal message
     * @param args the arguments
     */
    void execute(EntityBOT bot, String message, String... args);

    /**
     * Used to sort the command list
     * @param command the other command
     * @return a value in ASCII like {@link String#compareTo}
     */
    @Override
    default int compareTo(@NotNull ICommand command) {
        return this.getName().compareTo(command.getName());
    }

}
