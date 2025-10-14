package btwr.btwr_sl.lib.mixin.recipe;

import btwr.btwr_sl.lib.recipe.BTWRSLRecipes;
import btwr.btwr_sl.lib.util.CraftingResultSlotModHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingResultSlot.class)
public abstract class CraftingResultSlotMixin {
    
    @Shadow @Final private RecipeInputInventory input;

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    protected void setAdditionalDrops(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        MinecraftServer server = player.getWorld().getServer();
        if (server != null) {
            CraftingResultSlotModHandler.getInstance().dropAdditionalItemsOnTake(server, player, this.input);
        }
    }

    @Inject(method = "onTakeItem", at = @At("TAIL"))
    protected void setTickCraftLogic(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        player.setTimesCraftedThisTick(player.timesCraftedThisTick() + 1);
    }

    @Inject(
            method = "onTakeItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/slot/CraftingResultSlot;onCrafted(Lnet/minecraft/item/ItemStack;)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    protected void addRemainderOnTake(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        var server = player.getWorld().getServer();
        if (server == null) return;

        var positioned = this.input.createPositionedRecipeInput();
        var craftingInput = positioned.input();

        var optional = server.getRecipeManager()
                .getFirstMatch(BTWRSLRecipes.EXTENDED_SHAPELESS_RECIPE_TYPE, craftingInput, player.getWorld());

        if (optional.isPresent()) {
            CraftingResultSlotModHandler.getInstance().setRemainderForTools(player, this.input);
            ci.cancel(); // skip vanilla remainder logic
        }
    }
}

