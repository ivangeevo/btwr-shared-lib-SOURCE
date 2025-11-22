package org.btwr.shared_library.data;

import net.minecraft.entity.Entity;

/**
 * Base interface for per-entity attached data.
 *
 * Implementations store extra state for a specific entity type and can
 * participate in ticking and sync/serialization through the dirty flag.
 */
public interface EntityAttachmentBase<T extends Entity> {

    /**
     * Indicates whether this attachment's data has changed and needs syncing or saving.
     * <p>Implementations are expected to track this themselves.
     */
    default boolean isDirty() {
        return false;
    }

    /**
     * Mark the attachment as modified.
     * Call this whenever you mutate stored state.
     */
    default void markDirty() {
    }

    /**
     * Called every tick for the attached entity.
     * Use this to update internal state or run timed logic.
     */
    default void tick(T entity) {
    }


}
