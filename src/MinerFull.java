import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class MinerFull implements Entity, Animated, Miners{
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final Random rand = new Random();
    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;
    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    private static final String QUAKE_KEY = "quake";
    public static final String ORE_KEY = "ore";


    public MinerFull(
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

    public Point getPosition(){
        return position;
    }



    public void transformFull(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {
        MinerNotFull miner = MinerNotFull.createMinerNotFull(id, resourceLimit,
                position, actionPeriod,
                animationPeriod,
                images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }



    public static MinerFull createMinerFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images) {
        return new MinerFull(id, position, images,
                resourceLimit, resourceLimit, actionPeriod,
                animationPeriod);
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public List<PImage> getImages(){
        return images;
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }



    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    public void executeMinerFullActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> fullTarget =
                world.findNearest(position, BlackSmith.class);

        if (fullTarget.isPresent() && world.moveToFull(this,
                fullTarget.get(), scheduler)) {
            transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this,
                    Activity.createAction(this, world, imageStore),
                    actionPeriod);
        }
    }

    public void setResourceCount(int i){
        resourceCount += i;
    }

    public Point nextPositionMiner(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = position;
            }
        }

        return newPos;
    }

    public void setPosition(Point position){
        this.position = position;
    }


    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore) {
            scheduler.scheduleEvent(this,
                    Activity.createAction(this, world, imageStore),
                    actionPeriod);
            scheduler.scheduleEvent(this,
                    Animation.createAction(this, 0),
                    getAnimationPeriod());

    }

}
