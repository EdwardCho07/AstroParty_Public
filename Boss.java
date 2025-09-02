import mayflower.*;

/**
 * The main enemy in PVE mode that attacks the players.
 */
public class Boss extends AnimatedActor implements DamageableEntity
{
    private Player p1;
    private Player p2;
    private Player target;
    private MayflowerImage image;
    private int atkTimer;
    private Timer targetTimer;
    private Timer chargeTimer;
    private Timer shootTimer;
    private Timer deadTimer;
    private boolean stopped;
    private boolean charging;
    public boolean enraged;
    public boolean dead;
    private boolean added;
    private int health;
    private double velocity;

    private int distance = 120;
    
    public Boss(Player player1, Player player2) 
    {
        image = new MayflowerImage("img/Boss/1.png");
        setImage(image);
        
        p1 = player1;
        p2 = player2;
        atkTimer = 0;
        targetTimer = new Timer(5000000);
        chargeTimer = new Timer(1000000000);
        shootTimer = new Timer(Integer.MAX_VALUE);
        deadTimer = new Timer(Integer.MAX_VALUE);
        target = null;
        velocity = 1;
        stopped = false;
        charging = false;
        enraged = false;
        dead = false;
        added = false;
        health = 400;
    }
    public void act()
    {
        move();
        attack();
        shoot();
        isDead();
    }

    /**
     * Determines the fighting patterns of the boss
     *
     * @return none
     */
    public void attack()
    {
        int rand = 0;
        //randomly sets attack pattern every time interval
        if(atkTimer >= 300)
        {
            rand = (int)(Math.random() * 1 + 1);
            //charge mechanic
            switch(rand)
            {
                case 1:
                    charging = true;
                    chargeTimer.reset();
                    break;
                case 2:
                    
                    break;
                case 3: 
                    break;
            }
            atkTimer = 0;
        }
        if(charging)
        {
            if(chargeTimer.isDone())
            {
                charging = false;   
                velocity = 2;
            }
            else
                velocity = 7;
        }
        atkTimer++;
    }

    /**
     * Determines the movement of the boss
     *
     * @param none
     * @return none
     */
    private void move()
    {
        //resets targetting once in a while
        if(!stopped)
        {
            //automatically sets target to living player if one player is dead
            if(p1.dead)
            {
                target = p2;
            }
            else if(p2.dead)
                target = p1;
            else
            {
                if(targetTimer.isDone())
                {
                    if(Math.sqrt(getDX(p1) * getDX(p1) + getDY(p1) * getDY(p1)) < 
                        Math.sqrt(getDX(p2) * getDX(p2) + getDY(p2) * getDY(p2)))
                        target = p1;
                    else
                        target = p2;
                    targetTimer.reset();
                }
            }
            turnTowards(target);
            move(velocity);
        }
    }
    /**
     * Codes the constant attacks of the boss
     *
     * @param none
     * @return none
     */
    private void shoot()
    {
        if(!stopped)
        {
            if(shootTimer.isDone())
            {
            shootTimer.reset();
            int[] position = getBulletSpawnLocation();
            getWorld().addObject(new BossProjectile(getRotation(), 10), position[0], position[1]);
            }   
        }
        //adds permanent orbiting attacks when enraged
        if(enraged && !added)
        {
            added = true;
            getWorld().addObject(new OrbitingObjects(this, 0, "boss"), 0, 0);
            getWorld().addObject(new OrbitingObjects(this, 120, "boss"), 0, 0);
            getWorld().addObject(new OrbitingObjects(this, 240, "boss"), 0, 0);
        }
    }
        /**
     * triggers the events when a player is dead
     *
     * @param none
     * @return none
     */
    public void isDead()
    {
        if(!dead)
            deadTimer.reset();
        if(health <= 0)
        {
            dead = true;
            if(deadTimer.isDone())
            {
                Mayflower.setWorld(new PlayerWinScreen(false));
                getWorld().removeObject(this);
            }
            else
            {
                //boss slowly fades away as it dies
                stopped = true;
                if(image.getTransparency() < 100)
                    image.setTransparency(image.getTransparency() + 1);
                setImage(image);
            }
        }
    }
    private int[] getBulletSpawnLocation(){
        int x = getCenterX();
        int y = getCenterY();
        double angle = Math.toRadians(-getRotation());

        double yDist = distance * Math.sin(angle);
        double xDist = distance * Math.cos(angle);

        return new int[]{(int)(x + xDist), (int)(y - yDist)};
    }
    /**
     * codes the boss to take damage
     *
     * @param int that determines the amount of damage that is done to the boss 
     * @return none
     */
    public void GetDamaged(int damage)
    {
        health -= damage;
        //when the boss dies for the first time, it resets the health and checks enraged boolean
        if(!enraged && health <= 0)
        {
            image = new MayflowerImage("img/Boss/2.png");
            setImage(image);
            health = 1000;
            enraged = true;
        }
    }
    /**
     * returns the health of the boss
     *
     * @param 
     * @return health of the boss
     */
    public double getHealth()
    {
        return health;
    }
    /**
     * Returns the x-distance between this and another object
     *
     * @param which player
     * @return double value of x-distance
     */
    private double getDX(Player p)
    {
        return Math.abs(getX() - p.getX());
    }
    /**
     * Returns the y-distance between this and another object
     *
     * @param which player
     * @return double balue of y-distance
     */
    private double getDY(Player p)
    {
        return Math.abs(getY() - p.getY());
    }
}

