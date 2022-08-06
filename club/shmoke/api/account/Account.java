package club.shmoke.api.account;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

public final class Account {
    private final String username;
    private String status;
    private String mask;
    private String password;
    private ResourceLocation head;

    public Account(String username, String password) {
        this(username, password, "", "Unchecked");
    }

    public Account(String username, String password, String mask, String status) {
        this.mask = "";
        this.username = username;
        this.password = password;
        this.mask = mask;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public ResourceLocation getHead() {
        this.loadHead();
        return head;
    }

    private void loadHead() {
        if (head == null) {
            head = new ResourceLocation("heads/" + mask);
            ThreadDownloadImageData texture;
            if (mask == "")
                texture = new ThreadDownloadImageData(null, "https://minotar.net/avatar/Steve", null, null);
            else
                texture = new ThreadDownloadImageData(null, String.format("https://minotar.net/avatar/%s", mask), null, null);
            Minecraft.getMinecraft().getTextureManager().loadTexture(head, texture);
        }
    }
}
