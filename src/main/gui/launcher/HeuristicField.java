package main.gui.launcher;

import main.instances.Heuristic;

/**
 * A {@link Field} representing a heuristic for pathfinding.
 * The heuristic must be compatible with the specified map type.
 */
public class HeuristicField extends Field<Heuristic> {
    private final Launcher.MapArgument mapType;

    /**
     * Constructs a {@link HeuristicField} with the specified map type and heuristic value.
     *
     * @param mapType the map type associated with this heuristic
     * @param value   the heuristic value
     */
    public HeuristicField(Launcher.MapArgument mapType, Heuristic value) {
        super(value);
        this.mapType = mapType;
    }

    /**
     * Constructs a {@link HeuristicField} and automatically selects the heuristic based on the map type.
     *
     * @param mapType the map type associated with this heuristic
     */
    public HeuristicField(Launcher.MapArgument mapType) { //auto choose heuristic based on map type
        this(mapType, auto(mapType));
    }

    /**
     * Automatically selects the heuristic based on the map type.
     *
     * @param mapType the map type
     * @return the appropriate heuristic for the given map type (it's a default value)
     */
    private static Heuristic auto(Launcher.MapArgument mapType) {
        return switch(mapType) {
            case IMAGE -> Heuristic.MANHATTAN;
            case CONFIG -> Heuristic.CHEBYSHEV;
        };
    }

    /**
     * Validates the heuristic value based on the map type.
     *
     * @return {@code true} if the heuristic is compatible with the map type, {@code false} otherwise
     */
    @Override
    public boolean isValueValid() {
        return switch(mapType) {
            case IMAGE -> value == Heuristic.MANHATTAN;
            case CONFIG -> value == Heuristic.CHEBYSHEV || value == Heuristic.EUCLIDEAN || value == Heuristic.OCTILE;
        };
    }
}
