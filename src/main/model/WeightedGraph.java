package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure for representing a weighted graph.
 * This graph is designed to support shortest-path algorithms ({@link main.instances.PathFinderInstance}).
 */
public class WeightedGraph {
    private final List<Vertex> vertices;

    public record Type(String name, int value, String color) {}

    /**
     * Represents a vertex in the graph. Each vertex has a unique identifier,
     * a type, neighbors (both direct and diagonal), and attributes for graph traversal.
     */
    public static class Vertex {
        private final int n;
        private final Type type;
        private final List<Vertex> neighbors;
        private final List<Vertex> diagonalNeighbors;
        private double timeFromSource;
        private Vertex previous;

        /**
         * Constructs a {@link Vertex} with the specified identifier and type.
         *
         * @param n    the unique identifier for the vertex
         * @param type the type of the vertex
         */
        Vertex(int n, Type type) {
            this.n = n;
            this.type = type;
            this.neighbors = new ArrayList<>();
            this.diagonalNeighbors = new ArrayList<>();
        }

        /**
         * Adds a neighbor to this vertex.
         *
         * @param neighbor   the neighboring vertex to add
         * @param isDiagonal whether the neighbor is diagonal
         */
        public void addNeighbor(Vertex neighbor, boolean isDiagonal) {
            neighbors.add(neighbor);
            if(isDiagonal) diagonalNeighbors.add(neighbor);
        }

        /**
         * @return the unique identifier of this vertex
         */
        public int getN() {
            return n;
        }

        /**
         * @return the type of this vertex
         */
        public Type getType() {
            return type;
        }

        /**
         * @return the time from the source vertex to this vertex
         */
        public double getTimeFromSource() {
            return timeFromSource;
        }

        /**
         * Sets the time from the source vertex to this vertex.
         *
         * @param timeFromSource the time to set
         */
        public void setTimeFromSource(double timeFromSource) {
            this.timeFromSource = timeFromSource;
        }

        /**
         * @return the list of direct neighbors of this vertex
         */
        public List<Vertex> getNeighbors() {
            return neighbors;
        }

        /**
         * @return the vertex preceding this one in the shortest path
         */
        public Vertex getPrevious() {
            return previous;
        }

        /**
         * Sets the vertex preceding this one in the shortest path.
         *
         * @param previous the preceding vertex to set
         */
        public void setPrevious(Vertex previous) {
            this.previous = previous;
        }

        /**
         * @return the list of diagonal neighbors of this vertex
         */
        public List<Vertex> getDiagonalNeighbors() { return diagonalNeighbors; }

        /**
         * @return a {@link String} representation of the vertex, including its identifier,
         * type, neighbors, and previous vertex
         */
        @Override
        public String toString() {
            return "{" +
                    "n=" + n +
                    ", type=" + type.name +
                    ", neighbors=" + neighbors.stream().map(Vertex::getN).toList() +
                    ", previous=" + (previous != null ? previous.getN() : "no") +
                    '}';
        }
    }

    /**
     * Constructs an empty weighted graph.
     */
    public WeightedGraph() {
        this.vertices = new ArrayList<>();
    }

    /**
     * Adds a new vertex to the graph with the specified type.
     *
     * @param type the type of the new vertex
     */
    public void addVertex(Type type) {
        int n = vertices.size();
        vertices.add(new Vertex(n, type));
    }

    /**
     * @return a list of all vertices in the graph
     */
    public List<Vertex> getVertices() {
        return vertices;
    }

    /**
     * @return a {@link String} representation of the entire graph, listing all vertices
     */
    @Override
    public String toString() {
        return vertices.toString();
    }
}
