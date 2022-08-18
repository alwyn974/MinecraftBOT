package me.herrphoenix.obamabot.cmd.impl;

import me.herrphoenix.obamabot.cmd.builder.ICommandObama;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

/**
 * @author HerrPhoenix
 */
public class CommandTriggerEntry implements ICommandObama {
    @Override
    public String getName() {
        return "triggerEntry";
    }

    @Override
    public String getDescription() {
        return "debug command to test plot entrance";
    }

    @Override
    public String getUsage() {
        return String.format("%s%s <player>", getPrefix(), getName());
    }

    @Override
    public IExecutor executor() {
        return (bot, message, args) -> CommandHandler.getCommands().forEach(cmd -> {
            String ign = args[0];
            try {
                bot.getObama().chat("Triggering plot entry for " + ign + " in 5 seconds.");
                Thread.sleep(5000);
                bot.getObama().handlePlayer(ign);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
