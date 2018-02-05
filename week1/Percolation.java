import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private static final int VIRTUALTOP = 0;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int size;
    private final int virtualBottom;
    private int numberOfOpenSites;

    private boolean[] grid;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(size * size + 2); // 2 for top and bottom rows
        grid = new boolean[size * size + 2];

        // assign open status to virtual top row and column
        virtualBottom = (size * size) + 1;

        grid[VIRTUALTOP] = true;
        grid[virtualBottom] = true;
        numberOfOpenSites = 0;
    }

    public static void main(String[] args) {

        Percolation percolation = new Percolation(3);
        percolation.open(1, 3);
        StdOut.println("(1,3)" + percolation.isFull(1, 3));
        percolation.open(2, 3);
        StdOut.println("(2,3)" + percolation.isFull(2, 3));
        percolation.open(3, 3);
        StdOut.println("(3,3)" + percolation.isFull(3, 3));
        percolation.open(3, 1);
        StdOut.println("(3,1)" + percolation.isFull(3, 1));
        percolation.open(2, 1);
        StdOut.println("(2,1)" + percolation.isFull(2, 1));
        percolation.open(1, 1);
        StdOut.println("(1,1)" + percolation.isFull(1, 1));

    }

    private int twoDto1D(int row, int col) {
        return ((row - 1) * size + col);
    }

    // open site (row, col) size
    public void open(int row, int col) {
        int index;
        if (!isOpen(row, col)) {
            index = twoDto1D(row, col);
            grid[index] = true;
            numberOfOpenSites++;
            if (row == 1) // first  row of grid
            {
                weightedQuickUnionUF.union(index, VIRTUALTOP);
            }
            if (row == size) {
                weightedQuickUnionUF.union(index, virtualBottom);
            }

            // check for sorrounding open connection
            // left
            if (col > 1 && isOpen(row, col - 1)) {
                weightedQuickUnionUF.union(index, twoDto1D(row, col - 1));
            }
            // right
            if (col < size && isOpen(row, col + 1)) {
                weightedQuickUnionUF.union(index, twoDto1D(row, col + 1));
            }
            // up
            if (row > 1 && isOpen(row - 1, col)) {
                weightedQuickUnionUF.union(index, twoDto1D(row - 1, col));
            }
            // down
            if (row < size && isOpen(row + 1, col)) {
                weightedQuickUnionUF.union(index, twoDto1D(row + 1, col));
            }
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row > size) || (col > size) || (row <= 0) || (col <= 0)) {
            throw new IllegalArgumentException("from isOpen");
        } else
            return grid[twoDto1D(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row > size) || (col > size) || (row <= 0) || (col <= 0))
            throw new IllegalArgumentException("from isFull");
        else
            return weightedQuickUnionUF.connected(twoDto1D(row, col), VIRTUALTOP);


    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(VIRTUALTOP, virtualBottom);
    }
}
