package instances;

import model.WeightedGraph;

import java.util.ArrayList;
import java.util.List;

public class DijkstraInstance extends PathFinderInstance {
    public DijkstraInstance(WeightedGraph graph) {
        super(graph);
    }

    @Override
    public double searchPath(WeightedGraph.Vertex start, WeightedGraph.Vertex end) {
        delays.clear();
        path.clear();

        List<WeightedGraph.Vertex> Q = new ArrayList<>();

        for(WeightedGraph.Vertex v : graph.getVertices()) {
            v.setTimeFromSource(Double.POSITIVE_INFINITY);
            v.setPrevious(null);
            Q.add(v);
        }
        start.setTimeFromSource(0);
        int i = 0;
        while(Q.contains(end)) {
            WeightedGraph.Vertex u = findMin(Q);
            delays.put(i, u);
            //System.out.println("min : " + u);
            Q.remove(u);
            for(WeightedGraph.Vertex neighbor : u.getNeighbors()) {
                double neighborTime = neighbor.getTimeFromSource();
                double currentTime = u.getTimeFromSource();
                double weight = (double)(neighbor.getType().value() + u.getType().value())/2;
                if(neighborTime > currentTime + weight) {
                    //System.out.println("update : (" + neighbor.getType().value() + "+" + u.getType().value() + ")/2 = " + weight);
                    neighbor.setTimeFromSource(currentTime+weight);
                    neighbor.setPrevious(u);
                }
            }
            i++;
        }

        retrievePath(start, end);
        return end.getTimeFromSource();
    }

    private static WeightedGraph.Vertex findMin(List<WeightedGraph.Vertex> Q) {
        double distanceMin = Double.POSITIVE_INFINITY;
        WeightedGraph.Vertex min = null;
        for(WeightedGraph.Vertex s : Q) {
            if(s.getTimeFromSource() < distanceMin) {
                distanceMin = s.getTimeFromSource();
                min = s;
            }
        }

        return min;
    }
}
