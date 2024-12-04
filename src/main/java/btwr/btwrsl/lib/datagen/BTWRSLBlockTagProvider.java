package btwr.btwrsl.lib.datagen;

import btwr.btwrsl.tag.BTWRConventionalTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BTWRSLBlockTagProvider extends FabricTagProvider.BlockTagProvider {


    public BTWRSLBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);

    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        addToModTags();
        addToVanillaTags();
        addToConventionalTags();
    }

    private void addToVanillaTags()
    {


    }

    private void addToModTags()
    {
    }

    private void addToConventionalTags()
    {
        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.VANILLA_CONVERTING_BLOCKS)
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
