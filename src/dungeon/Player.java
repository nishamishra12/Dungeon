package dungeon;

import java.util.List;

/**
 * The interface represents the player playing in the dungeon, along with the treasures that
 * it possess, the current node that it is in the dungeon, and the move that the player takes inside
 * the dungeon.
 */
public interface Player {

  /**
   * This method provides the list of treasure that the player possess.
   *
   * @return the treasure list of the player
   */
  public List<Treasure> getTreasureList();

  /**
   * This method gives the current location (cave or tunnel) that the player is in the dungeon.
   *
   * @return the current location of the player
   */
  public Location getCurrentLocation();

  /**
   * This method moves the player in the direction given. The direction is valid when it is on of
   * the direction from the neighbor list of the current location of the player.
   *
   * @param direction this parameter takes the direction in which the player should move
   * @return the true if the player has successfully moved and false when the player hasn't
   * @throws IllegalArgumentException when the direction passed is invalid
   */
  public boolean move(Direction direction) throws IllegalArgumentException;

  /**
   * This method updates the list of treasure present with the player.
   * Adds treasure to the list when the player picks up treasure from the dungeon.
   */
  public void updateTreasureList();

  /**
   * This method provides the name of the player.
   *
   * @return the name of the player
   */
  public String getName();
}
