package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Shuang Ni
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        Comparator<Integer> compare = new VertexCompare();
        _following = new TreeSet<>(compare);
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        for (int vert : _G.vertices()) {
            setWeight(vert, Double.MAX_VALUE);
            _following.add(vert);
            setPredecessor(vert, 0);
        }
        setWeight(getSource(), 0.0);
        setPredecessor(getSource(), 0);
        _following.add(getSource());
        while (!_following.isEmpty()) {
            int next = _following.pollFirst();
            if (next == getDest()) {
                return;
            } else {
                for (int secnext : _G.successors(next)) {
                    double updated = getWeight(next) + getWeight(next, secnext);
                    if (updated < getWeight(secnext)) {
                        setWeight(secnext, updated);
                        setPredecessor(secnext, next);
                        if (_following.contains(secnext)) {
                            _following.remove(secnext);
                            _following.add(secnext);
                        } else {
                            _following.add(secnext);
                        }
                    }
                }
            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        ArrayList<Integer> result = new ArrayList<>();
        int temp = v;
        result.add(temp);
        while (temp != getSource()) {
            result.add(getPredecessor(temp));
            temp = getPredecessor(temp);
        }
        Collections.reverse(result);
        return result;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** Self-defined comparator class that compares the vertex. */
    private class VertexCompare implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            double a = getWeight(o1) + estimatedDistance(o1);
            double b = getWeight(o2) + estimatedDistance(o2);
            return Double.compare(a, b);
        }
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** An ordering of following vertices. */
    private TreeSet<Integer> _following;

}
