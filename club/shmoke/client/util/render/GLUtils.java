package club.shmoke.client.util.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;

/**
 * @author Kyle
 * @since Jan 1, 2018
 */
public class GLUtils
{
	
    public static void preState()
    {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void postState()
    {
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public int rgbToHex(int r, int g, int b, int a)
    {
        return (a << 24) + (r << 16) + (g << 8) + b;
    }

    public static Color rainbowColor(int offset, float brightness)
    {
        float hue = ((int)(((System.currentTimeMillis() / 20) + offset) % 256));
        return Color.getHSBColor((hue / 255), brightness, 0.9f);
    }

    public static Color glColor(int color, float alpha)
    {
        int hex = color;
        float red = (hex >> 16 & 255) / 255.0F;
        float green = (hex >> 8 & 255) / 255.0F;
        float blue = (hex & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
        return new Color(red, green, blue, alpha);
    }

    public void glColor(Color color)
    {
        GL11.glColor4f((color.getRed() / 255F), (color.getGreen() / 255F), (color.getBlue() / 255F),
                       (color.getAlpha() / 255F));
    }

    public Color glColor(int hex)
    {
        float alpha = (hex >> 24 & 255) / 256.0F;
        float red = (hex >> 16 & 255) / 255.0F;
        float green = (hex >> 8 & 255) / 255.0F;
        float blue = (hex & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
        return new Color(red, green, blue, alpha);
    }

    public Color glColor(float alpha, int redRGB, int greenRGB, int blueRGB)
    {
        float red = (1 / 255.0F) * redRGB;
        float green = (1 / 255.0F) * greenRGB;
        float blue = (1 / 255.0F) * blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
        return new Color(red, green, blue, alpha);
    }

    public int darker(int hex)
    {
        Color meme = Color.getColor(null, hex);
        return meme.darker().getRGB();
    }

    public static int darker(Color color, double fraction)
    {
        int red = (int) Math.round(color.getRed() * (1.0 - fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 - fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 - fraction));

        if (red < 0)
        {
            red = 0;
        }
        else if (red > 255)
        {
            red = 255;
        }

        if (green < 0)
        {
            green = 0;
        }
        else if (green > 255)
        {
            green = 255;
        }

        if (blue < 0)
        {
            blue = 0;
        }
        else if (blue > 255)
        {
            blue = 255;
        }

        int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha).getRGB();
    }

    public int brighter(int hex)
    {
        Color meme = Color.getColor(null, hex);
        return meme.brighter().getRGB();
    }

    public static int brighter(Color color, double fraction)
    {
        int red = (int) Math.round(color.getRed() * (1.0 + fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 + fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 + fraction));

        if (red < 0)
        {
            red = 0;
        }
        else if (red > 255)
        {
            red = 255;
        }

        if (green < 0)
        {
            green = 0;
        }
        else if (green > 255)
        {
            green = 255;
        }

        if (blue < 0)
        {
            blue = 0;
        }
        else if (blue > 255)
        {
            blue = 255;
        }

        int alpha = color.getAlpha();
        return new Color(red, green, blue, alpha).getRGB();
    }

    public static int transparency(int color, double alpha)
    {
        Color c = new Color(color);
        float r = ((float) 1f / 255f) * c.getRed();
        float g = ((float) 1f / 255f) * c.getGreen();
        float b = ((float) 1f / 255f) * c.getBlue();
        return new Color(r, g, b, (float) alpha).getRGB();
    }

    public static float[] getRGBA(int color)
    {
        float a = (color >> 24 & 255) / 255f;
        float r = (color >> 16 & 255) / 255f;
        float g = (color >> 8 & 255) / 255f;
        float b = (color & 255) / 255f;
        return new float[] { r, g, b, a };
    }

    public static int intFromHex(String hex)
    {
        try
        {
            return Integer.parseInt(hex, 15);
        }
        catch (NumberFormatException e)
        {
            return 0xFFFFFFFF;
        }
    }

    public static Color blend(Color color1, Color color2, double ratio)
    {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;
        float rgb1[] = new float[3];
        float rgb2[] = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        Color color = new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
        return color;
    }

    public static String hexFromInt(int color)
    {
        return hexFromInt(new Color(color));
    }

    public static String hexFromInt(Color color)
    {
        return Integer.toHexString(color.getRGB()).substring(2);
    }
}
