import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class OreBlob extends AnimatedEntity implements Animated, NextPosition{
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    private static final String QUAKE_KEY = "quake";
    public static final String ORE_KEY = "ore";


    public OreBlob(
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

    public int getactionPeriod(){
        return actionPeriod;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Optional<Entity> blobTarget =
                world.findNearest(position, Vein.class);
        long nextPeriod = actionPeriod;

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().getPosition();

            if (world.moveToOreBlob(this, blobTarget.get(), scheduler)) {
                Quake quake = Quake.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += actionPeriod;
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent( this,
                Activity.createAction(this, world, imageStore),
                nextPeriod);
    }

    public Point getPosition(){
        return position;
    }

    public static OreBlob createOreBlob(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images) {
        return new OreBlob(id, position, images, 0, 0,
                actionPeriod, animationPeriod);
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
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

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 || (occupant.isPresent() && !(occupant.get().getClass()
                == Ore.class))) {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 || (occupant.isPresent() && !(occupant.get().getClass()
                    == Ore.class))) {
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
            scheduler.scheduleEvent( this,
                    Activity.createAction(this, world, imageStore),
                    actionPeriod);
            scheduler.scheduleEvent(this,
                    Animation.createAction(this, 0),
                    getAnimationPeriod());
        }





}
