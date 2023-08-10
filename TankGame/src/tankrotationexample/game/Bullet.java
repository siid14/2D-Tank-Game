package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    private float x,y;
    private float vx, vy;
    private float charge = 1f;
    private float angle;
    private float R = 5;
    private BufferedImage img;
    private Rectangle hitbox;

    // constructor for creating a Bullet object
    public Bullet(float x, float y, BufferedImage img, float angle) {
        this.x = x + 55;
        this.y = y + 20;
        this.img = img;
        this.vx = 0;
        this.vy = 0;
        this.angle = angle;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
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
        x += vx;
        y += vy;

        // check and handle the bullet crossing the game screen borders
        checkBorder();

        this.hitbox.setLocation((int)x, (int)y);
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

    // increase the bullet's charge
    public void increaseCharge() {
        this.charge = this.charge + 0.05f;
    }

    // draw the bullet on the screen
   public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        rotation.scale(this.charge,this.charge); // scale the bullet to make it larger

        Graphics2D buffer = (Graphics2D) g;
        buffer.drawImage(this.img, rotation, null);

       // draw hit-box representation
       buffer.setColor(Color.RED); // You can choose any color you prefer
       buffer.drawRect((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void collides(GameObject obj2) {
    }

    @Override
    public boolean hasCollided() {
        return false;
    }

    // set the bullet's heading (position and angle)
    public void setHeading(float x, float y, float angle) {
       this.x = x + 55;
       this.y = y + 20;
       this.angle = angle;
    }
}
