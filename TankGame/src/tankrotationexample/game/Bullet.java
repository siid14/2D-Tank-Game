package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    private float x,y;
    private float vx, vy;
    private float angle;
    private float R = 5;
    private BufferedImage img;

    // constructor for creating a Bullet object
    public Bullet(float x, float y, BufferedImage img, float angle) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.vx = 0;
        this.vy = 0;
        this.angle = angle;
    }

    // getter methods for x and y positions
    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    // update the bullet's position
    void update(){
        // calculate the velocity components based on the angle
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));

        // update the bullet's position
        x -= vx;
        y -= vy;

        // check and handle the bullet crossing the game screen borders
        checkBorder();
    }

    // check and handle the bullet crossing the game screen borders
    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
    }

    /*public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int)x, (int)y, null);
    }*/

    // draw the bullet on the screen
   public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        rotation.scale(5,5); // scale the bullet to make it larger

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }
}
