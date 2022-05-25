package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Solver {
    private List<WorldState> sequenceNodes;
    private int numMoves;

    /** Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists. */
    public Solver(WorldState inital) {
        MinPQ<SearchNode> searchNodes = new MinPQ<>();
        SearchNode initNode = new SearchNode(inital, 0, null);
        sequenceNodes = new ArrayList<>();
        searchNodes.insert(initNode);
        numMoves = 0;
        SearchNode finish = null;

        while(!searchNodes.isEmpty()) {
            SearchNode currentNode =  searchNodes.delMin();
            numMoves = currentNode.numMoves;
            if (currentNode.world.isGoal()) {
                finish = currentNode;
                break;
            } else {
                for (WorldState neighbor : currentNode.world.neighbors()) {
                    if (!neighbor.equals(currentNode.world)) {
                        SearchNode node = new SearchNode(neighbor,
                                currentNode.numMoves + 1, currentNode);
                        searchNodes.insert(node);
                    }
                }
            }
        }

        while (finish != null) {
            sequenceNodes.add(finish.world);
            finish = finish.prevNode;
        }
        Collections.reverse(sequenceNodes);
    }

    private class SearchNode implements Comparable<SearchNode>{
        private WorldState world;
        private int numMoves;
        private SearchNode prevNode;
        private int priority;

        SearchNode(WorldState newWorld, int moves, SearchNode previous) {
            world = newWorld;
            numMoves = moves;
            prevNode = previous;
            priority = numMoves + world.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(SearchNode other) {
            return this.priority - other.priority;
        }
    }



    /** Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState. */
    public int moves() {
        return numMoves;
    }

    /** Returns a sequence of WorldStates from the initial WorldState
     to the solution. */
    Iterable<WorldState> solution() {
        return sequenceNodes;
    }
}
