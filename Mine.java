import mayflower.Actor;
import mayflower.MayflowerImage;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An actor that will explode and damage any enemy or player except the one that placed it when touched. It will also explode after a certain amount of time.
 */
public class Mine extends Actor {
    private Actor owner;
    private Timer explodeTimer;
    private Actor thisMine;

    private final int mineSize = 80;
    private final long explodeTime = 10000;

    public Mine(Actor owner){
        thisMine = this;
        explodeTimer = new Timer();
        this.owner = owner;

        explodeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                explode();
            }
        }, explodeTime);

        MayflowerImage image = new MayflowerImage("img/Player/Mine.png");
        image.scale(60, 60);
        setImage(image);
    }

    @Override
    public void act() {
        List<Actor> touchingActors = getObjectsInRange(mineSize, Actor.class);
        if(touchingActors != null && !touchingActors.isEmpty()){
            for(Actor a : touchingActors){
                if(a instanceof DamageableEntity && !a.equals(owner)){
                    explode();
                }
            }
        }
    }

    /**
     * This method will make the mine explode calling the animation, removing the actor, and damaging enemies nearby.
     */
    public void explode()
    {
        List<Actor> touchingActors = getObjectsInRange(mineSize, Actor.class);
        if(touchingActors != null && !touchingActors.isEmpty()){
            for(Actor a : touchingActors){
                if(a instanceof DamageableEntity && !a.equals(owner)){
                    ((DamageableEntity) a).GetDamaged(50);
                }
            }
        }

        explodeTimer.cancel();
        getWorld().addObject(new Explosion(), getX(), getY());
        getWorld().removeObject(thisMine);
    }
}
