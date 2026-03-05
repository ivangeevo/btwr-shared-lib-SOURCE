package org.btwr.shared_library.mixin.added;

import net.minecraft.item.*;
import org.btwr.shared_library.api.item.interfaces.added.ItemAdded;
import org.btwr.shared_library.api.util.CustomUseAction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ItemAddedMixin implements ItemAdded {

    @Override
    public CustomUseAction btwr$getCustomUseAction(ItemStack stack) {
        return CustomUseAction.NONE;
    }

    @Override
    public int btwr$getItemUseWarmupDuration() {
        return 7;
    }

}