package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Shuang Ni
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        _vertices = new ArrayList<>();
        _linkgraph = new ArrayList<>();
        _novertice = 0;
        _noedge = 0;
        _maxv = 0;
    }



    @Override
    public int vertexSize() {
        return _novertice;
    }

    @Override
    public int maxVertex() {
        if (_vertices == null || _vertices.isEmpty()) {
            return 0;
        } else {
            int index = 0;
            int maxind = 0;
            while (index < _vertices.size() - 1) {
                if (_vertices.get(index) < _vertices.get(index + 1)) {
                    maxind = index + 1;
                }
                index += 1;
            }
            return _vertices.get(maxind);
        }
    }

    @Override
    public int edgeSize() {
        return _noedge;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (_vertices == null || _vertices.isEmpty()) {
            return 0;
        } else if (!_vertices.contains(v)) {
            return 0;
        } else {
            int index = _vertices.indexOf(v);
            return _linkgraph.get(index).size();
        }
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return _vertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (_vertices == null || _vertices.isEmpty()) {
            return false;
        } else if (!_vertices.contains(u) || !_vertices.contains(v)) {
            return false;
        } else {
            int index = _vertices.indexOf(u);
            LinkedList<Integer> adj = _linkgraph.get(index);
            return adj.contains(v);
        }
    }

    @Override
    public int add() {
        if (_vertices.indexOf(0) == -1) {
            _maxv += 1;
            _vertices.add(_maxv);
            _novertice += 1;
            LinkedList<Integer> newly = new LinkedList<>();
            _linkgraph.add(newly);
            return _maxv;
        } else {
            int slot = _vertices.indexOf(0);
            _vertices.set(slot, slot + 1);
            _novertice += 1;
            LinkedList<Integer> restore = new LinkedList<>();
            _linkgraph.set(slot, restore);
            return slot + 1;
        }
    }

    @Override
    public int add(int u, int v) {
        if (_vertices == null || _vertices.isEmpty()
                || !_vertices.contains(u) || !_vertices.contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        } else {
            if (this.isDirected()) {
                if (!this.contains(u, v)) {
                    int index = _vertices.indexOf(u);
                    _linkgraph.get(index).add(v);
                    _noedge += 1;
                    return edgeId(u, v);
                }
                return 0;
            } else {
                if (!this.contains(u, v)) {
                    int index1 = _vertices.indexOf(u);
                    _linkgraph.get(index1).add(v);
                    if (u != v) {
                        int index2 = _vertices.indexOf(v);
                        _linkgraph.get(index2).add(u);
                    }
                    _noedge += 1;
                    return edgeId(u, v);
                }
                return 0;
            }
        }
    }

    @Override
    public void remove(int v) {
        if (_vertices != null && !_vertices.isEmpty()) {
            if (_vertices.contains(v)) {
                int index = _vertices.indexOf(v);
                _vertices.set(index, 0);
                LinkedList<Integer> zero = new LinkedList<>();
                int noed = _linkgraph.get(index).size();
                _noedge -= noed;
                _linkgraph.set(index, zero);
                _novertice -= 1;
                for (LinkedList<Integer> ea : _linkgraph) {
                    if (ea.contains(v)) {
                        ea.remove(Integer.valueOf(v));
                        if (isDirected()) {
                            _noedge -= 1;
                        }
                    }
                }
                if (_maxv == v) {
                    _maxv = maxVertex();
                }
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        if (_vertices == null || _vertices.isEmpty()
                || !_vertices.contains(u) || !_vertices.contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        } else {
            if (this.isDirected()) {
                if (this.contains(u, v)) {
                    int index = _vertices.indexOf(u);
                    _linkgraph.get(index).remove(Integer.valueOf(v));
                    _noedge -= 1;
                }
            } else {
                if (this.contains(u, v)) {
                    int both1 = _vertices.indexOf(u);
                    _linkgraph.get(both1).remove(Integer.valueOf(v));
                    int both2 = _vertices.indexOf(v);
                    _linkgraph.get(both2).remove(Integer.valueOf(u));
                    _noedge -= 1;
                }
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        ArrayList<Integer> copy = new ArrayList<>();
        for (int x: _vertices) {
            if (x != 0) {
                copy.add(x);
            }
        }
        return Iteration.iteration(copy);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        if (_vertices == null || !_vertices.contains(v)) {
            return Iteration.iteration(Collections.emptyIterator());
        } else {
            int index = _vertices.indexOf(v);
            LinkedList<Integer> mysuc = _linkgraph.get(index);
            return Iteration.iteration(mysuc);
        }
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        int bookkeep;
        ArrayList<int[]> alledges = new ArrayList<>();
        for (int i = 0; i < _vertices.size(); i += 1) {
            if (_vertices.get(i) != 0) {
                LinkedList<Integer> adj = _linkgraph.get(i);
                if (adj.isEmpty()) {
                    continue;
                } else {
                    bookkeep = _vertices.get(i);
                    for (Object anAdj : adj) {
                        int love = (int) anAdj;
                        int[] oneedge = new int[2];
                        oneedge[0] = bookkeep;
                        oneedge[1] = love;
                        alledges.add(oneedge);
                    }
                }
            }
        }
        return Iteration.iteration(alledges);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        if (!isDirected()) {
            if (v >= u) {
                return ((u + v) * (u + v + 1)) / 2 + v;
            } else {
                return ((u + v) * (u + v + 1)) / 2 + u;
            }
        }
        return ((u + v) * (u + v + 1)) / 2 + v;
    }

    /** Store all the vertices of the graph. */
    private ArrayList<Integer> _vertices;

    /** Maps the vertices to its neighbors. */
    private ArrayList<LinkedList<Integer>> _linkgraph;

    /** Keep track of the number of vertices. */
    private int _novertice;

    /** Keep track of the number of edges. */
    private int _noedge;

    /** Keep track of the maximum vertex. */
    private int _maxv;

}
