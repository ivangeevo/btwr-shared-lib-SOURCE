package org.btwr.shared_library.api.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.btwr.shared_library.BTWRSLMod;

import java.util.HashMap;
import java.util.Map;

public class HeadDropRegistry {

    private static final Map<EntityType<? extends LivingEntity>, Item> HEAD_DROPS_MAP = new HashMap<>();

    public static void registerDrop(EntityType<? extends LivingEntity> entityType, Item drop) {
        if (HEAD_DROPS_MAP.containsKey(entityType)) {
            BTWRSLMod.LOGGER.warn("Head drop already registered for: {}", entityType);
            return;
        }
        HEAD_DROPS_MAP.put(entityType, drop);
    }

    public static void registerDefaults() {
        HEAD_DROPS_MAP.put(EntityType.ZOMBIE, Items.ZOMBIE_HEAD);
        HEAD_DROPS_MAP.put(EntityType.SKELETON, Items.SKELETON_SKULL);
        HEAD_DROPS_MAP.put(EntityType.CREEPER, Items.CREEPER_HEAD);
        HEAD_DROPS_MAP.put(EntityType.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL);
        HEAD_DROPS_MAP.put(EntityType.PLAYER, Items.PLAYER_HEAD);
    }

    public static ItemStack getHeadForEntity(LivingEntity entity) {
        Item item = HEAD_DROPS_MAP.get(entity.getType());
        return item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }
}
