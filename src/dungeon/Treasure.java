package dungeon;

/**
 * This is an enumeration for the types of treasure present in the dungeon.
 */
public enum Treasure {

  DIAMOND("Diamond"),
  RUBY("Ruby"),
  SAPPHIRE("Sapphire");

  private final String treasure;

  Treasure(String treasure) {
    this.treasure = treasure;
  }

  public String getTreasure() {
    return treasure;
  }
}
