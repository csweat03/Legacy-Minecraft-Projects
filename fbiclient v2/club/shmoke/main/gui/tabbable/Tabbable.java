package club.shmoke.main.gui.tabbable;

import club.shmoke.Client;
import club.shmoke.api.cheat.Category;
import club.shmoke.api.cheat.Cheat;
import club.shmoke.api.cheat.property.Property;
import club.shmoke.api.utility.Utility;
import club.shmoke.api.utility.utilities.Styler;
import club.shmoke.main.events.KeyEvent;
import club.shmoke.main.gui.tabbable.elements.EnumTab;
import club.shmoke.main.gui.tabbable.elements.NumberTab;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Christian
 */
public class Tabbable extends Utility {

    private final List<Tab> CATEGORIES = new ArrayList<>();
    private final List<SubTab> CHEATS = new ArrayList<>(), PROPERTIES = new ArrayList<>();
    private int[] index = {0, 0, 0, 0};
    private Screen screen = Screen.CATEGORY;
    private boolean editingProperties = false, editingNumber = false;
    private int propertyIndex = 0;

    public Tabbable() {
        Arrays.stream(Category.values()).forEach(category -> CATEGORIES.add(new Tab(category)));
        Client.GET.CHEAT_MANAGER.getContents().forEach(cheat -> CHEATS.add(new SubTab(cheat.getCategory(), cheat)));
        CHEATS.forEach(cheat -> PROPERTIES.add(new SubTab(cheat.getCheat(), cheat.getCheat().getProperties())));
    }

    public void render() {
        int x = 2, y = 12;
        int wide = getWidestCategory() + 37;
        int wide2 = getWidestCheat() + 37;
        Gui.drawRect(x, y + (index[0] * 12), x + wide, y + (index[0] * 12) + 12, new Color(175, 175, 175, 195).getRGB());
        Gui.drawRect(x, y, x + wide, y + (CATEGORIES.size() * 12), new Color(20, 20, 20, 175).getRGB());
        for (Tab tab : CATEGORIES)
            font.drawStringWithShadow(Styler.switchCase(tab.getCategory().name()), x + 4, 2 + (y += 12) - 12, -1);

        if (screen != Screen.CATEGORY) {
            y = 12 + (index[0] * 12);
            x += wide + 1;
            for (SubTab subTab : CHEATS) {
                if (subTab.getCheat().getCategory() == getCurrentCategory()) {
                    if (subTab.getCheat() == getCurrentCheat())
                        Gui.drawRect(x, y, x + wide2, y + 12, new Color(175, 175, 175, 195).getRGB());
                    Gui.drawRect(x, y, x + wide2, y + 12, new Color(20, 20, 20, 175).getRGB());
                    font.drawStringWithShadow(Styler.switchCase(subTab.getCheat().getLabel()), x + 4, y + 2, -1);
                    y += 12;
                }
            }
        }
        if (screen != Screen.CATEGORY && screen != Screen.CHEAT) {
            y = 12 + ((index[0] + index[1]) * 12);
            x += wide2 + 1;
            for (SubTab subTab : PROPERTIES) {
                if (subTab.getCheat() == getCurrentCheat()) {
                    ArrayList properties = subTab.getProperties();
                    for (Object obj : properties) {
                        if (!(obj instanceof Property)) continue;
                        Property prop = (Property) obj;

                        if (prop == getCurrentProperty())
                            Gui.drawRect(x, y, x + wide2, y + 12, new Color(175, 175, 175, 195).getRGB());
                        Gui.drawRect(x, y, x + wide2, y + 12, new Color(20, 20, 20, 175).getRGB());
                        font.drawStringWithShadow(Styler.switchCase(prop.getLabel()), x + 4, y + 2, -1);

                        y += 12;
                    }
                }
            }
        }
        if (editingProperties) {
            y = 12 + ((index[0] + index[1]) * 12);
            x += wide2 + 1;

            new SubProperty(getCurrentProperty()).addField("", getCurrentProperty().getValue(), x, y);
        }
    }

    public void onKey(KeyEvent event) {
        if (event.getKey() == Keyboard.KEY_UP)
            up();
        else if (event.getKey() == Keyboard.KEY_DOWN)
            down();
        else if (event.getKey() == Keyboard.KEY_LEFT)
            left();
        else if (event.getKey() == Keyboard.KEY_RIGHT)
            right();
        else if (event.getKey() == Keyboard.KEY_RETURN)
            enter();
    }

    private void up() {
        if (screen == Screen.CATEGORY) {
            if (index[0] <= 0)
                index[0] = CATEGORIES.size() - 1;
            else
                index[0]--;
        } else if (screen == Screen.CHEAT && index[1] > -1) {
            if (index[1] <= 0)
                index[1] = getCheatsInCurrentCategory().size() - 1;
            else
                index[1]--;
        }

        if (editingNumber && ((Integer) getCurrentProperty().getValue() > (Integer) getCurrentProperty().getMin()))
            getCurrentProperty().setValue((Integer) (getCurrentProperty().getValue()) - (Integer) (getCurrentProperty().getInc()));
        else if (screen != Screen.CHEAT && screen != Screen.CATEGORY && screen != Screen.PROPERTY) {
            Enum[] modes = (Enum[]) getCurrentProperty().getValue().getClass().getEnumConstants();
            if (propertyIndex != 0)
                propertyIndex--;
            else
                propertyIndex = modes.length - 1;
            getCurrentProperty().setValue(modes[propertyIndex]);
        }
    }

