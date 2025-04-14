package btwr.btwr_sl.lib.datagen;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class BTWRSL_LangGenerator extends FabricLanguageProvider {

    public BTWRSL_LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        addTagNames(translationBuilder);
        addConfigEntries(translationBuilder);
    }

    protected void addTagNames(TranslationBuilder tb) {
        // Block Tags
        addTagName(BTWRConventionalTags.Blocks.VANILLA_CONVERTING_BLOCKS, "Vanilla Converting Blocks", tb);
        addTagName(BTWRConventionalTags.Blocks.MODDED_CONVERTING_BLOCKS, "Modded Converting Blocks", tb);
        addTagName(BTWRConventionalTags.Blocks.STUMP_BLOCKS, "Stump Blocks", tb);
        addTagName(BTWRConventionalTags.Blocks.FARMLAND_BLOCKS, "Farmland Blocks", tb);
        addTagName(BTWRConventionalTags.Blocks.WEB_BLOCKS, "Cobweb Blocks", tb);

        // Item Tags
        addTagName(BTWRConventionalTags.Items.PRIMITIVE_CHISELS, "Primitive Chisels", tb);
        addTagName(BTWRConventionalTags.Items.MODERN_CHISELS, "Modern Chisels", tb);
        addTagName(BTWRConventionalTags.Items.ADVANCED_CHISELS, "Advanced Chisels", tb);
        addTagName(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES, "Primitive Pickaxes", tb);
        addTagName(BTWRConventionalTags.Items.MODERN_PICKAXES, "Modern Pickaxes", tb);
        addTagName(BTWRConventionalTags.Items.ADVANCED_PICKAXES, "Advanced Pickaxes", tb);
        addTagName(BTWRConventionalTags.Items.PICKAXES_HARVEST_FULL_BLOCK, "Primitive Chisels", tb);
        addTagName(BTWRConventionalTags.Items.FULLY_MINES_STRATA_1, "Tools That Fully Mine 1st Layer Strata", tb);
        addTagName(BTWRConventionalTags.Items.FULLY_MINES_STRATA_2, "Tools That Fully Mine 2nd Layer Strata", tb);
        addTagName(BTWRConventionalTags.Items.FULLY_MINES_STRATA_3, "Tools That Fully Mine 3rd Layer Strata", tb);
        addTagName(BTWRConventionalTags.Items.PRIMITIVE_AXES, "Primitive Axes", tb);
        addTagName(BTWRConventionalTags.Items.MODERN_AXES, "Modern Axes", tb);
        addTagName(BTWRConventionalTags.Items.ADVANCED_AXES, "Advanced Axes", tb);
        addTagName(BTWRConventionalTags.Items.AXES_MAKE_PLANKS, "Axes That Can Make Planks", tb);
        addTagName(BTWRConventionalTags.Items.AXES_HARVEST_FULL_BLOCK, "Axes That Harvest Blocks Fully", tb);
        addTagName(BTWRConventionalTags.Items.PRIMITIVE_SHOVELS, "Primitive Shovels", tb);
        addTagName(BTWRConventionalTags.Items.MODERN_SHOVELS, "Modern Shovels", tb);
        addTagName(BTWRConventionalTags.Items.ADVANCED_SHOVELS, "Advanced Shovels", tb);
        addTagName(BTWRConventionalTags.Items.SHOVELS_HARVEST_FULL_BLOCK, "Shovels That Harvest Blocks Fully", tb);
        addTagName(BTWRConventionalTags.Items.PRIMITIVE_HOES, "Primitive Hoes", tb);
        addTagName(BTWRConventionalTags.Items.MODERN_HOES, "Modern Hoes", tb);
        addTagName(BTWRConventionalTags.Items.ADVANCED_HOES, "Advanced Hoes", tb);
        addTagName(BTWRConventionalTags.Items.SPIT_CAMPFIRE_ITEMS, "Spit Campfire Items", tb);
        addTagName(BTWRConventionalTags.Items.SHEARS, "Shears", tb);
        addTagName(BTWRConventionalTags.Items.CHICKEN_TEMPT_ITEMS, "Chicken Tempt Items", tb);
        addTagName(BTWRConventionalTags.Items.STRING_TOOL_MATERIALS, "String Tool Materials", tb);
        addTagName(BTWRConventionalTags.Items.GEARS, "Gears", tb);
        addTagName(BTWRConventionalTags.Items.COOKED_EGG_FOODS, "Cooked Eggs", tb);
        addTagName(BTWRConventionalTags.Items.COOKED_POTATO_FOODS, "Cooked Potatoes", tb);
        addTagName(BTWRConventionalTags.Items.COOKED_MEATS_FOR_SANDWICH, "Cooked Meats For Sandwich", tb);
        addTagName(BTWRConventionalTags.Items.COBBLESTONE_CRAFTING_MATERIALS, "Cobblestone Crafting Materials", tb);
        addTagName(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS, "Items that can do knockback", tb);
        addTagName(BTWRConventionalTags.Items.ON_CRAFT_WOODEN_SOUND, "Wooden sound on crafting", tb);
        addTagName(BTWRConventionalTags.Items.ON_CRAFT_SLIME_SOUND, "Slime sound on crafting", tb);
        addTagName(BTWRConventionalTags.Items.ON_CRAFT_SHEARS_CUT_SOUND, "Shears snip sound on crafting", tb);
        addTagName(BTWRConventionalTags.Items.WOODEN_TOOLS, "Wooden Tools", tb);
        addTagName(BTWRConventionalTags.Items.STONE_TOOLS, "Stone Tools", tb);
        addTagName(BTWRConventionalTags.Items.IRON_TOOLS, "Iron Tools", tb);
        addTagName(BTWRConventionalTags.Items.GOLDEN_TOOLS, "Golden Tools", tb);
        addTagName(BTWRConventionalTags.Items.DIAMOND_TOOLS, "Diamond Tools", tb);
        addTagName(BTWRConventionalTags.Items.NETHERITE_TOOLS, "Netherite Tools", tb);
    }

    protected void addConfigEntries(TranslationBuilder tb) {
        // Config
        addConfigTitle("config", "BTWR: Shared Library Config", tb);
        addConfigCategory("penalties", "Penalties", tb);
        addConfigEntry("penalty_override", "Penalty Display Override", tb);
        addConfigEntry("hunger_override", "Hunger Display Override", tb);
        addConfigEntry("render_y", "Y Offset", tb);
        addConfigEntry("draw_margin", "Draw Margin", tb);
        addConfigEntry("draw_mode", "Draw Mode", tb);
        addConfigTooltip("penalty_override",
                "Whether certain checks in the penalty\ndisplay should be forfeited.", tb);
        addConfigTooltip("hunger_override",
                "Forces the penalty display to think the hunger\n" +
                        "bar is being rendered. Does nothing\n" +
                        "if the hunger bar is actually rendered.", tb);
        addConfigTooltip("render_y",
                "Vertically offsets the penalty rendering by the given amount", tb);
        addConfigTooltip("draw_margin",
                "Padding to add when drawing penalties to the screen.\n" +
                        "Has no effect when draw mode is \"BTW\"", tb);
        addConfigTooltip("draw_mode",
                "Determines how penalties are drawn to the screen.\n" +
                        "\"BTW\" emulates Better Than Wolves rendering", tb);
        addConfigAnchor("BTW", "BTW", tb);
        addConfigAnchor("BOTTOM_RIGHT", "Bottom Right", tb);
        addConfigAnchor("TOP_RIGHT", "Top Right", tb);
        addConfigAnchor("BOTTOM_LEFT", "Bottom Left", tb);
        addConfigAnchor("TOP_LEFT", "Top Left", tb);

    }

    protected void addTagName(TagKey<?> tagKey, String value, TranslationBuilder tb) {
        tb.add(tagKey, value);
    }

    protected void addConfigTitle(String key, String value, TranslationBuilder tb) {
        tb.add("title.btwrsl." + key, value);
    }

    protected void addConfigCategory(String key, String value, TranslationBuilder tb) {
        tb.add("title.btwrsl.category" + key, value);
    }

    protected void addConfigEntry(String key, String value, TranslationBuilder tb) {
        tb.add("config.btwrsl." + key, value);
    }

    protected void addConfigTooltip(String key, String value, TranslationBuilder tb) {
        tb.add("config.btwrsl.tooltip." + key, value);
    }

    protected void addConfigAnchor(String key, String value, TranslationBuilder tb) {
        tb.add("config.btwrsl.draw_mode.anchor." + key, value);
    }
}
