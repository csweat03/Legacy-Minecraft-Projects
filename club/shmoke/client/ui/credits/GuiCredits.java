package club.shmoke.client.ui.credits;

import club.shmoke.client.Client;
import club.shmoke.client.util.render.RenderUtils;
import net.minecraft.client.gui.*;

import java.util.ArrayList;
import java.util.List;

public class GuiCredits extends GuiScreen {

    private List<String> credits = new ArrayList<>();

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 0)
            mc.displayGuiScreen(new GuiMainMenu());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int y = 0;
        String title = "Credits";
        addCredit("MaDMeDiaz#3378", "Helped w/ Hypixel Default Flight");
        addCredit("Kant#2835", "Gave Hypixel Criticals & Autoblock");
        addCredit("Jacobtread#3770", "Dragon Wings");
        RenderUtils.drawClientBackground();
        Gui.drawBorderedRect(RenderUtils.getResolution().getScaledWidth() / 2 - 155, 30, RenderUtils.getResolution().getScaledWidth() / 2 + 155, RenderUtils.getResolution().getScaledHeight() - 30, 2, -1, 0xaa111111);
        Client.INSTANCE.getFontManager().cl.drawCenteredStringWithShadow(title, width / 2, 5, -1);
        for (String text : credits) {
            Client.INSTANCE.getFontManager().c20.drawCenteredStringWithShadow(text, width / 2, 38 + y, -1);
            y += 15;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void addCredit(String name, String credit) {
        if (!credits.contains(name + ": " + credit))
            credits.add(name + ": " + credit);
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);
        buttonList.add(new GuiButton(0, sr.getScaledWidth() / 2 - 48, sr.getScaledHeight() - 26, 98, 20, "Done"));
    }
}
