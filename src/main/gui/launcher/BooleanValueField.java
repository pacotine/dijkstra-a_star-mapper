package main.gui.launcher;

public class BooleanValueField extends Field<Boolean>  {

    public BooleanValueField(boolean value) {
        super(value);
    }

    @Override
    public boolean isValueValid() {
        return true;
    }
}
