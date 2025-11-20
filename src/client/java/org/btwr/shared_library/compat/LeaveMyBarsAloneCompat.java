package org.btwr.shared_library.compat;

import fuzs.leavemybarsalone.LeaveMyBarsAlone;
import fuzs.leavemybarsalone.config.ClientConfig;
import net.fabricmc.loader.api.FabricLoader;
import org.btwr.shared_library.gui.hud.PenaltyDisplayManager;
import org.btwr.shared_library.gui.hud.StatusBarRenderInfo;

public class LeaveMyBarsAloneCompat {
    public static void init() {
        // Penalty text compatibility checks
        if (FabricLoader.getInstance().isModLoaded(LeaveMyBarsAlone.MOD_ID)) {
            PenaltyDisplayManager.HudYOffsetRegistry.register(((player, info) ->  {
                int offset = 0;
                boolean keepFoodBar = LeaveMyBarsAlone.CONFIG.get(ClientConfig.class).foodBar;

                if (keepFoodBar) {
                    if (StatusBarRenderInfo.getInstance().getRenderingMountHealth(player)) {
                        offset -= 10;
                    }
                }

                return offset;
            }));
        }
    }
}