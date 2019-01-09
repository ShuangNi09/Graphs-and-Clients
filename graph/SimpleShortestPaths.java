package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Shuang Ni
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        _corresP = new ArrayList<>();
        _corresW = new ArrayList<>();
        _predecessor = new ArrayList<>();
        _weight = new ArrayList<>();
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        if (!_G.contains(v)) {
            return Double.MAX_VALUE;
        } else {
            return _weight.get(_corresW.indexOf(v));
        }
    }

    @Override
    protected void setWeight(int v, double w) {
        if (!_corresW.contains(v)) {
            _corresW.add(v);
            _weight.add(w);
        } else {
            _weight.set(_corresW.indexOf(v), w);
        }
    }

    @Override
    public int getPredecessor(int v) {
        if (_corresP.contains(v)) {
            return _predecessor.get(_corresP.indexOf(v));
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    protected void setPredecessor(int v, int u) {
        if (!_corresP.contains(v)) {
            _corresP.add(v);
            _predecessor.add(u);
        } else {
            _predecessor.set(_corresP.indexOf(v), u);
        }
    }

    /** Corresponding weight for mapping. */
    private ArrayList<Integer> _corresW;

    /** Store the weights and match with _corresW. */
    private ArrayList<Double> _weight;

    /** Corresponding predecessor for mapping. */
    private ArrayList<Integer> _corresP;

    /** Store the predecessors and match with _corresP. */
    private ArrayList<Integer> _predecessor;

}
