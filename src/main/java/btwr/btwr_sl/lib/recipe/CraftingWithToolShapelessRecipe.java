package btwr.btwr_sl.lib.recipe;

import btwr.btwr_sl.lib.mixin.accessors.ShapelessRecipeJsonBuilderAccessorMixin;
import btwr.btwr_sl.lib.recipe.capability.AdditionalDropsRecipe;
import btwr.btwr_sl.tag.BTWRConventionalTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;
import java.util.Objects;

public class CraftingWithToolShapelessRecipe extends ShapelessRecipe implements AdditionalDropsRecipe {

    /** List of stacks to drop in addition to the result **/
    final DefaultedList<ItemStack> additionalDrops;

    public CraftingWithToolShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients, DefaultedList<ItemStack> additionalDrops) {
        super(group, category, result, ingredients);
        this.additionalDrops = additionalDrops;
    }

    @Override
    public RecipeType<?> getType() {
        return BTWRSLRecipes.CRAFTING_WITH_TOOL_SHAPELESS_RECIPE_TYPE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BTWRSLRecipes.CRAFTING_WITH_TOOL_SHAPELESS_RECIPE_SERIALIZER;
    }

    @Override
    public DefaultedList<ItemStack> getAdditionalDrops() {
        return this.additionalDrops;
    }

    public ItemStack getResult() {
        return this.getResult(null);
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput input) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(input.getSize(), ItemStack.EMPTY);

        for (int i = 0; i < defaultedList.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (stack.isIn(BTWRConventionalTags.Items.CRAFTING_WITH_TOOLS_ITEMS)) {
                ItemStack copiedStack = stack.copy();

                if (stack.isIn(BTWRConventionalTags.Items.DAMAGE_ON_CRAFTING_TOOLS)) {
                    if (stack.getDamage() < stack.getMaxDamage() - 1) {
                        copiedStack.setDamage(stack.getDamage() + 1);
                    }
                }

                defaultedList.set(i, copiedStack);
            }
        }

        return defaultedList;
    }

    public static class Serializer implements RecipeSerializer<CraftingWithToolShapelessRecipe> {
        private static final MapCodec<CraftingWithToolShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.STRING
                                        .optionalFieldOf("group", "")
                                        .forGetter(ShapelessRecipe::getGroup),
                                CraftingRecipeCategory.CODEC
                                        .fieldOf("category")
                                        .orElse(CraftingRecipeCategory.MISC)
                                        .forGetter(ShapelessRecipe::getCategory),
                                ItemStack.VALIDATED_CODEC
                                        .fieldOf("result")
                                        .forGetter(CraftingWithToolShapelessRecipe::getResult),
                                Ingredient.DISALLOW_EMPTY_CODEC
                                        .listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(Serializer::validateIngredients, DataResult::success)
                                        .forGetter(ShapelessRecipe::getIngredients),
                                ItemStack.OPTIONAL_CODEC
                                        .listOf()
                                        .fieldOf("additional_drops")
                                        .flatXmap(Serializer::validateAdditionalDrops, DataResult::success)
                                        .forGetter(CraftingWithToolShapelessRecipe::getAdditionalDrops)
                        )
                        .apply(instance, CraftingWithToolShapelessRecipe::new)
        );

        private static DataResult<DefaultedList<Ingredient>> validateIngredients(List<Ingredient> ingredients) {
            Ingredient[] ingredients2 = ingredients.stream()
                    .filter(ingredient -> !ingredient.isEmpty())
                    .toArray(Ingredient[]::new);
            if (ingredients2.length == 0) {
                return DataResult.error(() -> "No ingredients for shapeless recipe");
            } else {
                return ingredients2.length > 9
                        ? DataResult.error(() -> "Too many ingredients for shapeless recipe")
                        : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients2));
            }
        }

        private static DataResult<DefaultedList<ItemStack>> validateAdditionalDrops(List<ItemStack> stacks) {
            ItemStack[] filtered = stacks.stream()
                    .filter(ingredient -> !ingredient.isEmpty())
                    .toArray(ItemStack[]::new);

            return DataResult.success(DefaultedList.copyOf(ItemStack.EMPTY, filtered));
        }

        public static final PacketCodec<RegistryByteBuf, CraftingWithToolShapelessRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                CraftingWithToolShapelessRecipe.Serializer::write, CraftingWithToolShapelessRecipe.Serializer::read
        );

        @Override
        public MapCodec<CraftingWithToolShapelessRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, CraftingWithToolShapelessRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static CraftingWithToolShapelessRecipe read(RegistryByteBuf buf) {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);

            // Additional drops
            int additionalDropsCount = buf.readVarInt();
            DefaultedList<ItemStack> additionalDropsList = DefaultedList.ofSize(additionalDropsCount, ItemStack.EMPTY);
            for (int d = 0; d < additionalDropsCount; d++) {
                additionalDropsList.set(d, ItemStack.PACKET_CODEC.decode(buf));
            }
            return new CraftingWithToolShapelessRecipe(string, craftingRecipeCategory, itemStack, defaultedList, additionalDropsList);
        }

        private static void write(RegistryByteBuf buf, CraftingWithToolShapelessRecipe recipe) {
            buf.writeString(recipe.getGroup());
            buf.writeEnumConstant(recipe.getCategory());
            buf.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.getResult());

            // Additional drops
            buf.writeVarInt(recipe.getAdditionalDrops().size());
            for (ItemStack stack : recipe.getAdditionalDrops()) {
                ItemStack.PACKET_CODEC.encode(buf, stack);
            }
        }
    }

    public static class JsonBuilder extends ShapelessRecipeJsonBuilder {

        private final DefaultedList<ItemStack> additionalDrops = DefaultedList.of();

        public JsonBuilder(RecipeCategory category, ItemConvertible output, int count) {
            super(category, output, count);
        }

        public static JsonBuilder create(RecipeCategory category, ItemConvertible output) {
            return create(category, output, 1);
        }

        public static JsonBuilder create(RecipeCategory category, ItemConvertible output, int count) {
            return new JsonBuilder(category, output, count);
        }

        public JsonBuilder additionalDrop(ItemConvertible itemProvider) {
            return this.additionalDrop(itemProvider, 1);
        }

        public JsonBuilder additionalDrop(ItemConvertible itemProvider, int count) {
            this.additionalDrops.add(new ItemStack(itemProvider, count));
            return this;
        }

        public JsonBuilder additionalDrop(ItemStack stack) {
            this.additionalDrops.add(stack.copy());
            return this;
        }

        @Override
        public void offerTo(RecipeExporter exporter, Identifier recipeId) {
            ShapelessRecipeJsonBuilderAccessorMixin accessor = (ShapelessRecipeJsonBuilderAccessorMixin) this;

            accessor.accessValidate(recipeId);
            Advancement.Builder builder = exporter.getAdvancementBuilder()
                    .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                    .rewards(AdvancementRewards.Builder.recipe(recipeId))
                    .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
            accessor.getAdvancementBuilder().forEach(builder::criterion);
            CraftingWithToolShapelessRecipe recipe = new CraftingWithToolShapelessRecipe(
                    Objects.requireNonNullElse(accessor.getGroup(), ""),
                    CraftingRecipeJsonBuilder.toCraftingCategory(accessor.getCategory()),
                    new ItemStack(accessor.getOutput(), accessor.getCount()),
                    accessor.getInputs(),
                    this.additionalDrops
            );
            exporter.accept(recipeId, recipe, builder.build(recipeId.withPrefixedPath("recipes/" + accessor.getCategory().getName() + "/")));
        }


    }
}
