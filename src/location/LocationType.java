package location;

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
