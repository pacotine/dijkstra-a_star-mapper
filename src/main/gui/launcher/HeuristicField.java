package main.gui.launcher;

import main.instances.Heuristic;

public class HeuristicField extends Field<Heuristic> {
    private final Launcher.MapArgument mapType;
    public HeuristicField(Launcher.MapArgument mapType, Heuristic value) {
        super(value);
        this.mapType = mapType;
    }

    public HeuristicField(Launcher.MapArgument mapType) { //auto choose heuristic based on map type
        this(mapType, auto(mapType));
    }

    private static Heuristic auto(Launcher.MapArgument mapType) {
        return switch(mapType) {
            case IMAGE -> Heuristic.MANHATTAN;
            case CONFIG -> Heuristic.CHEBYSHEV;
        };
    }

    @Override
    public boolean isValueValid() {
        return switch(mapType) {
            case IMAGE -> value == Heuristic.MANHATTAN;
            case CONFIG -> value == Heuristic.CHEBYSHEV || value == Heuristic.EUCLIDEAN || value == Heuristic.OCTILE;
        };
    }
}
