import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class BlackSmith extends Entity{

    public BlackSmith(
            Point position,
            List<PImage> images) {
        super(images, position, 0);
    }

    public static BlackSmith createBlacksmith(
            Point position, List<PImage> images) {
        return new BlackSmith(position, images);
    }




}