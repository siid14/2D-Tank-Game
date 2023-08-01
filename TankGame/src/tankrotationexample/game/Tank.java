package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Resources.ResourceManager;
import tankrotationexample.Resources.ResourcePool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anthony-pc
 */
public class Tank{

    private float x;
    private float y;
    private float screen_x,screen_y;
    private float vx;
    private float vy;
    private float angle;
    List<Bullet> ammo = new ArrayList<>();
    private float R = 5;
    private float ROTATIONSPEED = 3.0f;
    static ResourcePool<Bullet> bPool;
    long timeSinceLastShot = 0L;
    long cooldown = 4000;
    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private ResourceManager Resources;

    /*static {
        bPool = new ResourcePool<>("bullet", 300);
        bPool.fillPool(Bullet.class, 300);
    }*/

    // constructor for creating a Tank object
    Tank(float x, float y, float vx, float vy, float angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
    }

    // getter and setter methods for x and y positions
    void setX(float x){ this.x = x; }

    void setY(float y) { this. y = y;}

    public float getScreen_x(){
        return screen_x;
    };

    public float getScreen_y(){
        return screen_y;
    };

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    // methods to handle key presses for tank movement and shooting
    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    // methods to handle shooting presses and releases
    public void toggleShootPressed() {
        this.ShootPressed = true;
    }

    public void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    // method to update tank's position and handle shooting
    void update() {
        // update tank's position based on the key presses
        if (this.UpPressed) {
            this.moveForwards();
        }

        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        // handle shooting
        if(this.ShootPressed && (this.timeSinceLastShot
         + this.cooldown) < System.currentTimeMillis()) {
            this.timeSinceLastShot = System.currentTimeMillis();

            // update the position of all bullets in the ammo list
            this.ammo.add(new Bullet(x,y, Resources.getSprite("bullet"), angle));
        }

        this.ammo.forEach((bullet -> bullet.update()));
        /*System.out.println(this.ammo.size());*/

        /*if(b != null){
            this.b.update();
        }*/

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    // helper method to move the tank backwards
    private void moveBackwards() {
        vx =  Math.round(R * Math.cos(Math.toRadians(angle)));
        vy =  Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       checkBorder();
       centerScreen();
    }

    // helper method to move the tank moveFowards
    private void moveForwards() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        centerScreen();
    }

    // helper method to center the screen around the tank's position
    private void centerScreen(){
        this.screen_x = this.x - GameConstants.GAME_SCREEN_WIDTH/4;
        this.screen_y = this.y - GameConstants.GAME_SCREEN_HEIGHT/2;

        if (screen_x < 0) {
            screen_x = 0;
        }

        if (screen_y < 0) {
            screen_y = 0;
        }

        if (screen_x > (GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2)) {
            screen_x = GameConstants.GAME_WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2;
        }

        if (screen_y > (GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT)) {
            screen_y = GameConstants.GAME_WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
        }
    }

    // helper method to check and handle the tank crossing the game world borders
    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_WORLD_WIDTH - 88) {
            x = GameConstants.GAME_WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_WORLD_HEIGHT - 80) {
            y = GameConstants.GAME_WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    // draw the tank and its bullets on the screen
    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.RED);
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());

        // draw all the bullets in the ammo list on the screen
        this.ammo.forEach(b -> b.drawImage(g2d));

        /*if(b != null){
            this.b.drawImage(g2d);
        }*/
        g2d.setColor(Color.GREEN);
        g2d.drawRect((int)x, (int)y-20, 100,15);

        long currentWidth = 100 - ((this.timeSinceLastShot + this.cooldown) - System.currentTimeMillis())/40;
        if(currentWidth > 100){
            currentWidth = 100;
        }
        g2d.fillRect((int)x, (int)y-20, (int)currentWidth,15);

    }


}
