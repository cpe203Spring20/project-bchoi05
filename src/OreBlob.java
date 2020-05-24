import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class OreBlob extends AnimatedEntity implements NextPosition{

    private static final String QUAKE_KEY = "quake";

    public OreBlob(
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod) {
        super(animationPeriod, 0, images, actionPeriod, position, 0);
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


    public static OreBlob createOreBlob(
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images) {
        return new OreBlob(position, images,
                actionPeriod, animationPeriod);
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





}
