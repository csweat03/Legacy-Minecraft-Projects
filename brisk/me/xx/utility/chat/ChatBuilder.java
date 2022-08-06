package me.xx.utility.chat;

import java.util.regex.Pattern;

import com.fbiclient.fbi.impl.Brisk;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class ChatBuilder {
	
	private ChatComponentText message;

	public ChatBuilder() {
		this.message = new ChatComponentText("");
	}

	public ChatBuilder appendPrefix() {
		this.appendText(Brisk.INSTANCE.NAME, ChatColor.RED).appendText(" > ", ChatColor.GRAY);
		return this;
	}

	public ChatComponentText getMessage() {
		return this.message;
	}

	public ChatBuilder fromString(String text) {
		final String regex = "[&\247]([a-fA-Fl-oL-O0-9])";
		text = text.replaceAll(regex, "\247$1");
		if (!Pattern.compile(regex).matcher(text).find()) {
			this.appendText(text, new ChatColor[0]);
			return this;
		}
		final String[] words = text.split(regex);
		int index = words[0].length();
		for (final String word : words) {
			try {
				if (index != words[0].length()) {
					String color;
					for (color = "\247" + text.charAt(index - 1); color.length() != 1; color = color.substring(1)
							.trim()) {
					}
					this.appendText(word, ChatColor.getFromCharacter(color.charAt(0)));
				}
			} catch (Exception ex) {
			}
			index += word.length() + 2;
		}
		return this;
	}

	public ChatBuilder appendText(final String text, final ChatColor... colors) {
		final ChatComponentText componentText = new ChatComponentText(text);
		ChatStyle chatStyle = new ChatStyle();
		for (final ChatColor color : colors) {
			switch (color) {
			case BOLD: {
				chatStyle.setBold(true);
				break;
			}
			case UNDERLINE: {
				chatStyle.setUnderlined(true);
				break;
			}
			case ITALIC: {
				chatStyle.setItalic(true);
				break;
			}
			case STRIKETHROUGH: {
				chatStyle.setStrikethrough(true);
				break;
			}
			case OBFUSCATED: {
				chatStyle.setObfuscated(true);
				break;
			}
			case RESET: {
				chatStyle = new ChatStyle();
				break;
			}
			default: {
				chatStyle.setColor(color.getBaseColor());
				break;
			}
			}
		}
		componentText.setChatStyle(chatStyle);
		this.message.appendSibling(componentText);
		return this;
	}

	public ChatBuilder appendText(final String text, final ChatColor color, final ClickEvent clickEvent) {
		final ChatComponentText componentText = new ChatComponentText(text);
		ChatStyle chatStyle = new ChatStyle();
		switch (color) {
		case BOLD: {
			chatStyle.setBold(true);
			break;
		}
		case UNDERLINE: {
			chatStyle.setUnderlined(true);
			break;
		}
		case ITALIC: {
			chatStyle.setItalic(true);
			break;
		}
		case STRIKETHROUGH: {
			chatStyle.setStrikethrough(true);
			break;
		}
		case OBFUSCATED: {
			chatStyle.setObfuscated(true);
			break;
		}
		case RESET: {
			chatStyle = new ChatStyle();
			break;
		}
		default: {
			chatStyle.setColor(color.getBaseColor());
			break;
		}
		}
		chatStyle.setChatClickEvent(clickEvent);
		componentText.setChatStyle(chatStyle);
		this.message.appendSibling(componentText);
		return this;
	}

	public ChatBuilder appendTextF(final String text, final Object... formatted) {
		final ChatComponentText componentText = new ChatComponentText(String.format(text, formatted));
		this.message.appendSibling(componentText);
		return this;
	}

	public void send() {
		Minecraft.getMinecraft().thePlayer.addChatMessage(this.message);
	}

	@Override
	public String toString() {
		return IChatComponent.Serializer.componentToJson(this.message);
	}
}
