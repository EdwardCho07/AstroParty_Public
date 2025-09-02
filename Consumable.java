import mayflower.Actor;
import mayflower.MayflowerImage;

import java.util.Stack;

/**
 * An object that the players can pick up and later on use.
 */
public class Consumable extends Actor {
    private int consumableHeight;
    private int consumableWidth;

    private ConsumableType type;

    public Consumable(ConsumableType type){
        String imageFilename = "";
        switch (type){
            case Laser:
                imageFilename = "img/Player/Player2LaserProj.png";
                consumableHeight = 15;
                consumableWidth = 40;
                break;
            case Mine:
                imageFilename = "img/Tiles/Mine.png";
                consumableHeight = 40;
                consumableWidth = 40;
                break;
            case TripleShot:
                imageFilename = "img/Tiles/TripleShot.png";
                consumableHeight = 40;
                consumableWidth = 40;
                break;
            case ContinuousShot:
                imageFilename = "img/Tiles/minePickup.png";
                consumableHeight = 40;
                consumableWidth = 40;
                break;
        }

        this.type = type;
        MayflowerImage image = new MayflowerImage(imageFilename);
        image.scale(consumableWidth, consumableHeight);
        setImage(image);
        setRotation(45);
    }

    @Override
    public void act() {

    }

    /**
     * Picks up this consumable to the stack given. Adds to the stack and removes the Consumable actor from the world.
     * @param consumableStack The player's consumable stack or inventory that this consumable will be added to
     */
    public void pickConsumable(Stack<ConsumableType> consumableStack){
        consumableStack.add(type);
        getWorld().removeObject(this);
    }
}
