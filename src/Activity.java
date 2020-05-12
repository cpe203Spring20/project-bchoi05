public final class Activity implements Action
{
    public Entity entity;
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public Activity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public void executeAction(
            EventScheduler scheduler)
    {
         if (entity instanceof MinerFull){
        ((MinerFull) entity).executeMinerFullActivity(world,
                imageStore, scheduler);
        }

        else if (entity instanceof MinerNotFull){
            ((MinerNotFull) entity).executeMinerNotFullActivity(world,
            imageStore, scheduler);
        }

         else if (entity instanceof Ore) {
             ((Ore) entity).executeOreActivity(world,
                     imageStore, scheduler);
         }

         else if (entity instanceof OreBlob) {
             ((OreBlob) entity).executeOreBlobActivity(world,
                     imageStore, scheduler);
         }

         else if (entity instanceof Quake) {
             ((Quake) entity).executeQuakeActivity(world,
                     imageStore, scheduler);
         }

         else if (entity instanceof Vein) {
             ((Vein) entity).executeVeinActivity(world,
                     imageStore, scheduler);
         }

         else {
            throw new UnsupportedOperationException(String.format(
                    "executeActivityAction not supported for %s",
                    entity.getClass()));
        }
    }

    public static Activity createAction(
            Entity entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore, 0);
    }

}
