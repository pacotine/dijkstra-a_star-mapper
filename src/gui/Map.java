package gui;

import instances.AStarInstance;
import instances.DijkstraInstance;
import instances.PathFinderInstance;
import model.WeightedGraph;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;

public class Map extends JComponent {
    private static final int DELAY = 3000; //ms, default: 1000ms
    private static final int TIMER = 70; //ms, default: 10ms
    private static final Color PATH_COLOR = Color.RED; //default: black
    private static final Color CURRENT_VERTEX_COLOR = Color.GREEN; //default: orange
    private static final Color PREVIOUS_PATH_COLOR = Color.ORANGE; //default: black
    private static final Color START_VERTEX_COLOR = Color.RED; //default: white
    private static final Color END_VERTEX_COLOR = Color.BLUE; //default: blue

    private final WeightedGraph graph;
    private final int pixelSize;
    private final int columns;
    private final int lines;
    private final WeightedGraph.Vertex start;
    private final WeightedGraph.Vertex end;

    public Map(WeightedGraph graph, int pixelSize, int columns, int lines, WeightedGraph.Vertex start, WeightedGraph.Vertex end) {
        this.graph = graph;
        this.pixelSize = pixelSize;
        this.columns = columns;
        this.lines = lines;
        this.start = start;
        this.end = end;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.cyan);
        g2.fill(new Rectangle2D.Double(0,0,columns*pixelSize, lines*pixelSize));

        int n = 0;
        for (WeightedGraph.Vertex v : graph.getVertices()) {
            String color = v.getType().color();
            int y = n / columns;
            int x = n % columns;

            //System.out.println(v.getN() + "/" + x + "/" + y + "/" + color);
            switch(color){
                case "green": g2.setPaint(Color.green); break;
                case "gray": g2.setPaint(Color.gray); break;
                case "blue": g2.setPaint(Color.blue); break;
                case "yellow": g2.setPaint(Color.yellow); break;
                case "magenta": g2.setPaint(Color.magenta); break;
                case "red": g2.setPaint(Color.red); break;
                case "pink": g2.setPaint(Color.pink); break;
                case "white": g2.setPaint(Color.white); break;
                default:
                    try {
                        g2.setPaint(Color.decode(color));
                    } catch(NumberFormatException ne) {
                        g2.setPaint(Color.black);
                    }
                    break;
            }
            g2.fill(new Rectangle2D.Double(x*pixelSize, y*pixelSize, pixelSize, pixelSize));

            if(n == start.getN()) {
                g2.setPaint(START_VERTEX_COLOR);
                g2.fill(new Rectangle2D.Double(x*pixelSize+((double)pixelSize/2)/2, y*pixelSize+((double)pixelSize/2)/2,
                        (double)pixelSize/2, (double)pixelSize/2));
            }
            if(n == end.getN()) {
                g2.setPaint(END_VERTEX_COLOR);
                g2.fill(new Rectangle2D.Double(x*pixelSize+((double)pixelSize/2)/2, y*pixelSize+((double)pixelSize/2)/2,
                        (double)pixelSize/2, (double)pixelSize/2));
            }

            n++;
        }
    }

    private void update(WeightedGraph.Vertex current) {
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        int n = current.getN();
        int y = n / columns;
        int x = n % columns;
        g2.setPaint(CURRENT_VERTEX_COLOR);
        double p = (double)pixelSize /2;
        g2.fill(new Ellipse2D.Double(x*pixelSize+p/2, y*pixelSize+p/2, p, p));

        if (current.getTimeFromSource() < Double.POSITIVE_INFINITY) {
            WeightedGraph.Vertex previous = current.getPrevious();
            if (previous != null) {
                int y2 = previous.getN() / columns;
                int x2 = previous.getN() % columns;
                g2.setPaint(PREVIOUS_PATH_COLOR);
                g2.setStroke(new BasicStroke((float)pixelSize/10));
                g2.draw(new Line2D.Double(x*this.pixelSize+p, y*this.pixelSize+p, x2*this.pixelSize+p,y2*this.pixelSize+p));
            }
        }
        this.getToolkit().sync();
    }

    public void showDijkstra() {
        showPathFinder(new DijkstraInstance(graph));
    }

    public void showAStar() {
        showPathFinder(new AStarInstance(graph, columns));
    }

    private void showPathFinder(PathFinderInstance pathFinderInstance) {
        if(this.graph == null) return;

        double pathTime = pathFinderInstance.searchPath(start, end);
        HashMap<Integer, WeightedGraph.Vertex> delays = pathFinderInstance.getDelays();
        List<WeightedGraph.Vertex> path = pathFinderInstance.getPath();

        int p = 0;
        for(int i = 0; i<graph.getVertices().size(); i++) {
            WeightedGraph.Vertex u = delays.get(i);
            if(u != null) {
                Timer dispatch = new Timer(DELAY + i * TIMER, evt -> update(u));
                p++;
                dispatch.setRepeats(false);
                dispatch.start();
            }
        }

        Timer pathDispatch = new Timer(DELAY+p*TIMER, evt -> drawPath(path));
        pathDispatch.setRepeats(false);
        pathDispatch.start();

        System.out.println("Best path in : " + pathTime);
    }

    private void drawPath(List<WeightedGraph.Vertex> path) {
        Graphics2D g2 = (Graphics2D)this.getGraphics();

        WeightedGraph.Vertex p = null;
        g2.setStroke(new BasicStroke((float)pixelSize/5));
        for (WeightedGraph.Vertex cur : path) {
            if (p != null) {
                g2.setPaint(PATH_COLOR);
                int i = p.getN() / columns;
                int j = p.getN() % columns;
                int i2 = cur.getN() / columns;
                int j2 = cur.getN() % columns;
                g2.draw(new Line2D.Double(j * this.pixelSize + (double)this.pixelSize /2,
                        i * this.pixelSize + (double)this.pixelSize /2,
                        j2 * this.pixelSize + (double)this.pixelSize /2,
                        i2 * this.pixelSize + (double)this.pixelSize /2));
            }
            p = cur;
        }
    }
}
