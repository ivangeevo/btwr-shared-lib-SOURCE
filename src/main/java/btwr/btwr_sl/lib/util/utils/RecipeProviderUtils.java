package btwr.btwr_sl.lib.util.utils;

import btwr.btwr_sl.lib.recipe.DisabledRecipe;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/** Utility interface for shared recipe methods */
public interface RecipeProviderUtils {

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

    /** Helper method to extract wood type from an item's translation key **/
    default String extractName(Item item) {
        String[] parts = item.getTranslationKey().split("\\.");
        return parts[parts.length - 1];
    }

    default String extractName(Block block) {
        String[] parts = block.getTranslationKey().split("\\.");
        return parts[parts.length - 1];
    }

    default String extractName(TagKey<?> tag) {
        String[] parts = tag.getTranslationKey().split("\\.");
        return parts[parts.length - 1];
    }

    class ID
    {
        public static Identifier ofMC(String item) { return Identifier.ofVanilla(item); }
        /** DS - Datapack Suite **/
        public static Identifier ofDS(String item) { return Identifier.of("btwr-ds", item); }
        /** BTWR: Core **/
        public static Identifier ofBTWR(String item) { return Identifier.of("btwr", item); }
        /** BWT - Better With Time **/
        public static Identifier ofBWT(String item)
        {
            return Identifier.of("bwt", item);
        }
        public static Identifier ofTE(String item) { return Identifier.of("tough_environment", item); }
        public static Identifier ofST(String item) { return Identifier.of("sturdy_trees", item); }
        public static Identifier ofSS(String item) { return Identifier.of("self_sustainable", item); }
        public static Identifier ofVG(String item) { return Identifier.of("vegehenna", item); }

    }
}
