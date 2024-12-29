package btwr.btwr_sl.lib.mixin.recipe;

import btwr.btwr_sl.lib.interfaces.added.recipe.ShapelessRecipeAdded;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ShapelessRecipe.class)
public abstract class ShapelessRecipeMixin implements ShapelessRecipeAdded
{
    @Unique
    DefaultedList<ItemStack> additionalDrops;

    @Override
    public DefaultedList<ItemStack> getAdditionalDrops() {
        return this.additionalDrops;
    }

    @Override
    public void setAdditionalDrops(DefaultedList<ItemStack> drops)
    {
        this.additionalDrops = drops;
    }
}
