package btwr.btwrsl;

import btwr.btwrsl.lib.recipe.BTWRSLRecipes;
import btwr.btwrsl.lib.util.PlaceableAsBlock;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRSLMod implements ModInitializer
{
    public static final String MOD_ID = "btwr-sl";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        BTWRSLRecipes.init();

    }


}
