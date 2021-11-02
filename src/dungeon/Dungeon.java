package dungeon;

import java.util.List;
import java.util.Map;

/**
 * The interface represents a dungeon in which the player will be moving from one direction
 * to another, along with the various location types, treasures present in the
 * dungeon, and the paths from start to end of the dungeon.
 */
public interface Dungeon {

  /**
   * This method moves the player from one location to the other based on the location value that
   * is passed.
   *
   * @param val this parameter takes the location value of the direction
   *            in which the player has to move
   * @return the result of the move
   * @throws IllegalArgumentException when the move entered is invalid
   */
  public String nextMove(String val) throws IllegalArgumentException;

  /**
   * This method checks if the player has reached end of the dungeon.
   *
   * @return true or false depending on if the player has reached end or not
   */
  public boolean hasReachedEnd();

  /**
   * This method provides the description of the current location the player is in along with
   * its next possible moves.
   *
   * @return the string representation of the current location and next possible moves
   */
  public String getLocationDescription();

  /**
   * This method updates the treasure list of the location and the player once the player decides
   * to pick up the treasure at the current location.
   */
  public void pickTreasure();

  /**
   * This method checks if the treasure is present at the current location.
   *
   * @return true or false based on whether the treasure is present at the location or not.
   */
  public boolean isTreasure();

  /**
   * This method provides the description of the treasure present in the current location that
   * the player is in.
   *
   * @return the string representation of the treasures present at the current location
   */
  public String getTreasureDescription();

  /**
   * This method gives the entire dungeon along with all the edges, nodes and its neighbors.
   *
   * @return the string representation of the dungeon.
   */
  public String dumpDungeon();

  /**
   * This method provides the all the locations present in the dungeon.
   *
   * @return list of location objects present in the dungeon
   */
  public List<Location> getLocationList();

  /**
   * This method is used to apply the BFS algorithm on the dungeon where the root node is set as
   * the src provided in the parameters and after creating the BFS path, it will provide the list of
   * all the nodes and their levels in the BFS tree.
   *
   * @param src Source that will be used as the root node of the BFS path.
   * @return the map that contains all the nodes and their levels in the created BFS tree.
   * @throws IllegalArgumentException if the src is null.
   */
  public Map<Location, Integer> bfs(Location src) throws IllegalArgumentException;

  /**
   * This method provides the player playing in the dungeon.
   *
   * @return the player
   */
  public Player getPlayer();

  /**
   * This method provides the end cave of the dungeon.
   *
   * @return the end cave
   */
  public Location getEndCave();

  /**
   * This method provides the start cave of the dungeon.
   *
   * @return the start cave.
   */
  public Location getStartCave();

  /**
   * This method provides the description of the current cave of the player and its treasures.
   *
   * @return the string representation of the player description
   */
  public String getPlayerDescription();
}
