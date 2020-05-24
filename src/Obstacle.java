import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class Obstacle extends Entity{

    public Obstacle(
            Point position,
            List<PImage> images) {
        super(images, position, 0);
    }

    public static Obstacle createObstacle(
            Point position, List<PImage> images) {
        return new Obstacle( position, images);
    }
}