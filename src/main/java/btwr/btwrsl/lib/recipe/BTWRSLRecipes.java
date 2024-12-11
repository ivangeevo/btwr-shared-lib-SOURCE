package btwr.btwrsl.lib.recipe;

import btwr.btwrsl.BTWRSLMod;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BTWRSLRecipes {
    public static final DisabledRecipe.Serializer DISABLED_RECIPE_SERIALIZER = new DisabledRecipe.Serializer();
    public static final RecipeType<DisabledRecipe> DISABLED_RECIPE_TYPE = new RecipeType<>() {};

    public static void init() {
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(BTWRSLMod.MOD_ID, "disabled"), DISABLED_RECIPE_TYPE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(BTWRSLMod.MOD_ID,"disabled"), DISABLED_RECIPE_SERIALIZER);
    }
}
