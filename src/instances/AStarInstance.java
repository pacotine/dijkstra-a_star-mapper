package instances;

import model.WeightedGraph;

import java.util.*;

public class AStarInstance extends PathFinderInstance {
    private final int mapSize;
    public AStarInstance(WeightedGraph graph, int mapSize) {
        super(graph);
        this.mapSize = mapSize;
    }

    @Override
    public double searchPath(WeightedGraph.Vertex start, WeightedGraph.Vertex end) {
        delays.clear();
        path.clear();

        List<WeightedGraph.Vertex> open = new ArrayList<>();
        open.add(start);

        HashMap<WeightedGraph.Vertex, Double> f = new HashMap<>();
        for(WeightedGraph.Vertex v : graph.getVertices()) {
            f.put(v, Double.POSITIVE_INFINITY);
            v.setTimeFromSource(Double.POSITIVE_INFINITY);
        }
        start.setTimeFromSource(0.0);
        f.replace(start, 0.0);

        int i = 0;
        while(!open.contains(end)) {
            WeightedGraph.Vertex u = findMinF(open, f);
            delays.put(i, u);
            open.remove(u);
            for(WeightedGraph.Vertex neighbor : u.getNeighbors()) {
                int x1 = u.getN() % mapSize;
                int y1 = u.getN() / mapSize;
                int x2 = end.getN() % mapSize;
                int y2 = end.getN() / mapSize;
                double dist = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
                double factor = u.getDiagonalNeighbors().contains(neighbor) ? Math.sqrt(2) : 2.0;
                double weight = (double)(neighbor.getType().value() + u.getType().value())/factor;

                double tentative = u.getTimeFromSource() + weight;
                if(tentative < neighbor.getTimeFromSource()) {
                    neighbor.setPrevious(u);
                    neighbor.setTimeFromSource(tentative);
                    f.replace(neighbor, tentative+dist);
                    if(!open.contains(neighbor)) open.add(neighbor);
                }
            }
            i++;
        }

        retrievePath(start, end);
        return end.getTimeFromSource();
    }

    private static WeightedGraph.Vertex findMinF(List<WeightedGraph.Vertex> open, HashMap<WeightedGraph.Vertex, Double> f) {
        double distanceMin = Double.POSITIVE_INFINITY;
        WeightedGraph.Vertex min = null;
        for(WeightedGraph.Vertex s : open) {
            if(f.get(s) < distanceMin) {
                distanceMin = f.get(s);
                min = s;
            }
        }

        return min;
    }


}
