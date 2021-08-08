package re.alwyn974.minecraft.bot.entity;

import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.auth.service.AuthenticationService;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.packetlib.BuiltinFlags;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import re.alwyn974.minecraft.bot.MinecraftBOT;
import re.alwyn974.minecraft.bot.cli.ParseResult;

import java.net.Proxy;
import java.util.List;

/**
 * The EntityBOT used to store all information about the user
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.13
 * @since 1.0.0
 */
public class EntityBOT {

    private final String host;
    private final int port;
    private final Proxy proxy;
    private final String username;
    private final String password;
    private final boolean debug;
    private final boolean autoReconnect;
    private Session client = null;
    private EntityPos pos = null;
    private double health = -1;
    private double food = -1;
    private Difficulty difficulty = null;
    private List<PlayerListEntry> players = null;

    /**
     * Instanciate the EntityBot with only username and password
     *
     * @param username the email of the premium account <br><strong>ONLY MOJANG ACCOUNT</strong>
     * @param password the password of the account
     */
    public EntityBOT(String username, String password) {
        this("localhost", username, password, false);
    }

    /**
     * @param username the email of the premium account <br><strong>ONLY MOJANG ACCOUNT</strong>
     * @param password the password of the premium account
     * @param debug    activate debug mode
     */
    public EntityBOT(String username, String password, boolean debug) {
        this("127.0.0.1", username, password, debug);
    }

    /**
     * @param host     the minecraft server address
     * @param username the email of the premium account <br><strong>ONLY MOJANG ACCOUNT</strong>
     * @param password the password of the premium account
     */
    public EntityBOT(String host, String username, String password) {
        this(host, 25565, username, password, false);
    }

    /**
     * @param host     the minecraft server address
     * @param username the email of the premium account <br><strong>ONLY MOJANG ACCOUNT</strong>
     * @param password the password of the premium account
     * @param debug    activate debug mode
     */
    public EntityBOT(String host, String username, String password, boolean debug) {
        this(host, 25565, username, password, debug);
    }

    /**
     * @param host     the minecraft server address
     * @param port     the minecraft server port
     * @param username the email of the premium account <br><strong>ONLY MOJANG ACCOUNT</strong>
     * @param password the password of the premium account
     */
    public EntityBOT(String host, int port, String username, String password) {
        this(host, port, username, password, false);
    }

    /**
     * @param host     the minecraft server address
     * @param port     the minecraft server port
     * @param username the email of the premium account <br><strong>ONLY MOJANG ACCOUNT</strong>
     * @param password the password of the premium account
     * @param debug    activate debug mode
     */
    public EntityBOT(String host, int port, String username, String password, boolean debug) {
        this(host, port, Proxy.NO_PROXY, username, password, debug, false);
    }

    /**
     * @param host          the minecraft server address
     * @param port          the minecraft server port
     * @param username      the email of the premium account <br><strong>ONLY MOJANG ACCOUNT</strong>
     * @param password      the password of the premium account
     * @param debug         activate debug mode
     * @param autoReconnect activate auto reconnect mode
     */
    public EntityBOT(String host, int port, String username, String password, boolean debug, boolean autoReconnect) {
        this(host, port, Proxy.NO_PROXY, username, password, debug, autoReconnect);
    }

    /**
     * Instanciate the EntityBOT
     *
     * @param host          the minecraft server address
     * @param port          the minecraft server port
     * @param proxy         the proxy
     * @param username      the email of the premium account <br><strong>ONLY MOJANG ACCOUNT</strong>
     * @param password      the password of the premium account
     * @param debug         activate debug mode
     * @param autoReconnect activate auto reconnect
     */
    public EntityBOT(String host, int port, Proxy proxy, String username, String password, boolean debug, boolean autoReconnect) {
        this.host = host;
        this.port = port;
        this.proxy = proxy;
        this.username = username;
        this.password = password;
        this.debug = debug;
        this.autoReconnect = autoReconnect;
    }

    /**
     * Instanciate the EntityBOT from {@link ParseResult}
     *
     * @param result the result of parsed arguments
     */
    public EntityBOT(ParseResult result) {
        this.host = result.getHost();
        this.port = result.getPort();
        this.username = result.getEmail();
        this.password = result.getPassword();
        this.debug = result.isDebug();
        this.proxy = Proxy.NO_PROXY;
        this.autoReconnect = result.isAutoReconnect();
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
     * Get the password
     *
     * @return the password
     */
    public String getPassword() {
        return password;
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
        if (this.password != null && !this.password.isEmpty()) {
            AuthenticationService authService = new AuthenticationService();
            authService.setUsername(this.getUsername());
            authService.setPassword(this.getPassword());
            authService.setProxy(this.getProxy());
            if (isDebug())
                MinecraftBOT.getLogger().debug("Authenticating with account [%s]", this.getUsername());
            authService.login();
            if (isDebug())
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

}
