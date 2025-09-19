package btwr.btwr_sl.lib.recipe;

import btwr.btwr_sl.lib.mixin.accessors.ShapelessRecipeJsonBuilderAccessorMixin;
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
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.Objects;

public class TestShapelessRecipe extends ShapelessRecipe {

    public TestShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients) {
        super(group, category, result, ingredients);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return BTWRSLRecipes.TEST_SHAPELESS_RECIPE_SERIALIZER;
    }


    public static class Serializer implements RecipeSerializer<TestShapelessRecipe> {
        private static final MapCodec<TestShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec(
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
                                .forGetter(r -> r.getResult(null)),
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .listOf()
                                .fieldOf("ingredients")
                                .flatXmap(ingredients -> {
                                    Ingredient[] ingredients2 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients2.length == 0) {
                                        return DataResult.error(() -> "No ingredients for shapeless recipe");
                                    }
                                    if (ingredients2.length > 16) {
                                        return DataResult.error(() -> "Too many ingredients for shapeless recipe");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients2));
                                }, DataResult::success)
                                .forGetter(ShapelessRecipe::getIngredients)
                ).apply(instance, TestShapelessRecipe::new)
        );

        public static final PacketCodec<RegistryByteBuf, TestShapelessRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                TestShapelessRecipe.Serializer::write,
                TestShapelessRecipe.Serializer::read
        );

        @Override
        public MapCodec<TestShapelessRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, TestShapelessRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static TestShapelessRecipe read(RegistryByteBuf buf) {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            return new TestShapelessRecipe(string, craftingRecipeCategory, itemStack, defaultedList);
        }

        private static void write(RegistryByteBuf buf, TestShapelessRecipe recipe) {
            buf.writeString(recipe.getGroup());
            buf.writeEnumConstant(recipe.getCategory());
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf, recipe.getResult(null));
        }
    }

    public static class JsonBuilder extends ShapelessRecipeJsonBuilder {
        public JsonBuilder(RecipeCategory category, ItemConvertible output, int count) {
            super(category, output, count);
        }

        public static JsonBuilder create(RecipeCategory category, ItemConvertible output) {
            return JsonBuilder.create(category, output, 1);
        }

        public static JsonBuilder create(RecipeCategory category, ItemConvertible output, int count) {
            return new JsonBuilder(category, output, count);
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
            TestShapelessRecipe shapelessRecipe = new TestShapelessRecipe(
                    Objects.requireNonNullElse(accessor.getGroup(), ""),
                    CraftingRecipeJsonBuilder.toCraftingCategory(accessor.getCategory()),
                    new ItemStack(accessor.getOutput(), accessor.getCount()),
                    accessor.getInputs()
            );
            exporter.accept(recipeId, shapelessRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + accessor.getCategory().getName() + "/"))
            );
        }
    }


}

