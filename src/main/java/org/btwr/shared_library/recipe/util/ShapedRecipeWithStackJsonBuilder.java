package org.btwr.shared_library.recipe.util;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ShapedRecipeWithStackJsonBuilder implements CraftingRecipeJsonBuilder {

    private final RecipeCategory category;
    private final ItemStack result;
    private final List<String> pattern = new ArrayList<>();
    private final Map<Character, Ingredient> inputs = new LinkedHashMap<>();
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private boolean showNotification = true;

    private ShapedRecipeWithStackJsonBuilder(RecipeCategory category, ItemStack result) {
        this.category = category;
        this.result = result;
    }

    public static ShapedRecipeWithStackJsonBuilder create(RecipeCategory category, ItemStack result) {
        return new ShapedRecipeWithStackJsonBuilder(category, result);
    }

    public static ShapedRecipeWithStackJsonBuilder create(ItemStack result) {
        return create(RecipeCategory.MISC, result);
    }

    public ShapedRecipeWithStackJsonBuilder pattern(String row) {
        this.pattern.add(row);
        return this;
    }

    public ShapedRecipeWithStackJsonBuilder input(Character c, ItemConvertible item) {
        return input(c, Ingredient.ofItems(item));
    }

    public ShapedRecipeWithStackJsonBuilder input(Character c, TagKey<Item> tag) {
        return input(c, Ingredient.fromTag(tag));
    }

    public ShapedRecipeWithStackJsonBuilder input(Character c, Ingredient ingredient) {
        if (this.inputs.containsKey(c)) throw new IllegalArgumentException("Symbol '" + c + "' is already defined!");
        if (c == ' ') throw new IllegalArgumentException("Symbol ' ' is reserved");
        this.inputs.put(c, ingredient);
        return this;
    }

    public ShapedRecipeWithStackJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public ShapedRecipeWithStackJsonBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public ShapedRecipeWithStackJsonBuilder showNotification(boolean show) {
        this.showNotification = show;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.result.getItem();
    }

    @Override
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        if (this.criteria.isEmpty()) throw new IllegalStateException("No way of obtaining recipe " + recipeId);

        RawShapedRecipe raw = RawShapedRecipe.create(this.inputs, this.pattern);

        ShapedRecipe recipe = new ShapedRecipe(
            Objects.requireNonNullElse(this.group, ""),
            CraftingRecipeJsonBuilder.toCraftingCategory(this.category),
            raw,
            this.result,
            this.showNotification
        );

        Advancement.Builder advancement = exporter.getAdvancementBuilder()
            .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
            .rewards(AdvancementRewards.Builder.recipe(recipeId))
            .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        this.criteria.forEach(advancement::criterion);

        exporter.accept(recipeId, recipe, advancement.build(
            recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")
        ));
    }
}