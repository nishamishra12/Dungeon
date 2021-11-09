package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import randomizer.Randomizer;

/**
 * This class represents a dungeon in which the player will be moving from one direction
 * in the dungeon to another, along with the various location types, treasures present in the
 * dungeon, and the paths from start to end of the dungeon.
 */
public class DungeonImpl implements Dungeon {

  private int rows;
  private int columns;
  private Randomizer randomizer;
  private List<Edge> edges;
  private List<Edge> mazeList;
  private int interconnectivity;
  private List<Location> locationList;
  private int treasurePercent;
  private Location startCave;
  private Location endCave;
  private Player player;
  private boolean wrapping;

  /**
   * Constructs a new dungeon where the player can move.
   *
   * @param rows              this parameter takes the no of rows the dungeon can have
   * @param columns           this parameter takes the no of columns the dungeon can have
   * @param interconnectivity this parameter takes the interconnectivity value of the dungeon
   * @param treasurePercent   this parameter takes the treasure percent of the dungeon
   * @param wrapping          this parameter takes the wrapping status of the dungeon
   * @param randomizer        this parameter takes the randomizer
   * @throws IllegalArgumentException when the values entered are invalid or null
   */
  public DungeonImpl(int rows, int columns, int interconnectivity, int treasurePercent,
                     boolean wrapping, Randomizer randomizer) throws IllegalArgumentException {

    if (rows <= 0) {
      throw new IllegalArgumentException("No of rows is invalid");
    }
    if (columns <= 0) {
      throw new IllegalArgumentException("No of columns is invalid");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Inter connectivity entered is invalid");
    }
    if (treasurePercent < 0 || treasurePercent > 100) {
      throw new IllegalArgumentException("Treasure percent is invalid. Should be between 0-100");
    }
    if (randomizer == null) {
      throw new IllegalArgumentException("Randomizer entered is null. Enter correct randomizer");
    }
    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = interconnectivity;
    this.treasurePercent = treasurePercent;
    this.randomizer = randomizer;
    this.wrapping = wrapping;
    this.edges = new ArrayList<>();
    KruskalAlgo maze = new KruskalAlgo();
    createMaze();
    mazeList = maze.kruskalAlgo(edges, rows * columns);
    createMazeList();
    createCaves();
    addNeighbors();
    setLocationType();
    addTreasureToCave();
    findMinPath();
    player = new PlayerImpl("John", startCave);
  }

