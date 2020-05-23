import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class Obstacle extends Entity{
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;


    public static final String ORE_KEY = "ore";


    public Obstacle(
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

    public int getImageIndex() {
        return imageIndex;
    }

    public List<PImage> getImages(){
        return images;
    }

    public void setPosition(Point position){
        this.position = position;
    }


    public static Obstacle createObstacle(
            String id, Point position, List<PImage> images) {
        return new Obstacle(id, position, images, 0, 0, 0,
                0);
    }
}