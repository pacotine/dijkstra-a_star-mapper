package main.instances;

import main.model.WeightedGraph;

import java.util.*;

/**
 * An implementation of the A* algorithm for finding the shortest path in a weighted graph.
 */
public class AStarInstance extends PathFinderInstance {
    private final int mapSize;
    private final Heuristic heuristic;

    /**
     * Constructs an {@link AStarInstance} for the specified graph, map size, and heuristic.
     *
     * @param graph the {@link WeightedGraph} to operate on
     * @param mapSize the size of the map (used for heuristic calculations)
     * @param heuristic the heuristic function for A*
     */
    public AStarInstance(WeightedGraph graph, int mapSize, Heuristic heuristic) {
        super(graph);
        this.mapSize = mapSize;
        this.heuristic = heuristic;
    }

    /**
     * Performs the A* algorithm to find the shortest path between the start and end vertices.
     *
     * @param start the starting vertex
     * @param end the ending vertex
     * @return the total cost of the shortest path
     */
    @Override
    public double searchPath(WeightedGraph.Vertex start, WeightedGraph.Vertex end) {
        delays.clear();
        path.clear();

        List<WeightedGraph.Vertex> open = new ArrayList<>();
        open.add(start);

        HashMap<WeightedGraph.Vertex, Double> f = new HashMap<>();
        for(WeightedGraph.Vertex v : graph.getVertices()) {
            f.put(v, Double.POSITIVE_INFINITY);
            v.setTimeFromSource(Double.POSITIVE_INFINITY);
        }
        start.setTimeFromSource(0.0);
        f.replace(start, 0.0);

        int i = 0;
        while(!open.contains(end)) {
            WeightedGraph.Vertex u = findMinF(open, f);
            delays.put(i, u);
            open.remove(u);
            for(WeightedGraph.Vertex neighbor : u.getNeighbors()) {
                int x1 = u.getN() % mapSize;
                int y1 = u.getN() / mapSize;
                int x2 = end.getN() % mapSize;
                int y2 = end.getN() / mapSize;
                double dist = heuristic.h(x1,y1,x2,y2);
                double factor = u.getDiagonalNeighbors().contains(neighbor) ? Math.sqrt(2) : 2.0;
                double weight = (double)(neighbor.getType().value() + u.getType().value())/factor;

                double tentative = u.getTimeFromSource() + weight;
                if(tentative < neighbor.getTimeFromSource()) {
                    neighbor.setPrevious(u);
                    neighbor.setTimeFromSource(tentative);
                    f.replace(neighbor, tentative+dist);
                    if(!open.contains(neighbor)) open.add(neighbor);
                }
            }
            i++;
        }

        retrievePath(start, end);
        return end.getTimeFromSource();
    }

    /**
     * Finds the vertex with the smallest f-score in the open list.
     *
     * @param open the open list of vertices
     * @param f the f-score map
     * @return the vertex with the smallest f-score
     */
    private static WeightedGraph.Vertex findMinF(List<WeightedGraph.Vertex> open, HashMap<WeightedGraph.Vertex, Double> f) {
        double distanceMin = Double.POSITIVE_INFINITY;
        WeightedGraph.Vertex min = null;
        for(WeightedGraph.Vertex s : open) {
            if(f.get(s) < distanceMin) {
                distanceMin = f.get(s);
                min = s;
            }
        }

        return min;
    }


}
