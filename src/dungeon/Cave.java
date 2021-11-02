package dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a location or node in the dungeon along with possible neighbors
 * of the current location, the treasures present at the current location,
 * and a check if the node has been visited.
 */
public class Cave implements Location {

  private int id;
  private Map<Direction, Location> directionCaveMap;
  private List<Treasure> treasureList;
  private LocationType locationType;
  private boolean isVisited;

  /**
   * Constructs a location node with its id.
   *
   * @param id this parameter takes the id of the location
   * @throws IllegalArgumentException when then id passed is invalid
   */
  public Cave(int id) throws IllegalArgumentException {

    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be negative");
    }
    directionCaveMap = new HashMap<Direction, Location>();
    treasureList = new ArrayList<Treasure>();
    this.id = id;
    this.isVisited = false;
  }

  /**
   * Copy constructor of the Cave class to maintain a defensive copy.
   *
   * @param location for which the copy is constructed
   * @throws IllegalArgumentException when the location entered is null
   */
  public Cave(Location location) {
    if (location == null) {
      throw new IllegalArgumentException("The location entered is null");
    }
    directionCaveMap = location.getNeighbors();
    treasureList = location.getTreasureList();
    this.id = location.getId();
    this.isVisited = location.isVisited();
    this.locationType = location.getLocationType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addNeighbors(Direction direction, Location location) throws IllegalArgumentException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    if (location == null) {
      throw new IllegalArgumentException("Location cannot be null");
    }
    this.directionCaveMap.put(direction, location);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<Direction, Location> getNeighbors() {
    Map<Direction, Location> directionLocationMapCopy =
            new HashMap<Direction, Location>(this.directionCaveMap);
    return directionLocationMapCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addTreasureList(Treasure treasure) throws IllegalArgumentException {
    if (treasure == null) {
      throw new IllegalArgumentException("Treasure argument is invalid");
    }
    this.treasureList.add(treasure);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Treasure> getTreasureList() {
    return new ArrayList<Treasure>(this.treasureList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LocationType getLocationType() {
    LocationType locationTypeCopy = this.locationType;
    return locationTypeCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLocationType(LocationType locationType) throws IllegalArgumentException {
    if (locationType == null) {
      throw new IllegalArgumentException("Location Type argument is invalid");
    }
    this.locationType = locationType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateVisit(boolean visit) {
    this.isVisited = visit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisited() {
    return this.isVisited;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeTreasure() {
    this.treasureList.clear();
  }
}
