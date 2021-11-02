package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the Kruskal Algorithm which is used to build the dungeon.
 */
class KruskalAlgo {

  private Map<Integer, Integer> parent;

  private void makeSet(int n) {
    parent = new HashMap<Integer, Integer>();
    for (int i = 0; i < n; i++) {
      parent.put(i, i);
    }
  }

  private int find(int k) {
    if (parent.get(k) == k) {
      return k;
    }
    return find(parent.get(k));
  }

  private void union(int a, int b) {
    int x = find(a);
    int y = find(b);
    parent.put(x, y);
  }

  public List<Edge> kruskalAlgo(List<Edge> edges, int n) {
    List<Edge> mst = new ArrayList<Edge>();
    KruskalAlgo ds = new KruskalAlgo();
    ds.makeSet(n);

    int index = 0;
    Collections.sort(edges, Comparator.comparingInt(e -> e.getWeight()));

    while (mst.size() != n - 1) {
      Edge nextEdge = edges.get(index++);
      int x = ds.find(nextEdge.getSrc());
      int y = ds.find(nextEdge.getDest());

      if (x != y) {
        mst.add(nextEdge);
        ds.union(x, y);
      }
    }
    return new ArrayList<Edge>(mst);
  }
}
