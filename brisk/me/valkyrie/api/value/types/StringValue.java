package me.valkyrie.api.value.types;

import java.lang.reflect.Field;

import me.valkyrie.api.value.Value;

public class StringValue extends Value<String> {
    public StringValue(final String label, final Field field, final Object object, final String description, final String[] aliases) {
        super(label, field, object, description, aliases);
    }

    @Override
    public void fromString(final String value) throws IllegalArgumentException {
        this.setValue(value);
    }
}
