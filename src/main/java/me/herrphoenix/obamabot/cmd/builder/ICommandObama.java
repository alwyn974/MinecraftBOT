package me.herrphoenix.obamabot.cmd.builder;

import me.herrphoenix.obamabot.cmd.ObamaCommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.ICommand;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

/**
 * @author HerrPhoenix
 */
public interface ICommandObama extends ICommand {
    @Override
    default String getPrefix() {
        return ObamaCommandHandler.getPrefix();
    }

    @Override
    default boolean needToBeConnected() { return false; }

    default EntityBOT getClient() {
        return ObamaCommandHandler.getClient();
    }
}
