package btwr.btwrsl.lib.datagen;

import btwr.btwrsl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class BTWRSL_ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public BTWRSL_ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        addToConventionalTags();
    }
    
    private void addToConventionalTags() {

        getOrCreateTagBuilder(BTWRConventionalTags.Items.STRING_TOOL_MATERIALS)
                .add(Items.STRING);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .add(Items.BAKED_POTATO);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.COOKED_MEATS_FOR_SANDWICH)
                .forceAddTag(ConventionalItemTags.COOKED_MEAT_FOODS)
                .forceAddTag(ConventionalItemTags.COOKED_FISH_FOODS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.DO_KNOCKBACK_ITEMS)
                // All swords too
                .forceAddTag(ItemTags.SWORDS)

                // Special weapon items
                .add(Items.TRIDENT)

                // Axes do knockback only if iron or above
                .add(Items.IRON_AXE)
                .add(Items.DIAMOND_AXE)
                .add(Items.NETHERITE_AXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.PRIMITIVE_PICKAXES)
                .add(Items.WOODEN_PICKAXE)
                .add(Items.STONE_PICKAXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.MODERN_PICKAXES)
                .add(Items.IRON_PICKAXE)
                .add(Items.GOLDEN_PICKAXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ADVANCED_PICKAXES)
                .add(Items.NETHERITE_PICKAXE)
                .add(Items.DIAMOND_PICKAXE);


        getOrCreateTagBuilder(BTWRConventionalTags.Items.PRIMITIVE_AXES)
                .add(Items.WOODEN_AXE)
                .add(Items.STONE_AXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.MODERN_AXES)
                .add(Items.IRON_AXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.DIAMOND_AXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ADVANCED_AXES)
                .add(Items.NETHERITE_AXE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
                .add(Items.IRON_AXE)
                .add(Items.DIAMOND_AXE)
                .addTag(BTWRConventionalTags.Items.ADVANCED_AXES);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.PRIMITIVE_HOES)
                .add(Items.WOODEN_HOE)
                .add(Items.STONE_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.MODERN_HOES)
                .add(Items.IRON_HOE)
                .add(Items.GOLDEN_HOE)
                .add(Items.DIAMOND_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ADVANCED_HOES)
                .add(Items.NETHERITE_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.SHEARS)
                .add(Items.SHEARS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.WOODEN_TOOLS)
                .add(Items.WOODEN_AXE)
                .add(Items.WOODEN_PICKAXE)
                .add(Items.WOODEN_SHOVEL)
                .add(Items.WOODEN_SWORD)
                .add(Items.WOODEN_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.STONE_TOOLS)
                .add(Items.STONE_AXE)
                .add(Items.STONE_PICKAXE)
                .add(Items.STONE_SHOVEL)
                .add(Items.STONE_SWORD)
                .add(Items.STONE_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.IRON_TOOLS)
                .add(Items.IRON_AXE)
                .add(Items.IRON_PICKAXE)
                .add(Items.IRON_SHOVEL)
                .add(Items.IRON_SWORD)
                .add(Items.IRON_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.GOLDEN_TOOLS)
                .add(Items.GOLDEN_AXE)
                .add(Items.GOLDEN_PICKAXE)
                .add(Items.GOLDEN_SHOVEL)
                .add(Items.GOLDEN_SWORD)
                .add(Items.GOLDEN_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.DIAMOND_TOOLS)
                .add(Items.DIAMOND_AXE)
                .add(Items.DIAMOND_PICKAXE)
                .add(Items.DIAMOND_SHOVEL)
                .add(Items.DIAMOND_SWORD)
                .add(Items.DIAMOND_HOE);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.NETHERITE_TOOLS)
                .add(Items.NETHERITE_AXE)
                .add(Items.NETHERITE_PICKAXE)
                .add(Items.NETHERITE_SHOVEL)
                .add(Items.NETHERITE_SWORD)
                .add(Items.NETHERITE_HOE);

        // Misc item crafting sound tags
        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_WOODEN_SOUND)
                .forceAddTag(ItemTags.PLANKS)
                .add(Items.STICK);

        // Tool item crafting sound tags
        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_WOODEN_TOOL_SOUND)
                .addTag(BTWRConventionalTags.Items.WOODEN_TOOLS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_STONE_TOOL_SOUND)
                .addTag(BTWRConventionalTags.Items.STONE_TOOLS);

        getOrCreateTagBuilder(BTWRConventionalTags.Items.ON_CRAFT_METALLIC_TOOL_SOUND)
                .addTag(BTWRConventionalTags.Items.IRON_TOOLS)
                .addTag(BTWRConventionalTags.Items.GOLDEN_TOOLS)
                .addTag(BTWRConventionalTags.Items.DIAMOND_TOOLS)
                .addTag(BTWRConventionalTags.Items.NETHERITE_TOOLS);


        }

}