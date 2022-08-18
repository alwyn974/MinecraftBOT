package re.alwyn974.minecraft.bot.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import re.alwyn974.minecraft.bot.MinecraftBOT;

import javax.swing.*;
import java.awt.*;

/**
 * The Frame for the Gui
 *
 * @author <a href="https://github.com/alwyn974">Alwyn974</a>
 * @version 1.0.15
 * @since 1.0.0
 */
public class MCBOTFrame extends JFrame {

    public MCBOTFrame() {
        setSystemLookAndFeel();

        this.setTitle("MinecraftBOT - Dev by Alwyn974 | Obama Edition by HerrPhoenix");
        this.setMinimumSize(new Dimension(800, 350));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(new MCBOTPanel());
        this.pack();

        this.setVisible(true);

        MinecraftBOT.getLogger().info("Starting MinecraftBOT...");
    }

    public void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            MinecraftBOT.getLogger().error("Can't set look and feel", e);
        }
    }

}
