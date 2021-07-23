package re.alwyn974.minecraft.bot.cli;

import org.apache.commons.cli.*;

/**
 * The command line parser
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class CLIParser {

    private static final Options options = new Options();

    /**
     * Parse the command line arguments
     *
     * @param args the arguments
     */
    public static void parse(String... args) throws ParseException {
        addOptions();
        CommandLineParser parser = new DefaultParser();
        parser.parse(options, args);
    }

    private static void addOptions() {
        options.addOption("h", "host", true, "Setup the host value (Default=127.0.0.1)");
        options.addOption("p", "port", true, "Setup the port value (Default=25565)");
        options.addOption("u", "user", true, "email of the user");
    }

}
