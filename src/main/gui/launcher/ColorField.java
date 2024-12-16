package main.gui.launcher;

import java.awt.*;

public class ColorField extends Field<String> {
    public ColorField(String value) {
        super(value);
    }

    @Override
    public boolean isValueValid() {
        try {
            Color.decode(value);
            return true;
        } catch(NumberFormatException ne) {
            return false;
        }
    }
}
