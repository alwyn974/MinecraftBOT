package re.alwyn974.minecraft.bot;

import re.alwyn974.logger.BasicLogger;
import re.alwyn974.logger.LoggerFactory;

public class MinecraftBOT {

    private static final String PROJECT_NAME = "MinecraftBOT";
    private static final BasicLogger logger = LoggerFactory.getLogger(PROJECT_NAME);

    public static BasicLogger getLogger() {
        return logger;
    }

    public static String getProjectName() {
        return PROJECT_NAME;
    }

}
