package dungeon;

class Edge
{
  private int src, dest, weight;

  public Edge(int src, int dest, int weight)
  {
    this.src = src;
    this.dest = dest;
    this.weight = weight;
  }

  @Override
  public String toString() {
    return "(" + src + ", " + dest + ", " + weight + ")";
  }

  public int getSrc() {
    return src;
  }

  public int getDest() {
    return dest;
  }

  public int getWeight() {
    return weight;
  }

}
