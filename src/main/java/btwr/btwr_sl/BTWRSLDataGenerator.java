package btwr.btwr_sl;

import btwr.btwr_sl.lib.datagen.BTWRSL_BlockTagProvider;
import btwr.btwr_sl.lib.datagen.BTWRSL_ItemTagProvider;
import btwr.btwr_sl.lib.datagen.BTWRSL_LangGenerator;
import btwr.btwr_sl.lib.datagen.BTWRSL_TestRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BTWRSLDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(BTWRSL_BlockTagProvider::new);
        pack.addProvider(BTWRSL_ItemTagProvider::new);
        pack.addProvider(BTWRSL_LangGenerator::new);
        pack.addProvider(BTWRSL_TestRecipeProvider::new);

    }

}
