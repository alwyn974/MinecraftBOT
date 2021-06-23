package re.alwyn974.minecraft.bot.entity;

import java.net.Proxy;

public class EntityBOT {

    private final String host;
    private final int port;
    private final Proxy proxy;
    private final String username;
    private final String password;
    private BlockPos connectPos;
    private BlockPos disconnectPos;

    public EntityBOT(String username, String password)  {
        this("localhost", username, password);
    }

    public EntityBOT(String host, String username, String password) {
        this(host, 25565, username, password);
    }

    public EntityBOT(String host, int port, String username, String password) {
        this(host, port, Proxy.NO_PROXY, username, password);
    }

    /**
     * Instanciate the EntityBOT
     * @param host the minecraft server address
     * @param port the minecraft server port
     * @param proxy the proxy
     * @param username the email of the premium account <br><strong>ONLY MOJANG ACCOUNT</strong>
     * @param password the password of the premium account
     */
    public EntityBOT(String host, int port, Proxy proxy, String username, String password) {
        this.host = host;
        this.port = port;
        this.proxy = proxy;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BlockPos getConnectPos() {
        return connectPos;
    }

    public BlockPos getDisconnectPos() {
        return disconnectPos;
    }

    public void setConnectPos(BlockPos connectPos) {
        this.connectPos = connectPos;
    }

    public void setDisconnectPos(BlockPos disconnectPos) {
        this.disconnectPos = disconnectPos;
    }

}
