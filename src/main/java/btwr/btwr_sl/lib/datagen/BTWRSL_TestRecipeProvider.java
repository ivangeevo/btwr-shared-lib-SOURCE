package btwr.btwr_sl.lib.datagen;

import btwr.btwr_sl.BTWRSLMod;
import btwr.btwr_sl.lib.recipe.ToolCraftingShapelessRecipeJsonBuilder;
import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BTWRSL_TestRecipeProvider extends FabricRecipeProvider {


    public BTWRSL_TestRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        /**
        // Test oak planks with axe recipe
        ToolCraftingShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.OAK_PLANKS, 4)
                .input(Items.OAK_LOG)
                .input(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
                .damagedOnCrafting(Ingredient.fromTag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS))
                .additionalDrop(Items.BIRCH_LEAVES)
                .additionalDrop(Items.COAL)
                .criterion("has_oak_log", conditionsFromItem(Items.OAK_LOG))
                .offerTo(exporter, Identifier.of(BTWRSLMod.MOD_ID, "oak_planks_from_tool_crafting"));
         **/

         }

}
