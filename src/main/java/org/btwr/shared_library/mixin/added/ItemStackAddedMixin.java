package org.btwr.shared_library.mixin.added;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.btwr.shared_library.api.item.interfaces.added.ItemStackAdded;
import org.btwr.shared_library.api.util.CustomUseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackAddedMixin implements ItemStackAdded {

    @Shadow public abstract Item getItem();

    @Override
    public CustomUseAction btwr$getCustomUseAction() {
        return this.getItem().btwr$getCustomUseAction((ItemStack)(Object)this);
    }

}