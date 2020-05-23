import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public class Vein extends ActiveEntity implements Animated{
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    private static final Random rand = new Random();
    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    public static final String ORE_KEY = "ore";

    public int getactionPeriod(){
        return actionPeriod;
    }

    public Vein(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }


    public static Vein createVein(
            String id, Point position, int actionPeriod, List<PImage> images) {
        return new Vein(id, position, images, 0, 0,
                actionPeriod, 0);
    }


    public Point getPosition(){
        return position;
    }


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Point> openPt = world.findOpenAround(position);

        if (openPt.isPresent()) {
            Ore ore = Ore.createOre(ORE_ID_PREFIX + id, openPt.get(),
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

    public int getImageIndex() {
        return imageIndex;
    }

    public List<PImage> getImages(){
        return images;
    }

    public void setPosition(Point position){
        this.position = position;
    }



    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {
            scheduler.scheduleEvent( this,
                    Activity.createAction(this, world, imageStore),
                    actionPeriod);
        }




}
