package re.alwyn974.minecraft.bot.cmd;

import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.chat.TranslateChat;
import re.alwyn974.minecraft.bot.cmd.utils.ICommand;
import re.alwyn974.minecraft.bot.cmd.utils.IExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * The list command, get the player connected
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.8
 * @since 1.0.8
 */
public class ListCommand implements ICommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Get the players connected";
    }

    @Override
    public String getUsage() {
        return String.format("%s%s", getPrefix(), getName());
    }

    @Override
    public boolean needToBeConnected() {
        return true;
    }

    @Override
    public IExecutor executor() {
        return ((bot, message, args) -> {
            List<String> playersName = new ArrayList<>();
            bot.getPlayers().forEach(p -> playersName.add(TranslateChat.getInstance().translateComponent(p.getDisplayName())));
            MinecraftBOT.getLogger().info("Players: %s", playersName.toString());
        });
    }

}
