package club.shmoke.client.ui.click.themes.other;

import org.lwjgl.input.Mouse;
import club.shmoke.client.Client;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.client.ui.click.Element;
import club.shmoke.client.ui.click.GuiClick;
import club.shmoke.client.ui.click.Theme;
import club.shmoke.client.ui.click.themes.other.elements.Panel;
import club.shmoke.client.ui.click.themes.other.elements.cheat.CheatButton;
import club.shmoke.client.util.render.RenderUtils;

import java.io.BufferedWriter;
import java.io.IOException;

public class OtherTheme extends Theme {

    public OtherTheme() {
        super("Other");
    }

    @Override
    public void insert() {
        if (getPanelList().isEmpty()) {
            Panel cheatPanel = new Panel();
            CheatButton cheatButton;
            for (Cheat.Type type : Cheat.Type.values()) {
                for (Cheat cheat : Client.INSTANCE.getCheatManager().getContents()) {
                    cheatButton = new CheatButton(cheatPanel, cheat);
                    cheatPanel.elements.add(cheatButton);
                }
                getPanelList().remove(new club.shmoke.client.ui.click.themes.horizon.elements.other.Panel(type.label()));
            }
            cheatPanel.setPosX(RenderUtils.getResolution().getScaledWidth() - RenderUtils.getResolution().getScaledWidth() + 100);
            cheatPanel.setPosY(RenderUtils.getResolution().getScaledHeight() - RenderUtils.getResolution().getScaledHeight() + 50);
            getPanelList().add(cheatPanel);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (Element e : getPanelList()) {
            e.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Element e : getPanelList()) {
            Panel p = (Panel) e;
            p.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Element e : getPanelList()) {
            Panel p = (Panel) e;
            p.update();
            p.drawElement(0, 0, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (int i = getPanelList().size() - 1; i >= 0; i--) {
            Panel p = (Panel) getPanelList().get(i);
//            if (p.isOverPanel(mouseX, mouseY)) {
//                Panel toMove = (Panel) getPanelList().get(getPanelList().size() - 1);
//                getPanelList().set(getPanelList().indexOf(p), toMove);
//                getPanelList().set(getPanelList().size() - 1, p);
            p.mouseClicked(mouseX, mouseY, button);
//                break;
//            }
        }
    }

    @Override
    public void handleMouseInput() {
        int direction = Mouse.getEventDWheel();
        //for (Element p : getPanelList()) {
        // ((Panel) p).handleMouseInput(direction);
        //}
    }

    @Override
    public void updatePanel(String category, float posx, float posy, int maxh, boolean mnmzd) {
        for (Element e : getPanelList()) {
            Panel p = (Panel) e;

//            if (p.category.toLowerCase().replaceAll(" ", "")
//                    .equalsIgnoreCase(category.toLowerCase().replaceAll(" ", ""))) {
//                p.setPosX(posx);
//                p.setPosY(posy);
//                p.maxHeight = maxh;
//                p.minimized = mnmzd;
//            }
        }
    }

    @Override
    public void writeSave(BufferedWriter writer) throws IOException {
        for (Element e : GuiClick.getTheme().getPanelList()) {
//            Panel p = (Panel) e;
//            writer.write(p.category + " = ");
//            writer.write(p.getPosX() + ", " + p.getPosY() + ", " + p.maxHeight + ", " + p.minimized);
//            writer.newLine();
        }
    }
}