package main.gui.launcher;

public class BooleanValueField extends Field<Boolean> {

    public BooleanValueField(Type type, boolean value) {
        super(type, value);
    }

    @Override
    public boolean isValueValid() {
        return true;
    }
}
