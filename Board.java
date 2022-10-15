import java.util.*;

public class Board {
    private final int[][] board;
    Stack<Board> neighbour = new Stack<>();

    public Board(int[][] board) {
        this.board = clone(board);
    }

    public static void main(String[] args) {

    }

    private int[][] swap(int[][] board, int row, int column, int row2, int column2) {
        int[][] swap = clone(board);
        int temp = swap[row][column];
        swap[row][column] = swap[row2][column2];
        swap[row2][column2] = temp;
        return swap;
    }

    private boolean isIndex(int rowIndex, int columnIndex) {
        int n = board.length;
        return rowIndex < n && rowIndex >= 0 && columnIndex >= 0 && columnIndex < n;
    }


    public int hamming(int[][] board) {
        int n = board.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != j + i * n + 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public int distance() {
        int total = 0;
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != j + i * n + 1) {
                    int square = board[i][j];
                    total += Math.abs((square % n)- j) + Math.abs((square / n)- i);
                }
            }
        }
        return total;
    }

    public boolean isGoal() {
        return distance() == 0;
    }

    public int[][] clone(int[][] board) {
        int[][] clone = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                clone[i][j] = board[i][j];
            }
        }
        return clone;
    }

    public Board twin(){
        int[][] twin = clone(this.board);
        if (twin[0][0] != 0 && twin[0][1] !=0){
            return new Board(swap(board,0,0,0,1));
        } else{
            return new Board(swap(board,1,0,1,1));
        }
    }

    private int blankPosition() {
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    return i * n + j;
                }
            }
        }
        return -1;
    }

    public boolean equals(Object y) {
        int n = board.length;
        if (y == null){
            return false;
        }
        if (y == this){
            return true;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        return Arrays.deepEquals(this.board, that.board);

    }

    public Iterable<Board> neighbour() {
        int position = blankPosition();
        int n = board.length;
        int i = position / n;
        int j = position % n;
        if (i < n - 1) {
            neighbour.push(new Board(swap(board, i, j, i + 1, j)));
        }
        if (i > 0) {
            neighbour.push(new Board(swap(board, i, j, i - 1, j)));
        }
        if (j < n - 1) {
            neighbour.push(new Board(swap(board, i, j, i, j + 1)));
        }
        if (j > 0) {
            neighbour.push(new Board(swap(board, i, j, i, j - 1)));
        }
        return neighbour;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        int n = board.length;
        string.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                string.append(String.format("%2d ", board[i][j]));
            }
            string.append("\n");
        }
        return string.toString();
    }


}
