package algorithms.percolation;

import java.util.HashMap;
import java.util.Map;

public class Percolation implements PercolationInterface {

  private final Map<Integer, Integer> grid;
  private final int n;
  private Integer topKey;
  private Integer bottomKey;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    this.n = n;
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
    i = validateInput(i);
    j = validateInput(j);
    open(getKey(i, j));
  }

  private void open(Integer key) {
    if (!isOpen(key)) {
      grid.put(key, key);
      setTopAndBottomKey(key);
      doConnections(key);
    }
  }

  private void setTopAndBottomKey(Integer key) {
    if (key < n) {
      if (null == topKey) {
        topKey = key;
      } else {
        union(key, topKey);
      }
    } else if (key > (n*n - n)) {
      if (null == bottomKey) {
        bottomKey = key;
      } else {
        union(key, bottomKey);
      }
    }
  }

  private void doConnections(Integer key) {
    Integer[] neighborKeys = {getTopKey(key), getRightKey(key), getBottomKey(key), getLeftKey(key)};
    for (Integer neighborKey :
        neighborKeys) {
      if (connectionIsNeeded(key, neighborKey)) {
        union(key, neighborKey);
      }
    }
  }

  private boolean union(Integer key, Integer neighborKey) {
    if (!areConnected(key, neighborKey)) {
      int i = getRoot(key);
      int j = getRoot(neighborKey);
      grid.replace(i, j);
      return true;
    }
    return false;
  }

  private boolean areConnected(Integer key, Integer neighborKey) {
    try {
      return getRoot(key).intValue() == getRoot(neighborKey).intValue();
    } catch (RuntimeException e) {
      return false;
    }
  }

  private Integer getTopKey(Integer key) {
    return key - n;
  }

  private Integer getBottomKey(Integer key) {
    return key + n;
  }

  private Integer getLeftKey(Integer key) {
    return key - 1;
  }

  private Integer getRightKey(Integer key) {
    return key + 1;
  }

  private boolean connectionIsNeeded(Integer key, Integer neighborKey) {
    return connectionIsValid(key, neighborKey) && isOpen(neighborKey);
  }

  private boolean connectionIsValid(Integer key, Integer neighborKey) {
    isKeyInBounds(neighborKey);
    int[] keyRowAndColumn = getRowAndColumn(key);
    int[] neighborKeyRowAndColumn = getRowAndColumn(neighborKey);
    int rowDifference = Math.abs(keyRowAndColumn[0] - neighborKeyRowAndColumn[0]);
    int columnDifference = Math.abs(keyRowAndColumn[1] - neighborKeyRowAndColumn[1]);
    return rowDifference <= 1 && columnDifference <= 1 && rowDifference + columnDifference <= 1;
  }

  @Override
  public boolean percolates() {
    return topKey != null && bottomKey != null && areConnected(topKey, bottomKey);
  }

  @Override
  public boolean isOpen(int i, int j) {
    i = validateInput(i);
    j = validateInput(j);
    return isOpen(getKey(i, j));
  }

  private boolean isOpen(Integer key) {
    return grid.get(key) != null;
  }

  @Override
  public boolean isFull(int i, int j) {
    i = validateInput(i);
    j = validateInput(j);
    return isFull(getKey(i, j));
  }

  private boolean isFull(Integer key) {
    return topKey != null && areConnected(key, topKey);
  }

  private int validateInput(int x) {
    if (!(0 < x && x <= n)) {
      throw new IllegalArgumentException();
    }
    return x - 1;
  }

  private boolean isKeyInBounds(Integer key) {
    return 0 < key && key <= n * n;
  }
}
