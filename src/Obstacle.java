import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class Obstacle implements Entity{
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

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    public int getAnimationPeriod() {
            throw new UnsupportedOperationException(
                    String.format("getAnimationPeriod not supported for %s",
                            getClass()));

    }

}