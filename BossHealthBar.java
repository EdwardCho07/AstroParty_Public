import mayflower.*;
/**
 * Health Bar for the Boss
 */
public class BossHealthBar extends Actor
{
    // instance variables - replace the example below with your own
    private MayflowerImage image;
    private Boss boss;
    private int type;
    private int current;
    private boolean revived;

    /**
     * Constructor for objects of class BossHealthBar
     */
    public BossHealthBar(int _type, Boss _boss)
    {
        // initialise instance variables
        switch(_type)
        {
            case 1:
                image = new MayflowerImage("img/Boss/BossBaseBar.png");
 
                break;
            case 2:
                image = new MayflowerImage("img/Boss/BossHPBar.png");

                break;
            case 3:
                image = new MayflowerImage("img/Boss/BossEnragedBar.png");
                image.setTransparency(100);
                break;
        }
        image.scale(800, 400);
        image.crop(40, 70, 680, 250);
        boss = _boss;
        type = _type;
        revived = false;
        setImage(image);
    }
    @Override
    public void act()
    {
       updateBar();
    }
    /**
     * Updates the boss health bar
     *
     * @param none
     * @return none
     */
    private void updateBar()
    {   
        //sets the health bar depending on the boss' current health
        if(!boss.enraged && type == 2)
        {
            current = (int)(680 * (boss.getHealth()/571)) + 200;
            image.crop(0, 0, current, 240);
            setImage(image);
        }
        else if(boss.enraged && type == 3)
        {
            //the 2nd bar is shown when the boss enters enraged mode
            if(!revived)
            {
                if(image.getTransparency() > 0)
                    image.setTransparency(image.getTransparency() - 1);
                else    
                    revived = true;
            }
            else
            {
                current = (int)(680 * (boss.getHealth()/1428)) + 200;
                image.crop(0, 0, current, 240);
            }
        }
        //removes the object if the boss dead or the first bar if the boss is in enraged status
        if(boss.dead || boss.enraged && type == 2)
        {
            getWorld().removeObject(this);
        }
    }
        
}
