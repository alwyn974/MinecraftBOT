package me.herrphoenix.obamabot.cmd.impl;

import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.cmd.builder.ICommandObama;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

/**
 * @author HerrPhoenix
 */
public class CommandPoints implements ICommandObama {
    @Override
    public String getName() {
        return "points";
    }

    @Override
    public String getDescription() {
        return "check the points of a player";
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

            if (ign.equalsIgnoreCase("add")) {
                if (args.length < 3) return;

                String player = args[1];
                int points = Integer.parseInt(args[2]);

                ObamaRegistry.getRegistry().addPoints(player, points);
                return;
            }
            if (ign.equalsIgnoreCase("remove")) {
                if (args.length < 3) return;

                String player = args[1];
                int points = Integer.parseInt(args[2]);

                ObamaRegistry.getRegistry().removePoints(player, points);
                return;
            }
            ObamaBOT.chat(ign + " currently has " + ObamaRegistry.getRegistry().getPoints(ign) + " points");
        };
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }
}
