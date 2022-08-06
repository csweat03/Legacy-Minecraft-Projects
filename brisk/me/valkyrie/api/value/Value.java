package me.valkyrie.api.value;

import java.lang.reflect.*;
import java.util.*;

public class Value<T> {
	private Value parent;
	protected String label;
	protected Field field;
	protected Object object;
	protected String description;
	protected String[] aliases;
	protected int id = -1;
	private Set<Value> children = new HashSet<Value>();

	public Value(String label, Field field, Object object, String description, String[] aliases) {
		this.label = label;
		this.field = field;
		this.object = object;
		this.description = description;
		this.aliases = aliases;
	}

	public Value getParent() {
		return this.parent;
	}

	public void setParent(Value parent) {
		this.parent = parent;
	}

	public Value<T> withId(int id) {
		this.id = id;
		return this;
	}

	public Set<Value> getChildren() {
		return this.children;
	}

	public void setChildren(Set<Value> children) {
		this.children = children;
	}

	public T getValue() {
		try {
			boolean accessible = this.field.isAccessible();
			this.field.setAccessible(true);
			Object value = this.field.get(this.object);
			this.field.setAccessible(accessible);
			return (T) value;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public void setValue(T value) {
		try {
			boolean accessible = this.field.isAccessible();
			this.field.setAccessible(true);
			this.field.set(this.object, value);
			this.field.setAccessible(accessible);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public boolean isLabel(String label) {
		if (label.equalsIgnoreCase(this.label)) {
			return true;
		}
		return Arrays.stream(this.aliases).anyMatch(alias -> alias.equalsIgnoreCase(label));
	}

	public void fromString(String value) throws IllegalArgumentException {
	}

	public String getLabel() {
		return this.label;
	}

	public Field getField() {
		return this.field;
	}

	public Object getObject() {
		return this.object;
	}

	public String getDescription() {
		return this.description;
	}

	public int getId() {
		return this.id;
	}

	public String getDisplayLabel() {
		ArrayList<String> names = new ArrayList<>();
		Value property = this;
		while (property != null) {
			names.add(property.getLabel());
			property = property.parent;
		}
		String full = "";
		for (String name : names) {
			full = String.format("-%s%s", name, full);
		}
		return full.substring(1);
	}

	public String getFormattedLabel() {
		return getDisplayLabel().replaceAll("_", " ");
	}

	public boolean matches(String input) {
		return input.equalsIgnoreCase(this.getDisplayLabel()) || input.equalsIgnoreCase(this.getLabel());
	}
}
