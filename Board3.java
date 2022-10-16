import java.util.ArrayList;

public class Board {
    private final int [][]blocks;
    private final int n;
    private static final int blank = 0;

    /**
     *  creates a n*n board
     * @param blocks an nxn board matrix
     */
    public Board(int[][] blocks) {
        n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, n);
        }
    }

    public static void main(String[] args) {
        int [][]matrix = new int[4][4];
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[0][2] = 3;
        matrix[0][3] = 4;
        matrix[1][0] = 5;
        matrix[1][1] = 0;
        matrix[1][2] = 7;
        matrix[1][3] = 8;
        matrix[2][0] = 9;
        matrix[2][1] = 6;
        matrix[2][2] = 11;
        matrix[2][3] = 12;
        matrix[3][0] =13;
        matrix[3][1] = 10;
        matrix[3][2] =14;
        matrix[3][3] = 15;
        Board board = new Board(matrix);

        System.out.println(board.manhattan());
        System.out.println(board);
        for (Board it : board.neighbors()) {
            System.out.println(it.toString());
        }
        System.out.println(board.twin().toString());
    }


    /**
     *
     * @return returns the Manhattan distance to target and sum of moves to it
     */
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int index = i * n + j + 1;
                if (blocks[i][j] != index && blocks[i][j] != blank){  // state is mispositioned
                    int row = (blocks[i][j] -1)/n; // correct row element
                    int col = (blocks[i][j] -1)% n; // correct col element
                    sum = sum + Math.abs(row - i) + Math.abs(col -j);
            }

                }
        }
        return sum;
    }

    /**
     *
     * @return checks whether all positions are correct (goal)
     */
    public boolean isGoal() {
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                int index = i * n + j + 1;
                if (blocks[i][j] != blank && blocks[i][j] != index){ // wrong position blocks[i][j]
                    return false;
                }

            }
        }
        return true;
    }

    /**
     *
     * @return returns duplicate of the original board
     */
    private int[][] duplicate() {
        int [][] board2 = new int[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(this.blocks[i], 0, board2[i], 0, n);
        return board2;
    }

    /**
     *
     * @param row row which we want to exchange with row 2
     * @param col col which we want to exchange with col 2
     * @param row2 row which we want to exchange with the first row
     * @param col2 col which we want to exchange with the first col
     * @return returns new board with the exchanged elements
     */
    private Board exchange(int row, int col, int row2, int col2) {
        int [][]board2 = duplicate();
        int temp = board2[row][col];
        board2[row][col] = board2[row2][col2];
        board2[row2][col2] = temp;
        return new Board(board2);
    }


    /**
     *
     * @return returns board achieved by exchagned pair of tiles
     */
    public Board twin() {
        int row = 0;
        int col = 0;
        int row2 = 1;
        int col2 = 1;
        if (blocks[row2][col2] == blank)
        {
            row2 = 1;
            col2 = 0;
        }
        if (blocks[row][col] == blank)
        {
            row = 1;
            col = 0;
        }
        return exchange(row, col, row2, col2);
    }

    /**
     *
     * @param y object which we want to compare with
     * @return determines whether two objects are equal
     */
    public boolean equals(Object y) {
        if (y == this){
            return true;
        }
        if (y == null){
            return false;
        }

        if (y.getClass().isInstance(this)){ // both this and y are same class
            Board boardY = (Board) y;
            if (boardY.n != this.n)
                return false;
            for (int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                    if (this.blocks[i][j] != boardY.blocks[i][j]){
                        return false;
                    }
                }
            }
        } else { // this and y dont belong to same class
            return false;
        }
        return true;
    }

    /**
     *
     * @return returs all neighbor tiles
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> boards = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == blank){  // 找到空格位置
                    // left neighbors
                    if (j > 0) {
                        Board left = exchange(i, j, i, j-1);
                        boards.add(left);
                    }
                    // right neighbors
                    if (j < n-1) {
                        Board right = exchange(i, j, i, j+1);
                        boards.add(right);
                    }
                    // upper neighbors
                    if (i > 0) {
                        Board up = exchange(i, j, i-1, j);
                        boards.add(up);
                    }

                    // bottom neighbors
                    if (i < n-1) {
                        Board bottom = exchange(i, j, i+1, j);
                        boards.add(bottom);
                    }
                }
            }
        }
        return boards;
    }

    /**
     *
     * @return returns a representation of this board
     */
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                build.append(blocks[i][j]).append("\t");
            }
            build.append("\n");
        }
        return build.toString();
    }
}
