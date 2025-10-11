package btwr.btwr_sl.lib.emi;

import btwr.btwr_sl.BTWRSLMod;
import btwr.btwr_sl.lib.recipe.BTWRSLRecipes;
import btwr.btwr_sl.lib.recipe.ExtendedShapelessRecipe;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.handler.CraftingRecipeHandler;
import dev.emi.emi.runtime.EmiReloadLog;
import net.minecraft.block.Blocks;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class BTWRSLEmiPlugin implements EmiPlugin
{
    public static EmiRecipeCategory CRAFTING_SHAPELESS_WITH_TOOL = category("crafting_shapeless_extended", EmiStack.of(Blocks.CRAFTING_TABLE));


    public static EmiRecipeCategory category(String id, EmiStack icon) {
        return new EmiRecipeCategory(Identifier.of(BTWRSLMod.MOD_ID, id), icon, icon::render);
    }

    @Override
    public void register(EmiRegistry registry) {
        this.registerCraftingShapelessWithTool(registry);
    }

    private void registerCraftingShapelessWithTool(EmiRegistry registry) {
        registry.addCategory(CRAFTING_SHAPELESS_WITH_TOOL);

        registry.addWorkstation(CRAFTING_SHAPELESS_WITH_TOOL, EmiStack.of(Blocks.CRAFTING_TABLE));
        registry.addRecipeHandler(ScreenHandlerType.CRAFTING, new CraftingRecipeHandler());

        for (ExtendedShapelessRecipe recipe : getRecipes(registry, BTWRSLRecipes.EXTENDED_SHAPELESS_RECIPE_TYPE)) {
            addRecipeSafe(registry, () -> new EmiExtendedShapelessRecipe(recipe), recipe);
        }
    }

    private static void addRecipeSafe(EmiRegistry registry, Supplier<EmiRecipe> supplier, Recipe<?> recipe) {
        try {
            registry.addRecipe(supplier.get());
        } catch (Throwable e) {
            EmiReloadLog.warn("Exception thrown when parsing vanilla a" + BTWRSLMod.MOD_ID + "recipe" + EmiPort.getId(recipe));
            EmiReloadLog.error(e);
        }
    }

    private static <C extends RecipeInput, T extends Recipe<C>> Iterable<T> getRecipes(EmiRegistry registry, RecipeType<T> type) {
        return registry.getRecipeManager().listAllOfType(type).stream().map(RecipeEntry::value)::iterator;
    }
}
