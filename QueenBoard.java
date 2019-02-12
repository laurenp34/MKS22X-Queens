import java.util.*;
import java.io.*;

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

  private boolean resetBoard() {
    int[][] board2 = new int[board.length][board.length];
    board = board2;
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

    for (int[] row: board) {
      for (int i: row) {
        if (i != 0) {
          throw new IllegalStateException("board can only have zeroes.");
        }
      }
    }
    int[][] queens = new int[board.length][2];
    queens[0][0] = 0;
    queens[0][1] = 0;
    int placed = 1;
    addQueen(0,0);
    return solve(1,1,queens,placed,false);
    //return true;

  }

  public boolean solve(int row, int col, int[][] queens, int placed,boolean falseAlarm){
    //System.out.println("trying: "+row+","+col);
    //System.out.println("Placed: "+Arrays.deepToString(queens));
    //System.out.println("placed: "+placed);
    //System.out.println(falseAlarm);
    //System.out.println(this);

    if (row == board.length) {
      System.out.println(this);
      return true;
    }

    if (col==board.length) {

      if (placed ==0) {
        resetBoard();
        return false;
      }

      if ((row==0 && !falseAlarm) || (row<0 && falseAlarm)) {
        resetBoard();
        return false;
      }

      int lastQueenR = queens[placed-1][0];
      int lastQueenC = queens[placed-1][1];
      removeQueen(lastQueenR,lastQueenC);
      queens[placed-1][0] = 0;
      queens[placed-1][1] = 0;
      placed --;

        //boolean found = false;
        int newR = lastQueenR;
        int newC = lastQueenC + 1;


        for (int i=newC;i<board[0].length;i++) {

          if (board[newR][i] >= 0) {
            queens[placed][0] = lastQueenR;
            queens[placed][1] = i;
            placed++;
            addQueen(lastQueenR,i);
            return solve(newR+1,0,queens,placed,false);
          }
        }
        return solve(newR-1,board.length,queens,placed,true);
      }

    if (row == board.length) {
      return true;
    }
    if (board[row][col] < 0) {
      return solve(row,col+1,queens,placed,false);
    } else {
      placed ++;
      queens[placed-1][0] = row;
      queens[placed-1][1] = col;
      addQueen(row,col);
      return solve(row+1,0,queens,placed,false);
    }


  }


  public static void main(String[] args) {
    QueenBoard q = new QueenBoard(11);
    System.out.println(q.solve());
    QueenBoard r = new QueenBoard(3);
    System.out.println(r.solve());
    System.out.println(r);
    System.out.println(q.solve());

  }

}
