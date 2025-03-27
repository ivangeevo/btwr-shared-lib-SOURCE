package btwr.btwr_sl.lib.gui;

import net.minecraft.client.MinecraftClient;

public interface HUDInitializeListener {
    void init(MinecraftClient client, PenaltyDisplayManager displayManager);
}
