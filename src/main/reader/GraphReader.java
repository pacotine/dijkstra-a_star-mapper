package main.reader;

import main.model.WeightedGraph;

import java.io.File;
import java.io.IOException;

public abstract class GraphReader {
    protected final File file;
    protected WeightedGraph weightedGraph;
    protected WeightedGraph.Vertex start;
    protected WeightedGraph.Vertex end;
    protected int lines;
    protected int columns;

    public GraphReader(String path) {
        this.file = new File(path);
        this.weightedGraph = null;
        this.start = null;
        this.end = null;
    }

    public WeightedGraph retrieveGraph() throws Exception {
        if(weightedGraph == null) throw new Exception("File should be read before retrieving the graph");
        return weightedGraph;
    }

    public WeightedGraph.Vertex retrieveStart() throws Exception {
        if(start == null) throw new Exception("No start point found");
        return start;
    }

    public WeightedGraph.Vertex retrieveEnd() throws Exception {
        if(end == null) throw new Exception("No end point found");
        return end;
    }

    public int retrieveLines() {
        return lines;
    }

    public int retrieveColumns() {
        return columns;
    }

    public abstract void read() throws IOException;
}
