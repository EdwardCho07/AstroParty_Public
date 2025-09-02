import mayflower.*;

/**
 * A actor that acts as an obstacle for players, enemies, and projectiles.
 */
public class Block extends Actor {
    /**
     * Creates a block object.
     * @param width Width of the block in pixels.
     * @param height Height of the block in pixels.
     */
    public Block(int width, int height) {
        MayflowerImage image = new MayflowerImage("img/Tiles/tempborder.png");
        image.scale(width, height);

        setImage(image);
    }

    public void act() {

    }
}
