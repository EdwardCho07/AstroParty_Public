import mayflower.*;
/**
 * Write a description of class PlayerWinScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PlayerWinScreen extends World
{
    // instance variables - replace the example below with your own
    MayflowerImage image;
    /**
     * Constructor for objects of class PlayerWinScreen
     */
    public PlayerWinScreen(boolean player1)
    {
        // initialise instance variables
        if(MyMayflower.pvp)
        {
            if(player1)
            {
                image = new MayflowerImage("img/BG/P1Win.png");
                addObject(new Button(150, 450, "menu1"), 735, 750);
            }
            else
            {
                image = new MayflowerImage("img/BG/P2Win.png");
                addObject(new Button(150, 450, "menu2"), 735, 750);
            }
        }
        else
        {
            image = new MayflowerImage("img/BG/WinScreen.png");
            addObject(new Button(150, 450, "menu3"), 735, 750);
        }
        setBackground(image);
    }
    @Override
    public void act()
    {
        if(Mayflower.isKeyDown(Keyboard.KEY_SPACE))
        {
            Mayflower.setWorld(new MainMenu());
        }
    }
}
