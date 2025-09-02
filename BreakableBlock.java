import mayflower.Actor;
import mayflower.MayflowerImage;

/**
 * A block that will break when hit by a bullet or laser.
 */
public class BreakableBlock extends Block implements DamageableEntity{
    public BreakableBlock(){
        super(60, 60);
        MayflowerImage image = new MayflowerImage("img/Tiles/YellowBox.png");
        image.scale(60, 60);

        setImage(image);
    }

    @Override
    public void GetDamaged(int damage) {
        //Destroy animation
        getWorld().removeObject(this);
    }

    @Override
    public void act() {

    }
}
