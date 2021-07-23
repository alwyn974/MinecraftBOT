package re.alwyn974.minecraft.bot.cli;

/**
 * The resulf of parsed args
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.9
 * @since 1.0.9
 */
public class ParseResult {

    private String host;
    private Integer port;
    private String email;
    private String password;
    private Boolean debug;
    private Boolean status;
    private Boolean help;

    /**
     * Get the host
     *
     * @return the host
     */
    public String getHost() {
        return host;
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
     * Get the port
     *
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Set the port
     *
     * @param port the port
     */
    public void setPort(Integer port) {
        this.port = port;
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
     * Set the email
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Set the password
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get if debug is activate
     *
     * @return debug value
     */
    public Boolean isDebug() {
        return debug;
    }

    /**
     * Set debug
     *
     * @param debug debug value
     */
    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    /**
     * Get if status is activate
     *
     * @return status value
     */
    public Boolean shouldPrintStatus() {
        return status;
    }

    /**
     * The status value
     *
     * @param status status value
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * Get if help is activate
     *
     * @return help value
     */
    public Boolean shouldPrintHelp() {
        return help;
    }

    /**
     * Set help value
     *
     * @param help help value
     */
    public void setHelp(Boolean help) {
        this.help = help;
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
                '}';
    }
}
