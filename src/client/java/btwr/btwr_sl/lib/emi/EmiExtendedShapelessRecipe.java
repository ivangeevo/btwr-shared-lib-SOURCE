package btwr.btwr_sl.lib.emi;

import btwr.btwr_sl.lib.recipe.ExtendedShapelessRecipe;
import btwr.btwr_sl.tag.BTWRConventionalTags;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.recipe.EmiShapelessRecipe;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

/**
 * EMI wrapper for CraftingWithToolShapelessRecipe.
 *
 * - Shows main output (handled by super)
 * - Shows additional drops as extra output slots
 * - Attempts to mark/display the tool ingredient(s) (by scanning ingredients for the tool tag)
 * - Exposes helper to apply tool damage programmatically
 */
public class EmiExtendedShapelessRecipe extends EmiShapelessRecipe {
    private final ExtendedShapelessRecipe recipe;
    private final int toolDamage;
    private final DefaultedList<ItemStack> additionalDrops;

    // You can replace this with your own green cross texture if you have one.
    private static final EmiTexture GREEN_PLUS = new EmiTexture(EmiPort.id("textures/gui/green_plus.png"),
            0, 0, 8, 8);

    public EmiExtendedShapelessRecipe(ExtendedShapelessRecipe recipe) {
        // CraftingWithToolShapelessRecipe extends ShapelessRecipe so EmiShapelessRecipe ctor works
        super((ShapelessRecipe) recipe);
        this.recipe = recipe;
        this.toolDamage = recipe.getToolDamage();
        this.additionalDrops = recipe.getAdditionalDrops();
    }

    /**
     * Return the configured tool damage amount.
     */
    public int getToolDamage() {
        return toolDamage;
    }

    /**
     * Return a copy of additional drops list (ItemStack copies) so callers can't mutate original list.
     */
    public List<ItemStack> getAdditionalDrops() {
        List<ItemStack> copies = new ArrayList<>(additionalDrops.size());
        for (ItemStack s : additionalDrops) copies.add(s.copy());
        return copies;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        super.addWidgets(widgets);

        // Add the small "+" indicator widget if there are extra drops
        if (!additionalDrops.isEmpty()) {
            int iconX = 92 + 20;
            int iconY = 14 + 32;

            widgets.add(new Widget() {
                @Override
                public Bounds getBounds() {
                    return new Bounds(iconX, iconY, 8, 8);
                }

                @Override
                public void render(DrawContext draw, int mouseX, int mouseY, float delta) {
                    var font = MinecraftClient.getInstance().textRenderer;
                    // draw black outline
                    draw.drawText(font, "+", iconX + 1, iconY, 0, false);
                    draw.drawText(font, "+", iconX - 1, iconY, 0, false);
                    draw.drawText(font, "+", iconX, iconY + 1, 0, false);
                    draw.drawText(font, "+", iconX, iconY - 1, 0, false);
                    // draw inner bright green "+"
                    draw.drawText(font, "+", iconX, iconY, 0x80FF20, false);
                }

                @Override
                public List<TooltipComponent> getTooltip(int mouseX, int mouseY) {
                    List<TooltipComponent> tips = new ArrayList<>();
                    tips.add(TooltipComponent.of(Text.literal("§aAdditional Drops:").asOrderedText()));
                    for (ItemStack s : additionalDrops) {
                        if (!s.isEmpty()) {
                            OrderedText line = Text.literal("  " + s.getCount() + "x " + s.getName().getString()).asOrderedText();
                            tips.add(TooltipComponent.of(line));
                        }
                    }
                    return tips;
                }
            });
        }
    }

    /**
     * Include additional drops in the displayed outputs so EMI's output list includes them.
     */
    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> outs = new ArrayList<>(super.getOutputs());
        for (ItemStack s : additionalDrops) {
            if (s != null && !s.isEmpty()) outs.add(EmiStack.of(s));
        }
        return outs;
    }

    /**
     * Helper to programmatically apply configured tool damage to a tool ItemStack.
     * Returns the post-damage ItemStack (may be EMPTY if broken).
     *
     * Note: your recipe's getRemainder already implements remainder/damage logic for crafting.
     * This helper can be used in other contexts where you want to apply the same damage behavior.
     */
    public ItemStack applyToolDamage(ItemStack toolStack) {
        if (toolStack == null || toolStack.isEmpty()) return ItemStack.EMPTY;

        // If the tool is damageable we mutate its damage value, else return original.
        if (toolStack.isDamageable() || toolStack.getMaxDamage() > 0) {
            ItemStack copy = toolStack.copy();
            int newDamage = copy.getDamage() + this.toolDamage;
            if (newDamage >= copy.getMaxDamage()) {
                // tool broken -> return empty
                return ItemStack.EMPTY;
            } else {
                copy.setDamage(newDamage);
                return copy;
            }
        }

        // Not damageable / no-op
        return toolStack.copy();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return super.getCategory();
    }

    @Override
    public String toString() {
        return "EmiCraftingWithToolShapelessRecipe[" + recipe + ", toolDamage=" + toolDamage + ", drops=" + additionalDrops.size() + "]";
    }
}
