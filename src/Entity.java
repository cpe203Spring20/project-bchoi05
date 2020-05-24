import processing.core.PImage;

import java.util.List;

public abstract class Entity {
    protected List<PImage> images;
    protected Point position;
    protected int imageIndex;
    public Entity(List<PImage> images, Point position, int imageIndex){
        this.images = images;
        this.position = position;
        this.imageIndex = imageIndex;
    }
    
    public Point getPosition(){ return position;};

    public List<PImage> getImages(){return images;}

    public int getImageIndex(){return imageIndex;};

    public void setPosition(Point position){
        this.position = position;
    }


}

