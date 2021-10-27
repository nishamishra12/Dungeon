package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A class to represent a disjoint set
class KruskalAlgo {
  List<Edge> MST;
  private final Map<Integer, Integer> parent = new HashMap<>();

  //constructor
  public KruskalAlgo(List<Edge> edge, int n) {
    kruskalAlgo(edge, n);
  }

  public KruskalAlgo() {
  }

  // perform MakeSet operation
  private void makeSet(int N) {
    // create `N` disjoint sets (one for each vertex)
    for (int i = 0; i < N; i++) {
      parent.put(i, i);
    }
  }

  // Find the root of the set in which element `k` belongs
  private int Find(int k) {
    // if `k` is root
    if (parent.get(k) == k) {
      return k;
    }
    // recur for the parent until we find the root
    return Find(parent.get(k));
  }

  // Perform Union of two subsets
  private void Union(int a, int b) {
    // find the root of the sets in which elements
    // `x` and `y` belongs
    int x = Find(a);
    int y = Find(b);
    parent.put(x, y);
  }

  // Function to construct MST using Kruskal’s algorithm
  public List<Edge> kruskalAlgo(List<Edge> edges, int N) {
    // stores the edges present in MST
    MST = new ArrayList();

    // Initialize `DisjointSet` class.
    // create a singleton set for each element of the universe.
    KruskalAlgo ds = new KruskalAlgo();
    ds.makeSet(N);

    int index = 0;

    // sort edges by increasing weight
    Collections.sort(edges, Comparator.comparingInt(e -> e.getWeight()));

    // MST contains exactly `V-1` edges
    while (MST.size() != N - 1) {
      // consider the next edge with minimum weight from the graph
      Edge next_edge = edges.get(index++);

      // find the root of the sets to which two endpoints
      // vertices of the next edge belongs
      int x = ds.Find(next_edge.getSrc());
      int y = ds.Find(next_edge.getDest());

      // if both endpoints have different parents, they belong to
      // different connected components and can be included in MST
      if (x != y) {
        MST.add(next_edge);
        ds.Union(x, y);
      }
    }
    System.out.println("mst list: " + MST);
    return MST;
  }
}
