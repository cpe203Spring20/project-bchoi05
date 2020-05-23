public abstract class ActiveEntity extends Entity{
    abstract int getactionPeriod();
    abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
}
