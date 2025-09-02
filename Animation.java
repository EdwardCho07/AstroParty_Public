import mayflower.*;

/**
 * A class that stores an animation clip in a series of frames.
 */
public class Animation{
    private MayflowerImage[] frames;
    private int framerate;
    private int currentFrame;
    private boolean singleUse;

    /**
     * Creates a new Animation clip
     * @param rate The frame rate for the animation
     * @param fileNames A string array containing the names for each of the frames in order.
     * @param singleUse Whether the animation runs only once. Ex: if true the animation will run only once and destroy itself.
     */
    public Animation(int rate, String[] fileNames, boolean singleUse){
        this(rate, fileNames);
        this.singleUse = singleUse;
    }

    /**
     * Creates a new Animation clip
     * @param rate The frame rate for the animation
     * @param fileNames A string array containing the names for each of the frames in order.
     */
    public Animation(int rate, String[] fileNames){
        frames = new MayflowerImage[fileNames.length];
        framerate = rate;
        currentFrame = 0;
        for(int i = 0; i < fileNames.length; i++){
            frames[i] = new MayflowerImage(fileNames[i]);
        }
        singleUse = false;
    }

    /**
     * Returns the frame rate of the animation clip.
     * @return An int representing the frame rate of the Animation clip.
     */
    public int getFrameRate(){
        return framerate;
    }

    /**
     * Gets the next frame in the animation.
     * @return A MayflowerImage which is the next frame. null, if the Animation is single use and all frames are used.
     */
    public MayflowerImage getNextFrame(){
        if(singleUse && currentFrame >= frames.length) return null;
        MayflowerImage reImage = frames[currentFrame];
        currentFrame++;

        if(singleUse && currentFrame >= frames.length) return null;
        currentFrame %= frames.length;

        return reImage;
    }

    /**
     * Changes the size of the animation.
     * @param w Width in pixels.
     * @param h Height in pixels.
     */
    public void scale(int w, int h){
        for(int i = 0; i < frames.length; i++){
            frames[i].scale(w, h);
        }
    }

    /**
     * Changes the transparency of the animation
     * @param u An int value 0-100 representing transparency. 0 is fully transparent, 100 is fully opaque.
     */
    public void setTransparency(int u){
        for(int i = 0; i < frames.length; i++){
            frames[i].setTransparency(u);
        }
    }

    /**
     * Flips the animation horizontally.
     */
    public void mirrorHorizontally(){
        for(int i = 0; i < frames.length; i++){
            frames[i].mirrorHorizontally();
        }
    }

    /**
     * Cuts the images for the animation
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void setBounds(int x, int y, int w, int h){
        for(int i = 0; i < frames.length; i++){
            frames[i].crop(x, y, w, h);
        }
    }
}
