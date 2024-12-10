import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws Exception {
        GraphFileReader graphFileReader = new GraphFileReader("assets/examples/test.txt");
        graphFileReader.read();

        WeightedGraph weightedGraph = graphFileReader.retrieveGraph();
        int lines = graphFileReader.retrieveLines();
        int columns = graphFileReader.retrieveColumns();
        WeightedGraph.Vertex start = graphFileReader.retrieveStart();
        WeightedGraph.Vertex end = graphFileReader.retrieveEnd();

        System.out.println(weightedGraph);
        System.out.println("start : " + start);
        System.out.println("end : " + end);
        System.out.println("map size : " + lines + " x " + columns);

        int pixelSize = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/(columns+10));

        Map map = new Map(weightedGraph, pixelSize, columns, lines, start, end);
        initWindow(map, lines, columns, pixelSize);
        map.repaint();

        //map.showDijkstra();
        map.showAStar();
    }

    private static void initWindow(Map map, int lines, int columns, int pixelSize) {
        JFrame window = new JFrame("Dijkstra VS A*");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(map);
        window.setPreferredSize(new Dimension(columns*pixelSize, lines*pixelSize+40));
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }
}