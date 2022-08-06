package me.valkyrie.api.value.factory;

import java.lang.reflect.*;
import java.util.*;

import me.valkyrie.api.value.types.BooleanValue;
import me.valkyrie.api.value.types.EnumValue;
import me.valkyrie.api.value.types.NumberValue;
import me.valkyrie.api.value.Val;
import me.valkyrie.api.value.Value;
import me.valkyrie.api.value.types.StringValue;
import me.valkyrie.api.value.types.child.Child;

public class AttributeFactory {
	
	public static Set<Value> create(Object object) {
		LinkedHashSet values = new LinkedHashSet();
		Arrays.stream(object.getClass().getDeclaredFields()).filter((field) -> {
			return field.isAnnotationPresent(Val.class) && getPropertyClass(field).isPresent()
					&& !field.isAnnotationPresent(Child.class);
		}).forEach((field) -> {
			values.add(createProperty(field, object).get());
		});
		return values;
	}

	private static Optional createProperty(Field field, Object parent) {
		Val val = field.getAnnotation(Val.class);
		Class propertyClass = (Class) getPropertyClass(field).get();

		try {
			Value value = (Value) propertyClass.getDeclaredConstructors()[0].newInstance(
					new Object[] { val.label(), field, parent, val.description(), val.aliases() });
			value.setChildren(findChildren(parent, value));
			return Optional.of(value);
		} catch (Exception var5) {
			return Optional.empty();
		}
	}

	private static Set<Value> findChildren(Object object, Value value) {
		LinkedHashSet values = new LinkedHashSet();
		Arrays.stream(object.getClass().getDeclaredFields()).filter((field) -> {
			return field.isAnnotationPresent(Child.class) && field.isAnnotationPresent(Child.class)
					&& getPropertyClass(field).isPresent()
					&& (field.getAnnotation(Child.class)).value().equalsIgnoreCase(value.getLabel());
		}).forEach((field) -> {
			Value child = (Value) createProperty(field, object).get();
			child.setParent(value);
			values.add(child);
		});
		return values;
	}

	private static Optional getPropertyClass(Field field) {
		String var1 = field.getType().getSimpleName().toLowerCase();
		byte var2 = -1;
		switch (var1.hashCode()) {
		case -1325958191:
			if (var1.equals("double")) {
				var2 = 4;
			}
			break;
		case -891985903:
			if (var1.equals("string")) {
				var2 = 1;
			}
			break;
		case 104431:
			if (var1.equals("int")) {
				var2 = 3;
			}
			break;
		case 3327612:
			if (var1.equals("long")) {
				var2 = 5;
			}
			break;
		case 64711720:
			if (var1.equals("boolean")) {
				var2 = 0;
			}
			break;
		case 97526364:
			if (var1.equals("float")) {
				var2 = 6;
			}
			break;
		case 1958052158:
			if (var1.equals("integer")) {
				var2 = 2;
			}
		}

		switch (var2) {
		case 0:
			return Optional.of(BooleanValue.class);
		case 1:
			return Optional.of(StringValue.class);
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			return Optional.of(NumberValue.class);
		default:
			return field.getType().isEnum() ? Optional.of(EnumValue.class) : Optional.empty();
		}
	}
}
