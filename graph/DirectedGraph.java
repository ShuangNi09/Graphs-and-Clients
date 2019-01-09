package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Shuang Ni
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        int degree = 0;
        for (int[] e: this.edges()) {
            if (e[1] == v) {
                degree += 1;
            }
        }
        return degree;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        ArrayList<Integer> pred = new ArrayList<>();
        for (int[] e: this.edges()) {
            if (e[1] == v) {
                pred.add(e[0]);
            }
        }
        return Iteration.iteration(pred);
    }


}
