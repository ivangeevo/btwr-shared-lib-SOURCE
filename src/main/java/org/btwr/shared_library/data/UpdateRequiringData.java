package org.btwr.shared_library.data;

import net.minecraft.entity.Entity;

/**
 * Simple attachment implementation that tracks a one-shot "dirty" flag.
 *
 * Once marked dirty, the flag stays set until the next `isDirty()` call,
 * which consumes and clears it.
 * <p>Useful for attachments that only need to signal “something changed since last check”.
 */
public class UpdateRequiringData<T extends Entity> implements EntityAttachmentBase<T> {
    // True when the attachment has been modified and requires processing/sync.
    private boolean updated;

    /**
     * Mark the attachment as modified.
     * The next call to `isDirty()` will return true.
     */
    @Override
    public void markDirty() {
        this.updated = true;
    }

    /**
     * Returns true once after the attachment was marked dirty.
     * Calling this clears the flag.
     */
    @Override
    public boolean isDirty() {
        if (!this.updated) {
            return false;
        }
        this.updated = false;
        return true;
    }
}
