package btwr.btwrsl;

import btwr.btwrsl.lib.datagen.BTWRSL_BlockTagProvider;
import btwr.btwrsl.lib.datagen.BTWRSL_ItemTagProvider;
import btwr.btwrsl.lib.datagen.BTWRSL_LangGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BTWRSLDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(BTWRSL_BlockTagProvider::new);
        pack.addProvider(BTWRSL_ItemTagProvider::new);
        pack.addProvider(BTWRSL_LangGenerator::new);

    }

}
