package tankrotationexample.Resources;

import tankrotationexample.game.Bullet;

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

    /**
     * Load a sprite from the given path using ImageIO.read() method.
     *
     * @param path The path of the sprite to load.
     * @return The loaded BufferedImage representing the sprite.
     * @throws IOException If an error occurs while loading the sprite image.
     */
    private static BufferedImage loadSprite(String path) throws IOException {
        return ImageIO.read(
                Objects.requireNonNull(ResourceManager
                        .class
                        .getClassLoader()
                        .getResource(path)));
    }

    /**
     * Initialize the sprites for the game by loading the necessary images.
     */
    private static void initSprites() {
        try {

            ResourceManager.sprites.put("tank1", loadSprite("tank/tank1.png")); // load and store tank1 sprite
            ResourceManager.sprites.put("tank2", loadSprite("tank/tank2.png")); // load and store tank2 sprite
            ResourceManager.sprites.put("bullet", loadSprite("bullet/bullet.jpg")); // load and store bullet sprite
            ResourceManager.sprites.put("rocket1", loadSprite("bullet/rocket1.png"));
            ResourceManager.sprites.put("rocket2", loadSprite("bullet/rocket2.png"));
            ResourceManager.sprites.put("break1", loadSprite("walls/break1.jpg"));
            ResourceManager.sprites.put("break2", loadSprite("walls/break2.jpg"));
            ResourceManager.sprites.put("unbreak", loadSprite("walls/unbreak.jpg"));
            ResourceManager.sprites.put("floor", loadSprite("floor/bg.bmp"));
            ResourceManager.sprites.put("health", loadSprite("powerups/health.png"));
            ResourceManager.sprites.put("shield", loadSprite("powerups/shield.png"));
            ResourceManager.sprites.put("speed", loadSprite("powerups/speed.png"));
            ResourceManager.sprites.put("menu", loadSprite("menu/title.png")); // load and store the menu image (title.png) sprite

            /*t = ImageIO.read(Objects.requireNonNull(ResourceManager.class.getClassLoader().getResource("menu/title.png"),"menu image is missing"));*/

        } catch (IOException e) {
            // throw a RuntimeException if there's an error loading any of the sprites
            throw new RuntimeException(e);
        }
    }

    /**
     * Load all necessary game resources.
     */
    public static void loadResources(){
        ResourceManager.initSprites();
    }

    /**
     * Get the sprite for a given type.
     * @param type The type of sprite to get (e.g., "tank1", "tank2", "bullet", "menu").
     * @return The BufferedImage representing the sprite.
     * @throws RuntimeException If the specified sprite type is missing from the sprite resources.
     */
    public static BufferedImage getSprite(String type) {
        if(!ResourceManager.sprites.containsKey(type)){
            throw new RuntimeException("%s is missing from sprite resources".formatted(type));
        }
        return ResourceManager.sprites.get(type);
    }

    public static void main(String[] args) {
        ResourcePool<Bullet> bPool = new ResourcePool<>("bullet", 300);
        bPool.fillPool(Bullet.class, 300);
        ResourceManager.initSprites();
        System.out.println();
    }

}
