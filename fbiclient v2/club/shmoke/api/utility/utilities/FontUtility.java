package club.shmoke.api.utility.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian
 */
public class FontUtility {

    public CFontRenderer def, small, smaller, smallerer;

    public void initialize() {
        def = addFont(18);
        small = addFont(16);
        smaller = addFont(14);
        smallerer = addFont(12);
    }

    private Font getFont(String label, int size) {
        Font font;
        try {
            String str = String.format("client/fonts/%s.ttf", label);
            InputStream ex = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(str)).getInputStream();
            font = Font.createFont(0, ex);
            font = font.deriveFont(0, size);
            //ont = new Font("Comfortaa", 0, size);
        } catch (Exception var3) {
            font = new Font("default", 0, size);
        }
        return font;
    }

    private Font getFont(int size) {
        return getFont("Myriad Pro", size);
    }

    private CFontRenderer addFont(int size) {
        return new CFontRenderer(getFont(size), true, true);
    }

    public class CFontRenderer extends CFont {
        private final int[] colorCode = new int[32];
        protected CharData[] boldChars = new CharData[256];
        protected CharData[] italicChars = new CharData[256];
        protected CharData[] boldItalicChars = new CharData[256];
        protected DynamicTexture texBold;
        protected DynamicTexture texItalic;
        protected DynamicTexture texItalicBold;

        public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
            super(font, antiAlias, fractionalMetrics);
            setupMinecraftColorcodes();
            setupBoldItalicIDs();
        }

        public float drawStringWithShadow(String text, double x, double y, int color) {
            y += 1;
            float shadowWidth = drawString(text, x + 0.75D, y + 0.75D, color, true);
            return Math.max(shadowWidth, drawString(text, x, y, color, false));
        }

        public float drawString(String text, float x, float y, int color) {
            y += 1;
            return drawString(text, x, y, color, false);
        }

        public float drawCenteredString(String text, float x, float y, int color) {
            return drawString(text, x - getStringWidth(text) / 2, y - getStringHeight(text) / 2, color);
        }

        public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
            float shadowWidth = drawString(text, x - getStringWidth(text) / 2 + 1.0D, y + 1.0D, color, true);
            return drawString(text, x - getStringWidth(text) / 2, y, color);
        }

        public float drawString(String text, double x, double y, int color, boolean shadow) {
            x -= 1;

            if (text == null) {
                return 0.0F;
            }

            if (color == 553648127) {
                color = 16777215;
            }

            if ((color & 0xFC000000) == 0) {
                color |= -16777216;
            }

            if (shadow) {
                color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
            }

            CharData[] currentData = this.charData;
            float alpha = (color >> 24 & 0xFF) / 255.0F;
            boolean randomCase = false;
            boolean bold = false;
            boolean italic = false;
            boolean strikethrough = false;
            boolean underline = false;
            boolean render = true;
            x *= 2.0D;
            y = (y - 3.0D) * 2.0D;

            if (render) {
                GL11.glPushMatrix();
                GlStateManager.scale(0.5D, 0.5D, 0.5D);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F,
                        (color & 0xFF) / 255.0F, alpha);
                int size = text.length();
                GlStateManager.enableColorMaterial();
                GlStateManager.bindTexture(tex.getGlTextureId());

                GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGlTextureId());

                for (int i = 0; i < size; i++) {
                    char character = text.charAt(i);

                    if ((character == '??') && (i < size)) {
                        int colorIndex = 21;

                        try {
                            colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (colorIndex < 16) {
                            bold = false;
                            italic = false;
                            underline = false;
                            strikethrough = false;
                            GlStateManager.bindTexture(tex.getGlTextureId());
                            currentData = this.charData;

                            if ((colorIndex < 0) || (colorIndex > 15)) {
                                colorIndex = 15;
                            }

                            if (shadow) {
                                colorIndex += 16;
                            }

                            int colorcode = this.colorCode[colorIndex];
                            GlStateManager.color((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F,
                                    (colorcode & 0xFF) / 255.0F, alpha);
                        } else if (colorIndex == 16) {
                            randomCase = true;
                        } else if (colorIndex == 17) {
                            bold = true;

                            if (italic) {
                                GlStateManager.bindTexture(texItalicBold.getGlTextureId());
                                currentData = this.boldItalicChars;
                            } else {
                                GlStateManager.bindTexture(texBold.getGlTextureId());
                                currentData = this.boldChars;
                            }
                        } else if (colorIndex == 18) {
                            strikethrough = true;
                        } else if (colorIndex == 19) {
                            underline = true;
                        } else if (colorIndex == 20) {
                            italic = true;

                            if (bold) {
                                GlStateManager.bindTexture(texItalicBold.getGlTextureId());
                                currentData = this.boldItalicChars;
                            } else {
                                GlStateManager.bindTexture(texItalic.getGlTextureId());
                                currentData = this.italicChars;
                            }
                        } else if (colorIndex == 21) {
                            bold = false;
                            italic = false;
                            randomCase = false;
                            underline = false;
                            strikethrough = false;
                            GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F,
                                    (color & 0xFF) / 255.0F, alpha);
                            GlStateManager.bindTexture(tex.getGlTextureId());
                            currentData = this.charData;
                        }

                        i++;
                    } else if ((character < currentData.length) && (character >= 0)) {
                        GL11.glBegin(GL11.GL_TRIANGLES);
                        drawChar(currentData, character, (float) x, (float) y);
                        GL11.glEnd();

                        if (strikethrough) {
                            drawLine(x, y + currentData[character].height / 2, x + currentData[character].width - 8.0D,
                                    y + currentData[character].height / 2, 1.0F);
                        }

                        if (underline) {
                            drawLine(x, y + currentData[character].height - 2.0D,
                                    x + currentData[character].width - 8.0D, y + currentData[character].height - 2.0D,
                                    1.0F);
                        }

                        x += currentData[character].width - 8 + this.charOffset;
                    }
                }

                GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
                GL11.glPopMatrix();
            }

            return (float) x / 2.0F;
        }

        @Override
        public int getStringWidth(String text) {
            if (text == null)
                return 0;

            int width = 0;
            CharData[] currentData = this.charData;
            boolean bold = false;
            boolean italic = false;
            int size = text.length();

            for (int i = 0; i < size; i++) {
                char character = text.charAt(i);

                if ((character == '??') && (i < size)) {
                    int colorIndex = "0123456789abcdefklmnor".indexOf(character);

                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                    } else if (colorIndex == 17) {
                        bold = true;

                        if (italic) {
                            currentData = this.boldItalicChars;
                        } else {
                            currentData = this.boldChars;
                        }
                    } else if (colorIndex == 20) {
                        italic = true;

                        if (bold) {
                            currentData = this.boldItalicChars;
                        } else {
                            currentData = this.italicChars;
                        }
                    } else if (colorIndex == 21) {
                        bold = false;
                        italic = false;
                        currentData = this.charData;
                    }

                    i++;
                } else if ((character < currentData.length) && (character >= 0)) {
                    width += currentData[character].width - 8 + this.charOffset;
                }
            }

            return width / 2;
        }

        public void setFont(Font font) {
            super.setFont(font);
            setupBoldItalicIDs();
        }

        public void setAntiAlias(boolean antiAlias) {
            super.setAntiAlias(antiAlias);
            setupBoldItalicIDs();
        }

        public void setFractionalMetrics(boolean fractionalMetrics) {
            super.setFractionalMetrics(fractionalMetrics);
            setupBoldItalicIDs();
        }

        private void setupBoldItalicIDs() {
            texBold = setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
            texItalic = setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
            texItalicBold = setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics,
                    this.boldItalicChars);
        }

        private void drawLine(double x, double y, double x1, double y1, float width) {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glLineWidth(width);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x1, y1);
            GL11.glEnd();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        public java.util.List<String> wrapWords(String text, double width) {
            java.util.List finalWords = new ArrayList();

            if (getStringWidth(text) > width) {
                String[] words = text.split(" ");
                String currentWord = "";
                char lastColorCode = 65535;

                for (String word : words) {
                    for (int i = 0; i < word.toCharArray().length; i++) {
                        char c = word.toCharArray()[i];

                        if ((c == '??') && (i < word.toCharArray().length - 1)) {
                            lastColorCode = word.toCharArray()[(i + 1)];
                        }
                    }

                    if (getStringWidth(currentWord + word + " ") < width) {
                        currentWord = currentWord + word + " ";
                    } else {
                        finalWords.add(currentWord);
                        currentWord = "??" + lastColorCode + word + " ";
                    }
                }

                if (currentWord.length() > 0)
                    if (getStringWidth(currentWord) < width) {
                        finalWords.add("??" + lastColorCode + currentWord + " ");
                        currentWord = "";
                    } else {
                        for (String s : formatString(currentWord, width)) {
                            finalWords.add(s);
                        }
                    }
            } else {
                finalWords.add(text);
            }

            return finalWords;
        }

        public java.util.List<String> formatString(String string, double width) {
            List finalWords = new ArrayList();
            String currentWord = "";
            char lastColorCode = 65535;
            char[] chars = string.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];

                if ((c == '??') && (i < chars.length - 1)) {
                    lastColorCode = chars[(i + 1)];
                }

                if (getStringWidth(currentWord + c) < width) {
                    currentWord = currentWord + c;
                } else {
                    finalWords.add(currentWord);
                    currentWord = "??" + lastColorCode + String.valueOf(c);
                }
            }

            if (currentWord.length() > 0) {
                finalWords.add(currentWord);
            }

            return finalWords;
        }

        private void setupMinecraftColorcodes() {
            for (int index = 0; index < 32; index++) {
                int noClue = (index >> 3 & 0x1) * 85;
                int red = (index >> 2 & 0x1) * 170 + noClue;
                int green = (index >> 1 & 0x1) * 170 + noClue;
                int blue = (index >> 0 & 0x1) * 170 + noClue;

                if (index == 6) {
                    red += 85;
                }

                if (index >= 16) {
                    red /= 4;
                    green /= 4;
                    blue /= 4;
                }

                this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
            }
        }

        /**
         * Trims a string to fit a specified Width.
         */
        public String trimStringToWidth(String text, int width) {
            return this.trimStringToWidth(text, width, false);
        }

        /**
         * Trims a string to a specified width, and will reverse it if par3 is set.
         */
        public String trimStringToWidth(String text, int width, boolean reverse) {
            StringBuilder stringbuilder = new StringBuilder();
            float f = 0.0F;
            int i = reverse ? text.length() - 1 : 0;
            int j = reverse ? -1 : 1;
            boolean flag = false;
            boolean flag1 = false;

            for (int k = i; k >= 0 && k < text.length() && f < (float) width; k += j) {
                char c0 = text.charAt(k);

                //HERE
                float f1 = 5.5f;

                if (flag) {
                    flag = false;

                    if (c0 != 108 && c0 != 76) {
                        if (c0 == 114 || c0 == 82) {
                            flag1 = false;
                        }
                    } else {
                        flag1 = true;
                    }
                } else if (f1 < 0.0F)
                    flag = true;
                else {
                    f += f1;

                    if (flag1)
                        ++f;
                }

                if (f > (float) width) {
                    break;
                }

                if (reverse) {
                    stringbuilder.insert(0, (char) c0);
                } else {
                    stringbuilder.append(c0);
                }
            }

            return stringbuilder.toString();
        }
    }

    public class CFont {
        protected CharData[] charData = new CharData[256];
        protected Font font;
        protected boolean antiAlias;
        protected boolean fractionalMetrics;
        protected int fontHeight = -1;
        protected int charOffset = 0;
        protected DynamicTexture tex;
        private float imgSize = 512;

        public CFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
            this.font = font;
            this.antiAlias = antiAlias;
            this.fractionalMetrics = fractionalMetrics;
            tex = setupTexture(font, antiAlias, fractionalMetrics, this.charData);
        }

        protected DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics,
                                              CharData[] chars) {
            BufferedImage img = generateFontImage(font, antiAlias, fractionalMetrics, chars);

            try {
                return new DynamicTexture(img);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics,
                                                  CharData[] chars) {
            int imgSize = (int) this.imgSize;
            BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
            g.setFont(font);
            g.setColor(new Color(255, 255, 255, 0));
            g.fillRect(0, 0, imgSize, imgSize);
            g.setColor(Color.WHITE);
            g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                    fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON
                            : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
            FontMetrics fontMetrics = g.getFontMetrics();
            int charHeight = 0;
            int positionX = 0;
            int positionY = 1;

            for (int i = 0; i < chars.length; i++) {
                char ch = (char) i;
                CharData charData = new CharData();
                Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
                charData.width = (dimensions.getBounds().width + 8);
                charData.height = dimensions.getBounds().height;

                if (positionX + charData.width >= imgSize) {
                    positionX = 0;
                    positionY += charHeight;
                    charHeight = 0;
                }

                if (charData.height > charHeight) {
                    charHeight = charData.height;
                }

                charData.storedX = positionX;
                charData.storedY = positionY;

                if (charData.height > this.fontHeight) {
                    this.fontHeight = charData.height;
                }

                chars[i] = charData;
                g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
                positionX += charData.width;
            }

            return bufferedImage;
        }

        public void drawChar(CharData[] chars, char c, float x, float y) throws ArrayIndexOutOfBoundsException {
            try {
                drawQuad(x, y, chars[c].width, chars[c].height, chars[c].storedX, chars[c].storedY, chars[c].width,
                        chars[c].height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth,
                                float srcHeight) {
            float renderSRCX = srcX / imgSize;
            float renderSRCY = srcY / imgSize;
            float renderSRCWidth = srcWidth / imgSize;
            float renderSRCHeight = srcHeight / imgSize;
            GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
            GL11.glVertex2d(x + width, y);
            GL11.glTexCoord2f(renderSRCX, renderSRCY);
            GL11.glVertex2d(x, y);
            GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
            GL11.glVertex2d(x, y + height);
            GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
            GL11.glVertex2d(x, y + height);
            GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
            GL11.glVertex2d(x + width, y + height);
            GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
            GL11.glVertex2d(x + width, y);
        }

        public int getStringHeight(String text) {
            return getHeight();
        }

        public int getHeight() {
            return (this.fontHeight - 8) / 2;
        }

        public int getStringWidth(String text) {
            int width = 0;

            for (char c : text.toCharArray()) {
                if ((c < this.charData.length) && (c >= 0)) {
                    width += this.charData[c].width - 8 + this.charOffset;
                }
            }

            return width / 2;
        }

        public boolean isAntiAlias() {
            return this.antiAlias;
        }

        public void setAntiAlias(boolean antiAlias) {
            if (this.antiAlias != antiAlias) {
                this.antiAlias = antiAlias;
                tex = setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
            }
        }

        public boolean isFractionalMetrics() {
            return this.fractionalMetrics;
        }

        public void setFractionalMetrics(boolean fractionalMetrics) {
            if (this.fractionalMetrics != fractionalMetrics) {
                this.fractionalMetrics = fractionalMetrics;
                tex = setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
            }
        }

        public Font getFont() {
            return this.font;
        }

        public void setFont(Font font) {
            this.font = font;
            tex = setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
        }

        protected class CharData {
            public int width;
            public int height;
            public int storedX;
            public int storedY;

            protected CharData() {
            }
        }

    }
}
