package btwr.btwr_sl.lib.recipe.old;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements.CriterionMerger;
import net.minecraft.advancement.AdvancementRewards.Builder;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class ToolCraftingShapelessRecipeJsonBuilder implements CraftingRecipeJsonBuilder {
    private final RecipeCategory category;
    private final Item output;
    private final int count;
    private final DefaultedList<Ingredient> inputs = DefaultedList.of();
    /** An ingredient that holds a tool placed as input **/
    private Ingredient tool = null;
    /** The damage to apply on tool crafting if present **/
    private int toolDamage = 0;
    private final DefaultedList<ItemStack> additionalDrops = DefaultedList.of();
    private final Map<String, AdvancementCriterion<?>> advancementBuilder = new LinkedHashMap<>();
    @Nullable
    private String group;

    public ToolCraftingShapelessRecipeJsonBuilder(RecipeCategory category, ItemConvertible output, int count) {
        this.category = category;
        this.output = output.asItem();
        this.count = count;
    }

    public static ToolCraftingShapelessRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output) {
        return new ToolCraftingShapelessRecipeJsonBuilder(category, output, 1);
    }

    public static ToolCraftingShapelessRecipeJsonBuilder create(RecipeCategory category, ItemConvertible output, int count) {
        return new ToolCraftingShapelessRecipeJsonBuilder(category, output, count);
    }

    public ToolCraftingShapelessRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public ToolCraftingShapelessRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return this.input(itemProvider, 1);
    }

    public ToolCraftingShapelessRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
        for(int i = 0; i < size; ++i) {
            this.input(Ingredient.ofItems(itemProvider));
        }

        return this;
    }

    public ToolCraftingShapelessRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public ToolCraftingShapelessRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for(int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }

        return this;
    }

    public ToolCraftingShapelessRecipeJsonBuilder toolInput(TagKey<Item> tag) {
        return this.toolInput(tag, 0);
    }
    public ToolCraftingShapelessRecipeJsonBuilder toolInput(TagKey<Item> tag, int damage) {
        this.toolDamage = damage;
        return this.toolInput(Ingredient.fromTag(tag));
    }

    public ToolCraftingShapelessRecipeJsonBuilder toolInput(ItemConvertible itemProvider) {
        return this.toolInput(itemProvider, 0);
    }

    public ToolCraftingShapelessRecipeJsonBuilder toolInput(ItemConvertible itemProvider, int damage) {
        this.toolInput(Ingredient.ofItems(itemProvider), damage);
        return this;
    }

    public ToolCraftingShapelessRecipeJsonBuilder toolInput(Ingredient ingredient) {
        return this.toolInput(ingredient, 0);
    }

    /** A tool is essentially an input; Allows adding damage **/
    public ToolCraftingShapelessRecipeJsonBuilder toolInput(Ingredient ingredient, int damage) {
        this.inputs.add(ingredient);
        this.tool = ingredient;
        this.toolDamage = damage;
        return this;
    }

    public ToolCraftingShapelessRecipeJsonBuilder additionalDrop(ItemConvertible itemProvider) {
        return this.additionalDrop(itemProvider, 1);
    }

    public ToolCraftingShapelessRecipeJsonBuilder additionalDrop(ItemConvertible itemProvider, int count) {
        this.additionalDrops.add(new ItemStack(itemProvider, count));
        return this;
    }

    public ToolCraftingShapelessRecipeJsonBuilder additionalDrop(ItemStack stack) {
        this.additionalDrops.add(stack.copy());
        return this;
    }

    public ToolCraftingShapelessRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
        this.advancementBuilder.put(string, advancementCriterion);
        return this;
    }

    public ToolCraftingShapelessRecipeJsonBuilder group(@Nullable String string) {
        this.group = string;
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
                .rewards(Builder.recipe(recipeId)).criteriaMerger(CriterionMerger.OR);
        Objects.requireNonNull(builder);
        this.advancementBuilder.forEach(builder::criterion);
        ToolCraftingShapelessRecipe recipe = new ToolCraftingShapelessRecipe(
                Objects.requireNonNullElse(this.group, ""),
                CraftingRecipeJsonBuilder.toCraftingCategory(this.category),
                new ItemStack(this.output, this.count),
                this.inputs,
                this.tool,
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
