package me.xx.utility.chat;

import net.minecraft.util.EnumChatFormatting;

public enum ChatColor {
    BLACK(EnumChatFormatting.BLACK), 
    DARK_BLUE(EnumChatFormatting.DARK_BLUE), 
    DARK_GREEN(EnumChatFormatting.DARK_GREEN), 
    DARK_AQUA(EnumChatFormatting.DARK_AQUA), 
    DARK_RED(EnumChatFormatting.DARK_RED), 
    DARK_PURPLE(EnumChatFormatting.DARK_PURPLE), 
    GOLD(EnumChatFormatting.GOLD), 
    GRAY(EnumChatFormatting.GRAY), 
    DARK_GRAY(EnumChatFormatting.DARK_GRAY), 
    BLUE(EnumChatFormatting.BLUE), 
    GREEN(EnumChatFormatting.GREEN), 
    AQUA(EnumChatFormatting.AQUA), 
    RED(EnumChatFormatting.RED), 
    LIGHT_PURPLE(EnumChatFormatting.LIGHT_PURPLE), 
    YELLOW(EnumChatFormatting.YELLOW), 
    WHITE(EnumChatFormatting.WHITE), 
    OBFUSCATED(EnumChatFormatting.OBFUSCATED), 
    BOLD(EnumChatFormatting.BOLD), 
    STRIKETHROUGH(EnumChatFormatting.STRIKETHROUGH), 
    UNDERLINE(EnumChatFormatting.UNDERLINE), 
    ITALIC(EnumChatFormatting.ITALIC), 
    RESET(EnumChatFormatting.RESET);
    
    private EnumChatFormatting baseColor;
    
    private ChatColor(final EnumChatFormatting baseColor) {
        this.baseColor = baseColor;
    }
    
    public EnumChatFormatting getBaseColor() {
        return this.baseColor;
    }
    
    public static ChatColor getFromCharacter(final char character) {
        for (final ChatColor chatColor : values()) {
            final EnumChatFormatting baseColor = chatColor.getBaseColor();
            if (baseColor.getFormattingCode() == character) {
                return chatColor;
            }
        }
        return null;
    }
}
