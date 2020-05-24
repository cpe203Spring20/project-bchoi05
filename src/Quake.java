import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class Quake extends AnimatedEntity{
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod) {
        super(animationPeriod, 0, images, actionPeriod, position, QUAKE_ANIMATION_REPEAT_COUNT);
    }

    public static Quake createQuake(
            Point position, List<PImage> images) {
        return new Quake(position, images, 0, 0);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }
}
