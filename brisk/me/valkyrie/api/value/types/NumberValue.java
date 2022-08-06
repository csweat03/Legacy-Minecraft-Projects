package me.valkyrie.api.value.types;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;

import me.valkyrie.api.value.Value;
import me.valkyrie.api.value.types.constrain.Clamp;
import me.valkyrie.api.value.types.constrain.Increment;

public class NumberValue<T extends Number> extends Value<T> {
    private T increment;
    private T min;
    private T max;

    public NumberValue(final String label, final Field field, final Object object, final String description, final String[] aliases) {
        super(label, field, object, description, aliases);
        this.increment = null;
        this.min = null;
        this.max = null;
        if (field.isAnnotationPresent(Increment.class)) {
            final Increment increment = field.getAnnotation(Increment.class);
            this.increment = (T) this.getAbsoluteNumberValue(increment.value());
        } else {
            this.increment = (T) this.getAbsoluteNumberValue("1");
        }
        if (field.isAnnotationPresent(Clamp.class)) {
            final Clamp clamp = field.getAnnotation(Clamp.class);
            this.min = (T) this.getAbsoluteNumberValue(clamp.min());
            this.max = (T) this.getAbsoluteNumberValue(clamp.max());
        } else {
            this.min = (T) this.getAbsoluteNumberValue("-1000");
            this.max = (T) this.getAbsoluteNumberValue("1000");
        }
    }

    @Override
    public void setValue(T value) {
        if (value.doubleValue() > this.max.doubleValue()) {
            value = this.max;
        }
        if (value.doubleValue() < this.min.doubleValue()) {
            value = this.min;
        }
        Object val = value;
        final String lowerCase = this.field.getType().getSimpleName().toLowerCase();
        switch (lowerCase) {
            case "float": {
                val = round(value.floatValue(), 2);
                break;
            }
            case "double": {
                val = round(value.doubleValue(), 2);
                break;
            }
        }
        try {
            final boolean accessible = this.field.isAccessible();
            this.field.setAccessible(true);
            this.field.set(this.object, val);
            this.field.setAccessible(accessible);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void increase() {
        try {
            final String lowerCase = this.field.getType().getSimpleName().toLowerCase();
            switch (lowerCase) {
                case "int":
                case "integer": {
                    this.setValue((T) Integer.valueOf(this.getValue().intValue() + this.increment.intValue()));
                    break;
                }
                case "float": {
                    this.setValue((T) Float.valueOf(this.getValue().floatValue() + this.increment.floatValue()));
                    break;
                }
                case "double": {
                    this.setValue((T) Double.valueOf(this.getValue().doubleValue() + this.increment.doubleValue()));
                    break;
                }
                case "long": {
                    this.setValue((T) Long.valueOf(this.getValue().longValue() + this.increment.longValue()));
                    break;
                }
            }
        } catch (Exception ex) {
        }
    }

    public void decrease() {
        try {
            final String lowerCase = this.field.getType().getSimpleName().toLowerCase();
            switch (lowerCase) {
                case "int":
                case "integer": {
                    this.setValue((T) Integer.valueOf(this.getValue().intValue() - this.increment.intValue()));
                    break;
                }
                case "float": {
                    this.setValue((T) Float.valueOf(this.getValue().floatValue() - this.increment.floatValue()));
                    break;
                }
                case "double": {
                    this.setValue((T) Double.valueOf(this.getValue().doubleValue() - this.increment.doubleValue()));
                    break;
                }
                case "long": {
                    this.setValue((T) Long.valueOf(this.getValue().longValue() - this.increment.longValue()));
                    break;
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void fromString(final String value) throws IllegalArgumentException {
        if (this.getAbsoluteNumberValue(value) == null) {
            throw new IllegalArgumentException();
        }
        this.setValue((T) this.getAbsoluteNumberValue(value));
    }

    public Number getAbsoluteNumberValue(final String text) {
        try {
            final String lowerCase = this.field.getType().getSimpleName().toLowerCase();
            switch (lowerCase) {
                case "int":
                case "integer": {
                    return Integer.parseInt(text);
                }
                case "float": {
                    return Float.parseFloat(text);
                }
                case "double": {
                    return Double.parseDouble(text);
                }
                case "long": {
                    return Long.parseLong(text);
                }
                default: {
                    return null;
                }
            }
        } catch (Exception exception) {
            return null;
        }
    }

    private static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static float round(final float value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }

    public T getIncrement() {
        return increment;
    }

}
