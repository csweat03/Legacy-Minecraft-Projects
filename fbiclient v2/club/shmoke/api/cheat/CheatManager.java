package club.shmoke.api.cheat;

import club.shmoke.api.event.EventHandler;
import club.shmoke.api.utility.utilities.ManagerUtility;
import club.shmoke.main.cheats.fight.Antibot;
import club.shmoke.main.cheats.fight.FastBow;
import club.shmoke.main.cheats.fight.Killaura;
import club.shmoke.main.cheats.misc.*;
import club.shmoke.main.cheats.move.Flight;
import club.shmoke.main.cheats.move.Speed;
import club.shmoke.main.cheats.user.*;
import club.shmoke.main.cheats.view.AntiEffects;
import club.shmoke.main.cheats.view.HUD;
import club.shmoke.main.cheats.view.GUI;
import club.shmoke.main.events.KeyEvent;
import club.shmoke.main.events.ServerEvent;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Christian
 */
public class CheatManager extends ManagerUtility<Cheat> {

    public void initialize() {
        addContent(
                new Sprint(), new HUD(), new Speed(), new Step(), new Jesus(), new Flight(), new Damage(),
                new Velocity(), new FastBow(), new Ping(), new Killaura(), new Scaffold(), new Antibot(),
                new Cleaner(), new NoSlow(), new NoFall(), new NoRotate(), new AutoTool(), new AntiEffects(),
                new Spin(), new GUI());
    }

    @EventHandler
    public void onKey(KeyEvent event) {
        for (Cheat cheat : getContents())
            if (event.getKey() == cheat.getKey() && (!cheat.getShift() || GuiScreen.isShiftKeyDown())) cheat.toggle();
    }

    @EventHandler
    public void onServer(ServerEvent event) {
        if (event.getType() != ServerEvent.Type.JOIN) return;

        PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
        packetbuffer.writeInt(0);
        int l33t = 751;
        for (int i = 0; i < l33t; i++)
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("bur|using", packetbuffer));
    }

    public List<Cheat> getSorted() {
        List<Cheat> cheats = new ArrayList<>(getContents());
        cheats.sort(Comparator.comparing(Cheat::getWidth).reversed());
        return cheats;
    }
}
