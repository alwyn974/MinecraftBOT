package me.herrphoenix.obamabot.cmd.impl;

import me.herrphoenix.obamabot.cmd.builder.ICommandObama;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

/**
 * @author HerrPhoenix
 */
public class CommandExempt implements ICommandObama {
    @Override
    public String getName() {
        return "exempt";
    }

    @Override
    public String getDescription() {
        return "exempt a player from taxes";
    }

    @Override
    public String getUsage() {
        return String.format("%s%s <player>", getPrefix(), getName());
    }

    @Override
    public IExecutor executor() {
        return (bot, message, args) -> CommandHandler.getCommands().forEach(cmd -> {
            String ign = args[0];
            ObamaRegistry.getRegistry().addLifetime(ign);
            bot.getObama().chat(ign + " will not be taxed");
        });
    }
}
