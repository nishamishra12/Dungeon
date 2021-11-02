package dungeon;

/**
 * This is an enumeration for the type of location present in the dungeon.
 */
public enum LocationType {

  CAVE("Cave"),
  TUNNEL("Tunnel");

  private final String locationType;

  LocationType(String locationType) {
    this.locationType = locationType;
  }

  public String getLocationType() {
    return locationType;
  }
}
