package algorithms.percolation;

public interface PercolationInterface {

  /** create N-by-N grid, with all sites blocked */
  void open(int i, int j);
  /** open site (row i, column j) if it is not open already */
  boolean percolates();
  /** is site (row i, column j) open? */
  boolean isOpen(int i, int j);
  /** is site (row i, column j) full? */
  boolean isFull(int row, int col);
}
