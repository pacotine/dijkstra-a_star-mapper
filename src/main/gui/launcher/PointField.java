package main.gui.launcher;

/**
 * A {@code Field} representing a point (vertex) in the graph.
 * The value must be a non-negative integer.
 */
public class PointField extends Field<Integer> {

    /**
     * Constructs a {@link PointField} by parsing the specified {@link String} value.
     *
     * @param value the {@link String} representation of the point value (i.e. the position of the vertex in the list of vertices)
     */
    public PointField(String value) {
        super(Integer.parseInt(value));
    }

    /**
     * Validates the point value.
     *
     * @return {@code true} if the point value is non-negative, {@code false} otherwise
     */
    @Override
    public boolean isValueValid() {
        return value >= 0;
    }
}
