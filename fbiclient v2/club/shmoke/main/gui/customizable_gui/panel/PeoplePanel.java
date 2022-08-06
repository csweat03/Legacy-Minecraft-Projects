package club.shmoke.main.gui.customizable_gui.panel;

import club.shmoke.Client;
import club.shmoke.api.utility.Utility;
import club.shmoke.main.gui.customizable_gui.GuiElements;
import club.shmoke.main.gui.overriden.CustomTextField;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class PeoplePanel extends GuiScreen {

    private GuiElements gui = Client.GET.PLAYER_GUI;

    private CustomTextField message;

    private int WIDTH = 125, HEIGHT = 200;
    private boolean dragging;

    private long lastMS = System.currentTimeMillis();

    private int[] dragDist = {0, 0};

    private Utility utility = new Utility();

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        draw(mouseX, mouseY);
    }

    public void draw(int mouseX, int mouseY) {

        if (message == null) {
            message = new CustomTextField(height / 2 + 8, gui.getX(), gui.getY() + HEIGHT - 50, WIDTH - 6, 16);
            message.setMaxStringLength(250);
            Keyboard.enableRepeatEvents(true);
        }

        if (dragging) {
            gui.setX(mouseX + dragDist[0]);
            gui.setY(mouseY + dragDist[1]);
        }


        if (message != null) {
            message.xPosition = gui.getX() + 3;
            message.yPosition = gui.getY() + HEIGHT - 47;
        }

        if (gui.isPinned())
            Gui.drawRect(gui.getX() - 1, gui.getY() - 1, gui.getX() + WIDTH + 1, gui.getY() + HEIGHT + 1, new Color(225, 225, 225, 65).getRGB());
        Gui.drawRect(gui.getX(), gui.getY(), gui.getX() + WIDTH, gui.getY() + 15, new Color(35, 41, 46).getRGB());
        Gui.drawRect(gui.getX(), gui.getY() + 15, gui.getX() + WIDTH, gui.getY() + HEIGHT, new Color(24, 25, 29).getRGB());

        createButton();
        createPlayerSlot();

        if (message != null)
            message.drawTextBox();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton != 0) return;

        if (mouseX >= gui.getX() && mouseX <= gui.getX() + WIDTH && mouseY >= gui.getY() && mouseY <= gui.getY() + HEIGHT - 30) {
            if (System.currentTimeMillis() < lastMS + 200)
                gui.setPinned(!gui.isPinned());
            lastMS = System.currentTimeMillis();
        }

        if (isHoveringPanel(mouseX, mouseY)) {
            dragging = true;
            dragDist = new int[]{gui.getX() - mouseX, gui.getY() - mouseY};
        }

        if (mouseX >= gui.getX() && mouseX <= gui.getX() + WIDTH && mouseY >= gui.getY() + HEIGHT - 30 && mouseY <= gui.getY() + HEIGHT)
            gui.setPane(gui.getPane() == 1 ? 0 : 1);

        if (message != null)
            message.mouseClicked(mouseX, mouseY, mouseButton);

    }

    @Override
    protected void keyTyped(char character, int key) throws IOException {
        super.keyTyped(character, key);

        if (message == null) return;

        if ((character == '\t') && (message.isFocused()))
            message.setFocused(!message.isFocused());

        message.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        dragging = false;

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    private boolean isHoveringPanel(int mouseX, int mouseY) {
        return mouseX >= gui.getX() && mouseX <= gui.getX() + WIDTH && mouseY >= gui.getY() && mouseY <= gui.getY() + 15 && mouseX != 0 && mouseY != 0;
    }

    private void createPlayerSlot() {
        int curY = 0;

        if (gui.getPane() == 0) {
            for (EntityPlayer player : Client.GET.FRIEND_MANAGER.getFriends().keySet()) {
                if (gui.getLookupTimer().hasReached(1000) && !gui.getPlayerHeads().contains(utility.renderUtility.fetchHeadImage(player))) {
                    gui.getPlayerHeads().add(utility.renderUtility.fetchHeadImage(player));
                    gui.getLookupTimer().reset();
                }

                Gui.drawRect((gui.getX() + 1), gui.getY() + 16 + curY, gui.getX() + WIDTH - 1, gui.getY() + 16 + 22 + curY, new Color(50, 56, 61).getRGB());

                utility.renderUtility.drawImage(gui.getPlayerHeads().get(gui.getPlayerHeads().indexOf(utility.renderUtility.fetchHeadImage(player))), gui.getX() + 2, gui.getY() + 15 + 2 + curY, 20, 20, true);

                Client.GET.FONT_UTILITY.def.drawString(player.getName(), gui.getX() + 1 + 23, gui.getY() + 18 + curY, 0xffdddddd);
                if (false)
                    Client.GET.FONT_UTILITY.smaller.drawString(String.format("playing \247c%s", "{server}"), gui.getX() + 1 + 25, gui.getY() + 29 + curY, 0xffdddddd);
            }
        }
    }

    private void createButton() {
        int y = gui.getY() + HEIGHT - 30;
        Gui.drawRect(gui.getX(), y, gui.getX() + WIDTH, y + 1, new Color(29, 30, 34).getRGB());

        if (gui.getPane() == 1 && gui.getButtonValue() < WIDTH / 2) {
            if (gui.getR() < 210) gui.setR(gui.getR() + 4);
            if (gui.getG() < 214) gui.setG(gui.getG() + 4);
            if (gui.getB() < 218) gui.setB(gui.getB() + 4);
            gui.setButtonValue(gui.getButtonValue() + 1);
        } else if (gui.getPane() == 0 && gui.getButtonValue() > 0) {
            if (gui.getR() > 54) gui.setR(gui.getR() - 4);
            if (gui.getG() > 50) gui.setG(gui.getG() - 4);
            if (gui.getB() > 55) gui.setB(gui.getB() - 4);
            gui.setButtonValue(gui.getButtonValue() - 1);
        }

        /* This will not change */
        Gui.drawRect(gui.getX() + 1, y + 1, gui.getX() + WIDTH - 1, y + 30 - 1, new Color(30, 36, 41).getRGB());

        Gui.drawRect(gui.getX() + 2 + gui.getButtonValue(), y + 2, gui.getX() + (WIDTH - 2) / 2 + gui.getButtonValue(), y + 30 - 2, 0xffbdbdbd);

        Client.GET.FONT_UTILITY.def.drawCenteredString("Friends", gui.getX() + (WIDTH - 2) / 2 / 2, y + 30 / 2, gui.getColor().getRGB());
        Client.GET.FONT_UTILITY.def.drawCenteredString("Rivals", gui.getX() + (WIDTH - 2) / 2 * 1.52f, y + 30 / 2, new Color(255 - gui.getR(), 255 - gui.getG(), 255 - gui.getB(), 255).getRGB());
    }
}
