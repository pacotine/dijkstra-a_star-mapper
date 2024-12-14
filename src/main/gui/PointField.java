package main.gui;

public class PointField extends Field<Integer> {


    public PointField(Type type, String value) {
        super(type, Integer.parseInt(value));
    }

    @Override
    public boolean isValueValid() {
        return value >= 0;
    }
}
