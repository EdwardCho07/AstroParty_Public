import mayflower.*;

/**
 * The initial start screen.
 */
public class MainMenu extends World
{
    /**
     * Constructor for objects of class MainMenu
     */
    public MainMenu()
    {
        MayflowerImage image = new MayflowerImage("img/BG/GameTitle.png");
        image.scale(1920, 1080);

        setBackground(image);
        //reset game variables
        MyMayflower.score1 = 0;
        MyMayflower.score2 = 0;
        MyMayflower.won1 = false;
        MyMayflower.won2 = false;

        addObject(new Button(150, 450, "startselection"), 735, 650);
    }

    @Override
    public void act() 
    {

    }
}
