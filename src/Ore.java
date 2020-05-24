import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class Ore extends ActiveEntity{
    private static final Random rand = new Random();
    private static final String BLOB_KEY = "blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;


    public Ore(
            Point position,
            List<PImage> images,
            int actionPeriod) {
        super(actionPeriod, images, position, 0);
    }

    public static Ore createOre(
            Point position, int actionPeriod, List<PImage> images) {
        return new Ore(position, images,
                actionPeriod);
    }


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Point pos = position;

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = OreBlob.createOreBlob(pos,
                actionPeriod / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + rand.nextInt(
                        BLOB_ANIMATION_MAX
                                - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }



}
