import processing.core.PImage;

import java.util.List;

public abstract class Entity {
    abstract Point getPosition();

    abstract List<PImage> getImages();

    abstract int getImageIndex();

    abstract void setPosition(Point position);


}

