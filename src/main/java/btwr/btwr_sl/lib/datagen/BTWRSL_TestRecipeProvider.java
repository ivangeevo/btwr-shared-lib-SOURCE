package btwr.btwr_sl.lib.datagen;

import btwr.btwr_sl.BTWRSLMod;
import btwr.btwr_sl.lib.recipe.old.CraftingWithToolShapelessRecipe;
import btwr.btwr_sl.lib.recipe.old.CraftingWithToolShapelessRecipeJsonBuilder;
import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
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

        // Test recipe: oak planks with axe
        CraftingWithToolShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, Items.OAK_PLANKS, 4)
                .toolInput(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
                .additionalDrop(Items.COAL_ORE, 4)
                .additionalDrop(Items.LADDER)
                .input(Items.OAK_LOG)
                .criterion("has_oak_log", conditionsFromItem(Items.OAK_LOG))
                .offerTo(exporter, Identifier.of(BTWRSLMod.MOD_ID, "oak_planks_from_crafting_with_tool"));

        /**
        CraftingWithToolShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, Items.OAK_PLANKS, 4)
                .input(Blocks.OAK_LOG)
                //.input()
                .additionalDrop(Items.COAL)
                .additionalDrop(Items.LADDER, 3)
                .criterion("has_oak_log", conditionsFromItem(Items.OAK_LOG))
                .offerTo(exporter, Identifier.of(BTWRSLMod.MOD_ID, "oak_planks_from_crafting_with_tool"));
         **/

    }

}
