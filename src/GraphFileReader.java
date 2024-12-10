import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GraphFileReader {
    private final File file;
    private final Scanner scanner;
    private WeightedGraph weightedGraph;
    private WeightedGraph.Vertex start;
    private WeightedGraph.Vertex end;
    private int lines;
    private int columns;

    public GraphFileReader(String path) throws FileNotFoundException {
        this.file = new File(path);
        this.scanner = new Scanner(file);
        this.weightedGraph = null;
        this.start = null;
        this.end = null;
    }

    public void read() {
        if(!file.exists()) throw new IllegalArgumentException("Cannot read this file : it doesn't exist");

        WeightedGraph weightedGraph = new WeightedGraph();
        skip(2);
        this.lines = retrieveInt("nlines");
        this.columns = retrieveInt("ncol");
        skip(1);
        Map<Character, WeightedGraph.Type> types = retrieveTypes();
        System.out.println(types);
        setVertices(weightedGraph, lines, columns, types);
        setNeighbors(weightedGraph.getVertices(), lines, columns);
        skip(2);
        this.start = retrieveVertex(weightedGraph, columns, "Start");
        this.end = retrieveVertex(weightedGraph, columns, "Finish");

        this.weightedGraph = weightedGraph;
    }

    public WeightedGraph retrieveGraph() throws Exception {
        if(weightedGraph == null) throw new Exception("File should be read before retrieving the graph");
        return weightedGraph;
    }

    public WeightedGraph.Vertex retrieveStart() throws Exception {
        if(start == null) throw new Exception("File should be read before retrieving this vertex");
        return start;
    }

    public WeightedGraph.Vertex retrieveEnd() throws Exception {
        if(end == null) throw new Exception("File should be read before retrieving this vertex");
        return end;
    }

    public int retrieveLines() {
        return lines;
    }

    public int retrieveColumns() {
        return columns;
    }

    private void setNeighbors(List<WeightedGraph.Vertex> vertices, int lines, int columns) {
        for(int line = 0; line < lines; line++) {
            for(int col = 0; col < columns; col++) {
                int current = line * columns + col;
                WeightedGraph.Vertex v = vertices.get(current);
                if(line < lines-1 && col < columns-1) v.addNeighbor(vertices.get((line+1)*columns+(col+1)));
                if(line < lines-1) v.addNeighbor(vertices.get((line+1)*columns+(col)));
                if(col > 0 && line < lines-1) v.addNeighbor(vertices.get((line+1)*columns+(col-1)));
                if(col < columns-1) v.addNeighbor(vertices.get((line)*columns+(col+1)));
                if(col > 0) v.addNeighbor(vertices.get((line)*columns+(col-1)));
                if(line > 0 && col < columns-1) v.addNeighbor(vertices.get((line-1)*columns+(col+1)));
                if(line > 0 && col > 0) v.addNeighbor(vertices.get((line-1)*columns+(col-1)));
                if(line > 0) v.addNeighbor(vertices.get((line-1)*columns+(col)));
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
            char typeName = args[0].charAt(0);
            String value = args.length > 1 ? args[1] : null;
            if(value == null) throw new IllegalArgumentException("No value found after " + typeName);

            line = scanner.nextLine();
            String color = line;

            types.put(typeName, new WeightedGraph.Type(typeName, Integer.parseInt(value), color));
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

    private void setVertices(WeightedGraph G, int lines, int columns, Map<Character, WeightedGraph.Type> types) {
        for (int line=0; line < lines; line++) {
            String c = scanner.next();
            for (int col=0; col < columns; col++) {
                G.addVertex(types.get(c.charAt(col)));
            }
        }
    }

    private void skip(int lines) {
        while(lines > 0) {
            scanner.nextLine();
            lines--;
        }
    }

    public static boolean isValidHexadecimalColor(String color)
    {
        if(color.charAt(0) != '#')
            return false;

        if(!(color.length() == 4 || color.length() == 7))
            return false;

        for(int i = 1; i < color.length(); i++)
            if (!((color.charAt(i) >= '0' && color.charAt(i) <= 9)
                    || (color.charAt(i) >= 'a' && color.charAt(i) <= 'f')
                    || (color.charAt(i) >= 'A' || color.charAt(i) <= 'F')))
                return false;

        return true;
    }
}
