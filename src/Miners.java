import processing.core.PImage;

import java.util.List;

public abstract class Miners extends AnimatedEntity{
    public Miners(int animationPeriod, int imageIndex, List<PImage> images, int actionPeriod, Point position, int count){
        super(animationPeriod, imageIndex, images, actionPeriod, position, count);
    }
    abstract void setResourceCount(int i);
    abstract Point nextPosition(WorldModel world, Point destPos);
}
