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
/*
  public boolean solve2() {

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
    return solve2(1,1,queens,placed,false);
    //return true;

  }*/

  public boolean solve() {
    addQueen(0,0);
    int[] cols = new int[board.length];
    cols[0] = 1;
    return solve(1,0,cols,false);
  }

  public boolean solve(int row, int lastCol, int[] cols, boolean del) {

    /*
    System.out.println(this);
    System.out.println(Arrays.toString(cols));
    System.out.println("row: "+row);
    */


    if (row == -1) {
      return false; //because when row 0 gets to the end (all scenarios built from there are tested), row becomes 0.
    }
    if (row == board.length) {
      return true; //return true if it's gotten through every row and to the end.
    }
    /*
    int column;
    if (del) { // del represents if the previous call deleted a queen.
      column = lastCol + 1; // start only from where you left off with the deleted queen.
    } else {
      column = 0;
    }
    */
    int column = 0;
    for (int col=column;col<board[row].length;col++) { // for every square in the row:
      //System.out.println("testing: "+row+","+col);

      //if square is unoccupied and unattacked (free):
      if (board[row][col] == 0) {
        //System.out.print("added\n");
        addQueen(row,col); // add the queen there.
        cols[row-1] = col; // add the queens column to cols.
        if (solve(row+1,col,cols,false)) {
          return true;
        }
        //System.out.println("removing @ "+(row)+", "+cols[row-1]);
        removeQueen(row,cols[row-1]);
        col = cols[row-1]; // store last because you need to know which col to start off in the prev row for next call.
        cols[row-1] = 0;
      } // recursive call to next row, del is false because we didn't remove a queen.

    }
    return false;
  }

    public int solve2() {
      int[] cols = new int[board.length];
      return solve2(0,0,cols,false);
    }

    public int solve2(int row, int lastCol, int[] cols, boolean del) {

      /*
      System.out.println(this);
      System.out.println(Arrays.toString(cols));
      System.out.println("row: "+row);
      */


      if (row == -1) {
        return 0; //because when row 0 gets to the end (all scenarios built from there are tested), row becomes 0.
      }
      if (row == board.length) {
        return 1; //return true if it's gotten through every row and to the end.
      }
      /*
      int column;
      if (del) { // del represents if the previous call deleted a queen.
        column = lastCol + 1; // start only from where you left off with the deleted queen.
      } else {
        column = 0;
      }
      */
      int column = 0;
      int count = 0;
      for (int col=column;col<board[row].length;col++) { // for every square in the row:
        //System.out.println("testing: "+row+","+col);

        //if square is unoccupied and unattacked (free):
        if (board[row][col] == 0) {
          //System.out.print("added\n");
          addQueen(row,col); // add the queen there.
          cols[row] = col; // add the queens column to cols.
          count += solve2(row+1,0,cols,false);
          //System.out.println("removing @ "+(row)+", "+cols[row-1]);
          removeQueen(row,cols[row]);
          //col = cols[row]; // store last because you need to know which col to start off in the prev row for next call.
          cols[row] = 0;
        } // recursive call to next row, del is false because we didn't remove a queen.

      }

    /*
    //if it passed through the entire rest of the column and didn't add any queens:
    System.out.println("removing queen @"+(row-1)+","+cols[row-2]);
    //remove the previous queen.
    removeQueen(row-1,cols[row-2]);
    int last = cols[row-2]; // store last because you need to know which col to start off in the prev row for next call.
    cols[row-2] = 0;
    */
    //return solve2(row-1,last,cols,true); // del becomes true because you want to start the next recursive call at cols+1
    return count;
  }

  private int getLastRow() {
    for (int i=board.length-1;i>= 0;i--) {
      for (int i2=0;i2<board.length;i2++) {
        if (board[i][i2] > 0) {
          return i;
        }
      }
    }
    return -1;
  }


  private int getLastCol(int row) {
      for (int i2=0;i2<board.length;i2++) {
        if (board[row][i2] > 0) {
          return i2;
        }
      }
      return -1;
    }

/*
  public int countSolutions2() {
    addQueen(0,0);
    int[] cols = new int[board.length];
    cols[0] = 1;
    return countSolutions2(1,0,cols,false,0);
  }

  public int countSolutions2(int row, int lastCol, int[] cols, boolean del, int count) {
    /*
    System.out.println(this);
    System.out.println(Arrays.toString(cols));
    System.out.println("row: "+row);
    */
