package re.alwyn974.minecraft.bot.entity;

import com.github.steveice10.mc.auth.exception.request.AuthPendingException;
import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.auth.service.MsaAuthenticationService;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.auth.util.HTTP;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.packetlib.BuiltinFlags;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.cli.ParseResult;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The EntityBOT used to store all information about the user
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.15
 * @since 1.0.0
 */
public class EntityBOT {

    private final String host;
    private final int port;
    private final Proxy proxy;
    private final String username;
    private final boolean premium;
    private final boolean debug;
    private final boolean autoReconnect;
    private final long reconnectDelay;
    private TcpClientSession client = null;
    private EntityPos pos = null;
    private double health = -1;
    private double food = -1;
    private Difficulty difficulty = null;
    private List<PlayerListEntry> players = null;

    private static final String TENANT_ID = "consumers";
    private static final URI MS_CODE_ENDPOINT = URI.create("https://login.microsoftonline.com/" + TENANT_ID + "/oauth2/v2.0/devicecode");
    private static final String CLIENT_ID = "024b97a3-d354-45e1-8855-75bb813b446d";


    /**
     * Instantiate the EntityBot with only username and password
     *
     * @param username the email of the premium account
     * @param premium  if the account is premium
     */
    public EntityBOT(String username, boolean premium) {
        this("127.0.0.1", username, premium, false);
    }

    /**
     * @param username the email of the premium account
     * @param premium  if the account is premium
     * @param debug    activate debug mode
     */
    public EntityBOT(String username, boolean premium, boolean debug) {
        this("127.0.0.1", username, premium, debug);
    }

    /**
     * @param host     the minecraft server address
     * @param username the email of the premium account
     * @param premium  if the account is premium
     */
    public EntityBOT(String host, String username, boolean premium) {
        this(host, 25565, username, premium, false);
    }

    /**
     * @param host     the minecraft server address
     * @param username the email of the premium account
     * @param premium  if the account is premium
     * @param debug    activate debug mode
     */
    public EntityBOT(String host, String username, boolean premium, boolean debug) {
        this(host, 25565, username, premium, debug);
    }

    /**
     * @param host     the minecraft server address
     * @param port     the minecraft server port
     * @param username the email of the premium account
     * @param premium  if the account is premium
     */
    public EntityBOT(String host, int port, String username, boolean premium) {
        this(host, port, username, premium, false);
    }

    /**
     * @param host     the minecraft server address
     * @param port     the minecraft server port
     * @param username the email of the premium account
     * @param premium  if the account is premium
     * @param debug    activate debug mode
     */
    public EntityBOT(String host, int port, String username, boolean premium, boolean debug) {
        this(host, port, username, premium, debug, false, 1000);
    }

    /**
     * @param host          the minecraft server address
     * @param port          the minecraft server port
     * @param username      the email of the premium account
     * @param premium       if the account is premium
     * @param debug         activate debug mode
     * @param autoReconnect activate auto reconnect mode
     */
    public EntityBOT(String host, int port, String username, boolean premium, boolean debug, boolean autoReconnect, long reconnectDelay) {
        this(host, port, Proxy.NO_PROXY, username, premium, debug, autoReconnect, reconnectDelay);
    }

    /**
     * Instantiate the EntityBOT
     *
     * @param host           the minecraft server address
     * @param port           the minecraft server port
     * @param proxy          the proxy
     * @param username       the email of the premium account
     * @param premium        if the account is premium
     * @param debug          activate debug mode
     * @param autoReconnect  activate auto reconnect
     * @param reconnectDelay delay before reconnect
     */
    public EntityBOT(String host, int port, Proxy proxy, String username, boolean premium, boolean debug, boolean autoReconnect, long reconnectDelay) {
        this.host = host;
        this.port = port;
        this.proxy = proxy;
        this.username = username;
        this.premium = premium;
        this.debug = debug;
        this.autoReconnect = autoReconnect;
        this.reconnectDelay = reconnectDelay;
    }

    /**
     * Instantiate the EntityBOT from {@link ParseResult}
     *
     * @param result the result of parsed arguments
     */
    public EntityBOT(ParseResult result) {
        this.host = result.getHost();
        this.port = result.getPort();
        this.username = result.getEmail();
        this.premium = result.isPremium();
        this.debug = result.isDebug();
        this.proxy = Proxy.NO_PROXY;
        this.autoReconnect = result.isAutoReconnect();
        this.reconnectDelay = result.getReconnectDelay();
    }

    /**
     * Get the host
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Get the port
     *
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Get the Proxy
     *
     * @return the proxy
     */
    public Proxy getProxy() {
        return proxy;
    }

    /**
     * Get the username
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Check if the account is premium
     *
     * @return true if the account is premium
     */
    public boolean isPremium() {
        return premium;
    }

