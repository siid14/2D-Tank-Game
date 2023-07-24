package tankrotationexample.Resources;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResourceManager {
    private final static Map<String, BufferedImage> sprites = new HashMap<>();
    private final static Map<String, List<BufferedImage>> animation = new HashMap<>();
    private final static Map<String, Clip> sounds = new HashMap<>();

    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(
                Objects.requireNonNull(ResourceManager
                        .class
                        .getClassLoader()
                        .getResource(path)));
    }

    private static void initSprites() {
        try {

            ResourceManager.sprites.put("tank1", loadSprite("tank/tank1.png"));
            ResourceManager.sprites.put("tank2", loadSprite("tank/tank2.png"));
            ResourceManager.sprites.put("bullet", loadSprite("bullet/bullet.jpg"));
            /*t = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResource("menu/title.png"),"menu image is missing"));*/
            ResourceManager.sprites.put("menu", loadSprite("menu/title.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadResources(){
        ResourceManager.initSprites();
    }

    public static BufferedImage getSprite(String type) {
        if(!ResourceManager.sprites.containsKey(type)){
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.sprites.get(type);
    }


    /*public static void main(String[] args) {
        ResourceManager.initSprites();
        System.out.println();
    }*/

}
