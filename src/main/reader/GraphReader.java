package main.reader;

import main.model.WeightedGraph;

import java.io.File;
import java.io.IOException;

/**
 * An abstract class representing a reader for graphs.
 * Handles file input and graph initialization, storing vertices, start, and end points,
 * as well as the graph's dimensions.
 * @see GraphFileReader
 * @see GraphImageReader
 */
public abstract class GraphReader {
    protected final File file;
    protected WeightedGraph weightedGraph;
    protected WeightedGraph.Vertex start;
    protected WeightedGraph.Vertex end;
    protected int lines;
    protected int columns;

    /**
     * Constructs a GraphReader for a specified file path.
     *
     * @param path the path to the file containing the graph data
     */
    public GraphReader(String path) {
        this.file = new File(path);
        this.weightedGraph = null;
        this.start = null;
        this.end = null;
    }

    /**
     * Retrieves the graph (for now, {@link WeightedGraph}) built by this reader.
     *
     * @return the constructed {@link WeightedGraph}
     * @throws Exception if the graph has not been read yet
     */
    public WeightedGraph retrieveGraph() throws Exception {
        if(weightedGraph == null) throw new Exception("File should be read before retrieving the graph");
        return weightedGraph;
    }

    /**
     * Retrieves the starting vertex of the graph.
     *
     * @return the starting vertex
     * @throws Exception if the starting vertex has not been identified
     */
    public WeightedGraph.Vertex retrieveStart() throws Exception {
        if(start == null) throw new Exception("No start point found");
        return start;
    }

    /**
     * Retrieves the ending vertex of the graph.
     *
     * @return the ending vertex
     * @throws Exception if the ending vertex has not been identified
     */
    public WeightedGraph.Vertex retrieveEnd() throws Exception {
        if(end == null) throw new Exception("No end point found");
        return end;
    }

    /**
     * Gives the height dimension (number of rows) of the graph.
     * @return the number of lines (rows) in the graph
     */
    public int retrieveLines() {
        return lines;
    }

    /**
     * Gives the width dimension (number of columns) of the graph.
     * @return the number of columns in the graph
     */
    public int retrieveColumns() {
        return columns;
    }

    /**
     * Reads and initializes the graph from the input source.
     *
     * @throws IOException if an error occurs during reading
     */
    public abstract void read() throws IOException;
}
