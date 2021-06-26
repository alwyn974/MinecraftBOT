package re.alwyn974.minecraft.bot.gui;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.mc.protocol.data.status.handler.ServerPingTimeHandler;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.packetlib.BuiltinFlags;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import re.alwyn974.logger.LoggerFactory;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.chat.TranslateChat;
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
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

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
    private final JButton statusButton = new JButton("Status");
    private final JButton clearButton = new JButton("Clear");
    private final JCheckBox debugBox = new JCheckBox("Debug", Boolean.parseBoolean(debug));
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
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(new JLabel("Host: "));

        topPanel.add(hostField, BorderLayout.PAGE_START);

        topPanel.add(new JLabel("Port: "));
        topPanel.add(portField, BorderLayout.PAGE_START);

        topPanel.add(new JLabel("Email: "));
        topPanel.add(usernameField, BorderLayout.PAGE_START);

        topPanel.add(new JLabel("Password: "));
        topPanel.add(passwordField, BorderLayout.PAGE_START);

        addButton(connectButton);
        addButton(disconnectButton);
        addButton(statusButton);
        addButton(clearButton);

        topPanel.add(debugBox, BorderLayout.PAGE_START);

        this.add(topPanel, BorderLayout.PAGE_START);
    }

    private void addButton(JButton button) {
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        topPanel.add(button, BorderLayout.PAGE_START);
    }

    private void addCenterPanel() {
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setBackground(Color.decode("#2c2f33"));

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
        } else if (e.getSource() == statusButton) {
            new Thread(() -> {
                SessionService sessionService = new SessionService();
                sessionService.setProxy(Proxy.NO_PROXY);

                MinecraftProtocol protocol = new MinecraftProtocol();
                Session client = new TcpClientSession(this.hostField.getText(), Integer.parseInt(this.portField.getText()), protocol);
                client.setFlag(BuiltinFlags.PRINT_DEBUG, this.debugBox.isSelected());
                client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
                client.setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, (ServerInfoHandler) (session, info) -> {
                    MinecraftBOT.getLogger().info("Version: %s, Protocol Version: %d", info.getVersionInfo().getVersionName(), info.getVersionInfo().getProtocolVersion());
                    MinecraftBOT.getLogger().info("Player Count: %d/%d", info.getPlayerInfo().getOnlinePlayers(), info.getPlayerInfo().getMaxPlayers());
                    List<String> players = new ArrayList<>();
                    for (GameProfile player : info.getPlayerInfo().getPlayers())
                        players.add(player.getName());
                    MinecraftBOT.getLogger().info("Players: %s", players.toString());
                    MinecraftBOT.getLogger().info("Description: %s", TranslateChat.translateComponent(info.getDescription()));
                });
                client.setFlag(MinecraftConstants.SERVER_PING_TIME_HANDLER_KEY, (ServerPingTimeHandler) (session, pingTime) ->
                       MinecraftBOT.getLogger().info("Server ping took %d ms", pingTime)
                );
                if (this.debugBox.isSelected())
                    MinecraftBOT.getLogger().debug("Connecting to Minecraft server: %s:%s", hostField.getText(), portField.getText());
                client.connect();
                try {
                    Thread.sleep(1000L);
                    client.disconnect("Disconnected");
                } catch (InterruptedException ex) {
                    MinecraftBOT.getLogger().error("Thread interrupt", ex);
                    client.disconnect("Disconnected");
                }
            }).start();
        } else if (e.getSource() == clearButton)
            logArea.setText("");

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
