package btwr.btwr_sl.lib.recipe;

import btwr.btwr_sl.lib.mixin.accessors.ShapelessRecipeJsonBuilderAccessorMixin;
import btwr.btwr_sl.lib.recipe.capability.AdditionalDropsRecipe;
import btwr.btwr_sl.lib.recipe.capability.CraftingWithToolRecipe;
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

public class ExtendedShapelessRecipe extends ShapelessRecipe implements AdditionalDropsRecipe, CraftingWithToolRecipe {

    /** Damage to apply to the tool used in crafting **/
    final int toolDamage;

    /** List of stacks to drop in addition to the result **/
    final DefaultedList<ItemStack> additionalDrops;

    public ExtendedShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients, int toolDamage, DefaultedList<ItemStack> additionalDrops) {
        super(group, category, result, ingredients);
        this.toolDamage = toolDamage;
        this.additionalDrops = additionalDrops;
    }

    @Override
    public RecipeType<?> getType() {
        return BTWRSLRecipes.EXTENDED_SHAPELESS_RECIPE_TYPE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BTWRSLRecipes.EXTENDED_SHAPELESS_RECIPE_SERIALIZER;
    }

    @Override
    public int getToolDamage() {
        return this.toolDamage;
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
        DefaultedList<ItemStack> remainders = DefaultedList.ofSize(input.getSize(), ItemStack.EMPTY);

        for (int i = 0; i < remainders.size(); i++) {
            ItemStack stack = input.getStackInSlot(i);

            if (stack.isIn(BTWRConventionalTags.Items.CRAFTING_WITH_TOOLS_ITEMS)) {
                ItemStack tool = stack.copy();

                if (stack.isIn(BTWRConventionalTags.Items.DAMAGE_ON_CRAFTING_TOOLS)) {
                    int newDamage = stack.getDamage() + this.getToolDamage();

                    // If it breaks or exceeds max durability, destroy it
                    if (newDamage >= tool.getMaxDamage()) {
                        remainders.set(i, ItemStack.EMPTY);
                        continue;
                    }

                    tool.setDamage(newDamage);
                }

                remainders.set(i, tool);
            }
        }

        return remainders;
    }

    public static class Serializer implements RecipeSerializer<ExtendedShapelessRecipe> {
        private static final MapCodec<ExtendedShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec(
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
                                        .forGetter(ExtendedShapelessRecipe::getResult),
                                Ingredient.DISALLOW_EMPTY_CODEC
                                        .listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(Serializer::validateIngredients, DataResult::success)
                                        .forGetter(ShapelessRecipe::getIngredients),
                                Codec.INT
                                        .fieldOf("tool_damage")
                                        .forGetter(CraftingWithToolRecipe::getToolDamage),
                                ItemStack.OPTIONAL_CODEC
                                        .listOf()
                                        .fieldOf("additional_drops")
                                        .flatXmap(Serializer::validateAdditionalDrops, DataResult::success)
                                        .forGetter(ExtendedShapelessRecipe::getAdditionalDrops)
                        )
                        .apply(instance, ExtendedShapelessRecipe::new)
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

        public static final PacketCodec<RegistryByteBuf, ExtendedShapelessRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                ExtendedShapelessRecipe.Serializer::write, ExtendedShapelessRecipe.Serializer::read
        );

        @Override
        public MapCodec<ExtendedShapelessRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ExtendedShapelessRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static ExtendedShapelessRecipe read(RegistryByteBuf buf) {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);

            // Tool damage
            int toolDamage = buf.readVarInt();

            // Additional drops
            int additionalDropsCount = buf.readVarInt();
            DefaultedList<ItemStack> additionalDropsList = DefaultedList.ofSize(additionalDropsCount, ItemStack.EMPTY);
            for (int d = 0; d < additionalDropsCount; d++) {
                additionalDropsList.set(d, ItemStack.PACKET_CODEC.decode(buf));
            }
            return new ExtendedShapelessRecipe(string, craftingRecipeCategory, itemStack, defaultedList, toolDamage, additionalDropsList);
        }

        private static void write(RegistryByteBuf buf, ExtendedShapelessRecipe recipe) {
            buf.writeString(recipe.getGroup());
            buf.writeEnumConstant(recipe.getCategory());
            buf.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.getResult());

            // Tool damage
            buf.writeVarInt(recipe.getToolDamage());

            // Additional drops
            buf.writeVarInt(recipe.getAdditionalDrops().size());
            for (ItemStack stack : recipe.getAdditionalDrops()) {
                ItemStack.PACKET_CODEC.encode(buf, stack);
            }
        }
    }

    public static class JsonBuilder extends ShapelessRecipeJsonBuilder {

        /** The damage to apply on tool crafting if present **/
        private int toolDamage = 0;

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

        /** Add tool damage if the tool used in crafting supports it **/
        public JsonBuilder withToolDamage() {
            return this.withToolDamage(1);
        }

        public JsonBuilder withToolDamage(int damage) {
            this.toolDamage = damage;
            return this;
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
            ExtendedShapelessRecipe recipe = new ExtendedShapelessRecipe(
                    Objects.requireNonNullElse(accessor.getGroup(), ""),
                    CraftingRecipeJsonBuilder.toCraftingCategory(accessor.getCategory()),
                    new ItemStack(accessor.getOutput(), accessor.getCount()),
                    accessor.getInputs(),
                    this.toolDamage,
                    this.additionalDrops
            );
            exporter.accept(recipeId, recipe, builder.build(recipeId.withPrefixedPath("recipes/" + accessor.getCategory().getName() + "/")));
        }


    }
}
