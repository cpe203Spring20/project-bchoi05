import java.util.List;
import processing.core.PImage;

public abstract class ActiveEntity extends Entity{
    protected int actionPeriod;
    public ActiveEntity(int actionPeriod, List<PImage> images, Point position, int imageIndex){
        super(images, position, imageIndex);
        this.actionPeriod = actionPeriod;

    }
    public int getactionPeriod()
    {
        return actionPeriod;
    }

    abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {
        scheduler.scheduleEvent( this,
                Activity.createAction(this, world, imageStore),
                actionPeriod);
    }
}
