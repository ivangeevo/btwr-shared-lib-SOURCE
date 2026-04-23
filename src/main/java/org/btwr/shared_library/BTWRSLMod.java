package org.btwr.shared_library;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.btwr.shared_library.api.block.util.OnFireConversionBlockMappings;
import org.btwr.shared_library.api.config.ConfigGroup;
import org.btwr.shared_library.api.config.TomlConfigManager;
import org.btwr.shared_library.api.item.ProgressiveCraftingItem;
import org.btwr.shared_library.recipe.BTWRSLRecipes;
import net.fabricmc.api.ModInitializer;
import org.btwr.shared_library.util.HeadDropRegistry;
import org.btwr.shared_library.util.RepairRecipeBlockedRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRSLMod implements ModInitializer {

    public static final String MOD_ID = "btwr_sl";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static BTWRSLMod instance;

    public static BTWRSLMod getInstance() {
        return instance;
    }

    @Override
    public void onInitialize() {
        instance = this;

        // Reload all configs when a server instance starts (SP or dedicated)
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            for (ConfigGroup group : TomlConfigManager.getAllGroups()) {
                group.load();
            }
        });

        //BTWRSounds.register();
        BTWRSLRecipes.register();
        OnFireConversionBlockMappings.register();

        // Block progressive crafting items recipes for all repair type recipes
        RepairRecipeBlockedRegistry.registerClass(ProgressiveCraftingItem.class, RepairRecipeBlockedRegistry.BlockType.ALL);

        // Register default pairs for head drops per entity type
        HeadDropRegistry.registerDefaults();
    }

}