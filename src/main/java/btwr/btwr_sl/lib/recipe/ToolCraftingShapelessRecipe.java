package btwr.btwr_sl.lib.recipe;

import btwr.btwr_sl.lib.interfaces.added.recipe.AdditionalDropsRecipe;
import btwr.btwr_sl.lib.interfaces.added.recipe.DamageOnCraftingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class ToolCraftingShapelessRecipe extends ShapelessRecipe implements AdditionalDropsRecipe, DamageOnCraftingRecipe {

    final DefaultedList<ItemStack> additionalDrops;
    final DefaultedList<Ingredient> damagedOnCrafting;

    public ToolCraftingShapelessRecipe(
            String group,
            CraftingRecipeCategory category,
            ItemStack result,
            DefaultedList<Ingredient> ingredients,
            DefaultedList<ItemStack> additionalDrops,
            DefaultedList<Ingredient> damageOnCrafting
    ) {
        super(group, category, result, ingredients);
        this.additionalDrops = additionalDrops;
        this.damagedOnCrafting = damageOnCrafting;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public DefaultedList<ItemStack> getAdditionalDrops() {
        return this.additionalDrops;
    }

    @Override
    public DefaultedList<Ingredient> getDamagedOnCrafting() {
        return damagedOnCrafting;
    }


    /**
    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput input) {
        DefaultedList<ItemStack> remainders = DefaultedList.ofSize(input.getSize(), ItemStack.EMPTY);

        for (int i = 0; i < input.getSize(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            // Optional: damage certain items instead of consuming
            if (stack.isDamageable() && stack.isIn(BTWRConventionalTags.Items.DAMAGE_ON_CRAFTING)) {
                if (stack.getDamage() < stack.getMaxDamage() - 1) {
                    ItemStack damaged = stack.copy();
                    damaged.setDamage(damaged.getDamage() + 1);
                    remainders.set(i, damaged);
                } else {
                    remainders.set(i, ItemStack.EMPTY);
                }
            }
            // fallback: use vanilla remainder if the item has one
            else if (stack.getItem().hasRecipeRemainder()) {
                remainders.set(i, new ItemStack(stack.getItem().getRecipeRemainder()));
            }
            // else: leave empty
            else {
                remainders.set(i, ItemStack.EMPTY);
            }
        }

        return remainders;
    }
    **/

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput input) {
        DefaultedList<ItemStack> remainders = DefaultedList.ofSize(input.getSize(), ItemStack.EMPTY);

        for (int i = 0; i < input.getSize(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            boolean damaged = false;

            // Check if this slot matches any of the recipe's damagedOnCrafting ingredients
            for (Ingredient ing : damagedOnCrafting) {
                if (!ing.isEmpty() && ing.test(stack)) {
                    if (stack.isDamageable() && stack.getDamage() < stack.getMaxDamage() - 1) {
                        ItemStack copy = stack.copy();
                        copy.setDamage(copy.getDamage() + 1);
                        remainders.set(i, copy);
                    } else {
                        remainders.set(i, ItemStack.EMPTY); // fully damaged → consumed
                    }
                    damaged = true;
                    break;
                }
            }

            if (damaged) continue;

            // Fallback: vanilla item-level remainder
            if (stack.getItem().hasRecipeRemainder()) {
                remainders.set(i, new ItemStack(stack.getItem().getRecipeRemainder()));
            }
            // else leave EMPTY
        }

        return remainders;
    }

    public static class Type implements RecipeType<ToolCraftingShapelessRecipe> {
        public static final ToolCraftingShapelessRecipe.Type INSTANCE = new ToolCraftingShapelessRecipe.Type();
        public static final String ID = "tool_crafting_shapeless";

    }

    public static class Serializer implements RecipeSerializer<ToolCraftingShapelessRecipe> {
        public static final ToolCraftingShapelessRecipe.Serializer INSTANCE = new Serializer();
        public static final String ID = "tool_crafting_shapeless";


        private static final MapCodec<ToolCraftingShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        Codec.STRING
                                .optionalFieldOf("group", "")
                                .forGetter(ToolCraftingShapelessRecipe::getGroup),
                        CraftingRecipeCategory.CODEC
                                .fieldOf("category").orElse(CraftingRecipeCategory.MISC)
                                .forGetter(ToolCraftingShapelessRecipe::getCategory),
                        ItemStack.VALIDATED_CODEC
                                .fieldOf("result")
                                .forGetter((recipe) -> recipe.getResult(null)),
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .listOf()
                                .fieldOf("ingredients")
                                .flatXmap(Serializer::validateIngredients, DataResult::success)
                                .forGetter(ToolCraftingShapelessRecipe::getIngredients),
                        ItemStack.OPTIONAL_CODEC
                                .listOf()
                                .fieldOf("additional_drops")
                                .flatXmap(Serializer::validateAdditionalDrops, DataResult::success)
                                .forGetter(ToolCraftingShapelessRecipe::getAdditionalDrops),
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .listOf()
                                .fieldOf("damaged_on_crafting")
                                .flatXmap(Serializer::validateDamagedOnCrafting, DataResult::success)
                                .forGetter(ToolCraftingShapelessRecipe::getDamagedOnCrafting)
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

        private static DataResult<DefaultedList<Ingredient>> validateDamagedOnCrafting(List<Ingredient> ingredients) {
            Ingredient[] filtered = ingredients.stream()
                    .filter(i -> !i.isEmpty())
                    .toArray(Ingredient[]::new);

            return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, filtered));
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
            int ingredientsCount = buf.readVarInt();
            DefaultedList<Ingredient> ingredientsList = DefaultedList.ofSize(ingredientsCount, Ingredient.EMPTY);
            for (int i = 0; i < ingredientsCount; i++) {
                ingredientsList.set(i, Ingredient.PACKET_CODEC.decode(buf));
            }
            ItemStack resultStack = ItemStack.PACKET_CODEC.decode(buf);
            int additionalDropsCount = buf.readVarInt();
            DefaultedList<ItemStack> additionalDropsList = DefaultedList.ofSize(additionalDropsCount, ItemStack.EMPTY);
            for (int i = 0; i < additionalDropsCount; i++) {
                additionalDropsList.set(i, ItemStack.PACKET_CODEC.decode(buf));
            }
            int damagedOnCraftingCount = buf.readVarInt();
            DefaultedList<Ingredient> damagedOnCraftingList = DefaultedList.ofSize(damagedOnCraftingCount, Ingredient.EMPTY);
            for (int i = 0; i < damagedOnCraftingCount; i++) {
                damagedOnCraftingList.set(i, Ingredient.PACKET_CODEC.decode(buf));
            }
            return new ToolCraftingShapelessRecipe(string, craftingRecipeCategory, resultStack, ingredientsList, additionalDropsList, damagedOnCraftingList);
        }

        private static void write(RegistryByteBuf buf, ToolCraftingShapelessRecipe recipe) {
            buf.writeString(recipe.getGroup());
            buf.writeEnumConstant(recipe.getCategory());
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf, recipe.getResult(null));
            buf.writeVarInt(recipe.getAdditionalDrops().size());
            for (ItemStack stack : recipe.getAdditionalDrops()) {
                ItemStack.PACKET_CODEC.encode(buf, stack);
            }

            buf.writeVarInt(recipe.getDamagedOnCrafting().size());
            for (Ingredient ingredient : recipe.getDamagedOnCrafting()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
        }
    }
}
