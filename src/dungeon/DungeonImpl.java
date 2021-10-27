package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import location.Cave;
import location.Direction;
import location.Location;
import location.LocationType;
import location.Treasure;
import player.Player;
import player.PlayerImpl;
import randomizer.ActualRandomizer;
import randomizer.Randomizer;

public class DungeonImpl implements Dungeon {

  private int rows;
  private int columns;
  private KruskalAlgo maze;
  private Randomizer randomizer;
  private List<Edge> edges = new ArrayList<>();
  private List<Edge> mazeList;
  private int interconnectivity;
  private List<Location> cavesList;
  private int treasurePercent;
  private Location startCave;
  private Location endCave;
  private Player player;
  private boolean wrapping;

  public DungeonImpl(int rows, int columns, int interconnectivity, int treasurePercent,
                     boolean wrapping, Randomizer randomizer) {
    this.rows = rows;
    this.columns = columns;
    this.interconnectivity = interconnectivity;
    this.treasurePercent = treasurePercent;
    this.randomizer = randomizer;
    this.wrapping = wrapping;
    maze = new KruskalAlgo();
    createMaze();
    mazeList = maze.kruskalAlgo(edges, rows * columns);
    createMazeList();
    createCaves();
    addNeighbors();
    setLocationType();
    addTreasureToCave();
    findMinPath();
    player = new PlayerImpl("Nisha", startCave);
    System.out.println("Start Cave: " + startCave.getId());
    System.out.println("End Cave: " + endCave.getId());
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
    System.out.println("original list: " + edges);
  }

  private void createMazeList() {
    List<Edge> extraList = new ArrayList<>(edges);
    extraList.removeAll(mazeList);
    Collections.shuffle(extraList);
    for (int i = 0; i < interconnectivity; i++) {
      mazeList.add(extraList.get(i));
    }
    if (this.wrapping) {
      int src = 0;
      for (int i = 0; i < rows; i++) {
        mazeList.add(new Edge(src, src + columns - 1, new ActualRandomizer().getNextInt(1, 10)));
        src = src + columns;
      }

      for (int i = 0; i < columns; i++) {
        mazeList.add(new Edge(i, i + (columns*2), new ActualRandomizer().getNextInt(1, 10)));
      }
    }
    System.out.println("list with interconnectivity: " + mazeList);
  }

  private void createCaves() {
    cavesList = new ArrayList<>();
    for (int i = 0; i < rows * columns; i++) {
      cavesList.add(new Cave(i));
    }
  }

