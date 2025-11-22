package org.btwr.shared_library.data;

import net.minecraft.entity.Entity;

public class UpdateRequiringData<T extends Entity> implements EntityAttachmentBase<T> {
    private boolean updated;

    @Override
    public void markDirty() {
        this.updated = true;
    }

    @Override
    public boolean isDirty() {
        if (!this.updated) {
            return false;
        }
        this.updated = false;
        return true;
    }
}
