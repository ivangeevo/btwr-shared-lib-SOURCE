package btwr.btwrsl.tag;

import net.fabricmc.fabric.impl.tag.convention.v2.TagRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

public class BTWRConventionalTags
{
    public static class Blocks
    {
        // Blocks that can convert on break and are from vanilla
        public static final TagKey<Block> VANILLA_CONVERTING_BLOCKS = createTag("vanilla_converting_blocks");

        // Blocks that can convert on break and are NOT from vanilla
        public static final TagKey<Block> MODDED_CONVERTING_BLOCKS = createTag("modded_converting_blocks");

        // Stump blocks grouped; initially used for Sturdy Tree's Stump blocks
        public static final TagKey<Block> STUMP_BLOCKS = createTag("stump_blocks");

        // Farmland viable blocks (can be planted on them)
        public static final TagKey<Block> FARMLAND_BLOCKS = createTag("farmland_blocks");

        // Combining Tag for Web like blocks ( Cobweb and Web Block from Animageddon)
        public static final TagKey<Block> WEB_BLOCKS = createTag("web_blocks");




        private static TagKey<Block> createTag(String tagId) {
            return TagRegistration.BLOCK_TAG.registerC(tagId);
        }

    }

    public static class Items
    {
        /**
         * Custom Mod Tool ranks;
         * <p>1.<b>Primitive; 2.Modern; 3.Advanced;</b>
         * <p>The different tool ranks are used to determine if a group of tools are allowed to harvest a block
         * or do some other specific action.
         */

        public static final TagKey<Item> PRIMITIVE_CHISELS = createTag("primitive_chisels");
        public static final TagKey<Item> MODERN_CHISELS = createTag("modern_chisels");

        public static final TagKey<Item> PRIMITIVE_PICKAXES = createTag("primitive_pickaxes");
        public static final TagKey<Item> MODERN_PICKAXES = createTag("modern_pickaxes");
        public static final TagKey<Item> ADVANCED_PICKAXES = createTag("advanced_pickaxes");

        public static final TagKey<Item> PRIMITIVE_AXES = createTag("primitive_axes");
        public static final TagKey<Item> MODERN_AXES = createTag("modern_axes");
        public static final TagKey<Item> ADVANCED_AXES = createTag("advanced_axes");
        public static final TagKey<Item> AXES_MAKE_PLANKS = createTag("axes_make_planks");

        public static final TagKey<Item> AXES_HARVEST_FULL_BLOCK = createTag("axes_harvest_full_block");

        public static final TagKey<Item> PRIMITIVE_SHOVELS = createTag("primitive_shovels");
        public static final TagKey<Item> MODERN_SHOVELS = createTag("modern_shovels");
        public static final TagKey<Item> ADVANCED_SHOVELS = createTag("advanced_shovels");
        public static final TagKey<Item> SHOVELS_HARVEST_FULL_BLOCK = createTag("shovels_harvest_full_block");

        public static final TagKey<Item> PRIMITIVE_HOES = createTag("primitive_hoes");
        public static final TagKey<Item> MODERN_HOES = createTag("modern_hoes");
        public static final TagKey<Item> ADVANCED_HOES = createTag("advanced_hoes");

        public static final TagKey<Item> SPIT_CAMPFIRE_ITEMS = createTag("spit_campfire_items");

        public static final TagKey<Item> SHEARS = createTag("shears");

        public static final TagKey<Item> CHICKEN_TEMPT_ITEMS = createTag("chicken_tempt_items");

        public static final TagKey<Item> STRING_TOOL_MATERIALS = createTag("string_tool_materials");

        // all mechanical gear items used in recipes normally
        public static final TagKey<Item> GEARS = createTag("gears");

        // food
        public static final TagKey<Item> COOKED_EGG_FOODS = createTag("foods/cooked_egg");

        public static final TagKey<Item> COOKED_POTATO_FOODS = createTag("foods/cooked_potato");
        public static final TagKey<Item> COOKED_MEATS = createTag("foods/cooked_meats");



        /** Tag for items that should do knockback if the config for
         *  knockback restriction is turned on.
         */
        public static final TagKey<Item> DO_KNOCKBACK_ITEMS = createTag("do_knockback_items");

        public static final TagKey<Item> ON_CRAFT_WOODEN_SOUND = createTag("on_craft_wooden_sound");

        public static final TagKey<Item> ON_CRAFT_SLIME_SOUND = createTag("on_craft_slime_sound");


        private static TagKey<Item> createTag(String tagId) {
            return TagRegistration.ITEM_TAG.registerC(tagId);
        }
    }
}
