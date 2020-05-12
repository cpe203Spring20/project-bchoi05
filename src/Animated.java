public interface Animated {
    void scheduleActions(EventScheduler scheduler,
                         WorldModel world,
                         ImageStore imageStore);
}
