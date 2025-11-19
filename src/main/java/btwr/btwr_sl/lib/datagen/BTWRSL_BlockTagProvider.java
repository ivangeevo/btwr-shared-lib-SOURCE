package btwr.btwr_sl.lib.datagen;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BTWRSL_BlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public BTWRSL_BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        addToModTags();
        addToVanillaTags();
        addToConventionalTags();
    }

    private void addToVanillaTags() {

    }

    private void addToModTags() {

    }

    private void addToConventionalTags() {

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_DIRT)
                .add(Blocks.DIRT);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_GRASS)
                .add(Blocks.GRASS_BLOCK);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.SHEARS_EFFICIENT)
                .forceAddTag(BlockTags.LEAVES)
                .add(Blocks.VINE)
                .add(Blocks.GLOW_LICHEN);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.VANILLA_CONVERTING_BLOCKS)
                .forceAddTag(BlockTags.OAK_LOGS)
                .forceAddTag(BlockTags.BIRCH_LOGS)
                .forceAddTag(BlockTags.SPRUCE_LOGS)
                .forceAddTag(BlockTags.JUNGLE_LOGS)
                .forceAddTag(BlockTags.ACACIA_LOGS)
                .forceAddTag(BlockTags.DARK_OAK_LOGS)
                .forceAddTag(BlockTags.MANGROVE_LOGS)
                .forceAddTag(BlockTags.CHERRY_LOGS)

                .forceAddTag(BlockTags.COAL_ORES)
                .forceAddTag(BlockTags.IRON_ORES)
                .forceAddTag(BlockTags.COPPER_ORES)
                .forceAddTag(BlockTags.GOLD_ORES)
                .forceAddTag(BlockTags.LAPIS_ORES)
                .forceAddTag(BlockTags.REDSTONE_ORES)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES)

                .forceAddTag(BlockTags.BASE_STONE_OVERWORLD)
                .forceAddTag(BlockTags.BASE_STONE_NETHER);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.FARMLAND_BLOCKS)
                .add(Blocks.FARMLAND);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.WEB_BLOCKS)
                .add(Blocks.COBWEB);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.LOOSEN_ON_IMPROPER_BREAK)
                .add(Blocks.DIRT)
                .add(Blocks.GRASS_BLOCK)
                .add(Blocks.PODZOL)
                .add(Blocks.COARSE_DIRT);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.STONE_STRATA1)
                .add(Blocks.STONE)
                .add(Blocks.GRANITE)
                .add(Blocks.DIORITE)
                .add(Blocks.ANDESITE);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.STONE_STRATA2)
                .add(Blocks.TUFF);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.STONE_STRATA3)
                .add(Blocks.DEEPSLATE);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.ORE_STRATA1)
                .add(Blocks.COAL_ORE)
                .add(Blocks.COPPER_ORE)
                .add(Blocks.IRON_ORE)
                .add(Blocks.GOLD_ORE)
                .add(Blocks.LAPIS_ORE)
                .add(Blocks.REDSTONE_ORE)
                .add(Blocks.EMERALD_ORE)
                .add(Blocks.DIAMOND_ORE);

        // No strata 2 by default, so no ores added
        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.ORE_STRATA2);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.ORE_STRATA3)
                .add(Blocks.DEEPSLATE_COAL_ORE)
                .add(Blocks.DEEPSLATE_COPPER_ORE)
                .add(Blocks.DEEPSLATE_IRON_ORE)
                .add(Blocks.DEEPSLATE_GOLD_ORE)
                .add(Blocks.DEEPSLATE_LAPIS_ORE)
                .add(Blocks.DEEPSLATE_REDSTONE_ORE)
                .add(Blocks.DEEPSLATE_EMERALD_ORE)
                .add(Blocks.DEEPSLATE_DIAMOND_ORE);

    }
}