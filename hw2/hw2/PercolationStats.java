package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
//import java.lang.Math;

public class PercolationStats {

    private static double[] arrStats;
    private static int tExperiments;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        tExperiments = T;
        arrStats = new double[tExperiments];

        for (int i = 0; i != T; ++i) {
            Percolation perc = pf.make(N);
            arrStats[i] = computeStat(N, perc);
        }
    }

    private double computeStat(int N, Percolation perc) {
        while (!perc.percolates()) {
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            perc.open(row, col);
        }

        return perc.numberOfOpenSites() / (N * N);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(arrStats);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arrStats);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(tExperiments));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(tExperiments));
    }
}
