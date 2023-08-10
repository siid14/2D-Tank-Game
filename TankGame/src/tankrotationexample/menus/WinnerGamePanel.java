package tankrotationexample.menus;

import tankrotationexample.Launcher;
import tankrotationexample.Resources.ResourceManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WinnerGamePanel extends JPanel {

    private BufferedImage menuBackground;
    private final Launcher lf;
    private String winner;

    public WinnerGamePanel(Launcher lf, String winner) {
        this.lf = lf;
        this.winner = winner;
        menuBackground = ResourceManager.getSprite("menu");
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JButton winnerButton = new JButton("Winner is " + this.winner); // Create a button with winner's name
        winnerButton.setFont(new Font("Courier New", Font.BOLD, 24));
        winnerButton.setBounds(120, 300, 250, 50);
        winnerButton.addActionListener((actionEvent -> this.lf.setFrame("game"))); // Add action listener

        // Add the winnerButton to the panel
        this.add(winnerButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
