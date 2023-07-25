package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall {
    private double x;
    private double y;
    private int width;
    private int height;
    private int health;

    // add any other properties or constants related to the wall as needed
    public Wall(double x, double y, int width, int height, int initialHealth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = initialHealth;
    }

    // getters and setters for the properties of the wall
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHealth() {
        return health;
    }

    // method to handle collisions with bullets or tanks
    public void handleCollision() {
        // reduce the health of the wall when it is hit by a bullet or a tank
        health--;
        // Perform any other actions related to the collision, such as checking for destruction of the wall
    }

    // method to check if the wall is destroyed
    public boolean isDestroyed() {
        return health <= 0;
    }

    // method to draw the wall on the screen
    public void draw(Graphics2D g2) {
        // use the graphics to draw the wall
        g2.setColor(Color.GRAY);
        g2.fillRect((int) x, (int) y, width, height); // customize the appearance of the wall

    }
}
