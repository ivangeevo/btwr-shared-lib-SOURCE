package org.btwr.shared_library.mixin.client;

import org.btwr.shared_library.event.EventHUDInitialized;
import org.btwr.shared_library.gui.hud.PenaltyDisplayManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.btwr.shared_library.gui.hud.StatusBarRenderInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    public abstract TextRenderer getTextRenderer();

    /**
     * Checks to see if renderFood was called and not intercepted,
     * indicating that hunger has been successfully rendered
     */
    @Inject(method = "renderFood", at = @At("TAIL"))
    private void renderFoodCheck(DrawContext context, PlayerEntity player, int top, int right, CallbackInfo ci) {
        StatusBarRenderInfo info = StatusBarRenderInfo.getInstance();
        info.setRenderingFood(true);
    }

    /**
     * Checks to see if renderMountHealth was called and not intercepted,
     * indicating that mount health has been successfully rendered
     */
    @Inject(method = "renderMountHealth", at = @At("TAIL"))
    private void renderMountHealthCheck(DrawContext context, CallbackInfo ci) {
        StatusBarRenderInfo info = StatusBarRenderInfo.getInstance();
        info.setRenderingMountHealth(true);
    }

    /**
     * Checks to see if renderArmor was called and not intercepted,
     * indicating that armor has been successfully rendered
     */
    @Inject(method = "renderArmor", at = @At("TAIL"))
    private static void renderArmorCheck(DrawContext context, PlayerEntity player, int i, int j, int k, int x, CallbackInfo ci) {
        StatusBarRenderInfo info = StatusBarRenderInfo.getInstance();
        info.setRenderingArmor(true);
    }

    /**
     * Checks to see if renderStatusBars after the air texture check was called and not intercepted,
     * indicating that air has been successfully rendered
     */
    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V", shift = At.Shift.AFTER))
    private void renderAirCheck(DrawContext context, CallbackInfo ci) {
        StatusBarRenderInfo info = StatusBarRenderInfo.getInstance();
        info.setRenderingAir(true);
    }

    /**
     * Injects penalty status rendering into the vanilla status bar renderer
     */
    @Inject(method = "renderStatusBars", at = @At("HEAD"))
    private void injectedRender(DrawContext context, CallbackInfo ci) {
        TextRenderer renderer = getTextRenderer();
        PenaltyDisplayManager dm = PenaltyDisplayManager.getInstance();

        dm.render(context, renderer);
    }

    /**
     * Sends out event for other mods to register their penalties
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void sendEvent(MinecraftClient client, CallbackInfo ci) {
        EventHUDInitialized.hudInitialized(client);
    }

}