    /**
     * Get if is in debug
     *
     * @return is in debug
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Get if auto reconnect is activate
     *
     * @return auto reconnect value
     */
    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    /**
     * Get reconnect delay
     *
     * @return the delay
     */
    public long getReconnectDelay() {
        return reconnectDelay;
    }

    /**
     * Get the client
     *
     * @return the client
     */
    public Session getClient() {
        return client;
    }

    /**
     * Get the position of the EntityBot
     *
     * @return the position
     */
    public EntityPos getPos() {
        return pos;
    }

    /**
     * Get the health
     *
     * @return the health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Get the food
     *
     * @return the food
     */
    public double getFood() {
        return food;
    }

    /**
     * Get the difficulty
     *
     * @return the difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Get the players online
     *
     * @return the players online
     */
    public List<PlayerListEntry> getPlayers() {
        return players;
    }

    /**
     * Set the health
     *
     * @param health the health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Set the food level
     *
     * @param food the food level
     */
    public void setFood(double food) {
        this.food = food;
    }

    /**
     * Set the position
     *
     * @param pos the new position
     */
    public void setPos(EntityPos pos) {
        this.pos = pos;
    }

    /**
     * Set the difficulty
     *
     * @param difficulty the new difficulty
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Set the players list
     *
     * @param players the new players list
     */
    public void setPlayers(List<PlayerListEntry> players) {
        this.players = players;
    }


    /**
     * Connect the bot to the server
     *
     * @throws RequestException if the premium account is invalid
     */
    public void connect() throws RequestException {
        MinecraftProtocol protocol;
        if (this.isPremium()) {
            MsCodeRequest request = new MsCodeRequest(CLIENT_ID);
            MsaAuthenticationService.MsCodeResponse response = HTTP.makeRequestForm(this.getProxy(), MS_CODE_ENDPOINT, request.toMap(), MsaAuthenticationService.MsCodeResponse.class);
            try {
                MinecraftBOT.getLogger().info("Please go to " + response.verification_uri.toURL() + " to authenticate your account - Code: " + response.user_code);
            } catch (MalformedURLException e) {
                MinecraftBOT.getLogger().error("Error while trying to get the url of the authentication page", e);
            }
            browse(response.verification_uri);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(response.user_code), null);

            AuthenticationService authService = new MsaAuthenticationService(CLIENT_ID, response.device_code);
            authService.setUsername(this.getUsername());
            authService.setProxy(this.getProxy());
            if (isDebug())
                MinecraftBOT.getLogger().debug("Authenticating with account [%s]", this.getUsername());

            int retryMax = 20;
            while (true) {
                try {
                    authService.login();
                    break;
                } catch (RequestException e) {
                    if (e instanceof AuthPendingException) {
                        MinecraftBOT.getLogger().debug("Authentication is pending, waiting for user to authenticate...");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ignored) {
                        }
                        if (retryMax == 0)
                            throw e;
                        retryMax--;
                    } else
                        throw e;
                }
            }

            if (isDebug() && authService.isLoggedIn())
                MinecraftBOT.getLogger().debug("Successfully authenticated [%s]", authService.getSelectedProfile().getName());

            protocol = new MinecraftProtocol(authService.getSelectedProfile(), authService.getAccessToken());
        } else
            protocol = new MinecraftProtocol(this.username);

        SessionService sessionService = new SessionService();
        sessionService.setProxy(this.getProxy());

        client = new TcpClientSession(this.getHost(), this.getPort(), protocol);
        client.setFlag(BuiltinFlags.PRINT_DEBUG, this.isDebug());
        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        client.addListener(new MCBOTSessionAdapter(this));
        if (isDebug()) {
            MinecraftBOT.getLogger().debug("Connecting to Minecraft server: %s:%d", this.getHost(), this.getPort());
            if (this.getProxy() != Proxy.NO_PROXY)
                MinecraftBOT.getLogger().debug("Connecting with a Proxy: %s", this.getProxy().toString());
        }
        client.connect();
    }

    public static void browse(URI uri) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                MinecraftBOT.getLogger().error("Error while trying to open the authentication page", e);
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + uri);
            } catch (IOException e) {
                MinecraftBOT.getLogger().error("Error while trying to open the authentication page", e);
            }
        }
    }

    static class MsCodeRequest {
        private final String client_id;
        private final String scope;

        protected MsCodeRequest(String clientId) {
            this.client_id = clientId;
            this.scope = "XboxLive.signin offline_access";
        }

        public Map<String, String> toMap() {
            Map<String, String> map = new HashMap<>();

            map.put("client_id", client_id);
            map.put("scope", scope);

            return map;
        }
    }
}
