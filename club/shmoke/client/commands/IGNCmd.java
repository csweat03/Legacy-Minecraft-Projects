package club.shmoke.client.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import club.shmoke.client.Client;
import club.shmoke.api.anticheat.Anticheat;
import club.shmoke.api.command.Command;
import club.shmoke.client.util.GameLogger;

public class IGNCmd extends Command {
	private static ArrayList<String> aliases = new ArrayList();

	public IGNCmd() {
		super("ign", "Gets user IGN", "ign/name/username", aliases);
		aliases.add("name");
		aliases.add("username");
		aliases.add("uname");
		aliases.add("ingamename");
	}

	@Override
	public void dispatch(String args[], String message) {
		if (args.length != 0) {
			GameLogger.log(this.syntaxMsg(), false, GameLogger.Type.ERROR);
		} else {
			Anticheat ac = Client.INSTANCE.getAnticheatManager().findAnticheat();
			final String str = "My minecraft IGN is: " + Minecraft.getMinecraft().session.getUsername() + ". "
					+ (ac == Anticheat.WATCHDOG ? "I am on: Hypixel"
							: ac == Anticheat.GWEN ? "I am on: Mineplex"
									: ac == Anticheat.GUARDIAN ? "I am on: Velt/Arcane" : "");
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Clipboard clipboard = toolkit.getSystemClipboard();
			final StringSelection strSel = new StringSelection(str);
			clipboard.setContents(strSel, null);
			GameLogger.log("Copied your IGN to your clipboard!", false, GameLogger.Type.INFO);
		}
	}
}
