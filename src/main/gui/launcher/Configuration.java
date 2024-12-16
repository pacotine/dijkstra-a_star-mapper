package main.gui.launcher;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private static final TimeField DEFAULT_DELAY = new TimeField(2000, 0, 10_000); //ms
    private static final TimeField DEFAULT_TIMER = new TimeField(10, 1, 20_000); //ms
    private static final ColorField DEFAULT_PATH_COLOR = new ColorField("#FF19A7");;
    private static final ColorField DEFAULT_CURRENT_VERTEX_COLOR = new ColorField("#8E09DB");
    private static final ColorField DEFAULT_PREVIOUS_PATH_COLOR = new ColorField("#FF9C19");
    private static final ColorField DEFAULT_START_VERTEX_COLOR = new ColorField("#FF194F");
    private static final ColorField DEFAULT_END_VERTEX_COLOR = new ColorField("#19A3FF");
    private static final BooleanValueField DEFAULT_SHOW_ANIMATION = new BooleanValueField(true);

    private final Map<Field.Type, Field<?>> values;

    public Configuration() {
        this.values = initDefault();
    }

    public void set(Field.Type type, Field<?> value) {
        if(values.containsKey(type)) values.replace(type, value);
        else values.put(type, value);
    }

    public Field<?> get(Field.Type type) {
        return values.get(type);
    }

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

        return defaultConfig;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
