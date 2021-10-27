package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import location.Direction;
import location.Location;
import location.Treasure;

public class PlayerImpl implements Player {

  private Location currentCave;
  private List<Treasure> treasureList = new ArrayList<>();
  private String name;

  public PlayerImpl(String name, Location currentCave) {
    this.name = name;
    this.currentCave = currentCave;
  }

  @Override
  public List<Treasure> getTreasureList() {
    return new ArrayList<>(this.treasureList);
  }

  @Override
  public Location getCurrentLocation() {
    Location currentCaveCopy = this.currentCave;
    return currentCaveCopy;
  }

  @Override
  public boolean move(Direction direction) {
    Map<Direction, Location> neighborMap = new HashMap<>(this.currentCave.getNeighbors());
    if(neighborMap.containsKey(direction)) {
      this.currentCave = neighborMap.get(direction);
      return true;
    }
    return false;
  }

  @Override
  public void updateTreasureList() {
    this.treasureList.addAll(this.currentCave.getTreasureList());
    this.currentCave.removeTreasure();
  }
}
