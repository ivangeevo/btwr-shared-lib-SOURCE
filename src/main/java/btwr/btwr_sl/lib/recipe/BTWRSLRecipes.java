package btwr.btwr_sl.lib.recipe;

import btwr.btwr_sl.BTWRSLMod;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BTWRSLRecipes {

    public static final DisabledRecipe.Serializer DISABLED_RECIPE_SERIALIZER = new DisabledRecipe.Serializer();
    public static final RecipeType<DisabledRecipe> DISABLED_RECIPE_TYPE = new RecipeType<>() {};

    public static final RecipeType<CraftingRecipe> TEST_SHAPELESS_RECIPE_TYPE = new RecipeType<>() {};
    public static final TestShapelessRecipe.Serializer TEST_SHAPELESS_RECIPE_SERIALIZER = new TestShapelessRecipe.Serializer();

    public static void register() {
        registerRecipe("disabled", DISABLED_RECIPE_TYPE, DISABLED_RECIPE_SERIALIZER);
        // Test shapeless recipe
        registerRecipe("test_crafting_shapeless", TEST_SHAPELESS_RECIPE_TYPE, TEST_SHAPELESS_RECIPE_SERIALIZER);
    }

    private static void registerRecipe(String path, RecipeType<?> type, RecipeSerializer<?> serializer) {
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(BTWRSLMod.MOD_ID, path), type);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(BTWRSLMod.MOD_ID, path), serializer);
    }

}
