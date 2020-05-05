import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import processing.core.PImage;

public final class ImageStore
{
    public Map<String, List<PImage>> images;
    public List<PImage> defaultImages;
    private static final String BGND_KEY = "background";
    private static final String MINER_KEY = "miner";
    private static final String OBSTACLE_KEY = "obstacle";
    private static final String ORE_KEY = "ore";
    private static final String SMITH_KEY = "blacksmith";
    private static final String VEIN_KEY = "vein";
    private static final int PROPERTY_KEY = 0;

    public ImageStore(PImage defaultImage) {
        this.images = new HashMap<>();
        defaultImages = new LinkedList<>();
        defaultImages.add(defaultImage);
    }

    public List<PImage> getImageList(String key) {
        return images.getOrDefault(key, defaultImages);
    }

    public boolean processLine(
            String line, WorldModel world)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return Functions.parseBackground(properties, world, this);
                case MINER_KEY:
                    return Functions.parseMiner(properties, world, this);
                case OBSTACLE_KEY:
                    return Functions.parseObstacle(properties, world, this);
                case ORE_KEY:
                    return Functions.parseOre(properties, world, this);
                case SMITH_KEY:
                    return Functions.parseSmith(properties, world, this);
                case VEIN_KEY:
                    return Functions.parseVein(properties, world, this);
            }
        }

        return false;
    }



}
