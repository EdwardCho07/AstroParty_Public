import mayflower.*;

/**
 * A health bar for a player.
 */
public class HealthBar extends Actor
{
    MayflowerImage image;
    private boolean base;
    private Player player;
    private int current;

    public HealthBar(Player p, boolean b)
    {
        player = p;
        base = b;
        if(base)
            image = new MayflowerImage("img/Player/Red.png");
        else 
            image = new MayflowerImage("img/Player/Green.png");
        
        image.scale(30, 6);
        setImage(image);
    }

    @Override
    public void act()
    {
        updateBar();
    }
    /**
     * Updates the health bar depending on the current health
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void updateBar()
    {
        setLocation(player.getX() + 10, player.getY() - 10);
        
        current = (int)(30 * (player.getHealth()/100)) > 0 ? (int)(30 * (player.getHealth()/100)) : 1;
        if(!base)
        {
            image.crop(0, 0, current, getHeight());
        }
        if(player.dead)
        {
            getWorld().removeObject(this);
        }
    }
}
