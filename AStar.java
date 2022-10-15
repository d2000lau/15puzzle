import edu.princeton.cs.algs4.MinPQ;


import java.util.*;

public class AStar {
    private final Stack<Board> solutionBoard;
    private boolean isSolvable;

    public AStar(Board initial) {
        isSolvable = false;
        solutionBoard = new Stack<>();
        MinPQ<SearchNode> search = new MinPQ<>();
        search.insert(new SearchNode(initial.twin(), null));
        search.insert(new SearchNode(initial, null));
        while (!search.min().board.isGoal()) {
            SearchNode node = search.delMin();
            for (Board board : node.board.neighbour())
                if (node.previousNode == null || !node.previousNode.board.equals(board)) {
                    search.insert(new SearchNode(board, node));
                }
        }

        SearchNode current = search.min();
        while (current.previousNode != null) {
            solutionBoard.push(current.board);
            current = current.previousNode;
        }
        solutionBoard.push(current.board);
        if (current.board.equals(initial)) {
            isSolvable = true;
        }
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return solutionBoard.size() - 1;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        Board board;
        int distance;
        int moves = 0;
        SearchNode previousNode;
        int priority;

        public SearchNode(Board board, SearchNode previousNode) {
            if (board == null) {
                throw new IllegalArgumentException();
            }
            this.board = board;
            this.distance = board.distance();
            if (previousNode != null) {
                this.moves = previousNode.moves + 1;
                this.previousNode = previousNode;
            }
            this.priority = this.distance + this.moves;
        }

        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.priority, o.priority);
        }
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solutionBoard;
        }
        return null;
    }

    public boolean isSolvable() {
        return isSolvable;
    }
}
