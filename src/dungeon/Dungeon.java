package dungeon;

public interface Dungeon {

  public void nextMove(String val);

  public boolean tillEnd();

  public String getLocationDescription();

  public void pickTreasure();

  public boolean isTreasure();

  public String getTreasureDescription();

  public void dumpDungeon();
}
