package location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cave implements Location {

  private int id;
  private Map<Direction, Location> directionCaveMap;
  private List<Treasure> treasureList;
  private LocationType locationType;
  private boolean isVisited;

  public Cave(int id) {
    directionCaveMap = new HashMap<>();
    treasureList = new ArrayList<>();
    this.id = id;
    this.isVisited = false;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void addNeighbors(Direction direction, Location location) {
    this.directionCaveMap.put(direction, location);
  }

  @Override
  public Map<Direction, Location> getNeighbors() {
    Map<Direction, Location> directionLocationMapCopy = new HashMap<>(this.directionCaveMap);
    return directionLocationMapCopy;
  }

  @Override
  public void addTreasureList(Treasure treasure) {
    this.treasureList.add(treasure);
  }

  @Override
  public List<Treasure> getTreasureList() {
    return new ArrayList<>(this.treasureList);
  }

  @Override
  public void setLocationType(LocationType locationType) {
    this.locationType = locationType;
  }

  @Override
  public LocationType getLocationType() {
    LocationType locationTypeCopy = this.locationType;
    return locationTypeCopy;
  }

  @Override
  public void updateVisit(boolean visit) {
    this.isVisited = visit;
  }

  @Override
  public boolean isVisited() {
    return this.isVisited;
  }

  @Override
  public void removeTreasure() {
    this.treasureList.clear();
  }
}
