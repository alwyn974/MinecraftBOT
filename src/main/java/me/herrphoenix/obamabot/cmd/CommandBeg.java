package me.herrphoenix.obamabot.cmd;

import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.cmd.utils.ICommandObamaPlayer;
import me.herrphoenix.obamabot.cmd.utils.IExecutorPlayer;

/**
 * @author HerrPhoenix
 */
public class CommandBeg implements ICommandObamaPlayer {
    @Override
    public boolean isAdminCommand() {
        return true;
    }

    @Override
    public String getName() {
        return "beg";
    }

    @Override
    public String getDescription() {
        return "beg obama for points";
    }

    @Override
    public String getUsage() {
        return String.format("%s%s", getPrefix(), getName());
    }

    @Override
    public IExecutorPlayer playerExecutor() {
        return ((bot, player, message, args) -> {
            if (!ObamaBOT.isBegCooldown) {
                ObamaBOT.isBegCooldown = true;
                ObamaBOT.handleBeg(player);
            }
        });
    }
}
