package org.btwr.shared_library.api.registry;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Public API for disabling recipe types at the data-loading stage.
 * Call {@link #disable(Identifier)} or {@link #disable(RecipeType)} from your
 * mod's initializer to prevent recipes of that type from being loaded.
 *
 * <p>Example usage:
 * <pre>{@code
 * // by direct reference
 * DisabledRecipeTypeRegistry.disable(RecipeType.SMELTING);
 *
 * // by identifier — works even if that type belongs to a mod not on your classpath
 * DisabledRecipeTypeRegistry.disable(Identifier.of("someothermod", "alloying"));
 * }</pre>
 */
public class DisabledRecipeTypeRegistry {

    private static final Set<Identifier> DISABLED = ConcurrentHashMap.newKeySet();

    private DisabledRecipeTypeRegistry() {}

    /** Disable by identifier. Safe to call even if the type isn't registered yet
     * (e.g. disabling a type from a mod that loads after yours).
     */
    public static void disable(Identifier id) {
        DISABLED.add(id);
    }

    /** Disable by direct reference. **/
    public static void disable(RecipeType<?> type) {
        Identifier id = Registries.RECIPE_TYPE.getId(type);
        if (id != null) DISABLED.add(id);
    }

    public static void enable(RecipeType<?> type) {
        Identifier id = Registries.RECIPE_TYPE.getId(type);
        if (id != null) DISABLED.remove(id);
    }

    public static boolean isDisabled(Identifier id) {
        return DISABLED.contains(id);
    }

    public static boolean isDisabled(RecipeType<?> type) {
        Identifier id = Registries.RECIPE_TYPE.getId(type);
        return id != null && DISABLED.contains(id);
    }

    public static boolean isEmpty() {
        return DISABLED.isEmpty();
    }

    public static Set<Identifier> getDisabledTypes() {
        return Collections.unmodifiableSet(DISABLED);
    }

}