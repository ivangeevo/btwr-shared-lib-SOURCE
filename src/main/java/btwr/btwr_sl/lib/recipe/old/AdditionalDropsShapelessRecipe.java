package btwr.btwr_sl.lib.recipe.old;

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
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class AdditionalDropsShapelessRecipe extends ShapelessRecipe implements AdditionalDropsRecipe {

    final DefaultedList<ItemStack> additionalDrops;

    public AdditionalDropsShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients, DefaultedList<ItemStack> additionalDrops) {
        super(group, category, result, ingredients);
        this.additionalDrops = additionalDrops;
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
        return additionalDrops;
    }

    public static class Type implements RecipeType<AdditionalDropsShapelessRecipe> {
        public static final AdditionalDropsShapelessRecipe.Type INSTANCE = new Type();
        public static final String ID = "additional_drops_shapeless";
    }

    public static class Serializer implements RecipeSerializer<AdditionalDropsShapelessRecipe> {
        public static final AdditionalDropsShapelessRecipe.Serializer INSTANCE = new Serializer();
        public static final String ID = "additional_drops_shapeless";

        private static final MapCodec<AdditionalDropsShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.STRING
                                        .optionalFieldOf("group", "")
                                        .forGetter(ShapelessRecipe::getGroup),
                                CraftingRecipeCategory.CODEC
                                        .fieldOf("category").orElse(CraftingRecipeCategory.MISC)
                                        .forGetter(ShapelessRecipe::getCategory),
                                ItemStack.VALIDATED_CODEC
                                        .fieldOf("result")
                                        .forGetter(recipe -> recipe.getResult(null)),
                                Ingredient.DISALLOW_EMPTY_CODEC
                                        .listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(
                                                ingredients -> {
                                                    Ingredient[] ingredients2 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                                    if (ingredients2.length == 0) {
                                                        return DataResult.error(() -> "No ingredients for shapeless recipe");
                                                    } else {
                                                        return ingredients2.length > 9
                                                                ? DataResult.error(() -> "Too many ingredients for shapeless recipe")
                                                                : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients2));
                                                    }
                                                },
                                                DataResult::success
                                        )
                                        .forGetter(ShapelessRecipe::getIngredients),
                                ItemStack.VALIDATED_CODEC
                                        .listOf()
                                        .optionalFieldOf("additional_drops", List.of())
                                        .xmap(
                                                list -> {
                                                    ItemStack[] filtered = list.stream()
                                                            .filter(stack -> !stack.isEmpty())
                                                            .toArray(ItemStack[]::new);
                                                    return DefaultedList.copyOf(ItemStack.EMPTY, filtered);
                                                },
                                                List::copyOf
                                        )
                                        .forGetter(AdditionalDropsShapelessRecipe::getAdditionalDrops)
                        )

                        .apply(instance, AdditionalDropsShapelessRecipe::new)
        );

        private static DataResult<DefaultedList<ItemStack>> validateAdditionalDrops(List<ItemStack> stacks) {
            ItemStack[] filtered = stacks.stream()
                    .filter(stack -> !stack.isEmpty())
                    .toArray(ItemStack[]::new);

            return DataResult.success(DefaultedList.copyOf(ItemStack.EMPTY, filtered));
        }


        /**
        private static DataResult<DefaultedList<ItemStack>> validateAdditionalDrops(List<ItemStack> stacks) {
            ItemStack[] filtered = stacks.stream()
                    .filter(ingredient -> !ingredient.isEmpty())
                    .toArray(ItemStack[]::new);

            return DataResult.success(DefaultedList.copyOf(ItemStack.EMPTY, filtered));
        }
         **/

        public static final PacketCodec<RegistryByteBuf, AdditionalDropsShapelessRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                AdditionalDropsShapelessRecipe.Serializer::write, AdditionalDropsShapelessRecipe.Serializer::read
        );

        @Override
        public MapCodec<AdditionalDropsShapelessRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, AdditionalDropsShapelessRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static AdditionalDropsShapelessRecipe read(RegistryByteBuf buf) {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);

            int ingredientsSize = buf.readVarInt();
            DefaultedList<Ingredient> ingredientsList = DefaultedList.ofSize(ingredientsSize, Ingredient.EMPTY);
            ingredientsList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));

            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);

            int additionalDropsSize = buf.readVarInt();
            DefaultedList<ItemStack> additionalDropsList = DefaultedList.ofSize(additionalDropsSize, ItemStack.EMPTY);
            additionalDropsList.replaceAll(empty -> ItemStack.PACKET_CODEC.decode(buf));

            return new AdditionalDropsShapelessRecipe(string, craftingRecipeCategory, itemStack, ingredientsList, additionalDropsList);
        }

        private static void write(RegistryByteBuf buf, AdditionalDropsShapelessRecipe recipe) {
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

        }
    }
}
