public class QueenBoard {
  private int[][] board;

  //0 represents an empty space
  //1 represents a queen there.
  //-1 represents a square under attack.
  public QueenBoard(int size) {
    board = new int[size][size];
  }

  private boolean addQueen(int r, int c) {
    board[r][c] = 1;
    return true;
  }
  private boolean removeQueen(int r, int c) {
    board[r][c] = 0;
    return true;
  }

  public String toString() {
    String result = "";
    for (int[] row: board) {
      for (int i: row) {
        if (i==1) {
          result += "Q";
        } else {
          result += "_";
        }
      }
      result += "\n";
    }
    return result;
  }

  public static void main(String[] args) {
    QueenBoard q = new QueenBoard(8);
    q.addQueen(1,5);
    q.addQueen(4,6);
    System.out.println(q);
    q.removeQueen(1,5);
    System.out.println(q);
  }

}
