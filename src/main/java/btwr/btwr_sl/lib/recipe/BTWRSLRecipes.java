package btwr.btwr_sl.lib.recipe;

import btwr.btwr_sl.BTWRSLMod;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BTWRSLRecipes {

    public static final DisabledRecipe.Serializer DISABLED_RECIPE_SERIALIZER = new DisabledRecipe.Serializer();
    public static final RecipeType<DisabledRecipe> DISABLED_RECIPE_TYPE = new RecipeType<>() {};

    public static final ExtendedShapelessRecipe.Serializer EXTENDED_SHAPELESS_RECIPE_SERIALIZER = new ExtendedShapelessRecipe.Serializer();
    public static final RecipeType<ExtendedShapelessRecipe> EXTENDED_SHAPELESS_RECIPE_TYPE = new RecipeType<>() {};

    public static void register() {
        // Disabled recipe used for disabling existing recipes
        registerRecipe("disabled", DISABLED_RECIPE_TYPE, DISABLED_RECIPE_SERIALIZER);

        // Extended shapeless recipe
        registerRecipe("crafting_shapeless_extended", EXTENDED_SHAPELESS_RECIPE_TYPE, EXTENDED_SHAPELESS_RECIPE_SERIALIZER);
    }

    private static void registerRecipe(String path, RecipeType<?> type, RecipeSerializer<?> serializer) {
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(BTWRSLMod.MOD_ID, path), type);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(BTWRSLMod.MOD_ID, path), serializer);
    }

}
