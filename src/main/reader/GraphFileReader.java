package main.reader;

import main.model.WeightedGraph;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GraphFileReader extends GraphReader {
    private final Scanner scanner;

    public GraphFileReader(String path) throws FileNotFoundException {
        super(path);
        this.scanner = new Scanner(file);
    }

    @Override
    public void read() {
        if(!file.exists()) throw new IllegalArgumentException("Cannot read this file : it doesn't exist");

        skip(2);
        this.lines = retrieveInt("nlines");
        this.columns = retrieveInt("ncol");
        skip(1);
        Map<Character, WeightedGraph.Type> types = retrieveTypes();
        System.out.println(types);
        this.weightedGraph = new WeightedGraph();
        setVertices(types);
        setNeighbors();
        skip(2);
        this.start = retrieveVertex(weightedGraph, columns, "Start");
        this.end = retrieveVertex(weightedGraph, columns, "Finish");
    }

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

    private int retrieveInt(String key) {
        String[] args = scanner.nextLine().split("=");
        String keyFound = args[0];
        String value = args.length > 1 ? args[1] : null;
        if(!keyFound.equals(key)) throw new IllegalArgumentException("Incorrect key found : " + keyFound);
        if(value == null) throw new IllegalArgumentException("No value found after " + keyFound);

        return Integer.parseInt(value);
    }

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

    private void setVertices(Map<Character, WeightedGraph.Type> types) {
        for (int line=0; line < lines; line++) {
            String c = scanner.next();
            for (int col=0; col < columns; col++) {
                weightedGraph.addVertex(types.get(c.charAt(col)));
            }
        }
    }

    private void skip(int lines) {
        while(lines > 0) {
            scanner.nextLine();
            lines--;
        }
    }
}
