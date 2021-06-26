package re.alwyn974.minecraft.bot;

import re.alwyn974.logger.BasicLogger;
import re.alwyn974.logger.LoggerFactory;

public class MinecraftBOT {

    private static final String PROJECT_NAME = "MinecraftBOT";
    private static final BasicLogger logger = LoggerFactory.getLogger("MinecraftBOT");

    public static BasicLogger getLogger() {
        return logger;
    }

    public static String getProjectName() {
        return PROJECT_NAME;
    }

    /*private static final String HOST = "play.yggdrasil-mc.eu";
    private static final int PORT = 25565;
    private static final Proxy AUTH_PROXY = Proxy.NO_PROXY;
    private static final String USERNAME = System.getenv("MC_BOT_USERNAME");
    private static final String PASSWORD = System.getenv("MC_BOT_PASSWORD");

    public static void main(String[] args) {
        status();
        login();
    }

    private static void status() {
        SessionService sessionService = new SessionService();
        sessionService.setProxy(AUTH_PROXY);

        MinecraftProtocol protocol = new MinecraftProtocol();
        Session client = new TcpClientSession(HOST, PORT, protocol);
        client.setFlag(BuiltinFlags.PRINT_DEBUG, true);
        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        client.setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, (ServerInfoHandler) (session, info) -> {
            System.out.println("Version: " + info.getVersionInfo().getVersionName() + ", " + info.getVersionInfo().getProtocolVersion());
            System.out.println("Player Count: " + info.getPlayerInfo().getOnlinePlayers() + " / " + info.getPlayerInfo().getMaxPlayers());
            System.out.println("Players: " + Arrays.toString(info.getPlayerInfo().getPlayers()));
            System.out.println("Description: " + TranslateChat.translateComponent(info.getDescription()));
            //System.out.println("Icon: " + Arrays.toString(info.getIconPng()));
        });
        client.setFlag(MinecraftConstants.SERVER_PING_TIME_HANDLER_KEY, (ServerPingTimeHandler) (session, pingTime) -> System.out.println("Server ping took " + pingTime + "ms"));
        client.connect();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect("Finished");
    }

    private static void login() {
        MinecraftProtocol protocol;
        try {
            AuthenticationService authService = new AuthenticationService();
            authService.setUsername(USERNAME);
            authService.setPassword(PASSWORD);
            authService.setProxy(AUTH_PROXY);
            authService.login();
            protocol = new MinecraftProtocol(authService.getSelectedProfile(), authService.getAccessToken());
            System.out.println("Successfully authenticated user.");
        } catch (RequestException e) {
            e.printStackTrace();
            return;
        }

        SessionService sessionService = new SessionService();
        sessionService.setProxy(AUTH_PROXY);

        Session client = new TcpClientSession(HOST, PORT, protocol);
        client.setFlag(BuiltinFlags.PRINT_DEBUG, true);
        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        client.addListener(new SessionAdapter() {
            @Override
            public void packetReceived(PacketReceivedEvent event) {
                if (event.getPacket() instanceof ServerJoinGamePacket) {
                    event.getSession().send(new ClientChatPacket("Hello"));
                } else if (event.getPacket() instanceof ServerChatPacket) {
                    Component message = event.<ServerChatPacket>getPacket().getMessage();
                    System.out.println(TranslateChat.translateComponent(message));
                    //event.getSession().disconnect("Finished");
                }
                //System.out.println(event.getPacket().getClass().getName());
            }

            @Override
            public void disconnected(DisconnectedEvent event) {
                System.out.println("Disconnected: " + event.getReason());
                if (event.getCause() != null) {
                    event.getCause().printStackTrace();
                }
            }
        });
        client.connect();
    }*/

}
