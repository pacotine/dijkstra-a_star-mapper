package model;

import java.util.ArrayList;
import java.util.List;

public class WeightedGraph {
    private final List<Vertex> vertices;

    public record Type(char name, int value, String color) {}

    public static class Vertex {
        private final int n;
        private final Type type;
        private final ArrayList<Vertex> neighbors;
        private double timeFromSource;
        private Vertex previous;

        Vertex(int n, Type type) {
            this.n = n;
            this.type = type;
            this.neighbors = new ArrayList<>();
        }

        public void addNeighbor(Vertex neighbor) {
            neighbors.add(neighbor);
        }

        public int getN() {
            return n;
        }

        public Type getType() {
            return type;
        }

        public double getTimeFromSource() {
            return timeFromSource;
        }

        public void setTimeFromSource(double timeFromSource) {
            this.timeFromSource = timeFromSource;
        }

        public ArrayList<Vertex> getNeighbors() {
            return neighbors;
        }

        public Vertex getPrevious() {
            return previous;
        }

        public void setPrevious(Vertex previous) {
            this.previous = previous;
        }

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

    public WeightedGraph() {
        this.vertices = new ArrayList<>();
    }

    public void addVertex(Type type) {
        int n = vertices.size();
        vertices.add(new Vertex(n, type));
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return vertices.toString();
    }
}
