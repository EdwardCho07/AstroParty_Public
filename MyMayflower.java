import mayflower.*;

public class MyMayflower extends Mayflower
{
    public static int score1;
    public static int score2;
    public static boolean won1;
    public static boolean won2;
    public static boolean pvp;
    //Constructor
    public MyMayflower()
    {
        //Create a window with 1920x1080 resolution
        super("Project 2", 1920, 1080);
        score1 = 0;
        score2 = 0;
        won1 = false;
        won2 = false;
        pvp = false;
    }

    public void init()
    {
        //Change this to true to run this program in fullscreen mode
        Mayflower.setFullScreen(true);
        World w =  new MainMenu();
        Mayflower.setWorld(w);
    }

    public static World getRandomPvpWorld(){
        double r = Math.random();
        if(r < 0.33){
            return new PvpWorld1();
        }
        else if(r < 0.67){
            return new PvpWorld2();
        }
        else if (r < 1) {
            return new PvpWorld3();
        }

        return null;
    }
}
