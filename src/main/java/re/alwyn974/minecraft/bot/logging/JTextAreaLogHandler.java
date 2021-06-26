package re.alwyn974.minecraft.bot.logging;

import re.alwyn974.logger.ILogHandler;
import re.alwyn974.logger.LogLevel;
import re.alwyn974.minecraft.bot.MinecraftBOT;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class JTextAreaLogHandler implements ILogHandler {

    private final JTextArea logArea;

    public JTextAreaLogHandler(JTextArea logArea) {
        this.logArea = logArea;
    }

    public void broadcastLog(LogLevel level, String msg) {
        SwingUtilities.invokeLater(() -> logArea.append(msg));
    }

    public void broadcastThrowable(LogLevel level, Throwable th) {
        String formatted = MinecraftBOT.getLogger().formatMessage(level, th.getMessage());
        System.out.println(formatted);
        SwingUtilities.invokeLater(() -> logArea.append(formatted));
    }

}
