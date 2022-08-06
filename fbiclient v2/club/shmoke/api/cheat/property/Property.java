package club.shmoke.api.cheat.property;

import club.shmoke.api.cheat.Cheat;

/**
 * @author Christian
 */
public class Property<T> {

    private Cheat cheat;
    private String label;
    private T value, min, max, def, inc;

    public Property(Cheat cheat, String label, T value) {
        this.cheat = cheat;
        this.label = label;
        this.value = def = value;
        cheat.addProperty(this);
    }

    public Property(Cheat cheat, String label, T value, T min, T max, T inc) {
        this.cheat = cheat;
        this.label = label;
        this.value = def = value;
        this.min = min;
        this.max = max;
        this.inc = inc;
        cheat.addProperty(this);
    }

    public Cheat getCheat() {
        return cheat;
    }

    public String getLabel() {
        return label;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T getDef() {
        return def;
    }

    public T getInc() {
        return inc;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public void setDef(T def) {
        this.def = def;
    }

    public void setInc(T inc) {
        this.inc = inc;
    }
}
