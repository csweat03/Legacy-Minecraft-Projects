package me.xx.api.command;

import java.util.Arrays;

import com.fbiclient.fbi.client.framework.helper.IChatBuilder;
import com.fbiclient.fbi.client.framework.helper.IHelper;

public abstract class Command implements IHelper, IChatBuilder {
	private CommandManifest commandManifest;

	public Command() {
		if (!this.getClass().isAnnotationPresent(CommandManifest.class)) {
			System.err.printf("Command class \"%s\" does not have a CommandManifest annotated",
					this.getClass().getSimpleName());
			return;
		}
		this.commandManifest = this.getClass().getAnnotation(CommandManifest.class);
	}

	public abstract void dispatch(String[] args, String input);

	public String getLabel() {
		return this.commandManifest.label();
	}

	public boolean matches(String input) {
		return input.equalsIgnoreCase(this.getLabel())
				|| Arrays.stream(this.commandManifest.handles()).anyMatch(input::equalsIgnoreCase);
	}
}
