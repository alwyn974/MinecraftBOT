package re.alwyn974.minecraft.bot;

import re.alwyn974.minecraft.bot.gui.MCBOTFrame;

import java.awt.GraphicsEnvironment;

public class Main {

    public static void main(String... args) {
        if (GraphicsEnvironment.isHeadless() || args.length > 0)
            runHeadless(args);
        else
            new MCBOTFrame();
    }

    private static void runHeadless(String... args) {
        //TODO: command line bot
    }

}
