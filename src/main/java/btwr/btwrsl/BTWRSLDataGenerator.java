package btwr.btwrsl;

import btwr.btwrsl.lib.datagen.BTWRSLBlockTagProvider;
import btwr.btwrsl.lib.datagen.BTWRSLItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BTWRSLDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(BTWRSLBlockTagProvider::new);
        pack.addProvider(BTWRSLItemTagProvider::new);

    }

}
