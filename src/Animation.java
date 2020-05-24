public final class Animation implements Action{
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Animation(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public void executeAction(
            EventScheduler scheduler) {
        ((AnimatedEntity) entity).nextImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(entity,
                    createAction(entity,
                            Math.max(repeatCount - 1,
                                    0)),
                    ((AnimatedEntity) entity).getAnimationPeriod());
        }
    }
    public static Animation createAction(Entity entity, int repeatCount) {
        return new Animation(entity, null, null,
                repeatCount);
    }
}
