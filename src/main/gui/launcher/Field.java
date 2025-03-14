package main.gui.launcher;

import java.util.Set;
import java.util.stream.Collectors;

import static main.gui.launcher.Launcher.ALL_MAP_TYPES;
import static main.gui.launcher.Launcher.ALL_PATH_FINDERS;

/**
 * Represents a generic field used for configuration (see {@link Configuration}) in the application.
 * Each field is parameterized with a type {@code T} and is associated with a value.
 * Specific field types are defined by the nested {@link Type} enumeration.
 *
 * @param <T> the type of the value held by this field
 */
public abstract class Field<T> {

    /**
     * Enumeration representing the types of configuration fields.
     * Each type is associated with a command-line argument, valid map types, and valid pathfinder algorithms.
     */
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
        SHOW_ANIMATION("--no-animation", ALL_MAP_TYPES),
        VERBOSE("--verbose", ALL_MAP_TYPES);

        private final String arg;
        private final Set<Launcher.MapArgument> types;
        private final Set<Launcher.PathFinderArgument> pathFinders;

        /**
         * Constructs a {@link Type} instance.
         *
         * @param arg         the command-line argument associated with this type
         * @param types       the valid map types for this field
         * @param pathFinders the valid pathfinder algorithms for this field (optional, defaults to all pathfinders)
         */
        Type(String arg, Launcher.MapArgument[] types, Launcher.PathFinderArgument... pathFinders) {
            this.arg = arg;
            this.types = Set.of(types);
            if(pathFinders.length == 0) this.pathFinders = Set.of(ALL_PATH_FINDERS);
            else this.pathFinders = Set.of(pathFinders);
        }

        /**
         * Constructs a {@link Type} instance with a single map type.
         *
         * @param arg         the command-line argument associated with this type
         * @param type        the single valid map type for this field
         * @param pathFinders the valid pathfinder algorithms for this field (optional, defaults to all pathfinders)
         */
        Type(String arg, Launcher.MapArgument type, Launcher.PathFinderArgument... pathFinders) {
            this(arg, new Launcher.MapArgument[]{type}, pathFinders);
        }

        /**
         * Resolves the {@link Type} based on the provided command-line argument, map type, and pathfinder algorithm.
         *
         * @param arg        the command-line argument to resolve
         * @param type       the map type
         * @param pathFinder the pathfinder algorithm
         * @return the matching {@link Type}
         * @throws IllegalArgumentException if the argument is invalid for the given map type or pathfinder algorithm
         */
        public static Type of(String arg, Launcher.MapArgument type, Launcher.PathFinderArgument pathFinder) {
            for(Type option : Type.values()) {
                if(option.getArg().equalsIgnoreCase(arg)) {
                    if(!option.getTypes().contains(type))
                        throw new IllegalArgumentException(
                                "invalid option '" + arg + "' for map type argument '" + type + "'"
                                        + "\nthis option is valid for: " +
                                        option.getTypes().stream().map(Launcher.MapArgument::getArg).collect(Collectors.joining(",")
                                        ));
                    if(!option.getPathFinders().contains(pathFinder))
                        throw new IllegalArgumentException(
                                "invalid option '" + arg + "' for pathfinder algorithm argument '" + pathFinder + "'"
                                        + "\nthis options is valid for: " +
                                        option.getPathFinders().stream().map(Launcher.PathFinderArgument::getArg).collect(Collectors.joining(",")
                                        ));
                    return option;
                }
            }
            throw new IllegalArgumentException("unknown option '" + arg + "'");
        }

        /**
         * Retrieves the command-line argument for this type.
         *
         * @return the command-line argument
         */
        public String getArg() {
            return arg;
        }

        /**
         * Retrieves the valid pathfinder algorithms for this type.
         *
         * @return a {@link Set} of valid pathfinder algorithms
         */
        public Set<Launcher.PathFinderArgument> getPathFinders() {
            return pathFinders;
        }

        /**
         * Retrieves the valid map types for this type.
         *
         * @return a {@link Set} of valid map types
         */
        public Set<Launcher.MapArgument> getTypes() {
            return types;
        }
    }

    protected final T value;

    /**
     * Constructs a {@link Field} with the specified value.
     *
     * @param value the value to associate with this field
     */
    public Field(T value) {
        this.value = value;
    }

    /**
     * Retrieves the value of this field.
     *
     * @return the value of type {@code T}
     */
    public T getValue() {
        return value;
    }

    /**
     * Validates the value associated with this field.
     *
     * @return {@code true} if the value is valid, {@code false} otherwise
     */
    public abstract boolean isValueValid();

    /**
     * Returns a {@link String} representation of this field.
     *
     * @return a {@link String} representing the field's value
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
