package re.alwyn974.minecraft.bot.logging;

import re.alwyn974.logger.BasicLogger;
import re.alwyn974.logger.ILogHandler;
import re.alwyn974.logger.LogLevel;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.entity.EntityBOT;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

/**
 * A handler to print text into a JTextArea
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.4
 * @since 1.0.0
 */
public class JTextAreaLogHandler implements ILogHandler, MouseListener {

    private final JTextArea logArea;
    private final BasicLogger logger;

    /**
     * Instanciate the log handler
     *
     * @param logArea the JTextArea to print
     */
    public JTextAreaLogHandler(JTextArea logArea, BasicLogger logger) {
        this.logArea = logArea;
        this.logger = logger;
        this.logArea.addMouseListener(this);
    }

    /**
     * The display of basic log
     *
     * @param level the level of the log
     * @param msg   the message to display
     */
    @Override
    public void broadcastLog(LogLevel level, String msg) {
        SwingUtilities.invokeLater(() -> logArea.append(msg.endsWith("\n") ? msg : msg + "\n"));
    }

    /**
     * The display of log with throwable
     *
     * @param level the level of the log
     * @param th    the throwable to display
     */
    @Override
    public void broadcastThrowable(LogLevel level, Throwable th) {
        String formatted = logger.formatMessage(level, th.getMessage());
        SwingUtilities.invokeLater(() -> logArea.append(formatted.endsWith("\n") ? formatted : formatted + "\n"));
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            int startOffset = this.logArea.viewToModel(new Point(x, y));
            String text = this.logArea.getText();
            int searchHttp = 0;
            int wordEndIndex = 0;
            String[] words = text.split("\\s");

            for (String word : words) {
                if (word.startsWith("https://") || word.startsWith("http://")) {
                    searchHttp = text.indexOf(word);
                    wordEndIndex = searchHttp + word.length();
                    if (startOffset >= searchHttp && startOffset <= wordEndIndex) {
                        try {
                            this.logArea.select(searchHttp, wordEndIndex);
                            EntityBOT.browse(new URI(word));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}