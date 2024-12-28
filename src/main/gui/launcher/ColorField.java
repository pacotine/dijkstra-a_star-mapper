package main.gui.launcher;

import java.awt.*;

/**
 * A {@link Field} representing a hexadecimal color value.
 * The value must be a valid hexadecimal color code.
 */
public class ColorField extends Field<String> {
    /**
     * Constructs a {@link ColorField} with the specified value.
     *
     * @param value the hexadecimal color value ({@link String} representation)
     */
    public ColorField(String value) {
        super(value);
    }

    /**
     * Validates the hexadecimal color value.
     *
     * @return {@code true} if the value is a valid hexadecimal color code, {@code false} otherwise
     */
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
