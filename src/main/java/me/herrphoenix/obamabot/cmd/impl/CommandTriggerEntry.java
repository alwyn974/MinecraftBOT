package me.herrphoenix.obamabot.cmd.impl;

import me.herrphoenix.obamabot.cmd.builder.ICommandObama;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

import java.util.Timer;
import java.util.TimerTask;

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
        return (bot, message, args) -> {
            String ign = args[0];

            bot.getObama().chat("Triggering plot entry for " + ign + " in 5 seconds.");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    bot.getObama().handlePlayer(ign);
                }
            }, 5000);
        };
    }
}