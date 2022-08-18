package me.herrphoenix.obamabot.cmd.impl;

import me.herrphoenix.obamabot.cmd.builder.ICommandObama;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

/**
 * @author HerrPhoenix
 */
public class CommandCheck implements ICommandObama {
    @Override
    public String getName() {
        return "check";
    }

    @Override
    public String getDescription() {
        return "check player's taxes";
    }

    @Override
    public String getUsage() {
        return String.format("%s%s <player>", getPrefix(), getName());
    }

    @Override
    public IExecutor executor() {
        return (bot, message, args) -> {
            String ign = args[0];

            if (ObamaRegistry.getRegistry().hasLifetime(ign)) {
                bot.getObama().chat(ign + " has lifetime");
                return;
            }
            if (ObamaRegistry.getRegistry().hasHourly(ign)) {
                bot.getObama().chat(ign + " has paid a hourly tax and it will expire on " +
                        ObamaRegistry.getRegistry().getExpire(ign));
                return;
            }
            bot.getObama().chat(ign + " has not paid anything.");
        };
    }
}
