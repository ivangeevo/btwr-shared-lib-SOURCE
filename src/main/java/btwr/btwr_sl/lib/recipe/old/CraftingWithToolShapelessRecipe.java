package btwr.btwr_sl.lib.recipe.old;

import btwr.btwr_sl.lib.mixin.accessors.ShapelessRecipeJsonBuilderAccessorMixin;
import btwr.btwr_sl.lib.recipe.BTWRSLRecipes;
import btwr.btwr_sl.lib.recipe.TestShapelessRecipe;
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

import java.util.List;
import java.util.Objects;

public class CraftingWithToolShapelessRecipe extends ShapelessRecipe implements AdditionalDropsRecipe, CraftingWithToolRecipe {

    /** Tool to craft the recipe with **/
    final Ingredient tool;

    /** Damage to apply to the tool if possible **/
    final int toolDamage;

    /** List of itemstacks to drop in addition to the result **/
    final DefaultedList<ItemStack> additionalDrops;

    public CraftingWithToolShapelessRecipe(
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
    public Ingredient getTool() {
        return this.tool;
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
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .fieldOf("tool")
                                .forGetter(CraftingWithToolShapelessRecipe::getTool),
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
            // Tool + tool damage
            Ingredient tool = Ingredient.PACKET_CODEC.decode(buf);
            int toolDamage = buf.readVarInt();

            // Additional drops
            int additionalDropsCount = buf.readVarInt();
            DefaultedList<ItemStack> additionalDropsList = DefaultedList.ofSize(additionalDropsCount, ItemStack.EMPTY);
            for (int i = 0; i < additionalDropsCount; i++) {
                additionalDropsList.set(i, ItemStack.PACKET_CODEC.decode(buf));
            }

            return new CraftingWithToolShapelessRecipe(
                    string, craftingRecipeCategory, resultStack, ingredientsList, tool, toolDamage, additionalDropsList
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

    public static class JsonBuilder extends ShapelessRecipeJsonBuilder {
        /** An ingredient that holds a tool placed as input **/
        private Ingredient tool = null;
        /** The damage to apply on tool crafting if present **/
        private int toolDamage = 0;
        private final DefaultedList<ItemStack> additionalDrops = DefaultedList.of();

        public JsonBuilder(RecipeCategory category, ItemConvertible output, int count) {
            super(category, output, count);
        }

        public static CraftingWithToolShapelessRecipe.JsonBuilder create(RecipeCategory category, ItemConvertible output) {
            return CraftingWithToolShapelessRecipe.JsonBuilder.create(category, output, 1);
        }

        public static CraftingWithToolShapelessRecipe.JsonBuilder create(RecipeCategory category, ItemConvertible output, int count) {
            return new CraftingWithToolShapelessRecipe.JsonBuilder(category, output, count);
        }

        public CraftingWithToolShapelessRecipe.JsonBuilder toolInput(TagKey<Item> tag) {
            return this.toolInput(tag, 0);
        }
        public CraftingWithToolShapelessRecipe.JsonBuilder toolInput(TagKey<Item> tag, int damage) {
            return this.toolInput(Ingredient.fromTag(tag), damage);
        }

        public CraftingWithToolShapelessRecipe.JsonBuilder toolInput(ItemConvertible itemProvider) {
            return this.toolInput(itemProvider, 0);
        }

        public CraftingWithToolShapelessRecipe.JsonBuilder toolInput(ItemConvertible itemProvider, int damage) {
            this.toolInput(Ingredient.ofItems(itemProvider), damage);
            return this;
        }

        public CraftingWithToolShapelessRecipe.JsonBuilder toolInput(Ingredient ingredient) {
            return this.toolInput(ingredient, 0);
        }

        /** A tool is essentially an input; Allows adding damage **/
        public CraftingWithToolShapelessRecipe.JsonBuilder toolInput(Ingredient ingredient, int damage) {
            ShapelessRecipeJsonBuilderAccessorMixin accessor = (ShapelessRecipeJsonBuilderAccessorMixin) this;

            accessor.getInputs().add(ingredient);
            this.tool = ingredient;
            this.toolDamage = damage;
            return this;
        }

        public CraftingWithToolShapelessRecipe.JsonBuilder additionalDrop(ItemConvertible itemProvider) {
            return this.additionalDrop(itemProvider, 1);
        }

        public CraftingWithToolShapelessRecipe.JsonBuilder additionalDrop(ItemConvertible itemProvider, int count) {
            this.additionalDrops.add(new ItemStack(itemProvider, count));
            return this;
        }

        public CraftingWithToolShapelessRecipe.JsonBuilder additionalDrop(ItemStack stack) {
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
                    this.tool,
                    this.toolDamage,
                    this.additionalDrops
            );
            exporter.accept(recipeId, recipe, builder.build(recipeId.withPrefixedPath("recipes/" + accessor.getCategory().getName() + "/"))
            );
        }
    }


}
