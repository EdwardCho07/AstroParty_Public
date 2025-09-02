import mayflower.MayflowerImage;

/**
 * A 60 by 60 square block that cannot be destroyed. A total of 32x18 can be placed.
 */
public class UnbreakableBlock extends Block{
    public UnbreakableBlock(){
        super(60, 60);

        MayflowerImage image = new MayflowerImage("img/Tiles/BlueBox.png");
        image.scale(60, 60);

        setImage(image);
    }
}
