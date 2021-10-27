package location;

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
