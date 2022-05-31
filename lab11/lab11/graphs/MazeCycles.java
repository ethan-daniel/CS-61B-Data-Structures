package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private boolean cycleFound = false;

    public MazeCycles(Maze m) {
        super(m);
        s = 0;
    }

    @Override
    public void solve() {
        dfsCycleFinder(s);
    }

    // Helper methods go here
    private void dfsCycleFinder(int v) {
        marked[v] = true;
        announce();

        if (cycleFound) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (marked[w]) {
                cycleFound = true;
            }
            if (!marked[w]) {
                //edgeTo[w] = v;
                announce();
                //distTo[w] = distTo[v] + 1;
                dfsCycleFinder(w);
                if (cycleFound) {
                    return;
                }
            }
        }
    }
}

