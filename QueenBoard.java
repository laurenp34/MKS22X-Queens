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
    //vertical
    for (int[] row: board) {
      if (row[c] != 1) {
        row[c] = -1;
      }
    }
    //horizontal.
    for (int i=0;i<board[r].length;i++) {
      if (board[r][i] != 1) {
        board[r][i] = -1;
      }
    }
    //diagonal up right./>
    for (int i=r,i2=c;i>=0&&i2<board[r].length;i--,i2++) {
      if (board[i][i2] != 1) {
        board[i][i2] = -1;
      }
    }
    //diagonal down left.</
    for (int i=r,i2=c;i2>=0&&i<board[r].length;i++,i2--) {
      if (board[i][i2] != 1) {
        board[i][i2] = -1;
      }
    }
    //diagonal up left <\
    for (int i=r,i2=c;i>=0&&i2>=0;i--,i2--) {
      if (board[i][i2] != 1) {
        board[i][i2] = -1;
      }
    }
    //diagonal down left \>
    for (int i=r,i2=c;i<board[r].length&&i2<board[r].length;i++,i2++) {
      if (board[i][i2] != 1) {
        board[i][i2] = -1;
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
        } if (i==-1) {
          result += "x";
        }
        if (i==0) {
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
    q.markAttack(4,6);
    System.out.println(q);
  }

}
