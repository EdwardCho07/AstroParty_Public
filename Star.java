import mayflower.*;
import java.util.List.*;
import java.util.List;
/**
 * Write a description of class OrbitingObjects here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Star extends Actor
{
    // instance variables - replace the example below with your own
    private MayflowerImage image;
    private int coefficient;
    private double angle;
    private Timer dmgTimer;
    /**
     * Constructor for objects of class OrbitingObjects
     */
    public Star()
    {
        // initialise instance variables
        image = new MayflowerImage("img/Boss/star.png");
        image.scale (100, 100);
        setImage(image);
        dmgTimer = new Timer(2000000000);
        coefficient = 800;
        angle = 0;
    }
    @Override
    public void act()
    {
        revolve();
        gravity();
        hit();
    }
    /**
     * Implements the orbiting feature of these objects
     */
    public void revolve()
    {
        /* Polar Equation for a lemniscate: r^2 = A^2(cos(20)) 
        * Parametric Equation: x - Acos(T)/(1+sin^2(T)) & y - Asin(T)cos(T)/(1+sin^2(T)) 
        * (960, 540) is the origin/center 
        */
        adjustAngle();
        double x = 960 + (coefficient * Math.cos(Math.toRadians(angle))) /
            (1 + Math.sin(Math.toRadians(angle)) * Math.sin(Math.toRadians(angle)));
        double y = 540 + (coefficient * Math.sin(Math.toRadians(angle)) * Math.cos(Math.toRadians(angle))) /
            (1 + Math.sin(Math.toRadians(angle)) * Math.sin(Math.toRadians(angle)));

        setLocation(x, y);
    }
    /**
     * Resets and updates the rotation of the object
     */
    private void adjustAngle()
    {
        if(angle < 360)
           angle += 0.1;
        else 
        {
            angle = 0;
        }
        //a little rotation
        setRotation(getRotation() + 1);
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
                ((DamageableEntity)(entity)).GetDamaged(20);
                dmgTimer.reset();
            }
        }
    }
    /**
     * Implements gravitational force of the star
     */
    private void gravity()
    {
        List<Player> players = getObjectsInRange(2000, Player.class);
        double accelX = 0;
        double accelY = 0;
        double radius = 0;
        for(Player player : players)
        {
            double pX = player.getCenterX();
            double pY = player.getCenterY();
            //calculating radius
            radius = Math.sqrt(Math.abs(pX - getCenterX()) * Math.abs(pX - getCenterX()) 
                + Math.abs(pY - getY()) * Math.abs(pY - getY()));
            //applying a gravitational force to the player, so F_g = G(Mm)/R^2, in which G * M will be a constant
            //F = ma so a = Constant/R^2 and factored by trig ratios to get linear vector
            accelX = (15000 / (radius * radius)) * (Math.abs(pX - getCenterX())/radius);
            accelY = (15000 / (radius * radius)) * (Math.abs(pY - getCenterY())/radius);
            if(getCenterX() < pX)
                accelX *= -1;
            if(getCenterY() < pY)
                accelY *= -1;
                
            //prevents the player from getting sucked in and thrown around the map
            if(accelX > .2)
                accelX = .2;
            else if(accelX < -.2)
                accelX = -.2;
            if(accelY > .2)
                accelY = .2;
            else if(accelY < -.2)
                accelY = -.2;
                
            player.changeVx(accelX, radius);
            player.changeVy(accelY, radius);
        }
    }
}
