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
     * @param verbose whether logs should be output
     * @return the total cost of the shortest path
     */
    @Override
    public double searchPath(WeightedGraph.Vertex start, WeightedGraph.Vertex end, boolean verbose) {
        delays.clear();
        path.clear();

        final int ex = end.getN() % mapSize;
        final int ey = end.getN() / mapSize;
        List<WeightedGraph.Vertex> open = new ArrayList<>();
        open.add(start);

        HashMap<WeightedGraph.Vertex, Double> f = new HashMap<>();
        for(WeightedGraph.Vertex v : graph.getVertices()) {
            f.put(v, Double.POSITIVE_INFINITY);
            v.setTimeFromSource(Double.POSITIVE_INFINITY);
        }
        start.setTimeFromSource(0.0);
        f.replace(start, heuristic.h(start.getN()%mapSize, start.getN()/mapSize, ex, ey));

        int i = 0;
        while(!open.contains(end)) {
            WeightedGraph.Vertex u = findMinF(open, f);
            if(verbose) System.out.println("selecting the vertex with the minimum f-score: " + u);

            WeightedGraph.Vertex temp = new WeightedGraph.Vertex(u.getN(), u.getType());
            temp.setPrevious(u.getPrevious()); //keeping the previous vertex at time i with shallow copy
            delays.put(i, temp);

            open.remove(u);
            for(WeightedGraph.Vertex neighbor : u.getNeighbors()) {
                int nx = neighbor.getN() % mapSize;
                int ny = neighbor.getN() / mapSize;
                double dist = heuristic.h(nx,ny,ex,ey);
                double factor = u.getDiagonalNeighbors().contains(neighbor) ? Math.sqrt(2) : 2.0;
                double weight = (double)(neighbor.getType().value() + u.getType().value())/factor;

                double tentative = u.getTimeFromSource() + weight;
                if(tentative < neighbor.getTimeFromSource()) {
                    neighbor.setPrevious(u);
                    neighbor.setTimeFromSource(tentative);
                    if(verbose) System.out.println("update neighbor " + neighbor.getN() + " f-score: " + tentative+dist);
                    f.replace(neighbor, tentative+dist);
                    if(!open.contains(neighbor)) open.add(neighbor);
                }
            }
            i++;
        }

        retrievePath(start, end, verbose);
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
