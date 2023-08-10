package tankrotationexample.game;

import jdk.jfr.consumer.RecordedStackTrace;
import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    // factory method to create a new instance of a GameObject based on its type
    public static GameObject newInstance(String type, float x, float y) throws UnsupportedOperationException {
        return switch (type) {
            case "9","3" -> new Wall(x, y, ResourceManager.getSprite("unbreak")); // create Wall object
            case "2" ->  new BreakableWall(x, y, ResourceManager.getSprite("break2")); // create BreakableWall object
            case "4" -> new Health(x, y, ResourceManager.getSprite("health")); // create Health object
            case "5" -> new Speed(x, y, ResourceManager.getSprite("speed")); // create Speed object
            case "6"->  new Shield(x, y, ResourceManager.getSprite("shield")); // create Shield object
            default -> throw new UnsupportedOperationException(); // unsupported type
        };
    }

    // draw the GameObject
    public void drawImage(Graphics g) {};

    // get the hitbox of the GameObject
    public abstract Rectangle getHitBox();

    // handle collision with another GameObject
    public abstract void collides(GameObject obj2);

    // check if the GameObject has collided with something
    public abstract boolean hasCollided();
}
