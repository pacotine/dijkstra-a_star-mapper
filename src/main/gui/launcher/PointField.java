package main.gui.launcher;

public class PointField extends Field<Integer> {


    public PointField(String value) {
        super(Integer.parseInt(value));
    }

    @Override
    public boolean isValueValid() {
        return value >= 0;
    }
}
