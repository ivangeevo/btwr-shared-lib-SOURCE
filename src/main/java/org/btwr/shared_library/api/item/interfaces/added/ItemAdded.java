package org.btwr.shared_library.api.item.interfaces.added;

import net.minecraft.item.ItemStack;
import org.btwr.shared_library.api.util.CustomUseAction;

public interface ItemAdded {

    default int btwr$getItemUseWarmupDuration() {
        throw new UnsupportedOperationException();
    }

    default CustomUseAction btwr$getCustomUseAction(ItemStack stack) {
        throw new UnsupportedOperationException();
    }

}