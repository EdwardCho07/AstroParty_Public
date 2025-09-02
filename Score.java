import mayflower.*;
/**
 * Write a description of class Score here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Score extends Actor
{
    private MayflowerImage image;
    private Timer timer;
    /**
     * Constructor for objects of class Score
     */
    public Score()
    {
        image = new MayflowerImage("img/BG/Scoreboard.png");
        setImage(image);
        timer = new Timer(Integer.MAX_VALUE);
    }

    @Override
    public void act()
    {
        changeScene();
    }
    /**
     * restarts the round if the game is not over; if the game is over, changes to corresponding win screen
     *
     * @param none
     * @return none
     */
    public void changeScene()
    {
        if(timer.isDone())
            if(MyMayflower.score1 == 4)
                Mayflower.setWorld(new PlayerWinScreen(true));
            else if(MyMayflower.score2 == 4)
                Mayflower.setWorld(new PlayerWinScreen(false));
            else
                Mayflower.setWorld(MyMayflower.getRandomPvpWorld());
    }
}
