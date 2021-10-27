package location;

public enum Direction {

  NORTH("North"),
  SOUTH("South"),
  EAST("East"),
  WEST("West");

  private final String direction;

  Direction(String direction) {
    this.direction = direction;
  }

  public String getDirection() {
    return direction;
  }
}
