package main.gui;

import main.gui.launcher.*;
import main.instances.AStarInstance;
import main.instances.DijkstraInstance;
import main.instances.Heuristic;
import main.instances.PathFinderInstance;
import main.model.WeightedGraph;

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

/**
 * This represents a graphical component used to visualize a graph,
 * render pathfinding algorithms, and animate their execution. It manages the graphical
 * representation of vertices, edges, and the optimal path between two vertices.
 */
public class Map extends JComponent {
    private final WeightedGraph graph;
    private final int pixelSize;
    private final int columns;
    private final int lines;

    private WeightedGraph.Vertex start;
    private WeightedGraph.Vertex end;
    private int delay;
    private int timer;
    private Color pathColor;
    private Color currentVertexColor;
    private Color previousPathColor;
    private Color startVertexColor;
    private Color endVertexColor;
    private boolean showAnimation;
    private boolean verbose;
    private final Configuration configuration;

    /**
     * Constructs a new {@code Map} instance to display the graph and perform pathfinding.
     *
     * @param graph         the {@link WeightedGraph} to be visualized
     * @param pixelSize     the size of each graphical cell (in pixels)
     * @param columns       the number of columns in the map grid
     * @param lines         the number of rows in the map grid
     * @param start         the starting vertex for the pathfinding algorithm
     * @param end           the target vertex for the pathfinding algorithm
     * @param configuration the configuration settings for the visualization, including colors and delays (see {@link Configuration})
     */
    public Map(WeightedGraph graph, int pixelSize, int columns, int lines,
               WeightedGraph.Vertex start, WeightedGraph.Vertex end,
               Configuration configuration) {
        this.graph = graph;
        this.pixelSize = pixelSize;
        this.columns = columns;
        this.lines = lines;
        this.start = start;
        this.end = end;
        this.configuration = configuration;
        setConfig();
    }

    /**
     * Paints the graph on the {@code Map} component, rendering vertices, edges,
     * and highlighting the start and end points.
     *
     * @param g the {@link Graphics} object used for drawing the component
     */
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
                g2.setPaint(startVertexColor);
                g2.fill(new Rectangle2D.Double(x*pixelSize+((double)pixelSize/2)/2, y*pixelSize+((double)pixelSize/2)/2,
                        (double)pixelSize/2, (double)pixelSize/2));
            }
            if(n == end.getN()) {
                g2.setPaint(endVertexColor);
                g2.fill(new Rectangle2D.Double(x*pixelSize+((double)pixelSize/2)/2, y*pixelSize+((double)pixelSize/2)/2,
                        (double)pixelSize/2, (double)pixelSize/2));
            }

            n++;
        }
    }

    /**
     * Updates the graphical representation of a vertex during pathfinding animation.
     *
     * @param current the current vertex being processed
     */
    private void update(WeightedGraph.Vertex current) {
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        int n = current.getN();
        int y = n / columns;
        int x = n % columns;
        g2.setPaint(currentVertexColor);
        double p = (double)pixelSize /2;
        g2.fill(new Ellipse2D.Double(x*pixelSize+p/2, y*pixelSize+p/2, p, p));

        if (current.getTimeFromSource() < Double.POSITIVE_INFINITY) {
            WeightedGraph.Vertex previous = current.getPrevious();
            if (previous != null) {
                int y2 = previous.getN() / columns;
                int x2 = previous.getN() % columns;
                g2.setPaint(previousPathColor);
                g2.setStroke(new BasicStroke((float)pixelSize/10));
                g2.draw(new Line2D.Double(x*this.pixelSize+p, y*this.pixelSize+p, x2*this.pixelSize+p,y2*this.pixelSize+p));
            }
        }
        this.getToolkit().sync();
    }

    /**
     * Executes and displays the pathfinding algorithm specified in the launcher configuration.
     *
     * @param pathFinderType the type of pathfinding algorithm to execute (A*, Dijkstra, etc.)
     * @see main.gui.launcher.Launcher.PathFinderArgument
     */
    public void display(Launcher.PathFinderArgument pathFinderType) {
        switch(pathFinderType) {
            case A_STAR -> showPathFinder(new AStarInstance(graph, columns, (Heuristic) configuration.get(Field.Type.HEURISTIC).getValue()));
            case DIJKSTRA -> showPathFinder(new DijkstraInstance(graph));
        }
    }

    /**
     * Configures the visual properties of the map and pathfinding animation based on the settings
     * in the {@link Configuration}.
     */
    private void setConfig() {
        String currentVertexCode = (String) configuration.get(Field.Type.CURRENT_VERTEX_COLOR).getValue();
        String previousVertexCode = (String) configuration.get(Field.Type.PREVIOUS_PATH_COLOR).getValue();
        String startVertexCode = (String) configuration.get(Field.Type.START_VERTEX_COLOR).getValue();
        String endVertexCode = (String) configuration.get(Field.Type.END_VERTEX_COLOR).getValue();
        String pathCode = (String) configuration.get(Field.Type.PATH_COLOR).getValue();

        this.currentVertexColor = Color.decode(currentVertexCode);
        this.previousPathColor = Color.decode(previousVertexCode);
        this.startVertexColor = Color.decode(startVertexCode);
        this.endVertexColor = Color.decode(endVertexCode);
        this.pathColor = Color.decode(pathCode);

        this.delay = (int) configuration.get(Field.Type.DELAY).getValue();
        this.timer = (int) configuration.get(Field.Type.TIME).getValue();

        this.showAnimation = (boolean) configuration.get(Field.Type.SHOW_ANIMATION).getValue();
        this.verbose = (boolean) configuration.get(Field.Type.VERBOSE).getValue();

        PointField startField = (PointField) configuration.get(Field.Type.START);
        if(startField != null) this.start = this.graph.getVertices().get(startField.getValue());
        PointField endField = (PointField) configuration.get(Field.Type.END);
        if(endField != null) this.end = this.graph.getVertices().get(endField.getValue());
    }

    /**
     * Displays the result of the pathfinding algorithm and optionally animates the processing.
     *
     * @param pathFinderInstance the instance of the pathfinding algorithm
     */
    private void showPathFinder(PathFinderInstance pathFinderInstance) {
        if(this.graph == null) return;

        double pathTime = pathFinderInstance.searchPath(start, end, verbose);
        HashMap<Integer, WeightedGraph.Vertex> delays = pathFinderInstance.getDelays();
        List<WeightedGraph.Vertex> path = pathFinderInstance.getPath();

        int p = 0;
        if(showAnimation) {
            for (int i = 0; i < graph.getVertices().size(); i++) {
                WeightedGraph.Vertex u = delays.get(i);
                if (u != null) {
                    Timer dispatch = new Timer(delay + i * timer, evt -> update(u));
                    p++;
                    dispatch.setRepeats(false);
                    dispatch.start();
                }
            }
        }

        Timer pathDispatch = new Timer(delay+p*timer, evt -> drawPath(path));
        pathDispatch.setRepeats(false);
        pathDispatch.start();

        System.out.println("Best path in : " + pathTime);
    }

    /**
     * Draws the shortest path determined by the pathfinding algorithm.
     *
     * @param path a list of vertices representing the optimal path
     */
    private void drawPath(List<WeightedGraph.Vertex> path) {
        Graphics2D g2 = (Graphics2D)this.getGraphics();

        WeightedGraph.Vertex p = null;
        g2.setStroke(new BasicStroke((float)pixelSize/5));
        for (WeightedGraph.Vertex cur : path) {
            if (p != null) {
                g2.setPaint(pathColor);
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
