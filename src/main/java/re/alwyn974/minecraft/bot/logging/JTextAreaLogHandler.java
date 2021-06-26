package re.alwyn974.minecraft.bot.logging;

import re.alwyn974.logger.ILogHandler;
import re.alwyn974.logger.LogLevel;
import re.alwyn974.minecraft.bot.MinecraftBOT;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * A handler to print text into a JTextArea
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @since 1.0.0
 * @version 1.0.4
 */
public class JTextAreaLogHandler implements ILogHandler {

    private final JTextArea logArea;

    /**
     * Instanciate the log handler
     * @param logArea the JTextArea to print
     */
    public JTextAreaLogHandler(JTextArea logArea) {
        this.logArea = logArea;
    }

    /**
     * The display of basic log
     * @param level the level of the log
     * @param msg the message to display
     */
    @Override
    public void broadcastLog(LogLevel level, String msg) {
        SwingUtilities.invokeLater(() -> logArea.append(msg.endsWith("\n") ? msg : msg + "\n"));
    }

    /**
     * The display of log with throwable
     * @param level the level of the log
     * @param th the throwable to display
     */
    @Override
    public void broadcastThrowable(LogLevel level, Throwable th) {
        String formatted = MinecraftBOT.getLogger().formatMessage(level, th.getMessage());
        SwingUtilities.invokeLater(() -> logArea.append(formatted.endsWith("\n") ? formatted : formatted + "\n"));
    }

}
