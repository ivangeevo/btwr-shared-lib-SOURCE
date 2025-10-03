package btwr.btwr_sl.lib.recipe;

import btwr.btwr_sl.lib.recipe.capability.AdditionalDropsRecipe;
import btwr.btwr_sl.lib.recipe.capability.CraftingWithToolRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CraftingWithToolShapelessRecipe extends ShapelessRecipe implements AdditionalDropsRecipe, CraftingWithToolRecipe {

    /** Damage to apply to the tool if possible **/
    final int toolDamage;

    /** List of itemstacks to drop in addition to the result **/
    final DefaultedList<ItemStack> additionalDrops;

    public CraftingWithToolShapelessRecipe(
            String group,
            CraftingRecipeCategory category,
            ItemStack result,
            DefaultedList<Ingredient> ingredients,
            int toolDamage,
            DefaultedList<ItemStack> additionalDrops
    ) {
        super(group, category, result, ingredients);
        this.toolDamage = toolDamage;
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

    @Override
    public int getToolDamage() {
        return toolDamage;
    }

    public static class Serializer implements RecipeSerializer<CraftingWithToolShapelessRecipe> {
        private static final MapCodec<CraftingWithToolShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        Codec.STRING
                                .optionalFieldOf("group", "")
                                .forGetter(CraftingWithToolShapelessRecipe::getGroup),
                        CraftingRecipeCategory.CODEC
                                .fieldOf("category")
                                .orElse(CraftingRecipeCategory.MISC)
                                .forGetter(CraftingWithToolShapelessRecipe::getCategory),
                        ItemStack.VALIDATED_CODEC
                                .fieldOf("result")
                                .forGetter((recipe) -> recipe.getResult(null)),
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .listOf()
                                .fieldOf("ingredients")
                                .flatXmap(Serializer::validateIngredients, DataResult::success)
                                .forGetter(CraftingWithToolShapelessRecipe::getIngredients),
                        Codec.INT
                                .fieldOf("tool_damage")
                                .forGetter(CraftingWithToolShapelessRecipe::getToolDamage),
                        ItemStack.OPTIONAL_CODEC
                                .listOf()
                                .fieldOf("additional_drops")
                                .flatXmap(Serializer::validateAdditionalDrops, DataResult::success)
                                .forGetter(CraftingWithToolShapelessRecipe::getAdditionalDrops)
                ).apply(instance, CraftingWithToolShapelessRecipe::new)
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

            // Read the result
            ItemStack resultStack = ItemStack.PACKET_CODEC.decode(buf);

            // Ingredients + count
            int ingredientsCount = buf.readVarInt();
            DefaultedList<Ingredient> ingredientsList = DefaultedList.ofSize(ingredientsCount, Ingredient.EMPTY);
            for (int i = 0; i < ingredientsCount; i++) {
                ingredientsList.set(i, Ingredient.PACKET_CODEC.decode(buf));
            }
            // Tool damage
            int toolDamage = buf.readVarInt();

            // Additional drops
            int additionalDropsCount = buf.readVarInt();
            DefaultedList<ItemStack> additionalDropsList = DefaultedList.ofSize(additionalDropsCount, ItemStack.EMPTY);
            for (int i = 0; i < additionalDropsCount; i++) {
                additionalDropsList.set(i, ItemStack.PACKET_CODEC.decode(buf));
            }

            return new CraftingWithToolShapelessRecipe(
                    string, craftingRecipeCategory, resultStack, ingredientsList, toolDamage, additionalDropsList
            );
        }

        private static void write(RegistryByteBuf buf, CraftingWithToolShapelessRecipe recipe) {
            buf.writeString(recipe.getGroup());
            buf.writeEnumConstant(recipe.getCategory());

            // Write the result
            ItemStack.PACKET_CODEC.encode(buf, recipe.getResult(null));

            // Ingredients
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            // Tool damage
            buf.writeVarInt(recipe.getToolDamage());

            // Additional drops
            buf.writeVarInt(recipe.getAdditionalDrops().size());
            for (ItemStack stack : recipe.getAdditionalDrops()) {
                ItemStack.PACKET_CODEC.encode(buf, stack);
            }
        }

    }

    public static class JsonBuilder implements CraftingRecipeJsonBuilder {
        private final RecipeCategory category;
        private final Item output;
        private final int count;
        private final DefaultedList<Ingredient> inputs = DefaultedList.of();
        private final Map<String, AdvancementCriterion<?>> advancementBuilder = new LinkedHashMap<>();
        @Nullable
        private String group;

        /** The damage to apply on tool crafting if present **/
        private int toolDamage = 0;

        private final DefaultedList<ItemStack> additionalDrops = DefaultedList.of();

        public JsonBuilder(RecipeCategory category, ItemConvertible output, int count) {
            this.category = category;
            this.output = output.asItem();
            this.count = count;
        }

        public static JsonBuilder create(RecipeCategory category, ItemConvertible output) {
            return new JsonBuilder(category, output, 1);
        }

        public static JsonBuilder create(RecipeCategory category, ItemConvertible output, int count) {
            return new JsonBuilder(category, output, count);
        }

        public JsonBuilder input(TagKey<Item> tag) {
            return this.input(Ingredient.fromTag(tag));
        }

        public JsonBuilder input(ItemConvertible itemProvider) {
            return this.input(itemProvider, 1);
        }

        public JsonBuilder input(ItemConvertible itemProvider, int size) {
            for (int i = 0; i < size; i++) {
                this.input(Ingredient.ofItems(itemProvider));
            }

            return this;
        }

        public JsonBuilder input(Ingredient ingredient) {
            return this.input(ingredient, 1);
        }

        public JsonBuilder input(Ingredient ingredient, int size) {
            for (int i = 0; i < size; i++) {
                this.inputs.add(ingredient);
            }

            return this;
        }

        public JsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
            this.advancementBuilder.put(string, advancementCriterion);
            return this;
        }

        public JsonBuilder group(@Nullable String string) {
            this.group = string;
            return this;
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
        public Item getOutputItem() {
            return this.output;
        }

        @Override
        public void offerTo(RecipeExporter exporter, Identifier recipeId) {
            this.validate(recipeId);
            Advancement.Builder builder = exporter.getAdvancementBuilder()
                    .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                    .rewards(net.minecraft.advancement.AdvancementRewards.Builder.recipe(recipeId))
                    .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
            this.advancementBuilder.forEach(builder::criterion);
            CraftingWithToolShapelessRecipe recipe = new CraftingWithToolShapelessRecipe(
                    Objects.requireNonNullElse(this.group, ""),
                    CraftingRecipeJsonBuilder.toCraftingCategory(this.category),
                    new ItemStack(this.output, this.count),
                    this.inputs,
                    this.toolDamage,
                    this.additionalDrops
            );
            exporter.accept(recipeId, recipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
        }

        private void validate(Identifier recipeId) {
            if (this.advancementBuilder.isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + recipeId);
            }
        }
    }

}
