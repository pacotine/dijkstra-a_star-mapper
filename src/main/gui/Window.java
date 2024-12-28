package main.gui;

import main.gui.launcher.Launcher;
import main.model.WeightedGraph;
import main.reader.GraphFileReader;
import main.reader.GraphImageReader;
import main.reader.GraphReader;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for initializing and displaying the graphical user interface
 * for the map visualization. It sets up the application window, processes map data, and integrates the
 * selected pathfinding algorithm and configuration.
 */
public class Window {
    private int width, height, pixelSize;
    private final Launcher launcher;

    /**
     * Constructs a new {@link Window} instance and initializes it with the provided arguments.
     *
     * @param args an array of command-line arguments passed to configure the application
     *             (these arguments are processed by the {@link Launcher})
     */
    public Window(String[] args) {
        this.launcher = new Launcher(args);
    }

    /**
     * Displays the main application window, processes the map data, and applies the selected
     * pathfinding algorithm.
     *
     * @throws Exception if an error occurs while retrieving or processing the map data
     */
    public void show() throws Exception {
        Map map = retrieveMap();
        System.out.println("init. map window (" + width + "x" + height + ") of type " + launcher.getMapType() + " for file '"
                + launcher.getPath() + "'" + " | " + launcher.getPathFinderType());

        initWindow(map);
        map.repaint();

        map.display(launcher.getPathFinderType());
    }

    /**
     * Retrieves the map data based on the configuration specified in the {@link Launcher}.
     * This includes reading the map (configuration file or image), creating a graph representation, and setting
     * initial and target vertices for pathfinding.
     *
     * @return a {@link Map} object initialized with the graph data, dimensions, and configuration
     * @throws Exception if the map file is invalid or cannot be read
     */
    private Map retrieveMap() throws Exception {
        String path = launcher.getPath();
        GraphReader graphReader = switch(launcher.getMapType()) {
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
        System.out.println("configuration: " + launcher.getConfiguration());

        int pixelSize = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/(lines > columns ? lines+1 : columns+1));
        this.pixelSize = pixelSize;

        return new Map(weightedGraph, pixelSize, columns, lines, start, end, launcher.getConfiguration());
    }

    /**
     * Initializes the main application window and attaches the map panel to it.
     *
     * @param map the {@link Map} object to be displayed in the window
     */
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
