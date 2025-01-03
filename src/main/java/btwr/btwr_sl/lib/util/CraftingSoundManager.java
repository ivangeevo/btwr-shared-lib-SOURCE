package btwr.btwr_sl.lib.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CraftingSoundManager {

    public static void playCraftingSound(ItemStack stack, PlayerEntity player) {
        World world = player.getWorld();
        BlockPos thisPos = player.getBlockPos();
        SoundEvent sound = null;
        float volume = 0.1F;
        float pitch = 1.0F;

        // Loop through the enum to find the matching tag
        for (CraftingSoundConfig config : CraftingSoundConfig.values()) {
            if (stack.isIn(config.getTag())) {
                sound = config.getSound();
                volume = config.getVolume();
                pitch = config.getBasePitch() + (world.random.nextFloat() * config.getPitchVariance());
                break;
            }
        }

        // Material-based sound handling (for tools)
        if ((stack.getItem() instanceof ToolItem toolItem)) {
            ToolMaterial material = toolItem.getMaterial();
            if (material == ToolMaterials.WOOD) {
                sound = SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
                pitch = 1.25F + world.random.nextFloat() * 0.25F;
            } else if (material == ToolMaterials.STONE) {
                sound = SoundEvents.BLOCK_ANVIL_LAND;
                pitch = world.random.nextFloat() * 0.25F + 1.75F;
            } else {
                sound = SoundEvents.BLOCK_ANVIL_USE;
                volume = 0.5F;
                pitch = world.random.nextFloat() * 0.25F + 1.25F;
            }
        }

        // Play the sound if one was found
        if (sound != null && world.isClient) {
            world.playSound(player, thisPos, sound, SoundCategory.BLOCKS, volume, pitch);
        }
    }
}
