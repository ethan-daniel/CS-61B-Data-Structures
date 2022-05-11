package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridLength;
    private int numOpenSites;
    private WeightedQuickUnionUF gridOfSites;
    private WeightedQuickUnionUF topGridOfSites;
    private boolean[] arrOfSitesOpenness;

    // create N-by-N grid, with all sites initially blocked
    // (For example: 20x20 grid has the max site (19,19) )
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        // Init. the grid with additional 2 sites for a
        // virtual top and virtual bottom site.
        gridLength = N;
        numOpenSites = 0;
        gridOfSites = new WeightedQuickUnionUF(gridLength * gridLength + 2);
        topGridOfSites = new WeightedQuickUnionUF(gridLength * gridLength + 2);
        arrOfSitesOpenness = new boolean[N * N + 2];

        // Init. the virtual top and bottom sites.
        arrOfSitesOpenness[N * N] = true;
        arrOfSitesOpenness[N * N + 1] = true;

        // Unions the top row to the virtual top site.
        // Unions the bottom row to the virtual bottom site.
        for (int i = 0; i != N; ++i) {
            gridOfSites.union(xyTo1D(0, i), N * N);
            gridOfSites.union(xyTo1D(N - 1, i), N * N - 1);
        }

    }

    // helper method to check that row and column indices are between 0
    // and N - 1.
    private boolean outOfBounds(int x, int y) {
        return x < 0 || x > gridLength - 1
                || y < 0 || y > gridLength - 1;
    }

    // helper method that generates a unique number to a position
    // on the grid.
    private int xyTo1D(int x, int y) {
        return x * gridLength + y;
    }

    private void tryUnion(int r, int c, int q) {
        if (!outOfBounds(r, c)) {
            if (isOpen(r, c)) {
                gridOfSites.union(xyTo1D(r, c), q);
                topGridOfSites.union(xyTo1D(r, c), q);
            }
        }

        if (r == -1) {
            gridOfSites.union(gridLength * gridLength, q);
            topGridOfSites.union(gridLength * gridLength, q);
        }

        if (r == gridLength) {
            gridOfSites.union(gridLength * gridLength - 1, q);
        }

    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (outOfBounds(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        if (!isOpen(row, col)) {
            int index = xyTo1D(row, col);
            arrOfSitesOpenness[index] = true;
            tryUnion(row, col - 1, index);
            tryUnion(row, col + 1, index);
            tryUnion(row - 1, col, index);
            tryUnion(row + 1, col, index);

            ++numOpenSites;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (outOfBounds(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return arrOfSitesOpenness[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (outOfBounds(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return topGridOfSites.connected(xyTo1D(row, col), gridLength * gridLength) && isOpen(row, col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return gridOfSites.connected(gridLength * gridLength, gridLength * gridLength - 1);
    }

    // use for unit testing
    public static void main(String[] args) {
        Percolation perc = new Percolation(10);
        perc.open(0,0);
        perc.open(1, 0);
        perc.open(2, 0);
        perc.open(3, 0);
        perc.open(4, 0);
        perc.open(5, 0);
        perc.open(6, 0);
        perc.open(7, 0);
        perc.open(8, 0);
        perc.open(9, 0);
    }

}
