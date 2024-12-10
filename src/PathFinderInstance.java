import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PathFinderInstance {
    protected final WeightedGraph graph;
    protected final HashMap<Integer, WeightedGraph.Vertex> delays;
    protected final List<WeightedGraph.Vertex> path;

    protected PathFinderInstance(WeightedGraph graph) {
        this.graph = graph;
        this.delays = new HashMap<>();
        this.path = new ArrayList<>();
    }

    public HashMap<Integer, WeightedGraph.Vertex> getDelays() {
        return delays;
    }

    public List<WeightedGraph.Vertex> getPath() {
        return path;
    }

    protected void retrievePath(WeightedGraph.Vertex start, WeightedGraph.Vertex end) {
        WeightedGraph.Vertex s = end;
        while(s != start) {
            System.out.println(s);
            path.add(s);
            s = s.getPrevious();
        }
        path.add(start);
    }

    protected abstract double searchPath(WeightedGraph.Vertex start, WeightedGraph.Vertex end);
}
