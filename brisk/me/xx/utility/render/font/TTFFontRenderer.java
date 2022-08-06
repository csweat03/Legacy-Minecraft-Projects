package me.xx.utility.render.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Locale;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class TTFFontRenderer implements IFontRendering {
	private Font font;
	private boolean fractionalMetrics;
	private CharacterData[] regularData;
	private CharacterData[] boldData;
	private CharacterData[] italicsData;
	private int[] colorCodes;
	private static int MARGIN = 4;
	private static char COLOR_INVOKER = '\247';
	private static int RANDOM_OFFSET;

	public TTFFontRenderer(Font font) {
		this(font, 256);
	}

	public TTFFontRenderer(Font font, int characterCount) {
		this(font, characterCount, true);
	}

	public TTFFontRenderer(Font font, boolean fractionalMetrics) {
		this(font, 256, fractionalMetrics);
	}

	public TTFFontRenderer(Font font, int characterCount, boolean fractionalMetrics) {
		this.fractionalMetrics = false;
		this.colorCodes = new int[32];
		this.font = font;
		this.fractionalMetrics = fractionalMetrics;
		this.regularData = this.setup(new CharacterData[characterCount], 0);
		this.boldData = this.setup(new CharacterData[characterCount], 1);
		this.italicsData = this.setup(new CharacterData[characterCount], 2);
	}

	private CharacterData[] setup(CharacterData[] characterData, int type) {
		this.generateColors();
		Font font = this.font.deriveFont(type);
		BufferedImage utilityImage = new BufferedImage(1, 1, 2);
		Graphics2D utilityGraphics = (Graphics2D) utilityImage.getGraphics();
		utilityGraphics.setFont(font);
		FontMetrics fontMetrics = utilityGraphics.getFontMetrics();
		for (int index = 0; index < characterData.length; ++index) {
			char character = (char) index;
			Rectangle2D characterBounds = fontMetrics.getStringBounds(character + "", utilityGraphics);
			float width = (float) characterBounds.getWidth() + 8.0f;
			float height = (float) characterBounds.getHeight();
			BufferedImage characterImage = new BufferedImage(MathHelper.ceiling_double_int(width),
					MathHelper.ceiling_double_int(height), 2);
			Graphics2D graphics = (Graphics2D) characterImage.getGraphics();
			graphics.setFont(font);
			graphics.setColor(new Color(255, 255, 255, 0));
			graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
			graphics.setColor(Color.WHITE);
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
					this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON
							: RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			graphics.drawString(character + "", 4, fontMetrics.getAscent());
			int textureId = GlStateManager.generateTexture();
			this.createTexture(textureId, characterImage);
			characterData[index] = new CharacterData(character, characterImage.getWidth(), characterImage.getHeight(),
					textureId);
		}
		return characterData;
	}

	private void createTexture(int textureId, BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
		for (int y = 0; y < image.getHeight(); ++y) {
			for (int x = 0; x < image.getWidth(); ++x) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) (pixel >> 16 & 0xFF));
				buffer.put((byte) (pixel >> 8 & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) (pixel >> 24 & 0xFF));
			}
		}
		buffer.flip();
		GlStateManager.bindTexture(textureId);
		GL11.glTexParameteri(3553, 10241, 9728);
		GL11.glTexParameteri(3553, 10240, 9728);
		GL11.glTexImage2D(3553, 0, 6408, image.getWidth(), image.getHeight(), 0, 6408, 5121, buffer);
		GlStateManager.bindTexture(0);
	}

	@Override
	public void drawString(String text, float x, float y, int color) {
		this.renderString(text, x, y, color, false);
	}

	@Override
	public void drawStringWithShadow(String text, float x, float y, int color) {
		GL11.glTranslated(0.5, 0.5, 0.0);
		this.renderString(text, x, y, color, true);
		GL11.glTranslated(-0.5, -0.5, 0.0);
		this.renderString(text, x, y, color, false);
	}

	private void renderString(String text, float x, float y, int color, boolean shadow) {
		if (text == "" || text.length() == 0) {
			return;
		}
		GL11.glPushMatrix();
		GlStateManager.scale(0.5, 0.5, 1.0);
		x -= 2.0f;
		y -= 2.0f;
		x += 0.5f;
		y += 0.5f;
		x *= 2.0f;
		y *= 2.0f;
		CharacterData[] characterData = this.regularData;
		boolean underlined = false;
		boolean strikethrough = false;
		boolean obfuscated = false;
		int length = text.length();
		float multiplier = shadow ? 4 : 1;
		float a = (color >> 24 & 0xFF) / 255.0f;
		float r = (color >> 16 & 0xFF) / 255.0f;
		float g = (color >> 8 & 0xFF) / 255.0f;
		float b = (color & 0xFF) / 255.0f;
		GlStateManager.color(r / multiplier, g / multiplier, b / multiplier, a);
		for (int i = 0; i < length; ++i) {
			char character = text.charAt(i);
			char previous = (i > 0) ? text.charAt(i - 1) : '.';
			if (previous != '\247') {
				if (character == '\247' && i < length) {
					int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
					if (index < 16) {
						obfuscated = false;
						strikethrough = false;
						underlined = false;
						characterData = this.regularData;
						if (index < 0 || index > 15) {
							index = 15;
						}
						if (shadow) {
							index += 16;
						}
						int textColor = this.colorCodes[index];
						GL11.glColor4d((textColor >> 16) / 255.0, (textColor >> 8 & 0xFF) / 255.0,
								(textColor & 0xFF) / 255.0, (double) a);
					} else if (index == 16) {
						obfuscated = true;
					} else if (index == 17) {
						characterData = this.boldData;
					} else if (index == 18) {
						strikethrough = true;
					} else if (index == 19) {
						underlined = true;
					} else if (index == 20) {
						characterData = this.italicsData;
					} else if (index == 21) {
						obfuscated = false;
						strikethrough = false;
						underlined = false;
						characterData = this.regularData;
						GL11.glColor4d(1.0 * (shadow ? 0.25 : 1.0), 1.0 * (shadow ? 0.25 : 1.0),
								1.0 * (shadow ? 0.25 : 1.0), (double) a);
					}
				} else if (character <= '\u00ff') {
					if (obfuscated) {
						character += (char) TTFFontRenderer.RANDOM_OFFSET;
					}
					this.drawChar(character, characterData, x, y);
					CharacterData charData = characterData[character];
					if (strikethrough) {
						this.drawLine(new Vector2f(0.0f, charData.height / 2.0f),
								new Vector2f(charData.width, charData.height / 2.0f), 3.0f);
					}
					if (underlined) {
						this.drawLine(new Vector2f(0.0f, charData.height - 15.0f),
								new Vector2f(charData.width, charData.height - 15.0f), 3.0f);
					}
					x += charData.width - 8.0f;
				}
			}
		}
		GL11.glPopMatrix();
		GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
		GlStateManager.bindTexture(0);
	}

	@Override
	public float getWidth(String text) {
		float width = 0.0f;
		CharacterData[] characterData = this.regularData;
		for (int length = text.length(), i = 0; i < length; ++i) {
			char character = text.charAt(i);
			char previous = (i > 0) ? text.charAt(i - 1) : '.';
			if (previous != '\247') {
				if (character == '\247' && i < length) {
					int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
					if (index == 17) {
						characterData = this.boldData;
					} else if (index == 20) {
						characterData = this.italicsData;
					} else if (index == 21) {
						characterData = this.regularData;
					}
				} else if (character <= '\u00ff') {
					CharacterData charData = characterData[character];
					width += (charData.width - 8.0f) / 2.0f;
				}
			}
		}
		return width + 2.0f;
	}

	@Override
	public float getHeight(String text) {
		float height = 0.0f;
		CharacterData[] characterData = this.regularData;
		for (int length = text.length(), i = 0; i < length; ++i) {
			char character = text.charAt(i);
			char previous = (i > 0) ? text.charAt(i - 1) : '.';
			if (previous != '\247') {
				if (character == '\247' && i < length) {
					int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
					if (index == 17) {
						characterData = this.boldData;
					} else if (index == 20) {
						characterData = this.italicsData;
					} else if (index == 21) {
						characterData = this.regularData;
					}
				} else if (character <= '\u00ff') {
					CharacterData charData = characterData[character];
					height = Math.max(height, charData.height);
				}
			}
		}
		return height / 2.0f - 2.0f;
	}

	private void drawChar(char character, CharacterData[] characterData, float x, float y) {
		CharacterData charData = characterData[character];
		charData.bind();
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glBegin(7);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2d((double) x, (double) (y + charData.height));
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2d((double) (x + charData.width), (double) (y + charData.height));
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2d((double) (x + charData.width), (double) y);
		GL11.glEnd();
		GL11.glDisable(3042);
		GL11.glPopMatrix();
		GL11.glBindTexture(3553, 0);
	}

	private void drawLine(Vector2f start, Vector2f end, float width) {
		GL11.glDisable(3553);
		GL11.glLineWidth(width);
		GL11.glBegin(1);
		GL11.glVertex2f(start.x, start.y);
		GL11.glVertex2f(end.x, end.y);
		GL11.glEnd();
		GL11.glEnable(3553);
	}

	private void generateColors() {
		for (int i = 0; i < 32; ++i) {
			int thingy = (i >> 3 & 0x1) * 85;
			int red = (i >> 2 & 0x1) * 170 + thingy;
			int green = (i >> 1 & 0x1) * 170 + thingy;
			int blue = (i >> 0 & 0x1) * 170 + thingy;
			if (i == 6) {
				red += 85;
			}
			if (i >= 16) {
				red /= 4;
				green /= 4;
				blue /= 4;
			}
			this.colorCodes[i] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
		}
	}

	public Font getFont() {
		return this.font;
	}

	static {
		TTFFontRenderer.RANDOM_OFFSET = 1;
	}

	class CharacterData {
		public char character;
		public float width;
		public float height;
		private int textureId;

		public CharacterData(char character, float width, float height, int textureId) {
			this.character = character;
			this.width = width;
			this.height = height;
			this.textureId = textureId;
		}

		public void bind() {
			GL11.glBindTexture(3553, this.textureId);
		}
	}

	@Override
	public void drawCenteredString(String string, float x, float y, int color) {
		drawStringWithShadow(string, (x - this.getWidth(string) / 2), y, color);
	}
}