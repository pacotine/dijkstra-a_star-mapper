package main.gui;

import main.Main;
import main.model.WeightedGraph;
import main.reader.GraphFileReader;
import main.reader.GraphImageReader;
import main.reader.GraphReader;

import javax.swing.*;
import java.awt.*;

public class Window {
    private static final String HELP_MESSAGE = String.format(
            """
            ~ Dijkstra/A* Mapper launcher ~
            ====
            Author: Guilford
            Repository: https://github.com/pacotine/dijkstra-a_star-mapper
            Version: %s
            ====
            
            Usage: java -jar dijkstra-a_star-mapper.jar <map_type> <path> [path_finder] [options]
            
            where:
            
            <map_type> includes
            image           to set the program to image mode (<path> will then be the path to the image file)
            config          to set the program to config mode (<path> will then be the path to the map configuration file)
            
            <path> is the path to the map source file, according to the mode (see <map_type>)
            
            [path_finder] includes
            --dijkstra      use Dijkstra's algorithm on this map
            --a-star        use A* algorithm on this map (this is the default [path_finder] set)
            
            [options] includes
            --no-animation  deactivate search animation before displaying the path found
            """, Main.getVersionFromManifest());

    public enum MapArgument {
        IMAGE("image"),
        CONFIG("config");

        private final String arg;
        MapArgument(String arg) {
            this.arg = arg;
        }

        public String getArg() {
            return arg;
        }

        public static MapArgument of(String arg) {
            for(MapArgument type : MapArgument.values()) {
                if(type.getArg().equalsIgnoreCase(arg)) return type;
            }

            return null;
        }
    }

    public enum PathFinderArgument {
        DIJKSTRA("--dijkstra"),
        A_STAR("--a-star");

        private final String arg;
        PathFinderArgument(String arg) {
            this.arg = arg;
        }

        public String getArg() {
            return arg;
        }

        public static PathFinderArgument of(String arg) {
            for(PathFinderArgument type : PathFinderArgument.values()) {
                if(type.getArg().equalsIgnoreCase(arg)) return type;
            }

            return null;
        }
    }

    private int width, height, pixelSize;
    private MapArgument mapType;
    private String path;
    private PathFinderArgument pathFinderType;
    private boolean showAnimation;

    public Window(String[] args) {
        init(args);
    }

    public void show() throws Exception {
        Map map = retrieveMap();
        System.out.println("init. map window (" + width + "x" + height + ") of type " + mapType + " for file '"
                + path + "'" + " | " + pathFinderType + " (animation : " + showAnimation + ")");

        initWindow(map);
        map.repaint();

        map.display(pathFinderType, showAnimation);
    }

    private Map retrieveMap() throws Exception {
        GraphReader graphReader = switch(mapType) {
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

        int pixelSize = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/(lines > columns ? lines+1 : columns+1));
        this.pixelSize = pixelSize;

        return new Map(weightedGraph, pixelSize, columns, lines, start, end);
    }

    private void init(String[] args) {
        int l = args.length;
        if(l == 1 && args[0].equals("--help")) {
            System.out.println(HELP_MESSAGE);
            System.exit(0);
        }
        try {
            this.mapType = MapArgument.of(args[0]);
            if(mapType == null) throw new IllegalArgumentException("unknown map type argument '" + args[0] + "'");
            this.path = args[1];

            if(l > 2) {
                this.pathFinderType = PathFinderArgument.of(args[2]);
                if(pathFinderType == null) throw new IllegalArgumentException("unknown path finder argument '" + args[2] + "'");
            }
            else this.pathFinderType = PathFinderArgument.A_STAR; //default

            if(l > 3) {
                if(args[3].equals("--no-animation")) this.showAnimation = false;
                else throw new IllegalArgumentException("unknown animation argument '" + args[3] + "'");
            } else this.showAnimation = true;
        } catch(ArrayIndexOutOfBoundsException ai) {
            System.err.println("missing argument");
            System.out.println();
        }
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
