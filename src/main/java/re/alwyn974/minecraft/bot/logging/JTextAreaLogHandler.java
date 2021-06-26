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
        SwingUtilities.invokeLater(() -> logArea.append(msg.endsWith("\n") ? msg : msg + "\n"));
    }

    public void broadcastThrowable(LogLevel level, Throwable th) {
        String formatted = MinecraftBOT.getLogger().formatMessage(level, th.getMessage());
        SwingUtilities.invokeLater(() -> logArea.append(formatted.endsWith("\n") ? formatted : formatted + "\n"));
    }

}
