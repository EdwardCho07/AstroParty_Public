import mayflower.Actor;
import mayflower.MayflowerImage;
import java.util.List;

public class BossProjectile extends Actor {
    public double speed = 2.0;

    public BossProjectile(int rotation, double _speed)
    {
        MayflowerImage image = new MayflowerImage("img/Boss/Bullet.png");
        image.scale(50, 50);
        setImage(image);
        setRotation(rotation);
        speed = _speed;
        
    }
    @Override
    public void act() {
        move(speed);

        List<Actor> entities = getIntersectingObjects(Actor.class);

        for(Actor entity : entities){
            if(entity instanceof DamageableEntity){
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
