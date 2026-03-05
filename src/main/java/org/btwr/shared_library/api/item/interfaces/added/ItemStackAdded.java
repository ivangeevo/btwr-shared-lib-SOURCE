package org.btwr.shared_library.api.item.interfaces.added;


import org.btwr.shared_library.api.util.CustomUseAction;

public interface ItemStackAdded {

    default CustomUseAction btwr$getCustomUseAction() {
        throw new UnsupportedOperationException();
    }

}