  private void addNeighbors() {
    for (int i = 0; i < cavesList.size(); i++) {
      for (int j = 0; j < mazeList.size(); j++) {
        if (cavesList.get(i).getId() == mazeList.get(j).getSrc()) {
          if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() == 1) {
            cavesList.get(i).addNeighbors(Direction.EAST, cavesList.get(mazeList.get(j).getDest()));
            cavesList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.WEST, cavesList.get(i));
          } else if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() == rows) {
            cavesList.get(i).addNeighbors(Direction.WEST, cavesList.get(mazeList.get(j).getDest()));
            cavesList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.EAST, cavesList.get(i));
          } else if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() == columns) {
            cavesList.get(i).addNeighbors(Direction.SOUTH, cavesList.get(mazeList.get(j).getDest()));
            cavesList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.NORTH, cavesList.get(i));
          } else if (mazeList.get(j).getDest() - mazeList.get(j).getSrc() > columns) {
            cavesList.get(i).addNeighbors(Direction.NORTH, cavesList.get(mazeList.get(j).getDest()));
            cavesList.get(mazeList.get(j).getDest())
                    .addNeighbors(Direction.SOUTH, cavesList.get(i));
          }
        }
      }
    }
  }

  //setLocationType
  private void setLocationType() {
    for (int i = 0; i < cavesList.size(); i++) {
      if (cavesList.get(i).getNeighbors().size() == 2) {
        cavesList.get(i).setLocationType(LocationType.TUNNEL);
      } else {
        cavesList.get(i).setLocationType(LocationType.CAVE);
      }
    }
  }

  //give treasure to cave
  private void addTreasureToCave() {

    List<Location> exclusiveCaveList = new ArrayList<>();

    for (int i = 0; i < cavesList.size(); i++) {
      if (cavesList.get(i).getLocationType().equals(LocationType.CAVE)) {
        exclusiveCaveList.add(cavesList.get(i));
      }
    }

    Collections.shuffle(exclusiveCaveList);

    int size = randomizer.getNextInt((int) Math.floor(exclusiveCaveList.size()
            * (treasurePercent / 100)), exclusiveCaveList.size());

    for (int i = 0; i < size; i++) {
      List<Treasure> treasures = new ArrayList<>(Arrays.asList(Treasure.values()));
      for (int j = 0; j < randomizer.getNextInt(0, treasures.size()) + 1; j++) {
        exclusiveCaveList.get(i).addTreasureList(treasures.get(randomizer.getNextInt(0, treasures.size())));
      }
    }
  }

  //start and end cave
  private void findMinPath() {
    List<Location> tempList = new ArrayList<>(cavesList);
    Collections.shuffle(tempList);
    startCave = tempList.get(randomizer.getNextInt(0, tempList.size()));
    endCave = tempList.get(randomizer.getNextInt(0, tempList.size()));
    if (startCave.getId() == endCave.getId()) {
      findMinPath();
    } else {
      if (!dfs(startCave, endCave, 0)) {
        findMinPath();
      }
    }
  }

  private boolean dfs(Location startCave, Location endCave, int count) {
    startCave.updateVisit(true); // to avoid cycle

    boolean result = false;

    if (count >= 5) {
      return true;
    }
    for (Direction d : Direction.values()) {
      if (startCave.getNeighbors().containsKey(d) && !startCave.getNeighbors().get(d).isVisited()) {
        count++;
        result = dfs(startCave.getNeighbors().get(d), endCave, count);
      }
    }
    startCave.updateVisit(false);
    return result;
  }

  public void nextMove(String val) {
    boolean result;
    switch (val) {
      case "N":
        result = player.move(Direction.NORTH);
        break;
      case "S":
        result = player.move(Direction.SOUTH);
        break;
      case "E":
        result = player.move(Direction.EAST);
        break;
      case "W":
        result = player.move(Direction.WEST);
        break;
      default:
        throw new IllegalArgumentException("Invalid move");
    }
    if (!result) {
      throw new IllegalArgumentException("This move is not possible");
    }
  }

  public void dumpDungeon() {
    for(int i=0; i<cavesList.size();i++) {
      System.out.println("\n Cave: "+cavesList.get(i).getId());
      for(Map.Entry<Direction, Location> map: cavesList.get(i).getNeighbors().entrySet()) {
        System.out.println(map.getKey()+"Cave: "+map.getValue().getId());
      }
    }
  }

  public void pickTreasure() {
    player.updateTreasureList();
  }

  public boolean tillEnd() {
    return player.getCurrentLocation().getId() != endCave.getId() ? true : false;
  }

  public String getLocationDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Current location in the maze: Cave " + player.getCurrentLocation().getId());
    for (int i = 0; i < player.getTreasureList().size(); i++) {
      stringBuilder.append("\n" + player.getTreasureList().get(i).getTreasure());
    }

    stringBuilder.append("\n Next possible moves: ");
    for (Map.Entry<Direction, Location> set : player.getCurrentLocation().getNeighbors().entrySet()) {
      stringBuilder.append("\n" + set.getKey().getDirection());
    }
    return stringBuilder.toString();
  }

  public String getTreasureDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    if (player.getCurrentLocation().getTreasureList().size() > 0) {
      stringBuilder.append("\n Treasure found in current cave.");
      for (int i = 0; i < player.getCurrentLocation().getTreasureList().size(); i++) {
        stringBuilder.append("\n" + player.getCurrentLocation().getTreasureList().get(i).getTreasure());
      }
      stringBuilder.append("\n There is treasure in the cave. Do you want to pick it up? (Y/N)");
    }
    return stringBuilder.toString();
  }

  public boolean isTreasure() {
    return player.getCurrentLocation().getTreasureList().size() > 0 ? true : false;
  }


}