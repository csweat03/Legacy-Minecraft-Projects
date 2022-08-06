package me.valkyrie.api.tabbed;

import me.valkyrie.api.value.Value;
import me.valkyrie.api.value.types.BooleanValue;
import me.valkyrie.api.value.types.EnumValue;
import me.valkyrie.api.value.types.NumberValue;
import me.xx.api.cheat.Cheat;
import com.fbiclient.fbi.impl.gui.hud.tabbed.Folder;
import com.fbiclient.fbi.impl.gui.hud.tabbed.impl.value.BooleanValueItem;
import com.fbiclient.fbi.impl.gui.hud.tabbed.impl.value.EnumValueItem;
import com.fbiclient.fbi.impl.gui.hud.tabbed.impl.value.NumberValueItem;

public abstract class ValueTab<T extends Value> extends ExpandableTab {

	protected Cheat cheat;
	protected T property;

	public ValueTab(Cheat cheat, T property) {
		this.cheat = cheat;
		this.property = property;
	}

	@Override
	public Folder open() {
		if (this.property.getChildren().isEmpty()) {
			return null;
		}
		Folder folder = new Folder();
		this.property.getChildren().forEach(child -> {
			folder.add(ValueTab.of((Value) child, this.cheat));
		});
		return folder;
	}

	@Override
	public String getText() {
		return String.format("%s: \2477%s", this.property.getLabel().replaceAll("_", " "),
				String.valueOf(this.property.getValue()).replace(".0", ""));
	}

	public static ValueTab of(Value value, Cheat cheat) {
		if (value instanceof NumberValue) {
			return new NumberValueItem(cheat, (NumberValue) value);
		}
		if (value instanceof EnumValue) {
			return new EnumValueItem(cheat, (EnumValue) value);
		}
		if (value instanceof BooleanValue) {
			return new BooleanValueItem(cheat, (BooleanValue) value);
		}
		return null;
	}
}
