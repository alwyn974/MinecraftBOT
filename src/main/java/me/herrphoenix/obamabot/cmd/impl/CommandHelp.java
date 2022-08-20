package me.herrphoenix.obamabot.cmd.impl;

import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.cmd.ObamaCommandHandler;
import me.herrphoenix.obamabot.cmd.builder.ICommandObama;
import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

/**
 * @author HerrPhoenix
 */
public class CommandHelp implements ICommandObama {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "display list of cmds";
    }

    @Override
    public String getUsage() {
        return String.format("%s%s", getPrefix(), getName());
    }

    @Override
    public IExecutor executor() {
        return ((bot, message, args) -> {
            StringBuilder bobTheBuilder = new StringBuilder();

            try {
                for (ICommandObama cmd : ObamaCommandHandler.getObamaCommands()) {
                    if (cmd.isAdminCommand()) continue;

                    bobTheBuilder.delete(0, bobTheBuilder.capacity());

                    bobTheBuilder
                            .append("* ")
                            .append(cmd.getPrefix())
                            .append(cmd.getName())
                            .append(" - ")
                            .append(cmd.getDescription())
                            .append(" [")
                            .append(cmd.getUsage())
                            .append("]");

                    ObamaBOT.chat(bobTheBuilder.toString());
                    Thread.sleep(ObamaBOT.CHAT_COOLDOWN);
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }
}
