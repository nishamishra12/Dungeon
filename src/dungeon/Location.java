package dungeon;

import java.util.List;
import java.util.Map;

/**
 * This interface represents a location or node in the dungeon along with possible neighbors
 * of the current location, the treasures present at the current location,
 * and a check if the node has been visited.
 */
public interface Location {

  /**
   * This method provides the id of the cave.
   *
   * @return the cave id
   */
  public int getId();

  /**
   * This method adds the given location to the specified direction in the current location
   * neighbor list.
   *
   * @param direction this parameter takes the direction of the new neighbor to be added
   * @param location  this parameter takes the new location that needs to be added as a neighbor
   * @throws IllegalArgumentException when the direction or location passed is null
   */
  public void addNeighbors(Direction direction, Location location) throws IllegalArgumentException;

  /**
   * This method provides all the neighbor location in different direction of the current
   * location.
   *
   * @return the map of direction and location of all the neighbors
   */
  public Map<Direction, Location> getNeighbors();

  /**
   * This method adds the treasures to the list of treasures in the location.
   *
   * @param treasure this parameter takes the treasure that needs to be added to the treasure list
   * @throws IllegalArgumentException when the treasure passed is null
   */
  public void addTreasureList(Treasure treasure) throws IllegalArgumentException;

  /**
   * This method provides all the treasures present at the location.
   *
   * @return the list of treasures
   */
  public List<Treasure> getTreasureList();

  /**
   * This method provides the location type of the location.
   *
   * @return the location type
   */
  public LocationType getLocationType();

  /**
   * This method sets the type of the location.
   *
   * @param locationType this parameter takes the type of location that needs to be set
   * @throws IllegalArgumentException when the type of location passed is null
   */
  public void setLocationType(LocationType locationType) throws IllegalArgumentException;

  /**
   * This method updates the visited status of the location.
   *
   * @param visit this parameter takes the visit value that needs to be updated
   */
  public void updateVisit(boolean visit);

  /**
   * This method gives the visit status of the location whether true or false depending on if the
   * location has been visited or not.
   *
   * @return the visit status
   */
  public boolean isVisited();

  /**
   * This method removes the treasures from the location.
   */
  public void removeTreasure();
}
