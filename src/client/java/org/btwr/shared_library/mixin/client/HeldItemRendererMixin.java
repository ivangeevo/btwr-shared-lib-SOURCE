package org.btwr.shared_library.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.btwr.shared_library.api.util.CustomUseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Shadow protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);
    @Shadow protected abstract void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);
    @Shadow public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
    private void injectProgressiveCraftUseAction(
            AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress,
            ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light, CallbackInfo ci
    ) {
        // Only handle our custom item — fall through to vanilla for everything else
        if (item.btwr$getCustomUseAction() == CustomUseAction.PROGRESSIVE_CRAFT) {
            boolean bl = hand == Hand.MAIN_HAND;
            Arm arm = bl ? player.getMainArm() : player.getMainArm().getOpposite();
            boolean bl2 = arm == Arm.RIGHT;

            matrices.push();

            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                this.applyProgressiveCraftTransformations(matrices, tickDelta, arm, item, player);
                float activeEquipProgress = (player.isUsingItem() && player.getActiveHand() == hand) ? 0.3F : equipProgress;
                this.applyEquipOffset(matrices, arm, activeEquipProgress);
                //this.applyEquipOffset(matrices, arm, equipProgress);
            } else {
                // Idle / swing state — standard item bob, no special offset
                float n   = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
                float mxx = 0.2F  * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) (Math.PI * 2));
                float fxx = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
                int o = bl2 ? 1 : -1;
                matrices.translate((float) o * n, mxx, fxx);
                this.applyEquipOffset(matrices, arm, equipProgress);
                this.applySwingOffset(matrices, arm, swingProgress);
            }

            this.renderItem(
                    player,
                    item,
                    bl2 ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
                    !bl2,
                    matrices,
                    vertexConsumers,
                    light
            );

            matrices.pop();
            ci.cancel();
        }
    }

    @Unique
    private void applyProgressiveCraftTransformations(
            MatrixStack matrices,
            float tickDelta,
            Arm arm,
            ItemStack stack,
            PlayerEntity player
    ) {
        int itemUseTimeLeft = player.getItemUseTimeLeft();
        int maxUseTime = stack.getMaxUseTime(player);
        int timeUsed = maxUseTime - itemUseTimeLeft;

        // Bob — gated behind warmup unlike vanilla
        float rawTime = (float) itemUseTimeLeft - tickDelta + 1.0F;
        if (timeUsed >= stack.getItem().btwr$getItemUseWarmupDuration()) {
            float bob = MathHelper.abs(MathHelper.cos(rawTime / 4.0F * (float) Math.PI) * 0.1F);
            matrices.translate(0.0F, bob, 0.0F);
        }

        // Raise — clamped to first 32 ticks instead of vanilla's full-duration pow curve
        int clampedUseCount = MathHelper.clamp(32 - timeUsed, 0, 32);
        float f = (float) clampedUseCount - tickDelta + 1.0F;
        float g = f / 32.0F;

        // 9x cubic easing (BTW original)
        g = g * g * g;
        g = g * g * g;
        g = g * g * g;

        float raise = 1.0F - g;
        int i = arm == Arm.RIGHT ? 1 : -1;

        matrices.translate(raise * 0.6F * (float) i, raise * -0.5F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) i * raise * 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(raise * 10.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) i * raise * 30.0F));
    }
}