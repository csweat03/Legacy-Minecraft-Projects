package club.shmoke.client.ui.click;

import java.io.IOException;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.shader.ShaderGroup;
import shadersmod.client.Shaders;
import club.shmoke.client.Client;
import club.shmoke.client.ui.click.themes.horizon.HorizonTheme;

public class GuiClick extends GuiScreen {
	
    int y = 0;

    public static Theme getTheme() {
        return theme;
    }
    public static int color = 0xFFD11EFF;
    public static void setTheme(Theme theme) {
        GuiClick.theme = theme;
    }

    private static Theme theme = new HorizonTheme();

    public static void insert() {
        theme.insert();
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        try {
            super.keyTyped(typedChar, keyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        theme.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        theme.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	this.color = Client.INSTANCE.color.getRGB();
        theme.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {       
      theme.mouseClicked(mouseX, mouseY, button);       
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        try {
            theme.handleMouseInput();
        } catch (Exception e){
            e.printStackTrace();
        }
        super.handleMouseInput();
    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.stopUseShader();
    }

    @Override
    public void initGui() {
        mc.entityRenderer.func_175069_a(EntityRenderer.shaderResourceLocations[18]);
    }
}
