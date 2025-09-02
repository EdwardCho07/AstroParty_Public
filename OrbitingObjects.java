import mayflower.*;
import java.util.List.*;
import java.util.List;
/**
 * Write a description of class OrbitingObjects here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class OrbitingObjects extends Actor
{
    // instance variables - replace the example below with your own
    private MayflowerImage image;
    private Actor center;
    private int radius;
    private int rotation;
    private int rotationRate;
    private Timer dmgTimer;
    /**
     * Constructor for objects of class OrbitingObjects
     */
    public OrbitingObjects(Actor barycenter, int _rotation, String type)
    {
        // initialise instance variables
        switch(type)
        {
            case "boss":
                image = new MayflowerImage("img/Boss/Bullet1.png");
                image.scale (40, 40);
                radius = 120;
                rotationRate = 4;
                break;
            case "planet":
                image = new MayflowerImage("img/Boss/planet.png");
                image.scale (60, 60);
                radius = 150;
                rotationRate = 2;
                break;
            case "moon":
                image = new MayflowerImage("img/Boss/moon.png");
                image.scale (40, 40);
                radius = 50;
                rotationRate = 4;
                break;
        }
        
        
        setImage(image);
        center = barycenter;
        dmgTimer = new Timer(2000000000);
        rotation = _rotation;
        setRotation(rotation);
    }
    @Override
    public void act()
    {
       rotate();
       hit();
       revolve();
    }
    /**
     * Implements the orbiting feature of these objects
     *
     * @param none
     * @return none
     */
    public void revolve()
    {
        double bossX = center.getCenterX();
        double bossY = center.getCenterY();
        //Polar cordinates: x = rcos() & y = rsin() + offset to center the object
        double x = bossX + radius * Math.cos(360 - Math.toRadians(rotation)) - getWidth()/2;
        double y = bossY + radius * -Math.sin(360 - Math.toRadians(rotation)) - getHeight()/2;
        
        setLocation(x, y);
    }
    /**
     * Implements the damaging mechanics when this object is in contact with the player
     *
     * @param none
     * @return none
     */
    public void hit()
    {
        List<Actor> entities = getIntersectingObjects(Actor.class);

        for(Actor entity : entities){
            if(entity instanceof DamageableEntity && dmgTimer.isDone())
            {
                ((DamageableEntity)(entity)).GetDamaged(10);
                dmgTimer.reset();
            }
        }
    }
    /**
     * Resets and updates the rotation of the object
     *
     * @param none
     * @return none
     */
    private void rotate()
    {
        if(rotation < 360)
            rotation = getRotation() + rotationRate;
        else
            rotation = 0;
        setRotation(rotation);
    }
}
