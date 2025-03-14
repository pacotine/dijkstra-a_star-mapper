package main.instances;

import main.model.WeightedGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Dijkstra algorithm for finding the shortest path in a {@link WeightedGraph}.
 */
public class DijkstraInstance extends PathFinderInstance {

    /**
     * Constructs a {@link DijkstraInstance} for the specified graph.
     *
     * @param graph the {@link WeightedGraph} to operate on
     */
    public DijkstraInstance(WeightedGraph graph) {
        super(graph);
    }

    /**
     * Performs the Dijkstra algorithm to find the shortest path between the start and end vertices.
     *
     * @param start the starting vertex
     * @param end the ending vertex
     * @return the total cost of the shortest path
     */
    @Override
    public double searchPath(WeightedGraph.Vertex start, WeightedGraph.Vertex end, boolean verbose) {
        delays.clear();
        path.clear();

        List<WeightedGraph.Vertex> Q = new ArrayList<>();

        for(WeightedGraph.Vertex v : graph.getVertices()) {
            v.setTimeFromSource(Double.POSITIVE_INFINITY);
            v.setPrevious(null);
            Q.add(v);
        }
        start.setTimeFromSource(0);
        int i = 0;
        //https://www.cs.cmu.edu/~15381-s19/recitations/rec2/rec2_sol.pdf
        while(Q.contains(end)) {
            //Dijkstra is worse than A*, especially if the heuristic of A* is a good one
            if(i%10000 == 0) System.out.println("searching..." +
                    " up to ~" + (1.0-(double)i/(double)this.graph.getVertices().size())*100.0 + "% remaining"); //max n
            WeightedGraph.Vertex u = findMin(Q);
            delays.put(i, u);
            if(verbose) System.out.println("selecting the vertex with the minimum weight: " + u);
            Q.remove(u);
            for(WeightedGraph.Vertex neighbor : u.getNeighbors()) {
                double neighborTime = neighbor.getTimeFromSource();
                double currentTime = u.getTimeFromSource();
                double factor = u.getDiagonalNeighbors().contains(neighbor) ? Math.sqrt(2) : 2.0;
                double weight = (double)(neighbor.getType().value() + u.getType().value())/factor;
                if(neighborTime > currentTime + weight) {
                    if(verbose) System.out.println("update neighbor value: (" + neighbor.getType().value() + "+" + u.getType().value() + ")/2 = " + weight);
                    neighbor.setTimeFromSource(currentTime+weight);
                    neighbor.setPrevious(u);
                }
            }
            i++;
        }

        retrievePath(start, end, verbose);
        return end.getTimeFromSource();
    }

    /**
     * Finds the vertex with the smallest distance in the priority queue.
     * <p>
     * <i>Note: we should use a {@link java.util.PriorityQueue} instead of {@link List}</i>
     *
     * @param Q the priority queue of vertices
     * @return the vertex with the smallest distance
     */
    private static WeightedGraph.Vertex findMin(List<WeightedGraph.Vertex> Q) {
        double distanceMin = Double.POSITIVE_INFINITY;
        WeightedGraph.Vertex min = null;
        for(WeightedGraph.Vertex s : Q) {
            if(s.getTimeFromSource() < distanceMin) {
                distanceMin = s.getTimeFromSource();
                min = s;
            }
        }

        return min;
    }
}
