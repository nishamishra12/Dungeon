# Readme Documentation - Dungeon

## About/Overview
The game consists of a dungeon, a network of tunnels and caves that are interconnected
so that player can explore the entire world by traveling from cave to cave through the tunnels that
connect them

The project creates a dungeon which will allow a player to enter in to the dungeon, move from one
location to another location via any of the possible directions - North, South, East, and West.
Pickup treasure from the cave if treasure is present. The dungeon created can be wrapped 
or unwrapped along with different degree of interconnectivity.

## List of Features
1. The dungeon starts by taking rows, columns, treasure percentage, wrapping status, and interconnectivity of the dungeon.
2. Treasure are randomly added to specific percentage of the caves.
3. Dungeon can wrap or not from one side to another.
4. A new dungeon is created randomly every time resulting in a different network each time the game begins.
5. Every location in the dungeon is connected to every other location. Increasing the degree of inter-connectivity increases number of paths from one location to another.
6. Player can move from one location to another in any of the 4 directions - North, South, East, West.
7. Player can decide to pick treasure located in the dungeon.
8. A list is maintained with the player of all the treasures.

## How to Run
1. Open Terminal
2. Navigate to the location where you have stored the jar file.
3. You need to provide the below mentioned parameters as command line arguments in the order:
   1. number of rows in the dungeon 2D matrix
   2. number of columns in the dungeon 2D matrix
   3. The degree of interconnectivity
   4. The percentage of caves which should contain treasure
   5. Boolean value showing if the dungeon is wrapping or non-wrapping
      1. Wrapping Dungeon : true
      2. Non-wrapping Dungeon : false
4. Run the jar file using the
   command java - jar Dungeon.jar <rows> <cols> <interConnectivity> <treasurePercent> <wrapping>

## How to use the program
1. Enter the no of rows, no of columns, treasure percentage, interconnectivity level, and wrapping status.
2. Dumping the dungeon
3. Displaying the description of the player, which includes the current location the player is in and the treasures that it holds.
4. Enter if the player wants to pick up the treasure or not.
5. Displaying the next possible moves the player can make from the current location.
6. Enter the next direction in which the player should move.
7. The player has to give direction in each iteration till the player reaches the end.
8. The program will terminate if incorrect value is provided for the direction.

## Description of Examples
### Run 1 – RUN 1.txt
This run shows an un-wrapped dungeon where player is moved from start to end.
1. Provide Command Line arguments for row, columns, interconnectivity, treasure percentage, and wrapping false
2. Dumping the dungeon
3. Printing the start and end cave of the dungeon
4. Starting the game, player is at the start cave
5. Printing player description
6. If treasure present at the location, print treasure description
7. Ask player if they want to pickup treasure
8. Pickup treasure if player enters 'Y'
9. Printing location description which shows all possible directions in which player can move
10. Enter the direction in which the player should move
11. Moving the player to new location
12. Iterate the same process till the player reaches the end cave

### Run 2 – RUN 2.txt
This run shows a wrapped dungeon where player is moved in every location in the dungeon.
1. Provide Command Line arguments for row, columns, interconnectivity, treasure percentage, and wrapping true
2. Dumping the dungeon
3. Printing the start and end cave of the dungeon
4. Starting the game, player is at the start cave
5. Printing player description
6. If treasure present at the location, print treasure description
7. Ask player if they want to pickup treasure
8. Pickup treasure if player enters 'Y'
9. Printing location description which shows all possible directions in which player can move
10. Enter the direction in which the player should move
11. Moving the player to new location
12. Iterate the same process and move the player in every node of the dungeon

## Design/Model Changes
1. Initially the Kruskal was supposed to be implemented in the dungeon class.
   However, now there is a new class which takes the edges and calculates the minimum spanning tree using Kruskal algorithm.
2. A new class to represent the edge of the dungeon is created.

## Assumptions
1. Each cave can have upto three treasures of any type
2. Treasure percentage is an integer

## Limitations
1. The player will pick up all the treasure at once from a location

## Citations
1. https://www.baeldung.com/java-spanning-trees-kruskal
2. https://www.geeksforgeeks.org/shortest-path-unweighted-graph/

