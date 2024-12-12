import gui.Map;
import model.WeightedGraph;
import reader.GraphFileReader;
import reader.GraphImageReader;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws Exception {
        //GraphFileReader graphReader = new GraphFileReader("assets/examples/heart.txt");
        GraphImageReader graphReader = new GraphImageReader("assets/examples/mona.png");
        graphReader.read();

        WeightedGraph weightedGraph = graphReader.retrieveGraph();
        int lines = graphReader.retrieveLines();
        int columns = graphReader.retrieveColumns();
        WeightedGraph.Vertex start = graphReader.retrieveStart();
        WeightedGraph.Vertex end = graphReader.retrieveEnd();

        System.out.println(weightedGraph);
        System.out.println("start : " + start);
        System.out.println("end : " + end);
        System.out.println("map size : " + lines + " x " + columns);

        int pixelSize = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/(lines > columns ? lines+1 : columns+1));

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