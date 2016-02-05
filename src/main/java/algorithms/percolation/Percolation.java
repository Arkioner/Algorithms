package algorithms.percolation;

import java.util.HashMap;
import java.util.Map;

public class Percolation implements PercolationInterface {

  private final Map<Integer, Integer> grid;
  private final int n;
//  private final int topKey;
//  private final int botKey;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    this.n = n;
//    this.topKey = n*n;
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

  private int[] getRowAndColumn(int key) {
    int[] rowAndColumn = new int[2];
    rowAndColumn[1] = key % n;
    rowAndColumn[0] = (key - rowAndColumn[1]) / n;
    return rowAndColumn;
  }

  /*private Integer getRoot(int i, int j) {
    Integer key = getKey(i, j);
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
  }*/

  private Integer getRoot(Integer key) {
    Integer value = grid.get(key);
    if (value != null) {
      if (value == key) {
        return key;
      }
      Integer rootValue = getRoot(value);
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
    if (!isOpen(valueAndKey)) {
      grid.put(valueAndKey, valueAndKey);
      doConnections(valueAndKey);
    }
  }

  private void doConnections(Integer key) {
    Integer neighborKey = getTopKey(key);
    Integer[] neighborKeys = {getTopKey(key)};
    if (null != neighborKey && isOpen(neighborKey)) {
      union(key, neighborKey);
    }
    Integer beforeKey = key - 1;
    Integer afterKey = key + 1;
    Integer bottomKey = key + n;
  }

  private boolean union(Integer key, Integer neighborKey) {
    if (!areConnected(key, neighborKey) && isValidConnection(key, neighborKey)) {
      return true;
    }
    return false;
  }

  private boolean areConnected(Integer key, Integer neighborKey) {
    return getRoot(key).intValue() == getRoot(neighborKey).intValue();
  }

  private Integer getTopKey(Integer key) {
    Integer topKey = key - n;
    if (topKey <= 0) {
      return null;
    }
    return topKey;
  }

  private Integer getBottomKey(Integer key) {
    Integer bottomKey = key + n;
    if (bottomKey >= n*n) {
      return null;
    }
    return bottomKey;
  }

  private Integer getLeftKey(Integer key) {
    Integer leftKey = key - 1;
    if (leftKey <= 0) {
      return null;
    }
    return leftKey;
  }

  private Integer getRightKey(Integer key) {
    Integer rightKey = key + 1;
    if (rightKey >= n*n) {
      return null;
    }
    return rightKey;
  }

  private boolean isValidConnection(Integer key, Integer neighborKey) {
    int[] keyRowAndColumn = getRowAndColumn(key);
    int[] neighborKeyRowAndColumn = getRowAndColumn(neighborKey);
    int rowDifference = Math.abs(keyRowAndColumn[0] - neighborKeyRowAndColumn[0]);
    int columnDifference = Math.abs(keyRowAndColumn[1] - neighborKeyRowAndColumn[1]);
    return rowDifference <= 1 && columnDifference <= 1 && rowDifference + columnDifference <= 1;
  }

  private boolean isConnectionIsNeeded(Integer key) {
    return isKeyInBounds(key) && isOpen(key);
  }

  @Override
  public boolean percolates() {
    return true;
  }

  @Override
  public boolean isOpen(int i, int j) {
    validateInput(i);
    validateInput(j);
    Integer key = getKey(i, j);
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

  private boolean isKeyInBounds(Integer key) {
    return 0 < key && key < n * n;
  }
}
