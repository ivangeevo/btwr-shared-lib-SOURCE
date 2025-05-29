package btwr.btwr_sl.lib.mixin;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract ComponentMap getComponents();

    @Shadow public abstract Item getItem();

    // sets items to stack up to 16 only
    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void setFoodMaxStackCount(CallbackInfoReturnable<Integer> cir) {
        if (this.getItem().getDefaultStack().isIn(BTWRConventionalTags.Items.STACKS_MAX_16)) {
            cir.setReturnValue(16);
        }
    }

    @Unique
    private boolean isMiscSetCountItem(ItemStack stack) {
        return stack.isOf(Items.BONE) || stack.isOf(Items.ROTTEN_FLESH);
    }

}
