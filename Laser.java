import mayflower.Actor;
import mayflower.MayflowerImage;

import java.util.*;

/**
 * A laser that will go through the entire screen width or height and damage everything in its way.
 */
public class Laser extends Actor {
    private Timer endTimer;
    private Laser thisLaser;
    private Set<Actor> alreadyHitEnemies;
    private Actor owner;

    public Laser(int rotation, int length, Actor owner, int pNum, int height){
        this.owner = owner;
        endTimer = new Timer();
        alreadyHitEnemies = new HashSet<Actor>();

        MayflowerImage image = new MayflowerImage("img/Player/Player" + pNum + "LaserProj2.png");
        image.scale(length, height);
        setImage(image);
        setRotation(rotation);
        thisLaser = this;

        endTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                getWorld().removeObject(thisLaser);
            }
        }, 750);
    }

    public Laser(int rotation, int length, Actor owner, int pNum){
        this(rotation, length, owner, pNum, 45);
    }

    @Override
    public void act() {
        for(Actor a : getIntersectingObjects(Actor.class)){
            if(a instanceof DamageableEntity && !alreadyHitEnemies.contains(a) && !a.equals(owner)){
                ((DamageableEntity) a).GetDamaged(50);
                alreadyHitEnemies.add(a);
            }
        }
    }
}
