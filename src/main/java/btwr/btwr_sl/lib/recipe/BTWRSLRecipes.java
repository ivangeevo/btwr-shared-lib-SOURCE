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

    public static final TestShapelessRecipe.Serializer TEST_SHAPELESS_RECIPE_SERIALIZER = new TestShapelessRecipe.Serializer();
    public static final RecipeType<TestShapelessRecipe> TEST_SHAPELESS_RECIPE_TYPE = new RecipeType<>() {};

    public static final CraftingWithToolShapelessRecipe.Serializer CRAFTING_WITH_TOOL_SHAPELESS_RECIPE_SERIALIZER = new CraftingWithToolShapelessRecipe.Serializer();
    public static final RecipeType<CraftingWithToolShapelessRecipe> CRAFTING_WITH_TOOL_SHAPELESS_RECIPE_TYPE = new RecipeType<>() {};

    public static void register() {
        // Disabled recipe used for disabling existing recipes
        registerRecipe("disabled", DISABLED_RECIPE_TYPE, DISABLED_RECIPE_SERIALIZER);

        // Test recipe
        registerRecipe("test_crafting_shapeless", TEST_SHAPELESS_RECIPE_TYPE, TEST_SHAPELESS_RECIPE_SERIALIZER);

        // Crafting with tools in shapeless recipes
        registerRecipe("crafting_shapeless_with_tool", CRAFTING_WITH_TOOL_SHAPELESS_RECIPE_TYPE, CRAFTING_WITH_TOOL_SHAPELESS_RECIPE_SERIALIZER);
    }

    private static void registerRecipe(String path, RecipeType<?> type, RecipeSerializer<?> serializer) {
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(BTWRSLMod.MOD_ID, path), type);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(BTWRSLMod.MOD_ID, path), serializer);
    }

}
