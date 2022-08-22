package me.herrphoenix.obamabot.cmd.utils;

import re.alwyn974.minecraft.bot.cmd.utils.ICommand;

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

    boolean isAdminCommand();
}