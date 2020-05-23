import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class BlackSmith extends Entity{
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;



    public BlackSmith(
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


    public static BlackSmith createBlacksmith(
            String id, Point position, List<PImage> images) {
        return new BlackSmith(id, position, images, 0, 0, 0,
                0);
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

    public Point getPosition(){
        return position;
    }




}