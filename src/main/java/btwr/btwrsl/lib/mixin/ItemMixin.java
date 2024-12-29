package btwr.btwrsl.lib.mixin;

import btwr.btwrsl.lib.util.PlaceableAsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin
{

    @Unique
    private static final PlaceableAsBlock item = PlaceableAsBlock.getInstance();

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectedUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        cir.setReturnValue(item.placeAsBlock(context));
    }

    @Inject(method = "onCraftByPlayer", at = @At("HEAD"))
    private void onOnCraft(ItemStack stack, World world, PlayerEntity player, CallbackInfo ci) {
        BlockPos thisPos = player.getBlockPos();
        SoundEvent craftingSound;
        float volume;
        float pitch;

        if ((Item)(Object)this instanceof ToolItem toolItem) {
            ToolMaterial material = toolItem.getMaterial();

            if (player.timesCraftedThisTick() == 0 && world.isClient) {

                // Different crafting sounds based on the tool material
                if (material == ToolMaterials.WOOD) {
                    craftingSound = SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
                    volume = 0.1f;
                    pitch = 1.25f + (world.random.nextFloat() * 0.25f);
                } else if (material == ToolMaterials.STONE) {
                    craftingSound = SoundEvents.BLOCK_ANVIL_LAND;
                    volume = 0.1f;
                    pitch = world.random.nextFloat() * 0.25f + 1.75f;
                } else {
                    craftingSound = SoundEvents.BLOCK_ANVIL_USE;
                    volume = 0.5f;
                    pitch = world.random.nextFloat() * 0.25f + 1.25f;
                }

                world.playSound(player, thisPos, craftingSound, SoundCategory.BLOCKS, volume, pitch);
                player.tick();
            }
        }

    }
}
