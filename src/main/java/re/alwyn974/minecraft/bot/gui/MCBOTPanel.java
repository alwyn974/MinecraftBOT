package re.alwyn974.minecraft.bot.gui;

import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import re.alwyn974.logger.LoggerFactory;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.cmd.utils.CommandHandler;
import re.alwyn974.minecraft.bot.entity.EntityBOT;
import re.alwyn974.minecraft.bot.logging.JTextAreaLogHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The Panel of the Gui
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.15
 * @since 1.0.0
 */
public class MCBOTPanel extends JPanel implements ActionListener {

    private final JPanel topPanel = new JPanel();
    private final JPanel topTopPanel = new JPanel();
    private final JPanel topBottomPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();

    private final JTextField usernameField = new JTextField(MinecraftBOT.getUsername());
    private final JPasswordField passwordField = new JPasswordField(MinecraftBOT.getPassword());
    private final JTextField hostField = new JTextField(MinecraftBOT.getHost());
    private final JTextField portField = new JTextField(MinecraftBOT.getPort());
    private final JTextField outputField = new JTextField();

    private final JButton connectButton = new JButton("Connect");
    private final JButton disconnectButton = new JButton("Disconnect");
    private final JButton statusButton = new JButton("Status");
    private final JButton clearButton = new JButton("Clear");
    private final JCheckBox debugBox = new JCheckBox("Debug", Boolean.parseBoolean(MinecraftBOT.getDebug()));
    private final JCheckBox autoReconnectBox = new JCheckBox("Auto Reconnect", Boolean.parseBoolean(MinecraftBOT.getAutoReconnect()));
    private final JSpinner reconnectDelay = new JSpinner();
    private final JTextArea logArea = new JTextArea();

    private EntityBOT bot = null;
    private Thread botThread = null;

    public MCBOTPanel() {
        this.setLayout(new BorderLayout());

        addTopPanel();
        addCenterPanel();
        addBottomPanel();
    }

    private void addTopPanel() {
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topTopPanel.setLayout(new BoxLayout(topTopPanel, BoxLayout.X_AXIS));
        topBottomPanel.setLayout(new BoxLayout(topBottomPanel, BoxLayout.X_AXIS));

        topTopPanel.add(new JLabel("Host: "));
        topTopPanel.add(hostField, BorderLayout.PAGE_START);

        topTopPanel.add(new JLabel("Port: "));
        topTopPanel.add(portField, BorderLayout.PAGE_START);

        topTopPanel.add(new JLabel("Email: "));
        topTopPanel.add(usernameField, BorderLayout.PAGE_START);

        topTopPanel.add(new JLabel("Password: "));
        topTopPanel.add(passwordField, BorderLayout.PAGE_START);

        topBottomPanel.add(autoReconnectBox, BorderLayout.PAGE_START);
        topBottomPanel.add(new JLabel("| (ms): "));
        reconnectDelay.setValue(Long.parseLong(MinecraftBOT.getReconnectDelay()));
        topBottomPanel.add(reconnectDelay);
        topBottomPanel.add(debugBox, BorderLayout.PAGE_START);

        addButton(connectButton);
        disconnectButton.setEnabled(false);
        addButton(disconnectButton);
        addButton(statusButton);
        addButton(clearButton);

        topPanel.add(topTopPanel);
        topPanel.add(topBottomPanel);
        this.add(topPanel, BorderLayout.PAGE_START);
    }

    private void addButton(JButton button) {
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        topBottomPanel.add(button, BorderLayout.PAGE_START);
    }

    private void addCenterPanel() {
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        logArea.setEditable(false);
        logArea.setAutoscrolls(true);
        logArea.setLineWrap(true);
        jScrollPane.getViewport().setView(logArea);

        LoggerFactory.addSharedHandler(new JTextAreaLogHandler(logArea));

        this.add(jScrollPane, BorderLayout.CENTER);
    }

    private void addBottomPanel() {
        bottomPanel.setLayout(new BorderLayout());
        JLabel chatLabel = new JLabel("Chat: ");
        bottomPanel.add(chatLabel, BorderLayout.WEST);

        outputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!new CommandHandler().execute(bot, outputField.getText()) && bot != null && bot.getClient().isConnected())
                        bot.getClient().send(new ServerboundChatPacket(outputField.getText()));
                    outputField.setText("");
                }
            }
        });
        bottomPanel.add(outputField, BorderLayout.CENTER);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectButton) {
            setFieldsEnabled(false);
            botThread = new Thread(() -> {
                try {
                    long delay = Long.parseLong(reconnectDelay.getValue().toString());
                    bot = new EntityBOT(hostField.getText(), Integer.parseInt(portField.getText()), usernameField.getText(), new String(passwordField.getPassword()), debugBox.isSelected(), autoReconnectBox.isSelected(), delay);
                    bot.connect();
                } catch (Exception ex) {
                    MinecraftBOT.getLogger().error("Error: %s", ex.getMessage());
                    ex.printStackTrace();
                    setFieldsEnabled(true);
                    botThread.interrupt();
                }
            });
            botThread.start();
        } else if (e.getSource() == disconnectButton) {
            if (bot != null)
                bot.getClient().disconnect("Disconnected");
            if (botThread != null)
                botThread.interrupt();
            setFieldsEnabled(true);
        } else if (e.getSource() == statusButton)
            MinecraftBOT.retrieveStatus(hostField.getText(), Integer.parseInt(portField.getText()), debugBox.isSelected());
        else if (e.getSource() == clearButton)
            logArea.setText("");
    }

    private void setFieldsEnabled(boolean enabled) {
        hostField.setEnabled(enabled);
        portField.setEnabled(enabled);
        usernameField.setEnabled(enabled);
        passwordField.setEnabled(enabled);
        connectButton.setEnabled(enabled);
        debugBox.setEnabled(enabled);
        autoReconnectBox.setEnabled(enabled);
        reconnectDelay.setEnabled(enabled);
        disconnectButton.setEnabled(!enabled);
    }

}
