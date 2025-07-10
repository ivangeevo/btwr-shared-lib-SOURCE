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




    }
}
