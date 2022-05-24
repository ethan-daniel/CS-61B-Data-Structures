package hw4.puzzle;

public class Board implements WorldState {

    /** Constructs a board from an N-by-N array of tiles where
     tiles[i][j] = tile at row i, column j */
    public Board(int[][] tiles) {

    }

    /** Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {
        return 0;
    }

    /** Returns the board size N */
    public int size() {
        return 0;
    }

    /** Returns the neighbors of the current board */
    public Iterable<WorldState> neighbors() {
        return null;
    }

    /** The number of tiles in the wrong position. */
    public int hamming() {
        return 0;
    }

    /** The sum of the Manhattan distances
     * (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions.*/
    public int manhattan() {
        return 0;
    }

    /** Estimated distance to goal. */
    public int estimatedDistanceToGoal() {
        return 0;
    }

    /** Returns true if this board's tile values are the same
     position as y's */
    public boolean equals(Object y) {
        return false;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
