package btwr.btwr_sl.lib.mixin;

import btwr.btwr_sl.lib.event.EventHUDInitialized;
import btwr.btwr_sl.lib.gui.PenaltyDisplayManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
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
        PenaltyDisplayManager dm = PenaltyDisplayManager.getInstance();
        dm.setRenderingFood(true);
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