    private void down() {
        if (screen == Screen.CATEGORY) {
            if (index[0] >= CATEGORIES.size() - 1)
                index[0] = 0;
            else
                index[0]++;
        } else if (screen == Screen.CHEAT) {
            if (index[1] >= getCheatsInCurrentCategory().size() - 1)
                index[1] = 0;
            else
                index[1]++;
        }


        if (editingNumber && ((Integer) getCurrentProperty().getValue() < (Integer) getCurrentProperty().getMax()))
            getCurrentProperty().setValue((Integer) (getCurrentProperty().getValue()) + (Integer) (getCurrentProperty().getInc()));
        else if (screen != Screen.CHEAT && screen != Screen.CATEGORY && screen != Screen.PROPERTY) {
            Enum[] modes = (Enum[]) getCurrentProperty().getValue().getClass().getEnumConstants();
            if (propertyIndex < modes.length - 1)
                propertyIndex++;
            else
                propertyIndex = 0;
            getCurrentProperty().setValue(modes[propertyIndex]);
        }
    }

    private void left() {
        if (screen == Screen.SUBPROPERTY) {
            index[3] = 0;
            editingProperties = editingNumber = false;
            screen = Screen.PROPERTY;
        } else if (screen == Screen.PROPERTY) {
            index[2] = 0;
            screen = Screen.CHEAT;
        } else if (screen == Screen.CHEAT) {
            index[1] = 0;
            screen = Screen.CATEGORY;
        }
    }

    private void right() {
        if (screen == Screen.CATEGORY) screen = Screen.CHEAT;
        else if (screen == Screen.CHEAT && getCurrentCheat().getProperties().size() > 0) screen = Screen.PROPERTY;
        else if (screen == Screen.PROPERTY) {
            screen = Screen.SUBPROPERTY;
            editingProperties = true;
        }
    }

    private void enter() {
        if (screen == Screen.CHEAT) {
            getCurrentCheat().toggle();
        } else if (screen == Screen.SUBPROPERTY) {
            for (Property properties : getCurrentCheat().getProperties()) {
                if (properties.getValue() instanceof Boolean) {
                    properties.setValue(!(Boolean) properties.getValue());
                } else if (properties.getValue() instanceof Integer || properties.getValue() instanceof Double) {
                    editingNumber = !editingNumber;
                }
            }
        }
    }

    private Property getCurrentProperty() {
        return getPropertiesInCurrentCheat().size() > 0 ? getPropertiesInCurrentCheat().get(index[2]) : null;
    }

    private Cheat getCurrentCheat() {
        return getCheatsInCurrentCategory().get(index[1]);
    }

    private Category getCurrentCategory() {
        return CATEGORIES.get(index[0]).getCategory();
    }

    private List<Property> getPropertiesInCurrentCheat() {
        return new ArrayList<>(getCurrentCheat().getProperties());
    }

    private List<Cheat> getCheatsInCurrentCategory() {
        List<Cheat> cheats = new ArrayList<>();
        for (Cheat cheat : Client.GET.CHEAT_MANAGER.getContents())
            if (cheat.getCategory() == getCurrentCategory()) cheats.add(cheat);
        return cheats;
    }

    private int getWidestCheat() {
        int width = 0;
        for (Cheat cheat : Client.GET.CHEAT_MANAGER.getContents()) {
            int cWidth = font.getStringWidth(cheat.getLabel());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

    private int getWidestCategory() {
        int width = 0;
        for (Category c : Category.values()) {
            String name = c.name();
            int cWidth = font.getStringWidth(
                    name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

    public enum Screen {
        CATEGORY, CHEAT, PROPERTY, SUBPROPERTY
    }

    private class SubProperty {

        private Property property;
        private int y = 0;

        public SubProperty(Property property) {
            this.property = property;
        }

        public Property getProperty() {
            return property;
        }

        private void addField(String label, Object prop, int startX, int startY) {
            y += startY;

            int width = font.getStringWidth(Styler.switchCase(label + prop));

            if (property.getValue() instanceof Enum) new EnumTab(property.getCheat(), property).draw(startX, startY);
            if (property.getValue() instanceof Number)
                new NumberTab(property.getCheat(), property).draw(startX, startY);

//            Gui.drawRect(startX, y, startX + width + 10, y + 12, new Color(175, 175, 175, 195).getRGB());
//            Gui.drawRect(startX, y, startX + width + 10, y + 12, new Color(20, 20, 20, 175).getRGB());
//            font.drawStringWithShadow(Styler.switchCase(label + prop), startX + 4, y + 2, -1);

        }
    }

}
