package org.btwr.shared_library.api.entity.interfaces.added;

public interface PlayerEntityAdded {

    default int btwr$timesCraftedThisTick() {
        throw new UnsupportedOperationException();
    }

    default void btwr$setTimesCraftedThisTick(int value) {
        throw new UnsupportedOperationException();
    }

}
