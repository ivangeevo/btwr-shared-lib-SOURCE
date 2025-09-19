package btwr.btwr_sl.lib.recipe.old;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class ToolCraftingShapelessRecipe extends ShapelessRecipe implements AdditionalDropsRecipe, DamageOnCraftingRecipe {

    /** Tool to craft the recipe with **/
    final Ingredient tool;

    /** Damage to apply to the tool if possible **/
    final int toolDamage;

    /** List of itemstacks to drop in addition to the result **/
    final DefaultedList<ItemStack> additionalDrops;

    public ToolCraftingShapelessRecipe(
            String group,
            CraftingRecipeCategory category,
            ItemStack result,
            DefaultedList<Ingredient> ingredients,
            Ingredient tool,
            int toolDamage,
            DefaultedList<ItemStack> additionalDrops
    ) {
        super(group, category, result, ingredients);
        this.tool = tool;
        this.toolDamage = toolDamage;
        this.additionalDrops = additionalDrops;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public DefaultedList<ItemStack> getAdditionalDrops() {
        return this.additionalDrops;
    }

    public Ingredient getTool() {
        return this.tool;
    }

    @Override
    public int getToolDamage() {
        return toolDamage;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput input) {
        DefaultedList<ItemStack> remainders = DefaultedList.ofSize(input.getSize(), ItemStack.EMPTY);

        for (int i = 0; i < input.getSize(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            if (!this.getTool().isEmpty() && this.getTool().test(stack)) {
                if (stack.isDamageable() && stack.getDamage() < stack.getMaxDamage() - this.getToolDamage()) {
                    ItemStack copy = stack.copy();
                    copy.setDamage(copy.getDamage() + this.getToolDamage());
                    remainders.set(i, copy);
                } else {
                    remainders.set(i, ItemStack.EMPTY); // fully damaged → consumed
                }
                break;
            }

            // Fallback: vanilla item-level remainder
            if (stack.getItem().hasRecipeRemainder()) {
                remainders.set(i, new ItemStack(stack.getItem().getRecipeRemainder()));
            }

        }

        return remainders;
    }

    public static class Serializer implements RecipeSerializer<ToolCraftingShapelessRecipe> {
        public static final ToolCraftingShapelessRecipe.Serializer INSTANCE = new Serializer();

        private static final MapCodec<ToolCraftingShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        Codec.STRING
                                .optionalFieldOf("group", "")
                                .forGetter(ToolCraftingShapelessRecipe::getGroup),
                        CraftingRecipeCategory.CODEC
                                .fieldOf("category")
                                .orElse(CraftingRecipeCategory.MISC)
                                .forGetter(ToolCraftingShapelessRecipe::getCategory),
                        ItemStack.VALIDATED_CODEC
                                .fieldOf("result")
                                .forGetter((recipe) -> recipe.getResult(null)),
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .listOf()
                                .fieldOf("ingredients")
                                .flatXmap(Serializer::validateIngredients, DataResult::success)
                                .forGetter(ToolCraftingShapelessRecipe::getIngredients),
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .fieldOf("tool")
                                .forGetter(ToolCraftingShapelessRecipe::getTool),
                        Codec.INT
                                .fieldOf("tool_damage")
                                .forGetter(ToolCraftingShapelessRecipe::getToolDamage),
                        ItemStack.OPTIONAL_CODEC
                                .listOf()
                                .fieldOf("additional_drops")
                                .flatXmap(Serializer::validateAdditionalDrops, DataResult::success)
                                .forGetter(ToolCraftingShapelessRecipe::getAdditionalDrops)
                ).apply(instance, ToolCraftingShapelessRecipe::new)
        );

        private static DataResult<DefaultedList<Ingredient>> validateIngredients(List<Ingredient> ingredients) {
            Ingredient[] filtered = ingredients.stream()
                    .filter(i -> !i.isEmpty())
                    .toArray(Ingredient[]::new);

            if (filtered.length == 0) {
                return DataResult.error(() -> "No ingredients for shapeless recipe");
            } else if (filtered.length > 9) {
                return DataResult.error(() -> "Too many ingredients for shapeless recipe");
            } else {
                return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, filtered));
            }
        }

        private static DataResult<DefaultedList<ItemStack>> validateAdditionalDrops(List<ItemStack> stacks) {
            ItemStack[] filtered = stacks.stream()
                    .filter(ingredient -> !ingredient.isEmpty())
                    .toArray(ItemStack[]::new);

            return DataResult.success(DefaultedList.copyOf(ItemStack.EMPTY, filtered));
        }

        public static final PacketCodec<RegistryByteBuf, ToolCraftingShapelessRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                ToolCraftingShapelessRecipe.Serializer::write, ToolCraftingShapelessRecipe.Serializer::read
        );

        @Override
        public MapCodec<ToolCraftingShapelessRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ToolCraftingShapelessRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static ToolCraftingShapelessRecipe read(RegistryByteBuf buf) {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);
            // Read the result
            ItemStack resultStack = ItemStack.PACKET_CODEC.decode(buf);

            // Ingredients + count
            int ingredientsCount = buf.readVarInt();
            DefaultedList<Ingredient> ingredientsList = DefaultedList.ofSize(ingredientsCount, Ingredient.EMPTY);
            for (int i = 0; i < ingredientsCount; i++) {
                ingredientsList.set(i, Ingredient.PACKET_CODEC.decode(buf));
            }
            // Tool + tool damage
            Ingredient tool = Ingredient.PACKET_CODEC.decode(buf);
            int toolDamage = buf.readVarInt();

            // Additional drops
            int additionalDropsCount = buf.readVarInt();
            DefaultedList<ItemStack> additionalDropsList = DefaultedList.ofSize(additionalDropsCount, ItemStack.EMPTY);
            for (int i = 0; i < additionalDropsCount; i++) {
                additionalDropsList.set(i, ItemStack.PACKET_CODEC.decode(buf));
            }

            return new ToolCraftingShapelessRecipe(string, craftingRecipeCategory, resultStack, ingredientsList, tool, toolDamage, additionalDropsList);
        }

        private static void write(RegistryByteBuf buf, ToolCraftingShapelessRecipe recipe) {
            buf.writeString(recipe.getGroup());
            buf.writeEnumConstant(recipe.getCategory());

            // Write the result
            ItemStack.PACKET_CODEC.encode(buf, recipe.getResult(null));

            // Ingredients
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            // Tool + tool damage
            Ingredient.PACKET_CODEC.encode(buf, recipe.getTool());
            buf.writeVarInt(recipe.getToolDamage());

            // Additional drops
            buf.writeVarInt(recipe.getAdditionalDrops().size());
            for (ItemStack stack : recipe.getAdditionalDrops()) {
                ItemStack.PACKET_CODEC.encode(buf, stack);
            }
        }

    }

}
