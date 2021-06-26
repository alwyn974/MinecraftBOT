package re.alwyn974.minecraft.bot.gui;

import re.alwyn974.minecraft.bot.MinecraftBOT;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import java.awt.Dimension;

public class MCBOTFrame extends JFrame {

    public MCBOTFrame() {
        setSystemLookAndFeel();

        this.setTitle("MinecraftBOT - Dev by Alwyn974");
        this.setPreferredSize(new Dimension(854, 480));
        this.setMinimumSize(new Dimension(800, 100)); // TODO: 26/06/2021
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(new MCBOTPanel());
        this.pack();

        this.setVisible(true);

        MinecraftBOT.getLogger().info("Starting MinecraftBOT...");
    }

    public void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            MinecraftBOT.getLogger().error("Can't set look and feel", e);
        }
    }

}
