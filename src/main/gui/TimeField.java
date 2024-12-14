package main.gui;

public class TimeField extends Field<Integer> {
    private int min, max;
    public TimeField(Type type, int value, int min, int max) {
        super(type, value);
        this.min = min;
        this.max = max;
    }

    public TimeField(Type type, String value, int min, int max) {
        this(type, Integer.parseInt(value), min, max);
    }

    @Override
    public boolean isValueValid() {
        return value >= min && value <= max;
    }
}
