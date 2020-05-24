import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public class Vein extends ActiveEntity{

    private static final Random rand = new Random();
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    public static final String ORE_KEY = "ore";


    public Vein(
            Point position,
            List<PImage> images,
            int actionPeriod) {
        super(actionPeriod, images, position, 0);
        this.imageIndex = 0;
    }


    public static Vein createVein(
            Point position, int actionPeriod, List<PImage> images) {
        return new Vein(position, images,
                actionPeriod);
    }


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Point> openPt = world.findOpenAround(position);

        if (openPt.isPresent()) {
            Ore ore = Ore.createOre(openPt.get(),
                    ORE_CORRUPT_MIN + rand.nextInt(
                            ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    imageStore.getImageList(ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActions(scheduler, world, imageStore);
        }
        scheduler.scheduleEvent( this,
                Activity.createAction(this, world, imageStore),
                actionPeriod);
    }




}
