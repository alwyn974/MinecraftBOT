package me.herrphoenix.obamabot.cmd;

import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.cmd.utils.ICommandObama;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
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
        return (bot, message, args) -> {
            if (args == null || args.length == 0 || args[0] == null) return;

            String ign = args[0];

            if (ObamaRegistry.getRegistry().hasLifetime(ign)) {
                ObamaBOT.chat(ign + " is already added to this category");
                return;
            }

            ObamaRegistry.getRegistry().addLifetime(ign);
            ObamaBOT.chat(ign + " will not be taxed");
        };
    }

    @Override
    public boolean isAdminCommand() {
        return true;
    }
}