  private void createMaze() {
    int[][] dunArr = new int[rows][columns];
    int counter = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        dunArr[i][j] = counter;
        counter++;
      }
    }

    //create edges for rows
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns - 1; j++) {
        edges.add(new Edge(dunArr[i][j], dunArr[i][j + 1], randomizer.getNextInt(1, 10)));
      }
    }
    //create edges for columns
    for (int i = 0; i < rows - 1; i++) {
      for (int j = 0; j < columns; j++) {
        edges.add(new Edge(dunArr[i][j], dunArr[i + 1][j], randomizer.getNextInt(1, 10)));
      }
    }
    if (wrapping) {
      for (int i = 0; i < rows; i++) {
        edges.add(new Edge(dunArr[i][0], dunArr[i][columns - 1], randomizer.getNextInt(0, 10)));
      }

      for (int i = 0; i < columns; i++) {
        edges.add(new Edge(dunArr[0][i], dunArr[rows - 1][i], randomizer.getNextInt(0, 10)));
      }
    }
  }

  private void createMazeList() {
    List<Edge> extraList = new ArrayList<>(edges);
    extraList.removeAll(mazeList);
    extraList = randomizer.shuffleList(extraList);

    if (interconnectivity > extraList.size()) {
      throw new IllegalArgumentException("Interconnectivity is wrong");
    }
    for (int i = 0; i < interconnectivity; i++) {
      mazeList.add(extraList.get(i));
    }
  }

  private void createCaves() {
    locationList = new ArrayList<>();
    for (int i = 0; i < rows * columns; i++) {
      locationList.add(new Cave(i));
    }
  }

  private void addNeighbors() {
    for (int i = 0; i < locationList.size(); i++) {
      for (int j = 0; j < mazeList.size(); j++) {
        if (locationList.get(i).getId() == mazeList.get(j).getSrc()) {
          if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() == 1) {
            locationList.get(i).addNeighbors(Direction.EAST,
                    locationList.get(mazeList.get(j).getDest()));
            locationList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.WEST, locationList.get(i));
          } else if (mazeList.get(j).getDest() - mazeList.get(j).getSrc()
                  == Math.abs(columns - 1)) {
            locationList.get(i).addNeighbors(Direction.WEST,
                    locationList.get(mazeList.get(j).getDest()));
            locationList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.EAST, locationList.get(i));
          } else if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() == columns) {
            locationList.get(i).addNeighbors(Direction.SOUTH,
                    locationList.get(mazeList.get(j).getDest()));
            locationList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.NORTH, locationList.get(i));
          } else if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() > columns) {
            locationList.get(i).addNeighbors(Direction.NORTH,
                    locationList.get(mazeList.get(j).getDest()));
            locationList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.SOUTH, locationList.get(i));
          }
        }
      }
    }
  }

  //setLocationType
  private void setLocationType() {
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getNeighbors().size() == 2) {
        locationList.get(i).setLocationType(LocationType.TUNNEL);
      } else {
        locationList.get(i).setLocationType(LocationType.CAVE);
      }
    }
  }

  //give treasure to cave
  private void addTreasureToCave() {
    List<Location> exclusiveCaveList = new ArrayList<>();
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getLocationType().equals(LocationType.CAVE)) {
        exclusiveCaveList.add(locationList.get(i));
      }
    }

    exclusiveCaveList = randomizer.shuffleList(exclusiveCaveList);

    int noOfCavesWithTreasure = (int) Math.ceil(((treasurePercent + randomizer.getNextInt(0,
            (100 - treasurePercent))) * exclusiveCaveList.size()) / 100.0);

    for (int i = 0; i < noOfCavesWithTreasure; i++) {
      List<Treasure> treasures = new ArrayList<>(Arrays.asList(Treasure.values()));
      for (int j = 0; j <= randomizer.getNextInt(0, treasures.size()); j++) {
        exclusiveCaveList.get(i).addTreasureList(treasures.get((randomizer
                .getNextInt(0, treasures.size())) % 3));
      }
    }
  }

  //start and end cave
  private void findMinPath() {

    List<Location> possibleStart = new ArrayList<Location>();

    //Only taking the caves as the possible start positions.
    for (Location location : locationList) {
      if (location.getLocationType() != LocationType.TUNNEL) {
        possibleStart.add(location);
      }
    }

    //Shuffling possible start to take random.
    possibleStart = randomizer.shuffleList(possibleStart);

    for (Location i : possibleStart) {
      this.startCave = i;
      Map<Location, Integer> level = bfs(this.startCave);
      this.endCave = null;
      for (Location name : level.keySet()) {
        if (level.get(name) >= 5 && name.getLocationType() == LocationType.CAVE) {
          this.endCave = name;
          break;
        }
      }
      if (this.endCave != null) {
        break;
      }
    }
    if (this.endCave == null) {
      throw new IllegalArgumentException("The matrix size is not valid, no start end possible!");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<Location, Integer> bfs(Location src) {
    if (src == null) {
      throw new IllegalArgumentException("Source location cannot be null");
    }

    LinkedList<Location> queue = new LinkedList();
    Map<Location, Integer> locationLevelMap = new HashMap<>();

    src.updateVisit(true);
    queue.add(src);
    locationLevelMap.put(src, 0);

    while (queue.size() != 0) {
      src = queue.poll();

      Iterator<Location> i = new ArrayList<Location>(src.getNeighbors().values()).listIterator();

      while (i.hasNext()) {
        Location n = i.next();
        if (!n.isVisited()) {
          n.updateVisit(true);
          queue.add(n);
          locationLevelMap.put(n, locationLevelMap.get(src) + 1);
        }
      }
    }

    for (Location i : locationList) {
      i.updateVisit(false);
    }
    return new HashMap<>(locationLevelMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String nextMove(String val) throws IllegalArgumentException {
    boolean result;
    if (val.equalsIgnoreCase("N")) {
      result = player.move(Direction.NORTH);
    } else if (val.equalsIgnoreCase("S")) {
      result = player.move(Direction.SOUTH);
    } else if (val.equalsIgnoreCase("E")) {
      result = player.move(Direction.EAST);
    } else if (val.equalsIgnoreCase("W")) {
      result = player.move(Direction.WEST);
    } else {
      throw new IllegalArgumentException("Invalid move");
    }
    if (!result) {
      return "This move is not possible, Enter another move";
    } else {
      return "Player moved";
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String dumpDungeon() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < locationList.size(); i++) {
      sb.append("\n" + locationList.get(i).getLocationType() + ": " + locationList.get(i).getId());
      Map<Direction, Location> treeMap = new TreeMap<Direction, Location>(
              locationList.get(i).getNeighbors());
      for (Map.Entry<Direction, Location> map : treeMap.entrySet()) {
        sb.append("\n-->" + map.getKey() + " Neighbor: " + map.getValue().getLocationType()
                + ": " + map.getValue().getId());
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void pickTreasure() {
    player.updateTreasureList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasReachedEnd() {
    return player.getCurrentLocation().getId() != endCave.getId() ? false : true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocationDescription() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("\nNext possible moves: ");
    Map<Direction, Location> treeMap = new TreeMap<>(player.getCurrentLocation().getNeighbors());
    for (Map.Entry<Direction, Location> set : treeMap.entrySet()) {
      stringBuilder.append("\n" + set.getKey().getDirection());
    }
    return stringBuilder.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTreasureDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    if (player.getCurrentLocation().getTreasureList().size() > 0) {
      stringBuilder.append("\nTreasure found in current cave.");
      for (int i = 0; i < player.getCurrentLocation().getTreasureList().size(); i++) {
        stringBuilder.append("\n" + player.getCurrentLocation()
                .getTreasureList().get(i).getTreasure());
      }
      stringBuilder.append("\nThere is treasure in the cave. Do you want to pick it up? (Y/N)");
    }
    return stringBuilder.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPlayerDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append("The player is in " + player.getCurrentLocation().getLocationType() + ": "
            + player.getCurrentLocation().getId());
    Map<Treasure, Integer> treasureMap = new TreeMap<>();
    for (int i = 0; i < player.getTreasureList().size(); i++) {
      if (treasureMap.containsKey(player.getTreasureList().get(i))) {
        treasureMap.put(player.getTreasureList().get(i),
                treasureMap.get(player.getTreasureList().get(i)) + 1);
      } else {
        treasureMap.put(player.getTreasureList().get(i), 1);
      }
    }
    if (treasureMap.isEmpty()) {
      sb.append("\nPlayer has no treasure");
    } else {
      sb.append("\nPlayer has following treasures:");
    }
    for (Map.Entry<Treasure, Integer> m : treasureMap.entrySet()) {
      sb.append(" " + m.getKey() + ": " + m.getValue());
    }
    return sb.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTreasure() {
    return player.getCurrentLocation().getTreasureList().size() > 0 ? true : false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Location> getLocationList() {
    return new ArrayList<Location>(this.locationList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Player getPlayer() {
    Player playerCopy = new PlayerImpl(this.player);
    return playerCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Location getEndCave() {
    Location endCaveCopy = new Cave(this.endCave);
    return endCaveCopy;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Location getStartCave() {
    Location startCaveCopy = new Cave(this.startCave);
    return startCaveCopy;
  }
}