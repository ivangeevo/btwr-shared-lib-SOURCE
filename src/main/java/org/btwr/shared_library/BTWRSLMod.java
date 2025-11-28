package org.btwr.shared_library;

import org.btwr.shared_library.recipe.BTWRSLRecipes;
import net.fabricmc.api.ModInitializer;
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
        //BTWRSounds.register();
        BTWRSLRecipes.register();
    }

}