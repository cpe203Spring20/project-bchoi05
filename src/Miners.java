import processing.core.PImage;

import java.util.List;

public interface Miners extends NextPosition{
    void setResourceCount(int i);
    Point getPosition();
    List<PImage> getImages();
    void setPosition(Point position);
}
