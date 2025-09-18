package btwr.btwr_sl.lib.datagen;

import btwr.btwr_sl.BTWRSLMod;
import btwr.btwr_sl.lib.recipe.TestShapelessRecipeJsonBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BTWRSL_TestRecipeProvider extends FabricRecipeProvider {

    public BTWRSL_TestRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        TestShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.OAK_PLANKS, 4)
                .input(Items.OAK_LOG)
                .input(Items.IRON_AXE)
                .criterion("has_oak_log", conditionsFromItem(Items.OAK_LOG))
                .offerTo(exporter, Identifier.of(BTWRSLMod.MOD_ID, "oak_planks_from_tool_crafting"));

        /**
        // Test recipe: oak planks with axe
        OGToolCraftingShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.OAK_PLANKS, 4)
                .input(Blocks.OAK_LOG)
                .tool(BTWRConventionalTags.Items.AXES_MAKE_PLANKS,100)
                .additionalDrop(Items.COAL)
                .additionalDrop(Items.LADDER, 3)
                .criterion("has_oak_log", conditionsFromItem(Items.OAK_LOG))
                .offerTo(exporter, Identifier.of(BTWRSLMod.MOD_ID, "oak_planks_from_tool_crafting"));
         **/

    }

}
