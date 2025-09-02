import mayflower.*;
/**
 * Write a description of class PlayerTag here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PlayerTag extends Actor
{
    // instance variables - replace the example below with your own
    private MayflowerImage image;
    private Player player;
    
    /**
     * Constructor for objects of class PlayerTag
     */
    public PlayerTag(int num, Player obj)
    {
        // initialise instance variables
        player = obj;
        if(num == 1)
        {
            image = new MayflowerImage("img/Player/P1Tag.png");
            image.scale(25 , 25);
        }
        else
        {
            image = new MayflowerImage("img/Player/P2Tag.png");
            image.scale(35 , 22);
        }
            
        setImage(image);
    } 
    @Override
    public void act() 
    {
        move();
    }
    /**
     * Moves the Tag with the corresponding player
     *
     * @param  none
     * @return none
     */
    public void move()
    {
        //sets the player tag above the player at all times
        if(player.pNum == 1)
            setLocation(player.getX() + 12, player.getY() - 30);
        else
            setLocation(player.getX() + 8, player.getY() - 30);
        //this object is destroyed when the player is dead
        if(player.dead)
            getWorld().removeObject(this);
    }    
}
