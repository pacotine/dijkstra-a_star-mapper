package main.gui.launcher;

import main.Main;

import java.util.HashMap;
import java.util.Map;

public class Launcher {
    public static final MapArgument[] ALL_MAP_TYPES = MapArgument.values();
    public static final PathFinderArgument[] ALL_PATH_FINDERS = PathFinderArgument.values();
    private static final String HELP_MESSAGE = String.format(
            """
            ~ Dijkstra/A* Mapper launcher ~
            ====
            Author: Guilford
            Repository: https://github.com/pacotine/dijkstra-a_star-mapper
            Version: %s
            ====
            
            Usage: java -jar dijkstra-a_star-mapper.jar <map_type> <path_finder_algorithm> <path> [options]
            
            where:
            
            <map_type> includes
            image               to set the program to image mode (<path> will then be the path to the image file)
            config              to set the program to config mode (<path> will then be the path to the map configuration file)
            
            <path_finder_algorithm> includes
            dijkstra            use Dijkstra's algorithm on this map
            a-star              use A* algorithm on this map
            
            <path> is the path to the map source file, according to the mode (see <map_type>)
            
            [options] includes
            --start             <point>     define the starting point, where <point> is a positive integer representing the <point>-th vertex of the graph | default: 0 for <map_type>=a-star, undefined otherwise
            
            --end               <point>     define the finish point, where <point> is a positive integer representing the <point>-th vertex of the graph | default: last vertex for <map-type>=a-star, undefined otherwise
            
            --time              <time>      set the execution time between each vertex (<time> minimum value: 1, maximum value: 20000) | default: 10ms
            
            --delay             <time>      set the delay before the display begins (<time> minimum value: 0, maximum value: 60000) | default: 2000ms
            
            --start-color       <color>     set the starting point vertex color to <color>, where <color> is a hexadecimal color code | default: #FF194F
            
            --end-color         <color>     set the finish point vertex color to <color>, where <color> is a hexadecimal color code | default: #19A3FF
            
            --previous-color    <color>     set the previous path color to <color> for the animation, where <color> is a hexadecimal color code | default: #FF9C19
            
            --current-color     <color>     set the current vertex color to <color> for the animation, where <color> is a hexadecimal color code | default: #8E09DB
            
            --path-color        <color>     set the (final) path color to <color>, where <color> is a hexadecimal color code | default: #FF19A7
            
            
            --no-animation                  deactivate search animation before displaying the path found
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

            throw new IllegalArgumentException("unknown map type argument '" + arg + "'");
        }
    }

    public enum PathFinderArgument {
        DIJKSTRA("dijkstra"),
        A_STAR("a-star");

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

            throw new IllegalArgumentException("unknown pathfinder argument '" + arg + "'");
        }
    }

    private MapArgument mapType;
    private String path;
    private PathFinderArgument pathFinderType;
    private Map<Field.Type, Field<?>> options;

    public Launcher(String[] args) {
        init(args);
    }

    private void init(String[] args) {
        int l = args.length;
        if(l == 1 && args[0].equals("--help")) {
            System.out.println(HELP_MESSAGE);
            System.exit(0);
        }
        int i = 2;
        try {
            this.mapType = MapArgument.of(args[0]);
            this.pathFinderType = PathFinderArgument.of(args[1]);
            this.path = args[2];
            this.options = new HashMap<>();

            while(i+1 < l) {
                Field.Type type = Field.Type.of(args[++i], mapType, pathFinderType);

                Field<?> field = switch(type) {
                    case START, END -> new PointField(type, args[++i]);
                    case HEURISTIC -> null; //TODO
                    case TIME -> new TimeField(type, args[++i], 1, 20_000);
                    case DELAY -> new TimeField(type, args[++i], 0, 60_000);
                    case START_VERTEX_COLOR,
                         END_VERTEX_COLOR,
                         PREVIOUS_PATH_COLOR,
                         CURRENT_VERTEX_COLOR,
                         PATH_COLOR -> new ColorField(type, args[++i]);
                    case SHOW_ANIMATION -> new BooleanValueField(type, false);
                };
                if(field.isValueValid()) options.put(type, field);
                else throw new IllegalArgumentException("value '" + field.getValue() + "' is invalid for option '" + type + "'");
            }
        } catch(ArrayIndexOutOfBoundsException ai) {
            System.err.println("missing argument after " + args[i-1]);
            System.out.println("\nNeed help? Use --help to display the documentation.");
            System.exit(0);
        }
    }

    public MapArgument getMapType() {
        return mapType;
    }

    public String getPath() {
        return path;
    }

    public PathFinderArgument getPathFinderType() {
        return pathFinderType;
    }

    public Map<Field.Type, Field<?>> getOptions() {
        return options;
    }
}
