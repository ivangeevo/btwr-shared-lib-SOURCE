package org.btwr.shared_library.api.entity.interfaces.added;

public interface LivingEntityAdded {

    default void btwr$setItemUseTime(int iCount) {
        throw new UnsupportedOperationException();
    }

}