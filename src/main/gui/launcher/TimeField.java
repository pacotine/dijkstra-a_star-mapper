package main.gui.launcher;

/**
 * A {@code Field} representing a time value.
 * The value must be within a specified range.
 */
public class TimeField extends Field<Integer> {
    private final int min, max;

    /**
     * Constructs a {@link TimeField} with the specified value, minimum, and maximum limits.
     *
     * @param value the time value
     * @param min   the minimum valid value
     * @param max   the maximum valid value
     */
    public TimeField(int value, int min, int max) {
        super(value);
        this.min = min;
        this.max = max;
    }

    /**
     * Constructs a {@link TimeField} by parsing the specified {@link String} value,
     * and using the given minimum and maximum limits.
     *
     * @param value the {@link String} representation of the time value (an integer)
     * @param min   the minimum valid value
     * @param max   the maximum valid value
     */
    public TimeField(String value, int min, int max) {
        this(Integer.parseInt(value), min, max);
    }

    /**
     * Validates the time value.
     *
     * @return {@code true} if the time value is within the range [min, max], {@code false} otherwise
     */
    @Override
    public boolean isValueValid() {
        return value >= min && value <= max;
    }
}
