package algorithms.percolation;

import java.util.HashMap;
import java.util.Map;

public class Percolation implements PercolationInterface {

  private final Map<Integer,Integer> grid;
  private final int n;
  private final int topKey;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    this.n = n;
    this.topKey = n*n;
    grid = new HashMap<>();
    /*for (int i = n; 0 < i ; i--) {
      for (int j = n; 0 < j ; j--) {
        Integer valueAndKey = getKey(i, j);
        grid.put(valueAndKey, valueAndKey);
      }
    }*/
  }

  private Integer getKey(int i, int j) {
    return i * n + j;
  }
  private int[] getRowAndColumn (int key) {
    int[] rowAndColumn = new int[2];
    rowAndColumn[1] = key % n;
    rowAndColumn[0] = (key - rowAndColumn[1]) / n;
    return rowAndColumn;
  }

  private Integer getRoot(int i, int j) {
    Integer key = getKey(i,j);
    Integer value = grid.get(key);
    if (value != null) {
      if (value == key) {
        return key;
      }
      int[] rowAndColumn = getRowAndColumn(key);
      Integer rootValue = getRoot(rowAndColumn[0], rowAndColumn[1]);
      grid.replace(key, rootValue);
      return rootValue;
    }
    throw new RuntimeException();
  }

  @Override
  public void open(int i, int j) {
    validateInput(i);
    validateInput(j);
    Integer valueAndKey = getKey(i, j);
    if(!isOpen(valueAndKey)) {
      grid.put(valueAndKey, valueAndKey);
      doConnections(valueAndKey);
    }
  }

  private void doConnections(Integer key) {
    Integer topKey = key - n;
    Integer beforeKey = key - 1;
    Integer afterKey = key + 1;
    Integer bottomKey = key + n;


  }

  private boolean checkIfValidConnection(Integer key, Integer originalKey) {
    return false;
  }

  private boolean checkIfConnectionIsNeeded(Integer key) {
    return checkKeyInBounds(key) && isOpen(key);
  }

  @Override
  public boolean percolates() {
    return false;
  }

  @Override
  public boolean isOpen(int i, int j) {
    validateInput(i);
    validateInput(j);
    Integer key = getKey(i,j);
    return isOpen(key);
  }

  private boolean isOpen(Integer key) {
    return grid.get(key) != null;
  }

  @Override
  public boolean isFull(int row, int col) {
    validateInput(row);
    validateInput(col);
    return false;
  }

  private void validateInput(int x) {
    if (!(0 < x && x <= n)) {
      throw new IllegalArgumentException();
    }
  }

  private boolean checkKeyInBounds(Integer key) {
    return 0 < key && key < n*n;
  }
}
