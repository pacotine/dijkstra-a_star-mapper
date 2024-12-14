package main.gui.launcher;

import java.util.Set;
import java.util.stream.Collectors;

import static main.gui.launcher.Launcher.ALL_MAP_TYPES;
import static main.gui.launcher.Launcher.ALL_PATH_FINDERS;

public abstract class Field<T> {
    public enum Type {
        START("--start", Launcher.MapArgument.IMAGE),
        END("--end", Launcher.MapArgument.IMAGE),
        HEURISTIC("--heuristic", ALL_MAP_TYPES, Launcher.PathFinderArgument.A_STAR),
        TIME("--time", ALL_MAP_TYPES),
        DELAY("--delay", ALL_MAP_TYPES),
        START_VERTEX_COLOR("--start-color", ALL_MAP_TYPES),
        END_VERTEX_COLOR("--end-color", ALL_MAP_TYPES),
        PREVIOUS_PATH_COLOR("--previous-color", ALL_MAP_TYPES),
        CURRENT_VERTEX_COLOR("--current-color", ALL_MAP_TYPES),
        PATH_COLOR("--path-color", ALL_MAP_TYPES),
        SHOW_ANIMATION("--no-animation", ALL_MAP_TYPES);

        private final String arg;
        private final Set<Launcher.MapArgument> types;
        private final Set<Launcher.PathFinderArgument> pathFinders;

        Type(String arg, Launcher.MapArgument[] types, Launcher.PathFinderArgument... pathFinders) {
            this.arg = arg;
            this.types = Set.of(types);
            if(pathFinders.length == 0) this.pathFinders = Set.of(ALL_PATH_FINDERS);
            else this.pathFinders = Set.of(pathFinders);
        }
        Type(String arg, Launcher.MapArgument type, Launcher.PathFinderArgument... pathFinders) {
            this(arg, new Launcher.MapArgument[]{type}, pathFinders);
        }

        public static Type of(String arg, Launcher.MapArgument type, Launcher.PathFinderArgument pathFinder) {
            for(Type option : Type.values()) {
                if(option.getArg().equalsIgnoreCase(arg)) {
                    if(!option.getTypes().contains(type))
                        throw new IllegalArgumentException(
                                "invalid option '" + arg + "' for map type argument '" + type + "'"
                                        + "\nvalid options are: " +
                                        option.getTypes().stream().map(Launcher.MapArgument::getArg).collect(Collectors.joining(",")
                                        ));
                    if(!option.getPathFinders().contains(pathFinder))
                        throw new IllegalArgumentException(
                                "invalid option '" + arg + "' for pathfinder algorithm argument '" + type + "'"
                                        + "\nvalid options are: " +
                                        option.getPathFinders().stream().map(Launcher.PathFinderArgument::getArg).collect(Collectors.joining(",")
                                        ));
                    return option;
                }
            }
            throw new IllegalArgumentException("unknown option '" + arg + "'");
        }

        public String getArg() {
            return arg;
        }

        public Set<Launcher.PathFinderArgument> getPathFinders() {
            return pathFinders;
        }

        public Set<Launcher.MapArgument> getTypes() {
            return types;
        }
    }

    protected final T value;
    private final Type type;

    public Field(Type type, T value) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public abstract boolean isValueValid();

    @Override
    public String toString() {
        return value.toString();
    }
}
