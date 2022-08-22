package me.herrphoenix.obamabot.cmd;

import me.herrphoenix.obamabot.ObamaBOT;
import me.herrphoenix.obamabot.cmd.utils.ICommandObama;
import me.herrphoenix.obamabot.registry.ObamaRegistry;
import me.herrphoenix.obamabot.utils.Utils;
import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

import java.util.Map;

/**
 * @author HerrPhoenix
 */
public class CommandPointsTop implements ICommandObama {

    @Override
    public String getName() {
        return "ptop";
    }

    @Override
    public String getDescription() {
        return "top points leaderboard";
    }

    @Override
    public String getUsage() {
        return String.format("%s%s", getPrefix(), getName());
    }

    @Override
    public IExecutor executor() {
        return ((bot, message, args) -> {
            String[] lb = new String[16];
            lb[0] = "Top Obama Points:";

            Map<String, Integer> points = Utils.sortByValue(ObamaRegistry.getRegistry().getPoints(), false);

            int i = 1;

            for (String player : points.keySet()) {
                if (i >= 16) break;

                StringBuilder bobTheBuilder = new StringBuilder();

                int amount = points.get(player);
                bobTheBuilder
                        .append(i)
                        .append("> ")
                        .append(player)
                        .append(" - ")
                        .append(amount)
                        .append(" points");

                lb[i] = bobTheBuilder.toString();

                i++;
            }

            for (String str : lb) {
                if (str == null) continue;

                ObamaBOT.chat(str);

                try {
                    Thread.sleep(ObamaBOT.CHAT_COOLDOWN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean isAdminCommand() {
        return true;
    }
}
