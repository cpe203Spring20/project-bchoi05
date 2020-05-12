import processing.core.PImage;

import java.util.List;

public interface Miners{
    Point nextPositionMiner(WorldModel world, Point destPos);
    void setResourceCount(int i);
    Point getPosition();
    List<PImage> getImages();
    int getImageIndex();
    void setPosition(Point position);
}
