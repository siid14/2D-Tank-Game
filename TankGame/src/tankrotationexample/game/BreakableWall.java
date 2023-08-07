package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject {
    float x,y;
    BufferedImage img;
    private Rectangle hitbox;
    private int life;
    private GameWorld gw;

    public BreakableWall(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitbox = new Rectangle((int)x,(int)y,this.img.getWidth(),this.img.getHeight());
        this.life = 100;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitbox.getBounds();
    }

    @Override
    public void collides(GameObject obj) {
        if(obj instanceof Bullet){
            //lose life
            this.life--;
            System.out.println("Bullet hit breakable wall so life : " + life + "-1 = " + (life - 1));
        }
    }

    @Override
    public boolean hasCollided() {
        if(this.life <= 0){
            return true;
        }
        return false;
    }


    public void drawImage(Graphics buffer) {
        buffer.drawImage(this.img, (int)x, (int)y, null);

        // System.out.println("BreakableWall drawImage called at x: " + x + ", y: " + y);
    }


}
