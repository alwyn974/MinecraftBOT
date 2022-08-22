package me.herrphoenix.obamabot.cmd.utils;

import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

/**
 * @author HerrPhoenix
 */
public interface ICommandObamaPlayer extends ICommandObama {
    @Override
    default IExecutor executor() {
        return (IExecutor) playerExecutor();
    }

    IExecutorPlayer playerExecutor();
}
