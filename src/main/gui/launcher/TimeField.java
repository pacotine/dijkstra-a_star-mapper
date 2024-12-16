package main.gui.launcher;

public class TimeField extends Field<Integer> {
    private final int min, max;
    public TimeField(int value, int min, int max) {
        super(value);
        this.min = min;
        this.max = max;
    }

    public TimeField(String value, int min, int max) {
        this(Integer.parseInt(value), min, max);
    }

    @Override
    public boolean isValueValid() {
        return value >= min && value <= max;
    }
}
