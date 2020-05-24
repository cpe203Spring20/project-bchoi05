import processing.core.PImage;
import java.util.List;

public abstract class AnimatedEntity extends ActiveEntity {
    protected int animationPeriod;
    protected int actionPeriod;
    protected int count;

    public AnimatedEntity(int animationPeriod, int imageIndex, List<PImage> images, int actionPeriod, Point position, int count){
        super(actionPeriod, images, position, imageIndex);
        this.animationPeriod = animationPeriod;
        this.actionPeriod = actionPeriod;
        this.count = count;
    }
    public void nextImage(){ imageIndex = (imageIndex + 1) % images.size();}
    public int getAnimationPeriod()
    {
        return animationPeriod;
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {

        scheduler.scheduleEvent(this,
                Activity.createAction(this, world, imageStore),
                actionPeriod);
        scheduler.scheduleEvent(this, Animation.createAction(this,
                count),
                getAnimationPeriod());
    }
}