/*
    if (row == -1) {
      return count; //because when row 0 gets to the end (all scenarios built from there are tested), row becomes 0.
    }
    if (row == board.length) {
      return count; //return true if it's gotten through every row and to the end.
    }
    /*
    int column;
    if (del) { // del represents if the previous call deleted a queen.
      column = lastCol + 1; // start only from where you left off with the deleted queen.
    } else {
      column = 0;
    }
    */ /*
    int column = 0;
    if (del) {
      column = cols[row-1];
    }
    for (int col=column;col<board[row].length;col++) { // for every square in the row:
      System.out.println("testing: "+row+","+col);

      //if square is unoccupied and unattacked (free):
      if (board[row][col] == 0) {
        //System.out.print("added\n");
        addQueen(row,col); // add the queen there.
        cols[row-1] = col; // add the queens column to cols.
        if (solve(row+1,col,cols,false)) {
          count++;
        }
        System.out.println("removing @ "+(row)+", "+cols[row-1]);
        col = getLastCol(getLastRow()) + 1;
        row = getLastRow();
        System.out.println("backtracking to "+getLastRow()+", "+col);
        removeQueen(getLastRow(), getLastCol(getLastRow()));
        System.out.println(this);
        col = cols[row-1]; // store last because you need to know which col to start off in the prev row for next call.
        cols[row-1] = 0;
      } // recursive call to next row, del is false because we didn't remove a queen.

    }

    /*
    //if it passed through the entire rest of the column and didn't add any queens:
    System.out.println("removing queen @"+(row-1)+","+cols[row-2]);
    //remove the previous queen.
    removeQueen(row-1,cols[row-2]);
    int last = cols[row-2]; // store last because you need to know which col to start off in the prev row for next call.
    cols[row-2] = 0;
    */
    //return solve2(row-1,last,cols,true); // del becomes true because you want to start the next recursive call at cols+1
    /*
    int[] newCols = new int[board.length];
    for (int i=0;i<board.length;i++) {
      if (getLastCol(i) != -1) {
        newCols[i] = getLastCol(i);
      }
    }
    return countSolutions2(getLastRow()-1,lastCol,newCols,true,count);
  }
  /*
  /*

  public int countSolutions3(int row, int lastCol, int[] cols, boolean del, int count) {

    int rowStore = row;
    int[] colsStore = cols;

    System.out.println(this);
    System.out.println(Arrays.toString(cols));
    System.out.println("row: "+row);



    if (row == -1) {
      return count; //because when row 0 gets to the end (all scenarios built from there are tested), row becomes 0.
    }
    int column;
    if (row == board.length || del) {
      column = cols[row-1];
      cols[row-1] = 0;//return true if it's gotten through every row and to the end.
    } else {
      column = 0;
    }
    /*
    int column;
    if (del) { // del represents if the previous call deleted a queen.
      column = lastCol + 1; // start only from where you left off with the deleted queen.
    } else {
      column = 0;
    }
    *//*

    for (int col=column;col<board[row].length;col++) { // for every square in the row:
      System.out.println("testing: "+row+","+col);

      //if square is unoccupied and unattacked (free):
      if (board[row][col] == 0) {
        System.out.print("added\n");
        addQueen(row,col); // add the queen there.
        cols[row-1] = col; // add the queens column to cols.
        rowStore++;
        if (solve(row+1,col,cols,false)) {

          count ++;
          System.out.println("count: "+count);

        } else {
        System.out.println("removing @ "+(row)+", "+cols[row-1]);
        removeQueen(row,cols[row-1]);
        col = cols[row-1]; // store last because you need to know which col to start off in the prev row for next call.
        cols[row-1] = 0;
      }
    }
      } // recursive call to next row, del is false because we didn't remove a queen.
    /*
    //if it passed through the entire rest of the column and didn't add any queens:
    System.out.println("removing queen @"+(row-1)+","+cols[row-2]);
    //remove the previous queen.
    removeQueen(row-1,cols[row-2]);
    int last = cols[row-2]; // store last because you need to know which col to start off in the prev row for next call.
    cols[row-2] = 0;
    */
    //return solve2(row-1,last,cols,true); // del becomes true because you want to start the next recursive call at cols+1
    /*
    System.out.println("eNDremoving @ "+(rowStore)+", "+cols[rowStore-1]);
    removeQueen(row,cols[rowStore-1]);
    //col = cols[rowStore-1]; // store last because you need to know which col to start off in the prev row for next call.
    cols[rowStore-1] = 0;

    return countSolutions3(rowStore-1,lastCol,cols,false,count);
  }

  public boolean solve2(int row, int col, int[][] queens, int placed,boolean falseAlarm){
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
            return solve2(newR+1,0,queens,placed,false);
          }
        }
        return solve2(newR-1,board.length,queens,placed,true);
      }

    if (row == board.length) {
      return true;
    }
    if (board[row][col] < 0) {
      return solve2(row,col+1,queens,placed,false);
    } else {
      placed ++;
      queens[placed-1][0] = row;
      queens[placed-1][1] = col;
      addQueen(row,col);
      return solve2(row+1,0,queens,placed,false);
    }


  }
  */

  /*

  public int countSolutions() {
    int[][] queens = new int[board.length][2];
    addQueen(0,0);
    int placed = 1;
    return countSolutions(1,1,queens,placed,false,0);
  }

  public int countSolutions(int row, int col, int[][] queens, int placed, boolean falseAlarm, int count) {


    System.out.println("trying: "+row+","+col);
    System.out.println("Placed: "+Arrays.deepToString(queens));
    System.out.println("placed: "+placed);
    System.out.println(falseAlarm);
    System.out.println(this);


    if (row == board.length) {
      System.out.println(this);
      count ++;
      return countSolutions(row-1,board.length,queens,placed,true,count);
    }

    if (col==board.length) {

      if (placed ==0) {
        resetBoard();
        return count;
      }

      if ((row==0 && !falseAlarm) || (row<0 && falseAlarm)) {
        resetBoard();
        return count;
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
            return countSolutions(newR+1,0,queens,placed,false,count);
          }
        }
        return countSolutions(newR-1,board.length,queens,placed,true,count);
      }


    if (board[row][col] < 0) {
      return countSolutions(row,col+1,queens,placed,false,count);
    } else {
      placed ++;
      queens[placed-1][0] = row;
      queens[placed-1][1] = col;
      addQueen(row,col);
      return countSolutions(row+1,0,queens,placed,false,count);
    }

  }
  */

  public int countSolutions() {

    for (int[] row: board) {
      for (int i: row) {
        if (i != 0) {
          throw new IllegalStateException("board must be cleared.");
        }
      }
    }

    int[] cols = new int[board.length];
    //addQueen(0,0);
    return countSolutions(0,0,cols,false);
  }

  public int countSolutions(int row, int lastCol, int[] cols, boolean del) {

    //System.out.println(this);
    //System.out.println(Arrays.toString(cols));
    //System.out.println("row: "+row);


    if (row == -1) {
      return 0; //because when row 0 gets to the end (all scenarios built from there are tested), row becomes 0.
    }
    if (row == board.length) {
      //System.out.println(this);
      return 1;
    }
    int count = 0;
    /*
    int column;
    if (del) { // del represents if the previous call deleted a queen.
      column = lastCol + 1; // start only from where you left off with the deleted queen.
    } else {
      column = 0;
    }
    */
    int column = 0;
    for (int col=column;col<board[row].length;col++) { // for every square in the row:
      //System.out.println("testing: "+row+","+col);

      //if square is unoccupied and unattacked (free):
      if (board[row][col] == 0) {
      //  System.out.print("added\n");
        addQueen(row,col); // add the queen there.
        cols[row] = col; // add the queens column to cols.
        count += countSolutions(row+1,lastCol,cols,false);
        //System.out.println("removing @ "+(row)+", "+cols[row]);
        removeQueen(row,cols[row]);
        //col = cols[row-1]; // store last because you need to know which col to start off in the prev row for next call.
        cols[row] = 0;
      } // recursive call to next row, del is false because we didn't remove a queen.

    }

    /*
    //if it passed through the entire rest of the column and didn't add any queens:
    System.out.println("removing queen @"+(row-1)+","+cols[row-2]);
    //remove the previous queen.
    removeQueen(row-1,cols[row-2]);
    int last = cols[row-2]; // store last because you need to know which col to start off in the prev row for next call.
    cols[row-2] = 0;
    */
    //return solve2(row-1,last,cols,true); // del becomes true because you want to start the next recursive call at cols+1
    resetBoard();
    return count;
  }


  public static void main(String[] args) {
    QueenBoard q = new QueenBoard(5);
    System.out.println(q.solve());
    q.resetBoard();
    //System.out.println(q.solve());
    System.out.println(q.solve2());
    System.out.println(q);

  }

}
