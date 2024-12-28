package main.gui.launcher;

/**
 * A {@link Field} representing a boolean value.
 * The value is always valid (as it's not associated to a specific argument value).
 * You can see this field as a switch field.
 */
public class BooleanValueField extends Field<Boolean>  {

    /**
     * Constructs a {@link BooleanValueField} with the specified value.
     *
     * @param value the boolean value
     */
    public BooleanValueField(boolean value) {
        super(value);
    }

    /**
     * Validates the boolean value.
     *
     * @return {@code true} always, since all boolean values are valid
     */
    @Override
    public boolean isValueValid() {
        return true;
    }
}
