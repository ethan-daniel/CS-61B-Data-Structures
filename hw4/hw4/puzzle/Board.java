package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;
import java.util.Objects;
public class Board implements WorldState {
    private final int BLANK = 0;
    private int[][] board;

    /** Constructs a board from an N-by-N array of tiles where
     tiles[i][j] = tile at row i, column j */
    public Board(int[][] tiles) {
        board = new int[tiles.length][tiles[0].length];

        for (int i = 0; i != tiles.length; ++i) {
            for (int j = 0; j != tiles.length; ++j) {
                board[i][j] = tiles[i][j];
            }
        }

    }

    /** Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {
        if (i < 0 || i >= size() || j < 0 || j >= size()) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return board[i][j];
    }

    /** Returns the board size N */
    public int size() {
        return board.length;
    }

    /** Returns the neighbors of the current board
     * Source: http://joshh.ug/neighbors.html */
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** The number of tiles in the wrong position. */
    public int hamming() {
        int count = 0;

        for (int i = 0; i != this.size(); ++i) {
            for (int j = 0; j != this.size(); ++j) {
                if (tileAt(i, j) != i * size() + j + 1 && tileAt(i, j) != BLANK) {
                    ++count;
                }
            }
        }

        return count;
    }

    /** The sum of the Manhattan distances
     * (sum of the vertical and horizontal distance)
     * from the tiles to their goal positions.
     * Source of Idea: MT2 Review Document. */
    public int manhattan() {
        int count = 0;
        for (int i = 0; i != size(); ++i) {
            for (int j = 0; j != size(); ++j) {
                if (tileAt(i, j) != BLANK) {
                    int row = (tileAt(i, j) - 1) / size();
                    int col = (tileAt(i, j) - 1) % size();
                    count += (Math.abs(i - row) + Math.abs(j - col));
                }
            }
        }
        return count;
    }

    /** Estimated distance to goal. */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /** Returns true if this board's tile values are the same
     position as y's */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board boardY = (Board) y;

        if (boardY.size() != this.size()) {
            return false;
        }

        for (int i = 0; i != this.size(); ++i) {
            for (int j = 0; j != this.size(); ++j) {
                if (this.tileAt(i, j) != boardY.tileAt(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
