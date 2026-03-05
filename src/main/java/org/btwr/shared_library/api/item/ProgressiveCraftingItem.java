package org.btwr.shared_library.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.btwr.shared_library.api.util.CustomUseAction;

public class ProgressiveCraftingItem extends Item {

    public static final int PROGRESS_TIME_INTERVAL = 4;
    public static final int DEFAULT_MAX_DAMAGE = (120 * 20 / PROGRESS_TIME_INTERVAL);

    public ProgressiveCraftingItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        player.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public CustomUseAction btwr$getCustomUseAction(ItemStack stack) {
        return CustomUseAction.PROGRESSIVE_CRAFT;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        int useCount = user.getItemUseTimeLeft();

        if (getMaxUseTime(stack, user) - useCount > btwr$getItemUseWarmupDuration()) {

            if (useCount % 4 == 0) {
                playCraftingFX(stack, world, user);
            }

            if (!world.isClient && (useCount & PROGRESS_TIME_INTERVAL) == 0) {
                int damage = stack.getDamage();

                damage -= 1;

                if (damage > 0) {
                    stack.setDamage(damage);
                }
                else {
                    // set item usage to immediately complete
                    user.btwr$setItemUseTime(1);
                }
            }
        }
    }

    /** Effects that happen during the progressive crafting process **/
    protected void playCraftingFX(ItemStack stack, World world, LivingEntity player) {
    }

    protected int getProgressiveCraftingMaxDamage() {
        return DEFAULT_MAX_DAMAGE;
    }

}