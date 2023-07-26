package tankrotationexample.game;

import tankrotationexample.Resources.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GameObject {

    public static GameObject newInstance(String type, float x, float y) throws UnsupportedOperationException {
        /*System.out.println("type : " + type);*/
        return switch (type) {
            case "9","3" -> new Wall(x, y, ResourceManager.getSprite("unbreak"));
            case "2" ->  new BreakableWall(x, y, ResourceManager.getSprite("break1"));
            case "4" -> new Health(x, y, ResourceManager.getSprite("health"));
            case "5" -> new Speed(x, y, ResourceManager.getSprite("speed"));
            case "6"->  new Shield(x, y, ResourceManager.getSprite("shield"));
            /*case "11" -> new Tank(x,y, ResourceManager.getSprite("tank1"));
            case "12" -> new Tank(x,y, ResourceManager.getSprite("tank2"));*/
            default -> throw new UnsupportedOperationException();
        };
    }

    public void drawImage(Graphics g) {}


}
