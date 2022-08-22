package me.herrphoenix.obamabot.cmd.utils;

import re.alwyn974.minecraft.bot.entity.EntityBOT;

/**
 * @author HerrPhoenix
 */
public interface IExecutorPlayer {
    void playerExecutor(EntityBOT bot, String player, String message, String... args);
}
