import mayflower.Actor;
import mayflower.MayflowerImage;

import java.util.List;

/**
 * A bullet that can damage enemies and players.
 */
public class Bullet extends Actor {
    private double speed = 2.0;
    private Actor owner;

    /**
     * Creates a bullet object
     * @param rotation The angle the bullet will move in. Rightward is 0 and increases up to 359 in a counterclockwise direction.
     * @param _speed The speed the bullet will move with in.
     * @param player A reference to the player that spawned the bullet.
     */
    public Bullet(int rotation, double _speed, int player, Actor owner){
        MayflowerImage image = new MayflowerImage("img/Player/0" + player + ".png");
        image.scale(25, 25);
        image.crop(0, 3, 25, 22);
        setImage(image);
        setRotation(rotation);
        speed = _speed;
        this.owner = owner;
    }

    @Override
    public void act() {
        move(speed);

        List<Actor> entities = getIntersectingObjects(Actor.class);

        for(Actor entity : entities){
            if(entity instanceof DamageableEntity && !entity.equals(owner)){
                ((DamageableEntity)(entity)).GetDamaged(10);
                getWorld().removeObject(this);
                break;
            }
            if(entity instanceof Block){
                getWorld().removeObject(this);
                break;
            }
        }
    }
}
