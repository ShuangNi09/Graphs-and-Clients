package graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 * Unit tests for the ShortestPaths class.
 *  @author Shuang Ni
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
        assertFalse("Initial graph contains nothing", g.contains(8, 4));
        assertEquals("Initial graph max index is 0", 0, g.maxVertex());
    }

    @Test
    public void basicStuff() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        assertEquals(3, g.maxVertex());
        assertEquals(3, g.vertexSize());
        g.remove(3);
        assertEquals(2, g.maxVertex());
        g.remove(2);
        g.remove(2);
        g.remove(1);
        assertEquals(0, g.vertexSize());
    }

    @Test
    public void morebasics() {
        DirectedGraph gg = new DirectedGraph();
        for (int i = 0; i < 10; i += 1) {
            gg.add();
        }
        gg.add(1, 10);
        gg.add(10, 9);
        gg.add(9, 8);
        gg.add(3, 5);
        gg.add(5, 2);
        gg.add(2, 5);
        Iteration<Integer> test = gg.vertices();
        assertEquals(Integer.valueOf(1), test.next());
        assertEquals(Integer.valueOf(2), test.next());
        assertEquals(6, gg.edgeSize());
        assertEquals(10, gg.vertexSize());
        gg.remove(2, 5);
        assertEquals(5, gg.edgeSize());
        gg.remove(2);
        assertEquals(9, gg.vertexSize());
        gg.remove(3);
        assertEquals(Integer.valueOf(3), test.next());
        Iteration<Integer> test2 = gg.vertices();
        test2.next();
        test2.next();
        assertEquals(Integer.valueOf(5), test2.next());
    }

    @Test
    public void successor() {
        DirectedGraph suc = new DirectedGraph();
        for (int i = 0; i < 4; i += 1) {
            suc.add();
        }
        suc.add(1, 2);
        suc.add(1, 3);
        suc.add(1, 4);
        suc.add(2, 4);
        suc.add(3, 4);
        Iteration<Integer> succ1 = suc.successors(1);
        int a = 0;
        while (succ1.hasNext()) {
            succ1.next();
            a += 1;
        }
        assertEquals(3, a);
        Iteration<int[]> edge1 = suc.edges();
        int b = 0;
        while (edge1.hasNext()) {
            edge1.next();
            b += 1;
        }
        assertEquals(5, b);
        suc.add(2, 1);
        suc.add(3, 1);
        suc.add(4, 1);
        suc.add(4, 2);
        suc.add(4, 3);
        Iteration<int[]> edge2 = suc.edges();
        int c = 0;
        while (edge2.hasNext()) {
            edge2.next();
            c += 1;
        }
        assertEquals(10, c);
    }

    @Test
    public void directed() {
        DirectedGraph suc = new DirectedGraph();
        for (int i = 0; i < 4; i += 1) {
            suc.add();
        }
        suc.add(1, 2);
        suc.add(1, 3);
        suc.add(1, 4);
        suc.add(2, 4);
        suc.add(3, 4);
        assertEquals(3, suc.inDegree(4));
        Iteration<Integer> pred = suc.predecessors(4);
        int predd = 0;
        while (pred.hasNext()) {
            pred.next();
            predd += 1;
        }
        assertEquals(3, predd);
    }

    @Test
    public void undirected() {
        UndirectedGraph xin = new UndirectedGraph();
        for (int i = 0; i < 4; i += 1) {
            xin.add();
        }
        xin.add(1, 2);
        xin.add(1, 3);
        xin.add(1, 4);
        xin.add(2, 4);
        xin.add(3, 4);
        assertEquals(xin.inDegree(1), xin.outDegree(1));
        Iteration<Integer> pred = xin.predecessors(1);
        int predd = 0;
        while (pred.hasNext()) {
            pred.next();
            predd += 1;
        }
        int suc = 0;
        Iteration<Integer> succ = xin.successors(1);
        while (succ.hasNext()) {
            succ.next();
            suc += 1;
        }
        assertEquals(predd, suc);
    }

    @Test
    public void ugremovetwo() {
        UndirectedGraph ug = new UndirectedGraph();
        ug.add();
        ug.add();
        ug.add(1, 2);
        ug.add();
        ug.add(1, 3);
        ug.add();
        ug.add(2, 3);
        ug.add(3, 4);
        assertTrue(ug.contains(3));
        ug.remove(3);
        assertFalse(ug.contains(3));
        ug.add();
        assertTrue(ug.contains(3));
        assertTrue(ug.contains(1, 2));
        assertTrue(ug.contains(2, 1));
        assertFalse(ug.contains(1, 3));
        assertFalse(ug.contains(2, 3));
        assertFalse(ug.contains(3, 4));
    }

    @Test
    public void testOutgoing() {
        UndirectedGraph ug = new UndirectedGraph();
        ug.add();
        ug.add();
        ug.add();
        ug.add();
        ug.add(1, 1);
        ug.add(1, 2);
        ug.add(1, 3);
        ug.add(1, 4);
        assertEquals(4, ug.outDegree(1));
        ug.remove(1, 1);
        assertEquals(3, ug.outDegree(1));
        ug.add(1, 1);
        ug.remove(1);
        assertEquals(0, ug.outDegree(1));
        assertEquals(0, ug.outDegree(2));
    }

    @Test
    public void testIndegree() {
        UndirectedGraph ug = new UndirectedGraph();
        ug.add();
        ug.add();
        ug.add();
        ug.add();
        ug.add(1, 1);
        ug.add(1, 2);
        ug.add(1, 3);
        ug.add(1, 4);
        assertEquals(4, ug.inDegree(1));
    }

    @Test
    public void testInDirected() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 4; i += 1) {
            g.add();
        }
        g.add(1, 1);
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        assertEquals(1, g.inDegree(1));
        assertEquals(4, g.outDegree(1));
    }

    @Test
    public void successor2() {
        DirectedGraph suc = new DirectedGraph();
        for (int i = 0; i < 10; i += 1) {
            suc.add();
        }
        suc.add(1, 2);
        suc.add(1, 3);
        suc.add(1, 4);
        suc.add(2, 4);
        suc.add(3, 4);
        Iteration<Integer> succ1 = suc.successors(1);
        int a = 0;
        while (succ1.hasNext()) {
            succ1.next();
            a += 1;
        }
        assertEquals(3, a);
        Iteration<int[]> edge1 = suc.edges();
        int b = 0;
        while (edge1.hasNext()) {
            edge1.next();
            b += 1;
        }
        assertEquals(5, b);
        suc.add(2, 1);
        suc.add(3, 1);
        suc.add(4, 1);
        suc.add(4, 2);
        suc.add(4, 3);
        Iteration<int[]> edge2 = suc.edges();
        int c = 0;
        while (edge2.hasNext()) {
            edge2.next();
            c += 1;
        }
        assertEquals(10, c);
    }

    @Test
    public void simple() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add(1, 2);
        g.add(2, 3);
        Testpath boba = new Testpath(g, 1, 3);
        boba.setWeight(1, 2, 1);
        boba.setWeight(2, 3, 10);
        boba.setPaths();
        List<Integer> path = boba.pathTo();
        assertArrayEquals(path.toArray(), new Integer[]{1, 2, 3});
    }

    @Test
    public void seven() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 1; i <= 7; i += 1) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(2, 3);
        g.add(2, 5);
        g.add(5, 3);
        g.add(3, 6);
        g.add(5, 6);
        g.add(2, 4);
        g.add(4, 5);
        g.add(5, 7);
        g.add(7, 4);
        g.add(7, 6);
        Testpath chicken = new Testpath(g, 1, 7);
        chicken.setWeight(1, 2, 2);
        chicken.setWeight(1, 3, 1);
        chicken.setWeight(2, 3, 5);
        chicken.setWeight(2, 5, 3);
        chicken.setWeight(5, 3, 1);
        chicken.setWeight(3, 6, 15);
        chicken.setWeight(5, 6, 4);
        chicken.setWeight(2, 4, 11);
        chicken.setWeight(4, 5, 2);
        chicken.setWeight(5, 7, 5);
        chicken.setWeight(7, 4, 1);
        chicken.setWeight(7, 6, 1);
        chicken.setPaths();
        List<Integer> path = chicken.pathTo();
        assertArrayEquals(path.toArray(), new Integer[]{1, 2, 5, 7});
        Testpath shicken = new Testpath(g, 1, 4);
        shicken.setWeight(1, 2, 2);
        shicken.setWeight(1, 3, 1);
        shicken.setWeight(2, 3, 5);
        shicken.setWeight(2, 5, 3);
        shicken.setWeight(5, 3, 1);
        shicken.setWeight(3, 6, 15);
        shicken.setWeight(5, 6, 4);
        shicken.setWeight(2, 4, 11);
        shicken.setWeight(4, 5, 2);
        shicken.setWeight(5, 7, 5);
        shicken.setWeight(7, 4, 1);
        shicken.setWeight(7, 6, 1);
        shicken.setPaths();
        List<Integer> path2 = shicken.pathTo();
        assertArrayEquals(path2.toArray(), new Integer[]{1, 2, 5, 7, 4});
    }

    @Test
    public void cycle() {
        DirectedGraph ug = new DirectedGraph();
        for (int i = 1; i <= 5; i += 1) {
            ug.add();
        }
        ug.add(1, 2);
        ug.add(2, 3);
        ug.add(3, 1);
        ug.add(3, 4);
        ug.add(4, 5);
        Testpath test = new Testpath(ug, 1, 5);
        test.setWeight(1, 2, 1);
        test.setWeight(2, 3, 1);
        test.setWeight(3, 1, 1);
        test.setWeight(3, 4, 18);
        test.setWeight(4, 5, 20);
        test.setPaths();
        List<Integer> path = test.pathTo();
        assertArrayEquals(path.toArray(), new Integer[]{1, 2, 3, 4, 5});
    }

    private class Testpath extends SimpleShortestPaths {
        Testpath(Graph G, int source, int destination) {
            super(G, source, destination);
            _idtrack = new ArrayList<>();
            _weights = new ArrayList<>();
        }

        void setWeight(int u, int v, int weight) {
            int edgeid = _G.edgeId(u, v);
            if (!_idtrack.contains(edgeid)) {
                _idtrack.add(edgeid);
                _weights.add(weight);
            } else {
                _weights.set(_idtrack.indexOf(edgeid), weight);
            }
        }

        @Override
        public double getWeight(int u, int v) {
            int edgeid = _G.edgeId(u, v);
            return _weights.get(_idtrack.indexOf(edgeid));
        }

        private ArrayList<Integer> _idtrack;
        private ArrayList<Integer> _weights;
    }

}
