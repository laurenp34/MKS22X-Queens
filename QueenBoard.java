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
  //this method marks the lines of attack for a queen placed at r, c.
  private boolean markAttack(int r, int c) {
    int rows = board.length;
    int cols = board[0].length;
    for (int[] row: board) {
      if (row[c] != 1) {
        row[c] = -1;
      }
    }
    for (int i: board[r]) {
      if (i != 1) {
        i = -1;
      }
    }
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

  public boolean solve() {
    addQueen(0,0);
    return true;

  }

  public boolean solve(int row, int col){
    return true;
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
