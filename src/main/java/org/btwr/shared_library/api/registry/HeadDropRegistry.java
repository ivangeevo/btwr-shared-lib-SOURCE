package org.btwr.shared_library.api.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.btwr.shared_library.BTWRSLMod;

import java.util.HashMap;
import java.util.Map;

/**
 * A registry for mapping entity types to their corresponding head drop items.
 *
 * <p>This registry is designed to be used by multiple mods. Any mod can register
 * its own custom mob head drops by calling {@link #registerDrop(EntityType, Item)}
 * during initialization.
 *
 * <p>Default vanilla head drops are registered via {@link #registerDefaults()},
 * which should be called once during the shared library's initialization.
 *
 * <p>Example usage from another mod:
 * <pre>{@code
 * // In your mod's onInitialize():
 * HeadDropRegistry.registerDrop(EntityType.BLAZE, MyItems.BLAZE_HEAD);
 * }</pre>
 */
public class HeadDropRegistry {

    private static final Map<EntityType<? extends LivingEntity>, Item> HEAD_DROPS_MAP = new HashMap<>();

    /**
     * Registers a head drop item for the given entity type.
     *
     * @param entityType the entity type to register a head drop for
     * @param drop       the item to drop as a head
     */
    public static void registerDrop(EntityType<? extends LivingEntity> entityType, Item drop) {
        if (HEAD_DROPS_MAP.containsKey(entityType)) {
            BTWRSLMod.LOGGER.warn("Head drop already registered for: {}", entityType);
            return;
        }
        HEAD_DROPS_MAP.put(entityType, drop);
    }

    /**
     * Registers the default vanilla head drops.
     */
    public static void registerDefaults() {
        HEAD_DROPS_MAP.put(EntityType.SKELETON, Items.SKELETON_SKULL);
        HEAD_DROPS_MAP.put(EntityType.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL);
        HEAD_DROPS_MAP.put(EntityType.PLAYER, Items.PLAYER_HEAD);
        HEAD_DROPS_MAP.put(EntityType.ZOMBIE, Items.ZOMBIE_HEAD);
        HEAD_DROPS_MAP.put(EntityType.CREEPER, Items.CREEPER_HEAD);
        HEAD_DROPS_MAP.put(EntityType.PIGLIN, Items.PIGLIN_HEAD);
    }

    /**
     * Returns the head drop item for the given entity
     *
     * <p>Always returns a fresh {@link ItemStack} instance so callers can safely
     * modify it (e.g. applying a skull owner profile for player heads) without
     * affecting other usages.
     *
     * @param entity the living entity to look up a head drop for
     * @return a new {@link ItemStack} of the registered head item, or
     *         {@link ItemStack#EMPTY} if none is registered
     */
    public static ItemStack getHeadForEntity(LivingEntity entity) {
        Item item = HEAD_DROPS_MAP.get(entity.getType());
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }
}