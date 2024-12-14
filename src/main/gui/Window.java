package main.gui;

import main.model.WeightedGraph;
import main.reader.GraphFileReader;
import main.reader.GraphImageReader;
import main.reader.GraphReader;

import javax.swing.*;
import java.awt.*;

public class Window {
    private int width, height, pixelSize;
    private final ArgumentsInterpreter interpreter;

    public Window(String[] args) {
        this.interpreter = new ArgumentsInterpreter(args);
    }

    public void show() throws Exception {
        Map map = retrieveMap();
        System.out.println("init. map window (" + width + "x" + height + ") of type " + interpreter.getMapType() + " for file '"
                + interpreter.getPath() + "'" + " | " + interpreter.getPathFinderType());

        initWindow(map);
        map.repaint();

        map.display(interpreter.getPathFinderType());
    }

    private Map retrieveMap() throws Exception {
        String path = interpreter.getPath();
        GraphReader graphReader = switch(interpreter.getMapType()) {
            case IMAGE -> new GraphImageReader(path);
            case CONFIG -> new GraphFileReader(path);
        };

        graphReader.read();

        WeightedGraph weightedGraph = graphReader.retrieveGraph();
        int lines = graphReader.retrieveLines();
        this.height = lines;
        int columns = graphReader.retrieveColumns();
        this.width = columns;
        WeightedGraph.Vertex start = graphReader.retrieveStart();
        WeightedGraph.Vertex end = graphReader.retrieveEnd();

        //System.out.println(weightedGraph);
        System.out.println("start : " + start);
        System.out.println("end : " + end);
        System.out.println("map size : " + lines + " x " + columns);
        System.out.println("configuration options: " + interpreter.getOptions());

        int pixelSize = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/(lines > columns ? lines+1 : columns+1));
        this.pixelSize = pixelSize;

        return new Map(weightedGraph, pixelSize, columns, lines, start, end, interpreter.getOptions());
    }

    private void initWindow(Map map) {
        JFrame window = new JFrame("Dijkstra VS A*");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(map);
        window.setPreferredSize(new Dimension(width*pixelSize, height*pixelSize+40));
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }
}
