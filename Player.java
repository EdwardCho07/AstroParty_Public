import mayflower.*;
import java.util.*;
/**
 * An player object that is controlled by a user. Create a ShootingActor object instead of a Player object to create a functional player.
 */
public class Player extends AnimatedActor implements DamageableEntity
{
    //movement variables
    private double vX;
    private double vY;
    private double maxVx;
    private double maxVy;
    private double aX;
    private double aY;
    private double fX;
    private double fY;
    private double angle;
    //status
    protected int pNum;
    private boolean boosted;
    private boolean pressed;
    private int pressedTimer;
    private int boostTimer;
    private int cdTimer;

    private final double recoilStrength = 5;
    //status
    public boolean blocked;
    public boolean dead;
    public boolean added;
    public boolean overrided;
    public int health;

    //animations
    private MayflowerImage forward;
    private MayflowerImage reverse;
    
    //Key binds
    private int rotateKey;
    private int reverseKey;
    private int boostKey;


    /**
     * Constructor for objects of class Player
     */
    protected Player(int num)
    {
        // initialise instance variables
        pNum = num;
        maxVx = 5;
        maxVy = 5;
        vX = 0;
        vY = 0;
        aX = .4;
        aY = .4;
        fX = 0;
        fY = 0;
        angle = 0;
        boosted = false;
        pressed = false;
        pressedTimer = 0;
        boostTimer = 0;
        cdTimer = 180;
        dead = false;
        health = 100;   
        added = false;
        overrided = false;
        
        forward = new MayflowerImage("img/Player/Player"
        + pNum + "Forward.png");
        reverse = new MayflowerImage("img/Player/Player"
        + pNum + "Reverse.png");
        forward.scale(54 , 54);
        reverse.scale(54 , 54);
        setImage(forward);

        //Set Keybinds
        if(num == 1){
            rotateKey = Keyboard.KEY_A;
            reverseKey = Keyboard.KEY_D;
            boostKey = Keyboard.KEY_LSHIFT;
        }
        else if(num == 2){
            rotateKey = Keyboard.KEY_LEFT;
            reverseKey = Keyboard.KEY_RIGHT;
            boostKey = Keyboard.KEY_ENTER;
        }

    }
    @Override
    public void act()
    {
        controls();
        isDead();
    }   
    /**
     * Gets the kep inputs for movement
     */
    public void controls()
    {
        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();
        setImage(forward);
        
        if(cdTimer < 180)
            cdTimer++;
            
        //sets acceleration & max velocity depending on rotation
        aX = 0.1 * Math.cos(-Math.toRadians(getRotation()));
        aY = -0.1 * Math.sin(-Math.toRadians(getRotation()));
        if(!overrided)
        {
            maxVx = Math.abs(7 * Math.cos(-Math.toRadians(getRotation())));
            maxVy = Math.abs(7 * Math.sin(-Math.toRadians(getRotation())));
        }
        //causes the player to rotate
        if(Mayflower.isKeyDown(rotateKey))
        {
            setRotation(getRotation() + 2);
        }
        //New combined controls for all players to reduce repetition.
        if(Mayflower.isKeyDown(reverseKey))
        {
            setImage(reverse);
            aX = -aX;
            aY = -aY;
            pressed = true;
            pressedTimer = 0;
        }
        //boost Mechanics
        if(Mayflower.isKeyDown(boostKey))  
            if(cdTimer >= 180)
            {
                boosted = true;
                boostTimer = 0;
            }  
        if(boosted)
        {
            cdTimer = 0;
            if(boostTimer < 15)
            {
                //enables boosting in the current direction
                if(Mayflower.isKeyDown(reverseKey))
                {
                    vX = -10 * Math.cos(-Math.toRadians(getRotation()));
                    vY = 10 * Math.sin(-Math.toRadians(getRotation()));
                }
                else
                {
                    vX = 10 * Math.cos(-Math.toRadians(getRotation()));
                    vY = -10 * Math.sin(-Math.toRadians(getRotation()));
                }
            }
            else
            {
                if(vX > 0)
                    vX = maxVx;
                else
                    vX = -maxVx;
                if(vY > 0)
                    vY = maxVy;
                else
                    vY = -maxVy;
                boosted = false;
            }
            boostTimer++;
        }  
        //This is default thrust; if there is no input, the player moves forward
        else
        {
            if(Math.abs(aX) < 0.001)
                aX = 0;
            if(Math.abs(aY) < 0.001)
                aY = 0;
            
            if(vX < maxVx && aX >= 0)
                vX += aX;
            else if(vX > -maxVx && aX <= 0)
                vX += aX;    
            //resets velocity to current max velocity when rotating
            else 
                if(vX > 0)
                    vX = maxVx;
                else
                    vX = -maxVx;
            if(vY < maxVy && aY >= 0)
                vY += aY;
            else if(vY > -maxVy && aY <= 0)
                vY += aY;
            else 
                if(vY > 0)
                    vY = maxVy;
                else
                    vY = -maxVy;     
            if(Math.abs(vX) < 0.001)
                vX = 0;
            if(Math.abs(vY) < 0.001)
                vY = 0;
        }
        
        //make sure that the player can't move when its path is obstruced
        int blockX = 0;
        int blockY = 0;
        List<Block> blocks = getObjectsInRange(35, Block.class);
        boolean xCheck;
        boolean yCheck;          
        if(blocks != null)
            for(Block block: blocks)
            {
                blockX = block.getX();
                blockY = block.getY();
                xCheck = between(blockX, blockX + block.getWidth(), (x+w)/2 );
                yCheck = between(blockY - 5, blockY + block.getHeight() - 5, (y+h)/2);
                
                //These conditionals check where the blocks are compared to the player & limit the player's movement
                if(!xCheck)
                    if(blockX < x)
                    {
                        if(vX < 0)
                            vX = 0;
                    }
                    else if(blockX > x)
                        if(vX > 0)
                            vX = 0;
                if(!yCheck)
                    if(blockY < y)
                    {
                        if(vY < 0)
                            vY = 0;
                    }
                    else if(blockY > y)
                        if(vY > 0)
                            vY = 0;            
            }
        //factors in the external gravitational force if it exists             
        vX += fX;
        vY += fY;  
        setLocation(x + vX, y + vY);
    }
    /**
     * codes the player to take damage
     * @param int that determines the amount of damage that is done to the player 
     */
    public void GetDamaged(int damage)
    {
        health -= damage;
    }
    /**
     * triggers the events when a player is dead
     */
    public void isDead()
    {
        if(health <= 0)
        {
            dead = true;
            if(!added && MyMayflower.pvp)
            {
                added = true;
                dead = true;
                getWorld().addObject(new Score(), 0, 0);
                //sets up the scoreboard
                if(pNum == 1)
                {
                    getWorld().addObject(new PlayerSprite(1, dead), 310, 600);
                    getWorld().addObject(new PlayerSprite(2, !dead), 310, 400);
                    //if the score was previously greater than or equal to 1, the player has won at least once
                    MyMayflower.won2 = true;
                    if(MyMayflower.score1 >= 1)
                    {
                        MyMayflower.won1 = true;
                        MyMayflower.score1--;
                    }
                    else
                        MyMayflower.won1 = false;
                    MyMayflower.score2++;
                }
                //same code but when player 2 is dead
                else
                {
                    getWorld().addObject(new PlayerSprite(1, !dead), 310, 600);
                    getWorld().addObject(new PlayerSprite(2, dead), 310, 400);
                    //if player 2 is dead player 1 has won
                    MyMayflower.won1 = true;
                    if(MyMayflower.score2 >= 1)
                    {
                        MyMayflower.won2 = true;
                        MyMayflower.score2--;
                    }
                    else
                        MyMayflower.won2 = false;
                    MyMayflower.score1++;
                }
            }   
            getWorld().removeObject(this);
        }
    }
    /**
     * returns true if the cordinate or dimension of the player is between two points
     * @param 3 int values of important cordinates: bottom/top bound and center
     * @return true or false
     */
    private boolean between(int low, int high, int center)
    {
        if(center >= low && center <= high)
            return true;
            
        return false;
    }
    /**
     * returns the x velocity of the player
     * @return    x velocity of the player
     */
    public double getVx()
    {
        return vX;
    }
    /**
     * returns the y velocity of the player
     * @return y velocity of the player
     */
    public double getVy()
    {
        return vY;
    }
    /**
     * changes the value of the external force on the player in the x-axis
     * @param double value of the change in vY
    */
    public void changeVx(double change, double radius)
    {
        fX = change;
        if(radius <= 150)
        {
            overrided = true;
            maxVx = 3.5;
        }
        else
            overrided = false;
    }
    /**
     * changes the value of the external force on the player in the y - axis
     * @param double value of the change in vY
    */
    public void changeVy(double change, double radius)
    {
        fY = change;
        if(radius <= 150)
        {
            overrided = true;
            maxVy = 3.5;
        }
        else
            overrided = false;
    }
    /**
     * returns the health of the player
     * @return health of the player
     */
    public double getHealth()
    {
        return health;
    }
    /**
     * Resets the velocity of the player and stops it
     */
    public void stop()
    {
        vX = 0;
        vY = 0;
    }
    /**
     * Pushes the player ship back after shooting a strong shot.
     */
    public void recoil()
    {
        double angle = Math.toRadians(360 - getRotation());
        vX = Math.cos(angle) * -recoilStrength;
        vY = Math.sin(angle) * recoilStrength;
    }
}

