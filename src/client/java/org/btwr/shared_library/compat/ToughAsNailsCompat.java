package org.btwr.shared_library.compat;

import net.fabricmc.loader.api.FabricLoader;
import org.btwr.shared_library.gui.hud.PenaltyDisplayManager;
import org.btwr.shared_library.gui.hud.StatusBarRenderInfo;
import toughasnails.core.ToughAsNails;
import toughasnails.init.ModConfig;

public class ToughAsNailsCompat {
    public static void init() {
        // Penalty text compatibility checks
        if (FabricLoader.getInstance().isModLoaded(ToughAsNails.MOD_ID)) {
            PenaltyDisplayManager.HudYOffsetRegistry.register(((player, info) ->  {
                int offset = 0;

                // Offset the text based on thirst bar if the player is not in water (because air renders over thirst)
                if (ModConfig.thirst.enableThirst && !StatusBarRenderInfo.getInstance().getRenderingAir(player)) {
                    offset -= 10;
                }

                return offset;
            }));
        }
    }
}