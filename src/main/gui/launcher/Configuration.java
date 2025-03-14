package main.gui.launcher;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the configuration settings for the application.
 * This class stores various customizable fields, such as delays, colors, and animation options,
 * with default values initialized during construction.
 * The configuration allows updates to individual fields while maintaining type safety.
 */
public class Configuration {
    private static final TimeField DEFAULT_DELAY = new TimeField(2000, 0, 10_000); //ms
    private static final TimeField DEFAULT_TIMER = new TimeField(10, 1, 20_000); //ms
    private static final ColorField DEFAULT_PATH_COLOR = new ColorField("#FF19A7");
    private static final ColorField DEFAULT_CURRENT_VERTEX_COLOR = new ColorField("#8E09DB");
    private static final ColorField DEFAULT_PREVIOUS_PATH_COLOR = new ColorField("#FF9C19");
    private static final ColorField DEFAULT_START_VERTEX_COLOR = new ColorField("#FF194F");
    private static final ColorField DEFAULT_END_VERTEX_COLOR = new ColorField("#19A3FF");
    private static final BooleanValueField DEFAULT_SHOW_ANIMATION = new BooleanValueField(true);
    private static final BooleanValueField DEFAULT_VERBOSE = new BooleanValueField(false);

    private final Map<Field.Type, Field<?>> values;

    /**
     * Constructs a new {@link Configuration} with default field values.
     * Initializes a map of {@link Field.Type} to their corresponding default {@link Field} instances.
     */
    public Configuration() {
        this.values = initDefault();
    }

    /**
     * Updates the value of a specific configuration field.
     *
     * @param type  the type of the field to update
     * @param value the new value to set
     * @throws NullPointerException if {@code type} or {@code value} is {@code null}
     */
    public void set(Field.Type type, Field<?> value) {
        if(values.containsKey(type)) values.replace(type, value);
        else values.put(type, value);
    }

    /**
     * Retrieves the value of a specific configuration field.
     *
     * @param type the type of the field to retrieve
     * @return the {@link Field} corresponding to the specified type, or {@code null} if the type is not present
     */
    public Field<?> get(Field.Type type) {
        return values.get(type);
    }

    /**
     * Initializes the configuration with default field values.
     *
     * @return a {@link Map} containing default values for each {@link Field.Type}
     */
    private static Map<Field.Type, Field<?>> initDefault() {
        final Map<Field.Type, Field<?>> defaultConfig = new HashMap<>();
        defaultConfig.put(Field.Type.DELAY, DEFAULT_DELAY);
        defaultConfig.put(Field.Type.TIME, DEFAULT_TIMER);
        defaultConfig.put(Field.Type.PATH_COLOR, DEFAULT_PATH_COLOR);
        defaultConfig.put(Field.Type.CURRENT_VERTEX_COLOR, DEFAULT_CURRENT_VERTEX_COLOR);
        defaultConfig.put(Field.Type.PREVIOUS_PATH_COLOR, DEFAULT_PREVIOUS_PATH_COLOR);
        defaultConfig.put(Field.Type.START_VERTEX_COLOR, DEFAULT_START_VERTEX_COLOR);
        defaultConfig.put(Field.Type.END_VERTEX_COLOR, DEFAULT_END_VERTEX_COLOR);
        defaultConfig.put(Field.Type.SHOW_ANIMATION, DEFAULT_SHOW_ANIMATION);
        defaultConfig.put(Field.Type.VERBOSE, DEFAULT_VERBOSE);

        return defaultConfig;
    }

    /**
     * Converts the configuration into a readable {@link String} representation.
     * Each field type and its corresponding value are listed in the output.
     *
     * @return a {@link String} representation of the configuration
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        values.forEach((type, field) -> {
            stringBuilder.append(type);
            stringBuilder.append(" : ");
            stringBuilder.append(field);
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }
}
