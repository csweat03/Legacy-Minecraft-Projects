package me.valkyrie.api.value.types;

import java.lang.reflect.Field;

import me.valkyrie.api.value.Value;

public class EnumValue extends Value<Enum> {
	
    public EnumValue(String label, Field field, Object object, String description, String[] aliases) {
        super(label, field, object, description, aliases);
    }

    public void fromString(String value) throws IllegalArgumentException {
        if (!this.field.getType().isEnum()) {
            return;
        }
        Object enumValue = Enum.valueOf((Class) this.field.getType(), value.toUpperCase());
        this.setValue((Enum) enumValue);
    }

    public Enum[] values() {
        Class clas = ((Enum) this.getValue()).getClass();
        return (Enum[]) clas.getEnumConstants();
    }
}
