package location;

import java.util.List;
import java.util.Map;

public interface Location {

  public int getId();

  public void addNeighbors(Direction direction, Location location);

  public Map<Direction,Location> getNeighbors();

  public void addTreasureList(Treasure treasure);

  public List<Treasure> getTreasureList();

  public void setLocationType(LocationType locationType);

  public LocationType getLocationType();

  public void updateVisit(boolean visit);

  public boolean isVisited();

  public void removeTreasure();
}
