package org.btwr.shared_library.data;

import net.minecraft.entity.Entity;

public interface EntityAttachmentBase<T extends Entity> {

    default void tick(T entity) {
    }

    default boolean isDirty() {
        return false;
    }

    default void markDirty() {
    }
}