package org.btwr.shared_library.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BTWRSL_TestRecipeProvider extends FabricRecipeProvider {

    public BTWRSL_TestRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        /**
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, Items.OAK_PLANKS, 4)
                .additionalDrop(Items.PUMPKIN_SEEDS, 2)
                .additionalDrop(Items.GOLD_INGOT)
                .withToolDamage(20)
                .input(Items.OAK_LOG)
                .input(Items.IRON_AXE)
                .criterion("has_oak_log", conditionsFromItem(Items.OAK_LOG))
                .offerTo(exporter, Identifier.of(BTWRSLMod.MOD_ID, "oak_planks_from_tool_crafting"));
         **/

    }

}