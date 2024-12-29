package btwr.btwr_sl;

import btwr.btwr_sl.lib.recipe.BTWRSLRecipes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRSLMod implements ModInitializer
{
    public static final String MOD_ID = "btwr_sl";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        BTWRSLRecipes.init();

    }


}
