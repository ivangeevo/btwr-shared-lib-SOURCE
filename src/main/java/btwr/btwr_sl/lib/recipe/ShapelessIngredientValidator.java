package btwr.btwr_sl.lib.recipe;

import com.mojang.serialization.DataResult;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

/**
 * <b># Extracts the validation rules for shapeless recipe ingredients.</b>
 *
 * <p>Vanilla inlines these checks directly in the codec, which makes extension messy.
 * This interface separates that concern so mods can swap in different rules without
 * rewriting the whole recipe serializer.
 *
 * <p><b>The default validator enforces the vanilla rules:</b>
 * <p>- Recipe must have at least one ingredient.
 * <p>- Recipe cannot exceed 9 ingredients (fits the crafting grid).
 */
public interface ShapelessIngredientValidator {
    DataResult<DefaultedList<Ingredient>> validate(List<Ingredient> ingredients);

    static ShapelessIngredientValidator defaultValidator() {
        return ingredients -> {
            Ingredient[] filtered = ingredients.stream()
                    .filter(ingredient -> !ingredient.isEmpty())
                    .toArray(Ingredient[]::new);

            if (filtered.length == 0) {
                return DataResult.error(() -> "No ingredients for shapeless recipe");
            }
            if (filtered.length > 9) {
                return DataResult.error(() -> "Too many ingredients for shapeless recipe");
            }

            return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, filtered));
        };
    }
}
