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
    markAttack(r,c);
    return true;
  }
  private boolean removeQueen(int r, int c) {
    board[r][c] = 0;
    removeAttack(r,c);
    return true;
  }

  //this method marks the lines of attack for a queen placed at r, c.
  //a square under attack will have value < 0 and will be -1 * # of times being attacked.
  private boolean markAttack(int r, int c) {
    int rows = board.length;
    int cols = board[0].length;
    //vertical
    for (int[] row: board) {
      if (row[c] != 1) {
        row[c]--;
      }
    }
    //horizontal.
    for (int i=0;i<board[r].length;i++) {
      if (board[r][i] != 1) {
        board[r][i]--;
      }
    }
    //diagonal up right./>
    for (int i=r,i2=c;i>=0&&i2<board[r].length;i--,i2++) {
      if (board[i][i2] != 1) {
        board[i][i2]--;
      }
    }
    //diagonal down left.</
    for (int i=r,i2=c;i2>=0&&i<board[r].length;i++,i2--) {
      if (board[i][i2] != 1) {
        board[i][i2]--;
      }
    }
    //diagonal up left <\
    for (int i=r,i2=c;i>=0&&i2>=0;i--,i2--) {
      if (board[i][i2] != 1) {
        board[i][i2]--;
      }
    }
    //diagonal down left \>
    for (int i=r,i2=c;i<board[r].length&&i2<board[r].length;i++,i2++) {
      if (board[i][i2] != 1) {
        board[i][i2]--;
      }
    }
    return true;
  }

  //given r,c of queen just removed fix the board to make areas now safe.
  private boolean removeAttack(int r, int c) {
    int rows = board.length;
    int cols = board[0].length;
    //vertical
    for (int[] row: board) {
      if (row[c] <0) {
        row[c]++;
      }
    }
    //horizontal.
    for (int i=0;i<board[r].length;i++) {
      if (board[r][i] <0) {
        board[r][i]++;
      }
    }
    //diagonal up right./>
    for (int i=r,i2=c;i>=0&&i2<board[r].length;i--,i2++) {
      if (board[i][i2] <0) {
        board[i][i2]++;
      }
    }
    //diagonal down left.</
    for (int i=r,i2=c;i2>=0&&i<board[r].length;i++,i2--) {
      if (board[i][i2] <0) {
        board[i][i2]++;
      }
    }
    //diagonal up left <\
    for (int i=r,i2=c;i>=0&&i2>=0;i--,i2--) {
      if (board[i][i2] <0) {
        board[i][i2]++;
      }
    }
    //diagonal down left \>
    for (int i=r,i2=c;i<board[r].length&&i2<board[r].length;i++,i2++) {
      if (board[i][i2] <0) {
        board[i][i2]++;
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
        } if (i<=-1) {
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
    int[][] queens = new int[8][2];
    addQueen(0,0);
    return solve(1,1,queens);
    //return true;

  }

  public boolean solve(int row, int col, int[] queens){
    if (col==board[row].length) {
      return false;
    }
    for (int i=row;i<queens.length;i++) {
      if ()
    }
    return true;
  }

  public static void main(String[] args) {
    QueenBoard q = new QueenBoard(8);
    q.addQueen(1,5);
    q.addQueen(4,6);
    System.out.println(q);
    q.removeQueen(1,5);
    q.removeAttack(1,5);
    System.out.println(q);
    System.out.println(q);

  }

}
