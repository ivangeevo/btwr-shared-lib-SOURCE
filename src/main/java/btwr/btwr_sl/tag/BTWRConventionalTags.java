package btwr.btwr_sl.tag;

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

        // Blocks are considered wooden but are special type/unaccounted for by vanilla tags
        // Mods should add their blocks to this tag if they want them to work with the
        // "Don't Spawn Mobs on Wood" config option from BTWR: Core
        public static final TagKey<Block> WOODEN_MISC_BLOCKS  = createTag("wooden_misc_blocks");

        // Stump blocks grouped; initially used for Sturdy Tree's Stump blocks
        public static final TagKey<Block> STUMP_BLOCKS = createTag("stump_blocks");

        // Farmland viable blocks (can be planted on them)
        public static final TagKey<Block> FARMLAND_BLOCKS = createTag("farmland_blocks");

        // Farmland-like blocks that do not require moisture to grow plants
        public static final TagKey<Block> ALWAYS_FERTILE_SOIL = createTag("always_fertile_soil");

        // Dirt blocks that can be converted to farmland (via BTWR: Core's alternative hoe mechanic or another mod's)
        public static final TagKey<Block> FARMLAND_VIABLE_DIRT = createTag("farmland_viable_dirt");

        // Grass blocks that can be converted to dirt on hoe left click break
        public static final TagKey<Block> FARMLAND_VIABLE_GRASS = createTag("farmland_viable_grass");

        // Combining Tag for Web like blocks ( Cobweb and Web Block from Animageddon)
        public static final TagKey<Block> WEB_BLOCKS = createTag("web_blocks");

        public static final TagKey<Block> STONE = createTag("stone");


        // existing conventional tags from fabric
        public static final TagKey<Block> ORES = createTag("ores");

        public static final TagKey<Block> SHEARS_EFFICIENT = createTag("shears_efficient");

        // Tags for blocks that loosen neighbors when broken with improper tool; mainly used in Tough Environment
        public static final TagKey<Block> LOOSEN_ON_IMPROPER_BREAK = createTag("loosen_on_improper_break");

        public static final TagKey<Block> LOOSEN_ON_IMPROPER_BREAK_SLABS = createTag("loosen_on_improper_break_slabs");



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
        public static final TagKey<Item> ADVANCED_CHISELS = createTag("advanced_chisels");

        public static final TagKey<Item> PRIMITIVE_PICKAXES = createTag("primitive_pickaxes");
        public static final TagKey<Item> MODERN_PICKAXES = createTag("modern_pickaxes");
        public static final TagKey<Item> ADVANCED_PICKAXES = createTag("advanced_pickaxes");
        public static final TagKey<Item> PICKAXES_HARVEST_FULL_BLOCK = createTag("pickaxes_harvest_full_block");

        public static final TagKey<Item> FULLY_MINES_STRATA_1 = createTag("fully_mines_strata_1");
        public static final TagKey<Item> FULLY_MINES_STRATA_2 = createTag("fully_mines_strata_2");
        public static final TagKey<Item> FULLY_MINES_STRATA_3 = createTag("fully_mines_strata_3");

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

        public static final TagKey<Item> WOODEN_TOOLS = createTag("wooden_tools");
        public static final TagKey<Item> STONE_TOOLS = createTag("stone_tools");
        public static final TagKey<Item> IRON_TOOLS = createTag("iron_tools");
        public static final TagKey<Item> GOLDEN_TOOLS = createTag("gold_tools");
        public static final TagKey<Item> DIAMOND_TOOLS = createTag("diamond_tools");
        public static final TagKey<Item> NETHERITE_TOOLS = createTag("netherite_tools");


        public static final TagKey<Item> SPIT_CAMPFIRE_ITEMS = createTag("spit_campfire_items");

        public static final TagKey<Item> SHEARS = createTag("shears");

        public static final TagKey<Item> CHICKEN_TEMPT_ITEMS = createTag("chicken_tempt_items");

        public static final TagKey<Item> STRING_TOOL_MATERIALS = createTag("string_tool_materials");

        // all mechanical gear items used in recipes normally
        public static final TagKey<Item> GEARS = createTag("gears");

        // food
        public static final TagKey<Item> COOKED_EGG_FOODS = createTag("foods/cooked_egg");

        public static final TagKey<Item> COOKED_POTATO_FOODS = createTag("foods/cooked_potato");
        public static final TagKey<Item> COOKED_MEATS_FOR_SANDWICH = createTag("foods/cooked_meats_for_sandwich");

        public static final TagKey<Item> COBBLESTONE_CRAFTING_MATERIALS = createTag("cobblestone_crafting_materials");


        /** Tag for items that should do knockback if the config for
         *  knockback restriction is turned on.
         */
        public static final TagKey<Item> DO_KNOCKBACK_ITEMS = createTag("do_knockback_items");

        // On craft tool items
        public static final TagKey<Item> ON_CRAFT_WOODEN_TOOL_SOUND = createTag("on_craft_wooden_tool_sound");
        public static final TagKey<Item> ON_CRAFT_STONE_TOOL_SOUND = createTag("on_craft_stone_tool_sound");
        /** Diamond tools usually also count as "metallic" for this tag. **/
        public static final TagKey<Item> ON_CRAFT_METALLIC_TOOL_SOUND = createTag("on_craft_metallic_tool_sound");


        // On craft misc items
        public static final TagKey<Item> ON_CRAFT_WOODEN_SOUND = createTag("on_craft_wooden_sound");

        public static final TagKey<Item> ON_CRAFT_SLIME_SOUND = createTag("on_craft_slime_sound");

        public static final TagKey<Item> ON_CRAFT_SHEARS_CUT_SOUND = createTag("on_craft_shears_cut_sound");

        public static final TagKey<Item> ON_CRAFT_FIZZ_SOUND = createTag("on_craft_fizz_sound");

        /** Items that have added remainder logic to get damaged when used in crafting
         * <p> This is used mostly in the CraftingWithToolShapelessRecipe</p>**/
        public static final TagKey<Item> DAMAGE_ON_CRAFTING = createTag("damage_on_crafting");

        private static TagKey<Item> createTag(String tagId) {
            return TagRegistration.ITEM_TAG.registerC(tagId);
        }
    }
}
