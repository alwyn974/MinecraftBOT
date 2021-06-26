package re.alwyn974.minecraft.bot.gui;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import re.alwyn974.logger.LoggerFactory;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.entity.EntityBOT;
import re.alwyn974.minecraft.bot.logging.JTextAreaLogHandler;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MCBOTPanel extends JPanel implements ActionListener {

    private final JPanel topPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();

    private final String username = System.getenv("MC_BOT_USERNAME");
    private final String password = System.getenv("MC_BOT_PASSWORD");
    private final String host = System.getenv("MC_BOT_HOST");
    private final String port = System.getenv("MC_BOT_PORT");
    private final String debug = System.getenv("MC_BOT_DEBUG");

    private final JTextField usernameField = new JTextField(username != null ? username : "");
    private final JPasswordField passwordField = new JPasswordField(password != null ? password : "");
    private final JTextField hostField = new JTextField(host != null ? host : "127.0.0.1");
    private final JTextField portField = new JTextField(port != null ? port : "25565");
    private final JTextField outputField = new JTextField();

    private final JButton connectButton = new JButton("Connect");
    private final JButton disconnectButton = new JButton("Disconnect");
    private final JCheckBox debugBox = new JCheckBox("Debug", Boolean.parseBoolean(debug));

    private EntityBOT bot = null;
    private Thread botThread = null;

    public MCBOTPanel() {
        this.setLayout(new BorderLayout());

        addTopPanel();
        addCenterPanel();
        addBottomPanel();
    }

    private void addTopPanel() {
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(new JLabel("Host: "));

        topPanel.add(hostField, BorderLayout.PAGE_START);

        topPanel.add(new JLabel("Port: "));
        topPanel.add(portField, BorderLayout.PAGE_START);

        topPanel.add(new JLabel("Email: "));
        topPanel.add(usernameField, BorderLayout.PAGE_START);

        topPanel.add(new JLabel("Password: "));
        topPanel.add(passwordField, BorderLayout.PAGE_START);

        connectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        connectButton.addActionListener(this);
        topPanel.add(connectButton, BorderLayout.PAGE_START);

        disconnectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        disconnectButton.addActionListener(this);
        topPanel.add(disconnectButton, BorderLayout.PAGE_START);

        topPanel.add(debugBox, BorderLayout.PAGE_START);

        this.add(topPanel, BorderLayout.PAGE_START);
    }

    private void addCenterPanel() {
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setBackground(Color.decode("#2c2f33"));

        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setAutoscrolls(true);
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
                if (bot != null && bot.getClient().isConnected() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    bot.getClient().send(new ClientChatPacket(outputField.getText()));
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
                bot = new EntityBOT(hostField.getText(), Integer.parseInt(portField.getText()), usernameField.getText(), new String(passwordField.getPassword()), debugBox.isSelected());
                try {
                    bot.connect();
                } catch (RequestException ex) {
                    MinecraftBOT.getLogger().error("Can't authenticate", ex);
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
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (bot != null) {
            if (!bot.getClient().isConnected()) {
                if (!this.connectButton.isEnabled())
                    this.connectButton.setEnabled(true);
                setFieldsEnabled(true);
            } else {
                outputField.setEnabled(true);
            }
        }
    }

    private void setFieldsEnabled(boolean enabled) {
        hostField.setEnabled(enabled);
        portField.setEnabled(enabled);
        usernameField.setEnabled(enabled);
        passwordField.setEnabled(enabled);
        connectButton.setEnabled(enabled);
        debugBox.setEnabled(enabled);
        disconnectButton.setEnabled(!enabled);
    }

}
