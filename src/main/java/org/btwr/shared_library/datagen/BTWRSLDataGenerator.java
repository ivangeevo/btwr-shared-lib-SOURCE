package org.btwr.shared_library.datagen;

import org.btwr.shared_library.datagen.provider.BTWRSL_BlockTagProvider;
import org.btwr.shared_library.datagen.provider.BTWRSL_ItemTagProvider;
import org.btwr.shared_library.datagen.provider.BTWRSL_LangGenerator;
import org.btwr.shared_library.datagen.provider.BTWRSL_TestRecipeProvider;
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