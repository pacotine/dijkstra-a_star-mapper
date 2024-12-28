package main.reader;

import main.model.WeightedGraph;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A concrete implementation of {@link GraphReader} that reads graph data from a text file.
 * This reader handles graph configuration files (see examples in `assets/examples` folder).
 * <p>
 * It uses a {@link Scanner} for reading the file line by line.
 */
public class GraphFileReader extends GraphReader {
    private final Scanner scanner;

    /**
     * Constructs a {@link GraphFileReader} for the specified file path.
     *
     * @param path the path to the graph configuration file (containing the graph data).
     * @throws FileNotFoundException if the file does not exist
     */
    public GraphFileReader(String path) throws FileNotFoundException {
        super(path);
        this.scanner = new Scanner(file);
    }

    /**
     * Reads the graph from the file, initializes its vertices, neighbors, start, and end points.
     *
     * @throws IllegalArgumentException if the file is invalid or contains unexpected data
     */
    @Override
    public void read() {
        if(!file.exists()) throw new IllegalArgumentException("Cannot read this file : it doesn't exist");

        skip(2);
        this.lines = retrieveInt("nlines");
        this.columns = retrieveInt("ncol");
        skip(1);
        Map<Character, WeightedGraph.Type> types = retrieveTypes();
        //System.out.println(types);
        this.weightedGraph = new WeightedGraph();
        setVertices(types);
        setNeighbors();
        skip(2);
        this.start = retrieveVertex(weightedGraph, columns, "Start");
        this.end = retrieveVertex(weightedGraph, columns, "Finish");
    }

    /**
     * Retrieves and sets neighbors for all vertices of this graph.
     * Graphs generated from configuration files allow diagonal neighbors (each vertex has up to 8 neighbors).
     */
    private void setNeighbors() {
        List<WeightedGraph.Vertex> vertices = weightedGraph.getVertices();
        for(int line = 0; line < lines; line++) {
            for(int col = 0; col < columns; col++) {
                int current = line * columns + col;
                WeightedGraph.Vertex v = vertices.get(current);
                if(line < lines-1 && col < columns-1) v.addNeighbor(vertices.get((line+1)*columns+(col+1)), true);
                if(line < lines-1) v.addNeighbor(vertices.get((line+1)*columns+(col)), false);
                if(col > 0 && line < lines-1) v.addNeighbor(vertices.get((line+1)*columns+(col-1)), true);
                if(col < columns-1) v.addNeighbor(vertices.get((line)*columns+(col+1)), false);
                if(col > 0) v.addNeighbor(vertices.get((line)*columns+(col-1)), false);
                if(line > 0 && col < columns-1) v.addNeighbor(vertices.get((line-1)*columns+(col+1)), true);
                if(line > 0 && col > 0) v.addNeighbor(vertices.get((line-1)*columns+(col-1)), true);
                if(line > 0) v.addNeighbor(vertices.get((line-1)*columns+(col)), false);
            }
        }
    }

    /**
     * Retrieves an integer specified on a line by the keyword {@code key}, positioned after the '='.
     * For example, this specification is valid: {@code ncols = 4} and this method will return {@code 4}.
     * @param key the expected key to read the integer
     * @return the integer specified by the line with the {@code key} keyword
     */
    private int retrieveInt(String key) {
        String[] args = scanner.nextLine().split("=");
        String keyFound = args[0];
        String value = args.length > 1 ? args[1] : null;
        if(!keyFound.equals(key)) throw new IllegalArgumentException("Incorrect key found : " + keyFound);
        if(value == null) throw new IllegalArgumentException("No value found after " + keyFound);

        return Integer.parseInt(value);
    }

    /**
     * Retrieves the different vertex types defined in the configuration file (in the dedicated {@code =Types=} section).
     * @return a {@link Map} containing the (ID, {@link main.model.WeightedGraph.Type}) pairs for this graph
     */
    private Map<Character, WeightedGraph.Type> retrieveTypes() {
        Map<Character, WeightedGraph.Type> types = new HashMap<>();
        String line;
        while((line = scanner.nextLine()) != null && !line.equals("==Graph==")) {
            String[] args = line.split("=");
            String typeName = args[0];
            String value = args.length > 1 ? args[1] : null;
            if(value == null) throw new IllegalArgumentException("No value found after " + typeName);

            line = scanner.nextLine();
            String color = line;

            types.put(typeName.charAt(0), new WeightedGraph.Type(typeName, Integer.parseInt(value), color));
        }

        return types;
    }

    /**
     * Retrieves a vertex from the graph specified by the {@code key} keyword in the configuration file by its coordinates.
     * @param G a {@link WeightedGraph}
     * @param columns the map width
     * @param key the expected key to read the vertex
     * @return the vertex specified by its coordinates in the configuration file and by the {@code key} keyword
     */
    private WeightedGraph.Vertex retrieveVertex(WeightedGraph G, int columns, String key) {
        String[] args = scanner.nextLine().split("=");
        String keyFound = args[0];
        String value = args.length > 1 ? args[1] : null;
        if(!keyFound.equals(key)) throw new IllegalArgumentException("Incorrect key found : " + keyFound);
        if(value == null) throw new IllegalArgumentException("No value found after " + keyFound);
        String[] points = value.split(",");
        int x = Integer.parseInt(points[0]);
        int y = Integer.parseInt(points[1]);

        return G.getVertices().get(x * columns + y);
    }

    /**
     * Read the {@code ==Graph==} section to configure the graph and add its vertices.
     * @param types a {@link Map} containing the (ID, {@link main.model.WeightedGraph.Type}) pairs for this graph
     */
    private void setVertices(Map<Character, WeightedGraph.Type> types) {
        for (int line=0; line < lines; line++) {
            String c = scanner.next();
            for (int col=0; col < columns; col++) {
                weightedGraph.addVertex(types.get(c.charAt(col)));
            }
        }
    }

    /**
     * Skip unnecessary or empty lines to the {@link Scanner}.
     * @param lines the number of lines to skip
     */
    private void skip(int lines) {
        while(lines > 0) {
            scanner.nextLine();
            lines--;
        }
    }
}
