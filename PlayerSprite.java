import mayflower.*;
/**
 * Write a description of class PlayerSprite here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PlayerSprite extends Actor
{
    // instance variables - replace the example below with your own
    private int pNum;
    private MayflowerImage image;
    private boolean lost;
    private boolean checked;
    private int previous;
    /**
     * Constructor for objects of class PlayerSprite
     */
    public PlayerSprite(int playerNumber, boolean lose)
    {
        // initialise instance variables
        pNum = playerNumber;
        image = new MayflowerImage("img/Player/Player"
        + pNum + "Forward.png");
        image.scale(5);
        setImage(image);
        lost = lose;
        previous = getX();
    }

    @Override
    public void act()
    {
        move();
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void move()
    {
        //sets the scoreboard across worlds
        if(!checked && pNum == 1)
        {
            previous = getX();
            if(!MyMayflower.won1)
                previous = 310;
            else if(lost)
                previous = 310 + ((MyMayflower.score1 + 1) * 300);
            else
                previous = 310 + ((MyMayflower.score1 - 1) * 300);
            if(previous < 310)
                previous = 310;
            checked = true;
            setLocation(previous, getY());
        }
        else if(!checked && pNum == 2)
        {
            if(!MyMayflower.won2)
                previous = 310;
            else if(lost)
                previous = 310 + ((MyMayflower.score2 + 1) * 300);
            else
                previous = 310 + ((MyMayflower.score2 - 1) * 300);
            if(previous < 310)
                previous = 310;
            checked = true;
            setLocation(previous, getY());
        }
        //moves the players up or down the scoreboard based on whether they won or lost
        if(lost)
        {
            if(getX() > 310)
                if(getX() > previous - 300)
                {
                    setRotation(getRotation() + 7);
                    setLocation(getX() - 6, getY());
                }
        }
        else
            if(getX() < previous + 300)
            {
                 setRotation(0);
                 setLocation(getX() + 6, getY());
            }
        }
    }

