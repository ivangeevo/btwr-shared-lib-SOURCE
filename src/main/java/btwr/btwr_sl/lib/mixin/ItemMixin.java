package btwr.btwr_sl.lib.mixin;

import btwr.btwr_sl.lib.util.CraftingSoundManager;
import btwr.btwr_sl.lib.util.PlaceableAsBlock;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements FabricItem {

    @Unique
    private static final PlaceableAsBlock item = PlaceableAsBlock.getInstance();

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectedUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        cir.setReturnValue(item.placeAsBlock(context));
    }

    @Inject(method = "onCraftByPlayer", at = @At("HEAD"))
    private void onOnCraftByPlayer(ItemStack stack, World world, PlayerEntity player, CallbackInfo ci) {
        CraftingSoundManager.getInstance().playCraftingSound(stack, world, player);
    }
}
