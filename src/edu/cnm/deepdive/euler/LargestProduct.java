package edu.cnm.deepdive.euler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LargestProduct {

  public static void main(String[] args) {
    int[][] data = readData("largest-product-data.txt");
    System.out.println(maxProduct(data, 4));
  }
  
  private static int[][] readData (String filename) {
    try (
        FileReader reader = new FileReader(filename); 
        BufferedReader buffer = new BufferedReader(reader)
        ) {
      ArrayList<int[]> data = new ArrayList<>();
      String line;
      while ((line = buffer.readLine()) != null) {
       String[] parts = line.split("\\s+");
       int[] row = (int[]) Arrays.stream(parts, 0, parts.length)
                                 .mapToInt((s) -> Integer.parseInt(s))
                                 .toArray();
       data.add(row);
      }
      return data.toArray(new int[data.size()][]); 
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
  
  private static boolean isInBounds(int row, int column, int[][] data) {
      return (row >= 0)
      && (column >- 0)
      && (row < data.length)
      && (column < data[row].length);
  }
  
  private static long maxProduct(int[][] data, int maxTerms) {
    long best = Long.MIN_VALUE;
    int[][] directions = {// Array of {row delta, col delta} pairs
      {-1, 1}, //sw-ne diagonal
      {0, 1}, // horizontal step
      {1, 1}, // nw-se diagonal
      {1, 0} // vertical step down
    };
    for (int startRow = 0; startRow < data.length; startRow++) {
      for (int startCol = 0; startCol < data[startRow].length; startCol++) {
        for (int[] direction : directions) {
          int endRow = startRow + (maxTerms - 1) * direction[0];
          int endCol = startCol + (maxTerms - 1) * direction[1];
          if (!isInBounds(endRow, endCol, data)) {
            continue;
          }
          long test = 1;
          for (int step = 0; step < maxTerms; step++) {
            int currentRow = startRow + step * direction[0];
            int currentCol = startCol + step * direction[1];
            test *= data[currentRow][currentCol];            
          }
          best = Math.max(best,  test);
        } 
      }
    }
    return best;
  }
}












