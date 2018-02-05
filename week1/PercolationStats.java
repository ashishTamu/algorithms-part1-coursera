import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE = 1.96;
    private final int trials;
    private final double[] percolationThreshold;

    public PercolationStats(int n, int trials) {
        if (n <= 0)
            throw new IllegalArgumentException("Invalid argument");
        if (trials <= 0)
            throw new IllegalArgumentException("Invalid argument : trails value > 0");

        // perform trials independent experiments on an n-by-n grid

        this.trials = trials;
        percolationThreshold = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);

                }
            }
            percolationThreshold[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }


    public static void main(String[] args) {
        int n = 200;
        int trial = 100;
        PercolationStats percolationStats = new PercolationStats(n, trial);
        StdOut.println(percolationStats.mean());
        StdOut.println(percolationStats.stddev());
        StdOut.println(percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }       // test client (described below)

    public double mean() {
        return StdStats.mean(percolationThreshold);
    }                        // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(percolationThreshold);
    }                     // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return (mean() - CONFIDENCE * (stddev()) / Math.sqrt((double) this.trials));

    }              // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return (mean() + CONFIDENCE * (stddev()) / Math.sqrt((double) this.trials));
    }             // high endpoint of 95% confidence interval
}
