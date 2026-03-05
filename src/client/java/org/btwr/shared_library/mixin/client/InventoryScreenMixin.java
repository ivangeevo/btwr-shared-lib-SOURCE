package org.btwr.shared_library.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.btwr.shared_library.recipe.ExtendedShapelessRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void drawExtendedIcon(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        InventoryScreen screen = (InventoryScreen) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        PlayerScreenHandler handler = client.player.playerScreenHandler;
        ExtendedShapelessRecipe recipe = getExtendedRecipe(handler);
        if (recipe == null) return;

        Slot resultSlot = handler.getSlot(0);
        int iconX = screen.x + resultSlot.x + 8;
        int iconY = screen.y + resultSlot.y + 16 + 2;

        drawPlus(context, iconX, iconY);

        // draw tooltip if hovering
        if (isHoveringPlus(mouseX, mouseY, iconX, iconY)) {
            var drops = recipe.getAdditionalDrops(); // List<ItemStack>
            if (!drops.isEmpty()) {
                // Start with a header
                List<Text> tooltipTexts = new ArrayList<>();
                tooltipTexts.add(Text.literal("Additional Drops:")); // <-- your header

                // Then add each drop's name
                drops.forEach(stack -> tooltipTexts.add(stack.getName()));

                context.drawTooltip(client.textRenderer, tooltipTexts, mouseX, mouseY);
            }
        }
    }

    @Unique
    private void drawPlus(DrawContext draw, int iconX, int iconY) {
        var font = MinecraftClient.getInstance().textRenderer;

        // Draw black outline
        draw.drawText(font, "+", iconX + 1, iconY, 0x000000, false);
        draw.drawText(font, "+", iconX - 1, iconY, 0x000000, false);
        draw.drawText(font, "+", iconX, iconY + 1, 0x000000, false);
        draw.drawText(font, "+", iconX, iconY - 1, 0x000000, false);

        // Draw inner bright green plus
        draw.drawText(font, "+", iconX, iconY, 0x80FF20, false);
    }

    @Unique
    private boolean isHoveringPlus(int mouseX, int mouseY, int iconX, int iconY) {
        int size = 8; // size of clickable area
        return mouseX >= iconX - size / 2 && mouseX <= iconX + size / 2
                && mouseY >= iconY - size / 2 && mouseY <= iconY + size / 2;
    }

    @Unique
    private ExtendedShapelessRecipe getExtendedRecipe(PlayerScreenHandler handler) {
        var client = MinecraftClient.getInstance();
        var world = client.world;
        if (world == null) return null;

        CraftingInventory craftingInv = (CraftingInventory) handler.getCraftingInput();

        return world.getRecipeManager().values().stream()
                .map(RecipeEntry::value)
                .filter(r -> r instanceof ExtendedShapelessRecipe)
                .map(r -> (ExtendedShapelessRecipe) r)
                .filter(r -> r.matchesCraftingInventory(craftingInv))
                .findFirst()
                .orElse(null);
    }
}
