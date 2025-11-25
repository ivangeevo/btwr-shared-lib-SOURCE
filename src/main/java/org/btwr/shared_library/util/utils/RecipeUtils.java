package org.btwr.shared_library.util.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import org.btwr.shared_library.recipe.DisabledRecipe;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/** Utility interface for shared recipe methods
 * <p> Implement this class into your recipe provider/generator class if you want to access the helper methods
 * */
public interface RecipeUtils {
    /**
     * Simplified wrappers for common namespaces.
     */
    default void disableVanilla(RecipeExporter exporter, String recipeId) {
        disableRecipe(exporter, "minecraft", recipeId);
    }

    default void disableVanilla(RecipeExporter exporter, ItemConvertible item) {
        disableRecipe(exporter, "minecraft", item);
    }

    default void disableVanilla(RecipeExporter exporter, ItemConvertible item, String suffix) {
        disableRecipe(exporter, "minecraft", item, suffix);
    }

    default void disableBTWR(RecipeExporter exporter, String recipeId) {
        disableRecipe(exporter, "org/btwr", recipeId);
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

    /** Disables a recipe by ItemConvertible **/
    default void disableRecipe(RecipeExporter exporter, String namespace, ItemConvertible item) {
        String path = Registries.ITEM.getId(item.asItem()).getPath();
        disableRecipe(exporter, namespace, path);
    }

    /**
     * Disables a recipe for an item, with optional suffix.
     */
    default void disableRecipe(RecipeExporter exporter, String namespace, ItemConvertible item, String suffix) {
        String path = Registries.ITEM.getId(item.asItem()).getPath();
        if (suffix != null && !suffix.isEmpty()) path += suffix;
        disableRecipe(exporter, namespace, path);
    }

    /** Helper method to extract wood type from an item's translation key **/
    default String extractName(Item item) {
        String[] parts = item.getTranslationKey().split("\\.");
        return parts[parts.length - 1];
    }
    /** Helper method to extract wood type from a block's translation key **/
    default String extractName(Block block) {
        String[] parts = block.getTranslationKey().split("\\.");
        return parts[parts.length - 1];
    }

    default String extractName(TagKey<?> tag) {
        String[] parts = tag.getTranslationKey().split("\\.");
        return parts[parts.length - 1];
    }
}