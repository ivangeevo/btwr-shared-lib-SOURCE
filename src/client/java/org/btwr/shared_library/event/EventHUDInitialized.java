package org.btwr.shared_library.event;

import org.btwr.shared_library.BTWRSLMod;
import org.btwr.shared_library.gui.HUDInitializeListener;
import org.btwr.shared_library.gui.PenaltyDisplayManager;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;

public class EventHUDInitialized {

    /**
     * List of HUD initialization listeners
     */
    private static ArrayList<HUDInitializeListener> hudListeners = new ArrayList<>();

    /**
     * Registers a HUD init listener
     * @param listener Listener to register
     */
    public static void register(HUDInitializeListener listener) {
        BTWRSLMod.LOGGER.info("Registering listeners in {}", listener.getClass().getSimpleName());
        hudListeners.add(listener);
    }

    /**
     * Called from mixin when HUD is initialized
     */
    public static void hudInitialized(MinecraftClient client) {
        for (HUDInitializeListener listener : hudListeners) {
            BTWRSLMod.LOGGER.info("Initializing penalties in {}", listener.getClass().getSimpleName());
            listener.init(client, PenaltyDisplayManager.getInstance());
        }
    }

}
