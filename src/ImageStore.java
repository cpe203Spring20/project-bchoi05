import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;


public final class ImageStore {
    public Map<String, List<PImage>> images;
    public List<PImage> defaultImages;
    public static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

    public ImageStore(PImage defaultImage) {
        this.images = new HashMap<>();
        defaultImages = new LinkedList<>();
        defaultImages.add(defaultImage);
    }

    public List<PImage> getImageList(String key) {
        return images.getOrDefault(key, defaultImages);
    }


    public void loadImages(
            Scanner in, PApplet screen)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                processImageLine(images, in.nextLine(), screen);
            }
            catch (NumberFormatException e) {
                System.out.println(
                        String.format("Image format error on line %d",
                                lineNumber));
            }
            lineNumber++;
        }
    }

    public static void processImageLine(
            Map<String, List<PImage>> images, String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = Functions.getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    Functions.setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }

    public static PImage getCurrentImage(Object entity) {
        if (entity instanceof Background) {
            return ((Background) entity).images.get(
                    ((Background) entity).imageIndex);
        } else if (entity instanceof Entity) {
            return ((Entity) entity).images.get(((Entity) entity).imageIndex);
        } else {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            entity));
        }
    }

}
