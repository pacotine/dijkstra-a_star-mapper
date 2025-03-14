package main.instances;

import main.model.WeightedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An abstract base class for shortest path algorithms on a weighted graph.
 * Provides functionality to store delays, track paths, and retrieve the shortest path.
 */
public abstract class PathFinderInstance {
    protected final WeightedGraph graph;
    protected final HashMap<Integer, WeightedGraph.Vertex> delays;
    protected final List<WeightedGraph.Vertex> path;

    /**
     * Constructs a {@link PathFinderInstance} for the specified graph.
     *
     * @param graph the graph (for now, {@link WeightedGraph}) to operate on
     */
    protected PathFinderInstance(WeightedGraph graph) {
        this.graph = graph;
        this.delays = new HashMap<>();
        this.path = new ArrayList<>();
    }

    /**
     * @return the mapping of step indices to vertices processed during the algorithm
     */
    public HashMap<Integer, WeightedGraph.Vertex> getDelays() {
        return delays;
    }

    /**
     * @return the shortest path as a list of vertices, determined after the search
     */
    public List<WeightedGraph.Vertex> getPath() {
        return path;
    }

    /**
     * Reconstructs the shortest path from the start to the end vertex.
     *
     * @param start the starting vertex
     * @param end the ending vertex
     */
    protected void retrievePath(WeightedGraph.Vertex start, WeightedGraph.Vertex end, boolean verbose) {
        if(verbose) System.out.println("\n\nfinal path (from the end to the beginning):");
        WeightedGraph.Vertex s = end;
        while(s != start) {
            if(verbose) System.out.println(s);
            path.add(s);
            s = s.getPrevious();
        }
        path.add(start);
    }

    /**
     * Searches for the shortest path between the start and end vertices.
     *
     * @param start the starting vertex
     * @param end the ending vertex
     * @return the total cost of the shortest path
     */
    public abstract double searchPath(WeightedGraph.Vertex start, WeightedGraph.Vertex end, boolean verbose);
}
