package player;

import java.util.List;

import location.Direction;
import location.Location;
import location.Treasure;

public interface Player {

  public List<Treasure> getTreasureList();

  public Location getCurrentLocation();

  public boolean move(Direction direction);

  public void updateTreasureList();

}
