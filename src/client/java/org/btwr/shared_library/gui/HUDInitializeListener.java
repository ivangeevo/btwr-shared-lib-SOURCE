package org.btwr.shared_library.gui;

import net.minecraft.client.MinecraftClient;

public interface HUDInitializeListener {
    void init(MinecraftClient client, PenaltyDisplayManager displayManager);
}
