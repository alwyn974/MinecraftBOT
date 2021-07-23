package re.alwyn974.minecraft.bot.cli;

import org.apache.commons.cli.*;
import re.alwyn974.minecraft.bot.MinecraftBOT;

/**
 * The command line parser
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.9
 * @since 1.0.9
 */
public class CLIParser {

    private static final Options options = new Options();
    private static CommandLine cmd;

    /**
     * Parse the command line arguments
     *
     * @param args the arguments
     */
    public static void parse(String... args) throws ParseException {
        addOptions();
        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);
        ParseResult result = parseResult();
        if (result.shouldPrintHelp())
            printHelp();
    }

    /**
     * Add the options
     */
    private static void addOptions() {
        options.addOption("h", "host", true, "Setup the host value (Default=127.0.0.1)");
        options.addOption("p", "port", true, "Setup the port value (Default=25565)");
        options.addOption("u", "user", true, "Email of the user");
        options.addOption(null, "password", true, "Password of the user");
        options.addOption("d", "debug", false, "Activate debug");
        options.addOption("s", "status", false, "Display only the status of the server");
        options.addOption(null, "help", false, "Show this help page");
    }

    private static ParseResult parseResult() {
        ParseResult result = new ParseResult();
        result.setHost(cmd.hasOption("h") ? cmd.getOptionValue("h") : MinecraftBOT.getHost());
        result.setPort(Integer.parseInt(cmd.hasOption("p") ? cmd.getOptionValue("p") : MinecraftBOT.getPort()));
        result.setEmail(cmd.hasOption("u") ? cmd.getOptionValue("u") : MinecraftBOT.getUsername());
        result.setPassword(cmd.hasOption("password") ? cmd.getOptionValue("password") : MinecraftBOT.getPassword());
        result.setStatus(cmd.hasOption("s"));
        result.setDebug(cmd.hasOption("d"));
        result.setHelp(cmd.hasOption("help"));
        return result;
    }

    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(MinecraftBOT.getProjectName(), options);
        System.exit(0);
    }

}
