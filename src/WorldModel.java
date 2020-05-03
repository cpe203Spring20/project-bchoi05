import processing.core.PImage;

import java.util.*;

public final class WorldModel
{
    public int numRows;
    public int numCols;
    private Background background[][];
    private Entity occupancy[][];
    public Set<Entity> entities;

    public static final int ORE_REACH = 1;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }
    public void removeEntity(Point position) {
        removeEntityAt(position);
    }
    public void removeEntityAt(Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.position = new Point(-1, -1);
            entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }
    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < numRows && pos.x >= 0
                && pos.x < numCols;
    }
    public Entity getOccupancyCell(Point pos) {
        return occupancy[pos.y][pos.x];
    }

    public void setOccupancyCell(
            Point pos, Entity entity)
    {
        occupancy[pos.y][pos.x] = entity;
    }
    public Optional<PImage> getBackgroundImage(
            Point pos)
    {
        if (withinBounds(pos)) {
            return Optional.of(getCurrentImage(getBackgroundCell(pos)));
        }
        else {
            return Optional.empty();
        }
    }
    public Background getBackgroundCell(Point pos) {
        return background[pos.y][pos.x];
    }
    public Optional<Entity> findNearest(Point pos, EntityKind kind)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : entities) {
            if (entity.kind == kind) {
                ofType.add(entity);
            }
        }

        return pos.nearestEntity(ofType);
    }

    public boolean moveToNotFull(
            Entity miner,
            Entity target,
            EventScheduler scheduler) {
        if (miner.position.adjacent(target.position)) {
            miner.resourceCount += 1;
            removeEntity(target.position);
            scheduler.unscheduleAllEvents(target);

            return true;
        } else {
            Point nextPos = miner.nextPositionMiner(this, target.position);

            if (!miner.position.equals(nextPos)) {
                Optional<Entity> occupant = getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                moveEntity(miner, nextPos);
            }
            return false;
        }
    }
    public boolean moveToFull(
            Entity miner,
            Entity target,
            EventScheduler scheduler)
    {
        if (miner.position.adjacent(target.position)) {
            return true;
        }
        else {
            Point nextPos = miner.nextPositionMiner(this, target.position);

            if (!miner.position.equals(nextPos)) {
                Optional<Entity> occupant = getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                moveEntity(miner, nextPos);
            }
            return false;
        }
    }

    public boolean moveToOreBlob(
            Entity blob,
            Entity target,
            EventScheduler scheduler)
    {
        if (blob.position.adjacent(target.position)) {
            removeEntity(target.position);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = blob.nextPositionOreBlob(this, target.position);

            if (!blob.position.equals(nextPos)) {
                Optional<Entity> occupant = getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                moveEntity(blob, nextPos);
            }
            return false;
        }
    }
    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    /*
   Assumes that there is no entity currently occupying the
   intended destination cell.
*/
    public void addEntity(Entity entity) {
        if (withinBounds(entity.position)) {
            setOccupancyCell(entity.position, entity);
            entities.add(entity);
        }
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.position;
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.position = pos;
        }
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }

    public void setBackground(Point pos, Background background)
    {
        if (withinBounds(pos)) {
            setBackgroundCell(pos, background);
        }
    }


    public void setBackgroundCell(Point pos, Background background1)
    {
        background[pos.y][pos.x] = background1;
    }

    public void tryAddEntity(Entity entity) {
        if (isOccupied(entity.position)) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    public Optional<Point> findOpenAround(Point pos) {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
                Point newPt = new Point(pos.x + dx, pos.y + dy);
                if (withinBounds(newPt) && !isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
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
