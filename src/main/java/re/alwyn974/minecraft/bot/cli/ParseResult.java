package re.alwyn974.minecraft.bot.cli;

/**
 * The result of parsed args
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.13
 * @since 1.0.9
 */
public class ParseResult {

    private String host;
    private int port;
    private String email;
    private String password;
    private boolean debug;
    private boolean status;
    private boolean help;
    private boolean autoReconnect;

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
    public int getPort() {
        return port;
    }

    /**
     * Get the email
     *
     * @return the email
     */
    public String getEmail() {
        return email;
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
     * Get if debug is activate
     *
     * @return debug value
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Get if status is activate
     *
     * @return status value
     */
    public boolean shouldPrintStatus() {
        return status;
    }

    /**
     * Get if help is activate
     *
     * @return help value
     */
    public boolean shouldPrintHelp() {
        return help;
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
     * Set the host
     *
     * @param host the host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Set the port
     *
     * @param port the port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Set the email
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the password
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set debug
     *
     * @param debug debug value
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * The status value
     *
     * @param status status value
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Set help value
     *
     * @param help help value
     */
    public void setHelp(boolean help) {
        this.help = help;
    }

    /**
     * Set auto reconnect value
     *
     * @param autoReconnect auto reconnect value
     */
    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }

    @Override
    public String toString() {
        return "ParseResult{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", debug=" + debug +
                ", status=" + status +
                ", help=" + help +
                ", autoReconnect=" + autoReconnect +
                '}';
    }
}
