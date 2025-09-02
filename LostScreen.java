import mayflower.*;
/**
 * Write a description of class PlayerWinScreen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LostScreen extends World
{
    // instance variables - replace the example below with your own
    MayflowerImage image;
    /**
     * Constructor for objects of class PlayerWinScreen
     */
    public LostScreen()
    {
        // initialise instance variables

        image = new MayflowerImage("img/BG/DeadScreen.png");
        addObject(new Button(150, 450, "menu1"), 735, 750);
        image.scale(1920,1080);
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
