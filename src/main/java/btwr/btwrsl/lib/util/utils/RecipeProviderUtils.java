package btwr.btwrsl.lib.util.utils;

import btwr.btwrsl.lib.recipe.DisabledRecipe;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/** Utility interface for shared recipe methods */
public interface RecipeProviderUtils {

    /**
     * Simplified wrappers for common namespaces.
     */
    default void disableVanilla(RecipeExporter exporter, String recipeId) {
        disableRecipe(exporter, "minecraft", recipeId);
    }

    default void disableVanilla(RecipeExporter exporter, ItemConvertible item, String suffix) {
        disableRecipe(exporter, "minecraft", item, suffix);
    }

    default void disableBTWR(RecipeExporter exporter, String recipeId) {
        disableRecipe(exporter, "btwr", recipeId);
    }

    default void disableBWT(RecipeExporter exporter, String recipeId) {
        disableRecipe(exporter, "bwt", recipeId);
    }

    default void disableTE(RecipeExporter exporter, String recipeId) {
        disableRecipe(exporter, "tough_environment", recipeId);
    }

    default void disableVG(RecipeExporter exporter, String recipeId) {
        disableRecipe(exporter, "vegehenna", recipeId);
    }

    /**
     * Disables a recipe by namespace and ID.
     */
    default void disableRecipe(RecipeExporter exporter, String namespace, String recipeId) {
        exporter.accept(Identifier.of(namespace, recipeId), new DisabledRecipe(), null);
    }

    /**
     * Disables a recipe for an item, with optional suffix.
     */
    default void disableRecipe(RecipeExporter exporter, String namespace, ItemConvertible item, String suffix) {
        String path = Registries.ITEM.getId(item.asItem()).getPath();
        if (suffix != null && !suffix.isEmpty()) path += suffix;
        disableRecipe(exporter, namespace, path);
    }

    /**
     * Utility methods to fetch items by ID.
     */
    default Item grabRaw(Identifier id) {
        return Registries.ITEM.get(id);
    }

    default Item grabRaw(String itemID) {
        return Registries.ITEM.get(Identifier.ofVanilla(itemID));
    }

    default Item grabRaw(String namespace, String itemID) {
        return Registries.ITEM.get(Identifier.of(namespace, itemID));
    }
}
