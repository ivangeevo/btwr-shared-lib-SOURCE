// +++START EDIT+++
package org.btwr.shared_library.effect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.btwr.shared_library.sound.BTWRSounds;

import java.util.function.DoubleSupplier;

// TODO: Fix categories for each effect? Right now they are all MISC
@Environment(EnvType.CLIENT)
public class BTWREffectManager {
    public static final int ANIMAL_BIRTHING_EFFECT_ID = 2222;
    public static final int SAW_DAMAGE_EFFECT_ID = 2223;
    public static final int NETHER_GROTH_SPORES_EFFECT_ID = 2224;
    public static final int GHAST_SCREAM_EFFECT_ID = 2225;
    public static final int BURP_SOUND_EFFECT_ID = 2226;
    public static final int FIRE_FIZZ_EFFECT_ID = 2227;
    public static final int GHAST_MOAN_EFFECT_ID = 2228;
    public static final int MINING_CHARGE_EXPLOSION_EFFECT_ID = 2229;
    public static final int HOPPER_EJECT_XP_EFFECT_ID = 2230;
    public static final int ITEM_COLLECTION_POP_EFFECT_ID = 2231;
    public static final int XP_EJECT_POP_EFFECT_ID = 2232;
    public static final int HOPPER_CLOSE_EFFECT_ID = 2233;
    public static final int REDSTONE_CLICK_EFFECT_ID = 2234;
    public static final int MECHANICAL_DEVICE_EXPLODE_EFFECT_ID = 2235;
    public static final int BLOCK_PLACE_EFFECT_ID = 2236;
    public static final int DYNAMITE_FUSE_EFFECT_ID = 2237;
    public static final int LOW_PITCH_CLICK_EFFECT_ID = 2238;
    public static final int WOLF_HURT_EFFECT_ID = 2239;
    public static final int CHICKEN_HURT_EFFECT_ID = 2240;
    public static final int BLOCK_DISPENSER_SMOKE_EFFECT_ID = 2241;
    public static final int COMPANION_CUBE_DEATH_EFFECT_ID = 2242;
    public static final int POSSESSED_CHICKEN_EXPLOSION_EFFECT_ID = 2243;
    public static final int ENDERMAN_COLLECT_BLOCK_EFFECT_ID = 2244;
    public static final int ENDERMAN_CONVERT_BLOCK_EFFECT_ID = 2245;
    public static final int ENDERMAN_PLACE_BLOCK_EFFECT_ID = 2246;
    public static final int ENDERMAN_CHANGE_DIMENSION_EFFECT_ID = 2247;
    public static final int SOUL_URN_SHATTER_EFFECT_ID = 2248;
    public static final int MELON_EXPLODE_EFFECT_ID = 2249;
    public static final int PUMPKIN_EXPLODE_EFFECT_ID = 2250;
    public static final int GOURD_IMPACT_SOUND_EFFECT_ID = 2251;
    public static final int DESTROY_BLOCK_RESPECT_PARTICLE_SETTINGS_EFFECT_ID = 2252;
    public static final int COW_REGEN_MILK_EFFECT_ID = 2253;
    public static final int COW_MILKING_EFFECT_ID = 2254;
    public static final int COW_CONVERSION_TO_MOOSHROOM_EFFECT_ID = 2255;
    public static final int WOLF_HOWL_EFFECT_ID = 2256;
    public static final int WOLF_CONVERSION_TO_DIRE_WOLF_EFFECT_ID = 2257;
    public static final int CREEPER_SNIP_EFFECT_ID = 2258;
    public static final int POSSESSED_PIG_TRANSFORMATION_EFFECT_ID = 2259;
    public static final int POSSESSED_VILLAGER_TRANSFORMATION_EFFECT_ID = 2260;
    public static final int SHEEP_REGROW_WOOL_EFFECT_ID = 2261;
    public static final int SQUID_TENTACLE_FLING_EFFECT_ID = 2262;
    public static final int CREATE_SNOW_GOLEM_EFFECT_ID = 2263;
    public static final int CREATE_IRON_GOLEM_EFFECT_ID = 2264;
    public static final int TOSS_THE_MILK_EFFECT_ID = 2265;
    public static final int APPLY_DUNG_TO_WOLF_EFFECT_ID = 2266;
    public static final int REMOVE_STUMP_EFFECT_ID = 2267;
    public static final int SHAFT_RIPPED_OFF_EFFECT_ID = 2268;
    public static final int STONE_RIPPED_OFF_EFFECT_ID = 2269;
    public static final int GRAVEL_RIPPED_OFF_EFFECT_ID = 2270;
    public static final int WOOD_BLOCK_DESTROYED_EFFECT_ID = 2271;
    public static final int BLOCK_DESTROYED_WITH_IMPROPER_TOOL_EFFECT_ID = 2272;
    public static final int POSSESSED_SQUID_TRANSFORMATION_EFFECT_ID = 2273;
    public static final int APPLY_MORTAR_EFFECT_ID = 2274;
    public static final int LOOSE_BLOCK_STUCK_TO_MORTAR_EFFECT_ID = 2275;
    public static final int SMOLDERING_LOG_FALL_EFFECT_ID = 2276;
    public static final int SMOLDERING_LOG_EXPLOSION_EFFECT_ID = 2277;
    public static final int WATER_EVAPORATION_EFFECT_ID = 2278;
    public static final int CREATE_WITHER_EFFECT_ID = 2279;
    public static final int LIGHTNING_STRIKE_EFFECT_ID = 2280;
    public static final int FLAMING_NETHERRACK_FALL_EFFECT_ID = 2281;
    public static final int CACTUS_EXPLOSION_EFFECT_ID = 2282;
    public static final int ANIMAL_EATING_EFFECT_ID = 2283;
    public static final int WOLF_EATING_EFFECT_ID = 2284;
    public static final int FAILED_EATING_EFFECT_ID = 2285;
    public static final int SOUL_SPREAD_EFFECT_ID = 2286;
    public static final int POSSESSION_COMPLETE_EFFECT_ID = 2287;
    public static final int INFUSION_EFFECT_ID = 2288;
    public static final int BLOCK_CONVERT_EFFECT_ID = 2289;
    public static final int LOG_STRIP_EFFECT_ID = 2290;
    public static final int DIRT_TILLING_EFFECT_ID = 2291;
    public static final int GLOOM_BITES_EFFECT_ID = 2292;
    public static final int IRON_DOOR_EFFECT_ID = 2293;
    public static final int BLOCK_BREAK_EFFECT_ID = 2294;
    public static final int WOOD_DOOR_EFFECT_ID = 2295;

    public static void initEffects() {
        /**
        initBlockEffects();
        
        EffectHandler.effectMap.put(ANIMAL_BIRTHING_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F);

            for (int counter = 0; counter < 10; counter++) {
                double bloodX = pos.getX() + world.getRandom().nextDouble();
                double bloodY = pos.getY() + 1.0D + world.getRandom().nextDouble();
                double bloodZ = pos.getZ() + world.getRandom().nextDouble();

                world.addParticle("reddust", bloodX, bloodY, bloodZ, 0.0D, 0.0D, 0.0D);
            }

            for (int i = 0; i < 10; i++) {
                double bloodX = pos.getX() - 0.5D + world.getRandom().nextDouble();
                double bloodY = pos.getY() + world.getRandom().nextDouble();
                double bloodZ = pos.getZ() - 0.5D + world.getRandom().nextDouble();

                world.addParticle("dripLava", bloodX, bloodY, bloodZ, 0.0D, 0.0D, 0.0D);
            }
        });

        EffectHandler.effectMap.put(SAW_DAMAGE_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos,
                    "minecart.base",
                    1.00F + (world.getRandom().nextFloat() * 0.1F),        // volume
                    2.0F + (world.getRandom().nextFloat() * 0.1F));    // pitch

            int facing = data;

            // emit blood particles

            BlockPos targetPos = new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ(), facing);

            for (int i = 0; i < 10; i++) {
                float smokeX = (float) targetPos.pos.getX() + world.getRandom().nextFloat();
                float smokeY = (float) targetPos.pos.getY() + world.getRandom().nextFloat();
                float smokeZ = (float) targetPos.pos.getZ() + world.getRandom().nextFloat();

                world.addParticle("reddust", smokeX, smokeY, smokeZ, 0.0D, 0.0D, 0.0D);
            }
        });

        EffectHandler.effectMap.put(NETHER_GROTH_SPORES_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, BTWRSounds.SOUL_SCREAM.sound(), 4.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);

            for (int i = 0; i < 10; i++) {
                world.addParticle("hugeexplosion",
                        pos.getX() + world.getRandom().nextDouble() * 10.0D - 5D,
                        pos.getY() + world.getRandom().nextDouble() * 10.0D - 5D,
                        pos.getZ() + world.getRandom().nextDouble() * 10.0D - 5D,
                        0.0D, 0.0D, 0.0D);
            }
        });

        EffectHandler.effectMap.put(GHAST_SCREAM_EFFECT_ID, (world, player, pos, state) -> {
            float screamPitch = world.getRandom().nextFloat() * 0.4F + 0.8F;

            if (data == 1) {
                // low pitch used by Soulforged Steel
                screamPitch = world.getRandom().nextFloat() * 0.4F + 0.25F;
            }

            world.playSound(pos, "mob.ghast.scream", 1.0F, screamPitch);
        });

        EffectHandler.effectMap.put(BURP_SOUND_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "random.burp",1.0F, world.getRandom().nextFloat() * 0.4F + 0.7F)
        );

        EffectHandler.effectMap.put(FIRE_FIZZ_EFFECT_ID, (world, player, pos, state) -> {
            float fizzVolume = 0.5F;
            float fizzPitch = 2.6F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8F;

            if (data == 1) {
                fizzVolume = 0.1F;
                fizzPitch = 1F + +(world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F;
            }

            world.playSound(pos, "random.fizz", fizzVolume, fizzPitch);
        });

        EffectHandler.effectMap.put(GHAST_MOAN_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "mob.ghast.moan",0.5F, 2.6F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8F)
        );

        EffectHandler.effectMap.put(MINING_CHARGE_EXPLOSION_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "random.explode", 4F, (1.0F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F) * 0.7F);
            world.addParticle("hugeexplosion", pos, 0.0D, 0.0D, 0.0D);
        });

        EffectHandler.effectMap.put(HOPPER_EJECT_XP_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "liquid.lavapop", 0.5F + world.getRandom().nextFloat() * 0.25F, 0.5F + world.getRandom().nextFloat() * 0.25F);

            for (int i = 0; i < 4; i++) {
                world.addParticle("slime", pos.getX(), pos.getY() - 0.6, pos.getZ(), 0.0D, 0.0D, 0.0D);
            }
        });

        EffectHandler.effectMap.put(ITEM_COLLECTION_POP_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "random.pop", 0.25F, ((world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F)
        );

        EffectHandler.effectMap.put(XP_EJECT_POP_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "liquid.lavapop", 0.5F + world.getRandom().nextFloat() * 0.25F, 0.5F + world.getRandom().nextFloat() * 0.25F)
        );

        EffectHandler.effectMap.put(HOPPER_CLOSE_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "mob.irongolem.walk", 1.0F, 1.25F)
        );

        EffectHandler.effectMap.put(REDSTONE_CLICK_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "random.click", 0.75F, 2.0F)
        );

        EffectHandler.effectMap.put(MECHANICAL_DEVICE_EXPLODE_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "mob.zombie.woodbreak", 0.5F, 0.60F + (world.getRandom().nextFloat() * 0.25F));
            world.addParticle("explode", pos, 0D, 0D, 0D);

            for (int i = 0; i < 20; i++) {
                double smokeX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double smokeY = pos.getY() + world.getRandom().nextDouble() - 0.5D;
                double smokeZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double smokeVelX = (smokeX - pos.getX()) * 0.33D;
                double smokeVelY = (smokeY - pos.getY()) * 0.33D;
                double smokeVelZ = (smokeZ - pos.getZ()) * 0.33D;

                world.addParticle("smoke", smokeX, smokeY, smokeZ, smokeVelX, smokeVelY, smokeVelZ);
            }
        });

        EffectHandler.effectMap.put(BLOCK_PLACE_EFFECT_ID, (world, player, pos, state) -> {
            int blockID = data;
            Block block = Block.blocksList[blockID];

            if (block != null) {
                world.playSound(pos,
                        block.stepSound.getPlaceSound(),
                        (block.stepSound.getPlaceVolume() + 1.0F) / 2.0F,
                        block.stepSound.getPlacePitch() * 0.8F);
            }
        });

        EffectHandler.effectMap.put(DYNAMITE_FUSE_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "random.fuse", 1.0F, 1.0F)
        );

        EffectHandler.effectMap.put(LOW_PITCH_CLICK_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "random.click", 0.10F, 0.5F)
        );

        EffectHandler.effectMap.put(WOLF_HURT_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "mob.wolf.hurt", 0.4F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F)
        );

        EffectHandler.effectMap.put(CHICKEN_HURT_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "mob.chicken.hurt", 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F)
        );

        EffectHandler.effectMap.put(BLOCK_DISPENSER_SMOKE_EFFECT_ID, (world, player, pos, state) -> {
            int facing = data;

            BlockPos targetDeltaPos = new BlockPos(0, 0, 0, facing);

            double ejectX = pos.getX() + targetDeltaPos.pos.getX() * 0.6D;
            double ejectY = pos.getY() + targetDeltaPos.pos.getY() * 0.6D;
            double ejectZ = pos.getZ() + targetDeltaPos.pos.getZ() * 0.6D;

            for (int i = 0; i < 10; i++) {
                double d4 = world.getRandom().nextDouble() * 0.2D + 0.01D;

                double smokeX = ejectX + targetDeltaPos.pos.getX() * 0.01D + (world.getRandom().nextDouble() - 0.5D) * targetDeltaPos.pos.getX() * 0.5D;
                double smokeY = ejectY + targetDeltaPos.pos.getY() * 0.01D + (world.getRandom().nextDouble() - 0.5D) * 0.5D;
                double smokeZ = ejectZ + targetDeltaPos.pos.getZ() * 0.01D + (world.getRandom().nextDouble() - 0.5D) * targetDeltaPos.pos.getZ() * 0.5D;

                double smokeVelX = targetDeltaPos.pos.getX() * d4 + world.getRandom().nextGaussian() * 0.01D;
                double smokeVelY = targetDeltaPos.pos.getY() * d4 - 0.03D + world.getRandom().nextGaussian() * 0.01D;
                double smokeVelZ = targetDeltaPos.pos.getZ() * d4 + world.getRandom().nextGaussian() * 0.01D;

                world.addParticle("smoke", smokeX, smokeY, smokeZ, smokeVelX, smokeVelY, smokeVelZ);
            }
        });

        EffectHandler.effectMap.put(COMPANION_CUBE_DEATH_EFFECT_ID, (world, player, pos, state) -> {
            CompanionCubeBlock.spawnHearts(world, (int) pos.getX(), (int) pos.getY(), (int) pos.getZ());
            world.playSound(pos, "mob.wolf.whine", 0.5F, 2.6F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8F);
        });

        EffectHandler.effectMap.put(POSSESSED_CHICKEN_EXPLOSION_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "random.explode", 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F);
            world.playSound(pos, "mob.chicken.hurt", 2.0F, world.getRandom().nextFloat() * 0.4F + 1.2F);

            for (int i = 0; i < 10; i++) {
                double bloodX = pos.getX() + world.getRandom().nextDouble();
                double bloodY = pos.getY() + 1.0D + world.getRandom().nextDouble();
                double bloodZ = pos.getZ() + world.getRandom().nextDouble();

                world.addParticle("reddust", bloodX, bloodY, bloodZ, 0.0D, 0.0D, 0.0D);
            }

            for (int i = 0; i < 10; i++) {
                double bloodX = pos.getX() - 0.5D + world.getRandom().nextDouble();
                double bloodY = pos.getY() + world.getRandom().nextDouble() * 0.5F;
                double bloodZ = pos.getZ() - 0.5D + world.getRandom().nextDouble();

                world.addParticle("dripLava", bloodX, bloodY, bloodZ, 0.0D, 0.0D, 0.0D);
            }

            for (int i = 0; i < 300; i++) {
                double bloodX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double bloodY = pos.getY() - 1.0D;// + world.getRandom().nextDouble() * 0.25D;
                double bloodZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double bloodVelX = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
                double bloodVelY = 0.2D + world.getRandom().nextDouble() * 0.6D;
                double bloodVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.5D;

                // 331 = redstone dust based particles
                world.addParticle("iconcrack_331", bloodX, bloodY, bloodZ, bloodVelX, bloodVelY, bloodVelZ);
            }

            for (int i = 0; i < 25; i++) {
                double boneX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double boneY = pos.getY() - 1.0D;// + world.getRandom().nextDouble() * 0.25D;
                double boneZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double boneVelX = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
                double boneVelY = 0.2D + world.getRandom().nextDouble() * 0.6D;
                double boneVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.5D;

                // 352 = bone based particles
                world.addParticle("iconcrack_352", boneX, boneY, boneZ, boneVelX, boneVelY, boneVelZ);
            }
        });

        EffectHandler.effectMap.put(ENDERMAN_COLLECT_BLOCK_EFFECT_ID, (world, player, pos, state) -> {
            int blockID = data & 0xfff;
            int metadata = data >> 12 & 0xff;

            Block block = Block.blocksList[blockID];

            if (block != null) {
                world.playSound(pos, block.stepSound.getBreakSound(), (block.stepSound.getBreakVolume() + 1.0F) / 2.0F, block.stepSound.getBreakPitch() * 0.8F);
                world.effectRenderer.addBlockDestroyEffects((int) pos.getX(), (int) pos.getY(), (int) pos.getZ(), blockID, metadata);
            }
        });

        EffectHandler.effectMap.put(ENDERMAN_CONVERT_BLOCK_EFFECT_ID, (world, player, pos, state) -> {
            int blockID = data & 0xfff;
            int metadata = data >> 12 & 0xff;
            Block block = Block.blocksList[blockID];

            if (block != null) {
                world.playSound(pos, block.stepSound.getBreakSound(), (block.stepSound.getBreakVolume() + 1.0F) / 2.0F, block.stepSound.getBreakPitch() * 0.8F);
                world.effectRenderer.addBlockDestroyEffects((int) pos.getX(), (int) pos.getY(), (int) pos.getZ(), blockID, metadata);
            }

            for (int i = 0; i < 25; i++) {
                double particleX = pos.getX() + (world.getRandom().nextDouble() - 0.5D) * 1.5D;
                double particleY = pos.getY() + (world.getRandom().nextDouble() - 0.5D);
                double particleZ = pos.getZ() + (world.getRandom().nextDouble() - 0.5D) * 1.5D;

                world.addParticle("mobSpell", particleX, particleY, particleZ, 0D, 0D, 0D);
            }

            world.playSound(pos, "mob.endermen.portal", 1.0F, 1.0F);
        });

        EffectHandler.effectMap.put(ENDERMAN_PLACE_BLOCK_EFFECT_ID, (world, player, pos, state) -> {
            int blockID = data & 0xfff;
            Block block = Block.blocksList[blockID];

            if (block != null) {
                world.playSound(pos, block.stepSound.getStepSound(), (block.stepSound.getStepVolume() + 1.0F) / 2.0F, block.stepSound.getStepPitch() * 0.8F);
            }

            world.playSound(pos, "mob.endermen.hit", 1.0F, 1.0F);
        });

        EffectHandler.effectMap.put(ENDERMAN_CHANGE_DIMENSION_EFFECT_ID, (world, player, pos, state) -> {
            world.addParticle("largeexplode", pos, 0.0D, 0.0D, 0.0D);
            world.playSound(pos, BTWRSounds.ENDERMAN_CHANGE_DIMENSION.sound(), 3.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
        });

        EffectHandler.effectMap.put(SOUL_URN_SHATTER_EFFECT_ID, (world, player, pos, state) -> {
            for (int i = 0; i < 8; i++) {
                world.addParticle("snowballpoof", pos, 0.0D, 0.0D, 0.0D);
            }

            world.playSound(pos, "random.glass", 1.0F, 1.2F / (world.getRandom().nextFloat() * 0.2F + 0.9F));
            world.playSound(pos, "mob.ghast.scream", 0.2F, world.getRandom().nextFloat() * 0.2F + 0.5F);

            for (int i = 0; i < 100; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() * 3D - 1.5D;
                double particleY = pos.getY() + world.getRandom().nextDouble() * 3D - 1.5D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() * 3D - 1.5D;

                world.addParticle("mobSpell", particleX, particleY, particleZ, 0, 0, 0);
            }
        });

        EffectHandler.effectMap.put(COW_REGEN_MILK_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 0.6F)
        );

        EffectHandler.effectMap.put(COW_MILKING_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 0.6F);
            String milkParticle = "iconcrack_332"; // snowball

            for (int i = 0; i < 50; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double particleY = pos.getY() - 0.45D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double particleVelX = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
                double particleVelY = world.getRandom().nextDouble() * 0.25D;
                double particleVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.5D;

                world.addParticle(milkParticle, particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }
        });

        EffectHandler.effectMap.put(COW_CONVERSION_TO_MOOSHROOM_EFFECT_ID, (world, player, pos, state) -> {
            world.addParticle("largeexplode", pos, 0.0D, 0.0D, 0.0D);
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F);

            float hurtPitch = (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F;

            if (data > 0) {
                // child pitch
                hurtPitch += 0.5F;
            }

            world.playSound(pos, "mob.cow.hurt", 1.0F, hurtPitch);
        });

        EffectHandler.effectMap.put(WOLF_HOWL_EFFECT_ID, (world, player, pos, state) -> {
            float soundVolume;
            float soundPitch;

            if (data > 0) {
                // dire howl
                soundVolume = 10F;
                soundPitch = (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.05F + 0.55F;
            } else {
                // regular wolf howl
                soundVolume = 8.5F;
                soundPitch = (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1F;
            }

            EntityPlayer localPlayer = world.thePlayer;

            if (localPlayer != null) {
                if (localPlayer.posY < 64) {
                    float volumeMultiplier = (float) (localPlayer.posY / 64D);

                    soundVolume *= volumeMultiplier;

                    if (soundVolume < 1F) {
                        soundVolume = 1F;
                    }
                }
            }

            world.playSound(pos, "mob.wolf.howl", soundVolume, soundPitch);
        });

        EffectHandler.effectMap.put(WOLF_CONVERSION_TO_DIRE_WOLF_EFFECT_ID, (world, player, pos, state) -> {
            world.addParticle("largeexplode", pos, 0.0D, 0.0D, 0.0D);
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F);
            world.playSound(pos, "mob.wolf.growl", 8.5F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.05F + 0.55F);
        });

        EffectHandler.effectMap.put(CREEPER_SNIP_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "mob.sheep.shear", 1.0F, 1.0F);
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.1F + 0.7F);

            String creeperSnipParticle = "iconcrack_332"; // snowball

            for (int i = 0; i < 50; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double particleY = pos.getY() - 0.45D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double particleVelX = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
                double particleVelY = world.getRandom().nextDouble() * 0.25D;
                double particleVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.5D;

                // 360 = melon slice based particles
                world.addParticle(creeperSnipParticle, particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }
        });

        EffectHandler.effectMap.put(POSSESSED_PIG_TRANSFORMATION_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "mob.pig.death", 2.0F, world.getRandom().nextFloat() * 0.4F + 1.2F);
            world.playSound(pos, "mob.zombiepig.zpigangry", 2.0F, ((world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F) * 1.8F);
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F);
            world.addParticle("largeexplode", pos, 0.0D, 0.0D, 0.0D);

            for (int i = 0; i < 50; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double particleY = pos.getY() - 1.0D;// + world.getRandom().nextDouble() * 0.25D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double particleVelX = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
                double particleVelY = 0.2D + world.getRandom().nextDouble() * 0.6D;
                double particleVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.5D;

                // 319 = raw pork based particles
                world.addParticle("iconcrack-319", particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }
        });

        EffectHandler.effectMap.put(POSSESSED_VILLAGER_TRANSFORMATION_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "ambient.weather.thunder", 3.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
            world.playSound(pos, "mob.ghast.affectionate scream", 2.0F, 0.5F + world.getRandom().nextFloat() * 0.25F);
            world.addParticle("largeexplode", pos, 0.0D, 0.0D, 0.0D);

            // basically a duplicate of the witch particle effect
            for (int i = 0; i < world.getRandom().nextInt(35) + 10; ++i) {
                world.addParticle("witchMagic",
                        pos.getX() + world.getRandom().nextGaussian() * 0.125D,
                        pos.getY() + 2.0D + world.getRandom().nextGaussian() * 0.125D,
                        pos.getZ() + world.getRandom().nextGaussian() * 0.125D,
                        0.0D, 0.0D, 0.0D);
            }
        });

        EffectHandler.effectMap.put(SHEEP_REGROW_WOOL_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "step.cloth", 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F)
        );

        EffectHandler.effectMap.put(SQUID_TENTACLE_FLING_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 0.6F);

            if (world.gameSettings.particleSetting <= 1) {
                int blockID = Block.waterStill.blockID;
                int metadata = 0;

                Block block = Block.blocksList[blockID];

                for (int i = 0; i < 4; ++i) {
                    for (int j = 0; j < 4; ++j) {
                        for (int k = 0; k < 4; ++k) {
                            if (world.gameSettings.particleSetting == 0 || world.getRandom().nextInt(3) == 0) {
                                double particleX = i + (i + 0.5D) / 4D;
                                double particleY = j + (j + 0.5D) / 4D;
                                double particleZ = k + (k + 0.5D) / 4D;

                                EntityDiggingFX digEffect = new EntityDiggingFX(world,
                                        particleX, particleY, particleZ,
                                        particleX - i - 0.5D, particleY - j - 0.5D, particleZ - k - 0.5D,
                                        block, metadata);

                                digEffect.applyRenderColor(metadata);
                                world.effectRenderer.addEffect(digEffect);
                            }
                        }
                    }
                }
            }
        });

        EffectHandler.effectMap.put(CREATE_SNOW_GOLEM_EFFECT_ID, (world, player, pos, state) -> {
            for (int i = 0; i < 120; ++i) {
                world.addParticle("snowshovel",
                        (int) pos.getX() + world.getRandom().nextDouble(), ((int) pos.getY() - 2) + world.getRandom().nextDouble() * 2.5D, (int) pos.getZ() + world.getRandom().nextDouble(),
                        0.0D, 0.0D, 0.0D);
            }

            for (int i = 0; i < 8; i++) {
                world.addParticle("snowballpoof", pos, 0.0D, 0.0D, 0.0D);
            }

            world.playSound(pos, "random.glass", 1.0F, 1.2F / (world.getRandom().nextFloat() * 0.2F + 0.9F));
            world.playSound(pos, "mob.enderdragon.growl", 0.25F, world.getRandom().nextFloat() * 0.2F + 1.8F);

            for (int i = 0; i < 100; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() * 3D - 1.5D;
                double particleY = pos.getY() + world.getRandom().nextDouble() * 3D - 1.5D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() * 3D - 1.5D;

                world.addParticle("mobSpell", particleX, particleY, particleZ, 0, 0, 0);
            }
        });

        EffectHandler.effectMap.put(CREATE_IRON_GOLEM_EFFECT_ID, (world, player, pos, state) -> {
            for (int i = 0; i < 120; ++i) {
                world.addParticle("snowballpoof",
                        (int) pos.getX() + world.getRandom().nextDouble(), ((int) pos.getY() - 2) + world.getRandom().nextDouble() * 2.5D, (int) pos.getZ() + world.getRandom().nextDouble(),
                        0.0D, 0.0D, 0.0D);
            }

            for (int i = 0; i < 8; i++) {
                world.addParticle("snowballpoof", pos, 0.0D, 0.0D, 0.0D);
            }

            world.playSound(pos, "random.glass", 1.0F, 1.2F / (world.getRandom().nextFloat() * 0.2F + 0.9F));
            world.playSound(pos, "mob.irongolem.death", 1.0F, world.getRandom().nextFloat() * 0.2F + 0.5F);
            world.playSound(pos, "mob.enderdragon.growl", 0.5F, world.getRandom().nextFloat() * 0.2F + 1.5F);

            for (int i = 0; i < 100; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() * 3D - 1.5D;
                double particleY = pos.getY() + world.getRandom().nextDouble() * 3D - 1.5D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() * 3D - 1.5D;

                world.addParticle("mobSpell", particleX, particleY, particleZ, 0, 0, 0);
            }
        });
        
        EffectHandler.effectMap.put(TOSS_THE_MILK_EFFECT_ID, (world, player, pos, state) -> {
            for (int i = 0; i < 120; ++i) {
                world.addParticle("snowballpoof",
                        (int) pos.getX() + world.getRandom().nextDouble(), ((int) pos.getY() - 2) + world.getRandom().nextDouble() * 2.5D, (int) pos.getZ() + world.getRandom().nextDouble(),
                        0.0D, 0.0D, 0.0D);
            }

            float soundPitch = 2.0F;

            if (data > 0) {
                soundPitch = 1.2F;
            }
            
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 0.5F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.1F + 0.6F);
            world.playSound(pos, "random.classic_hurt", 0.25F, soundPitch);
        });
        
        EffectHandler.effectMap.put(APPLY_DUNG_TO_WOLF_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "mob.wolf.whine", 0.5F, 1.5F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.4F);

            for (int i = 0; i < 15; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double particleY = pos.getY() - 0.5D + world.getRandom().nextDouble() * 0.25D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double particleVelX = (world.getRandom().nextDouble() - 0.5D) * 0.25D;
                double particleVelY = 0.1D + world.getRandom().nextDouble() * 0.1D;
                double particleVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.25D;

                // 319 = raw pork based particles
                world.addParticle("iconcrack_491", particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }

            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 0.5F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.1F + 0.8F);
        });
        
        EffectHandler.effectMap.put(POSSESSED_SQUID_TRANSFORMATION_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1.0F);
            world.playSound(pos, "mob.ghast.scream", 10F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 1F);

            for (int i = 0; i < 10; i++) {
                double explodeX = pos.getX() + (world.getRandom().nextDouble() - 0.5D) * 4D;
                double explodeY = pos.getY() + (world.getRandom().nextDouble() - 0.5D) * 4D;
                double explodeZ = pos.getZ() + (world.getRandom().nextDouble() - 0.5D) * 4D;

                world.addParticle("largeexplode", explodeX, explodeY, explodeZ, 0.0D, 0.0D, 0.0D);
            }
        });

        EffectHandler.effectMap.put(APPLY_MORTAR_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 0.70F + world.getRandom().nextFloat() * 0.1F, 0.85F + world.getRandom().nextFloat() * 0.1F)
        );

        EffectHandler.effectMap.put(LOOSE_BLOCK_STUCK_TO_MORTAR_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 0.15F + world.getRandom().nextFloat() * 0.1F, 0.6F + world.getRandom().nextFloat() * 0.1F)
        );

        EffectHandler.effectMap.put(SMOLDERING_LOG_FALL_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "mob.zombie.woodbreak", 1.25F, 0.5F + world.getRandom().nextFloat() * 0.1F);
            world.playSound(pos, "mob.ghast.fireball", 1F, 0.5F + world.getRandom().nextFloat() * 0.1F);
        });

        EffectHandler.effectMap.put(SMOLDERING_LOG_EXPLOSION_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "mob.zombie.wood", 1.25F, 0.5F + world.getRandom().nextFloat() * 0.1F);
            world.addParticle("largeexplode", pos, 0D, 0D, 0D);

            for (int i = 0; i < 10; i++) {
                world.addParticle("fccinders", pos, 0D, 0D, 0D);
            }
        });

        EffectHandler.effectMap.put(WATER_EVAPORATION_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "random.fizz", 0.5F, 2.6F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.8F);

            for (int i = 0; i < 8; i++) {
                world.addParticle("largesmoke",
                        pos.getX() + world.getRandom().nextDouble() - 0.5D,
                        pos.getY() + world.getRandom().nextDouble() - 0.5D,
                        pos.getZ() + world.getRandom().nextDouble() - 0.5D,
                        0D, 0D, 0D);
            }
        });

        EffectHandler.effectMap.put(CREATE_WITHER_EFFECT_ID, (world, player, pos, state) -> {
            for (int i = 0; i < 120; ++i) {
                world.addParticle("snowballpoof",
                        (int) pos.getX() + world.getRandom().nextDouble(), ((int) pos.getY() - 2) + world.getRandom().nextDouble() * 2.5D, (int) pos.getZ() + world.getRandom().nextDouble(),
                        0.0D, 0.0D, 0.0D);
            }

            for (int i = 0; i < 8; i++) {
                world.addParticle("snowballpoof", pos, 0.0D, 0.0D, 0.0D);
            }

            world.playSound(pos, "random.glass", 1.0F, 1.2F / (world.getRandom().nextFloat() * 0.2F + 0.9F));
            world.playSound(pos, "mob.wither.death", 1.0F, world.getRandom().nextFloat() * 0.2F + 0.5F);
            world.playSound(pos, "mob.enderdragon.growl", 0.5F, world.getRandom().nextFloat() * 0.2F + 1.5F);

            for (int i = 0; i < 100; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() * 3D - 1.5D;
                double particleY = pos.getY() + world.getRandom().nextDouble() * 3D - 1.5D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() * 3D - 1.5D;

                world.addParticle("mobSpell", particleX, particleY, particleZ, 0, 0, 0);
            }
        });

        EffectHandler.effectMap.put(LIGHTNING_STRIKE_EFFECT_ID, (world, player, pos, state) -> {
            world.addParticle("largeexplode", pos, 0D, 0D, 0D);
            world.playSound(pos, "random.explode", 4F, 0.5F + world.getRandom().nextFloat() * 0.2F);
            world.playSound(pos, "ambient.weather.thunder", 10000F, 0.8F + world.getRandom().nextFloat() * 0.2F);
        });

        EffectHandler.effectMap.put(FLAMING_NETHERRACK_FALL_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "mob.ghast.fireball", 0.1F, 0.75F + world.getRandom().nextFloat() * 0.1F)
        );

        EffectHandler.effectMap.put(CACTUS_EXPLOSION_EFFECT_ID, (world, player, pos, state) -> {
            for (int i = 0; i < 150; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double particleY = pos.getY() - 0.45D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double particleVelX = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
                double particleVelY = world.getRandom().nextDouble() * 0.7D;
                double particleVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.5D;

                // 338 = reed based particles
                world.addParticle("iconcrack_338", particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }
        });

        EffectHandler.effectMap.put(ANIMAL_EATING_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "random.eat", 0.75F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 0.6F);

            for (int i = 0; i < 25; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double particleY = pos.getY();
                double particleZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double particleVelX = (world.getRandom().nextDouble() - 0.5D) * 0.25D;
                double particleVelY = world.getRandom().nextDouble() * 0.35D;
                double particleVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.25D;

                // 361 = pumpkin seeds
                world.addParticle("iconcrack_361", particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }
        });

        EffectHandler.effectMap.put(WOLF_EATING_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, "random.burp", 1.0F, world.getRandom().nextFloat() * 0.4F + 0.7F);

            for (int i = 0; i < 25; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double particleY = pos.getY();
                double particleZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;

                double particleVelX = (world.getRandom().nextDouble() - 0.5D) * 0.25D;
                double particleVelY = world.getRandom().nextDouble() * 0.35D;
                double particleVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.25D;

                // 281 = bowl based particles
                world.addParticle("iconcrack_281", particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }
        });

        EffectHandler.effectMap.put(FAILED_EATING_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "random.burp", 0.25F, world.getRandom().nextFloat() * 0.3F + 1F)
        );
        
        EffectHandler.effectMap.put(SOUL_SPREAD_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, BTWRSounds.SOUL_SPREAD.sound(), 1F, 1F - world.getRandom().nextFloat() * 0.2F)
        );
    
        EffectHandler.effectMap.put(POSSESSION_COMPLETE_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, BTWRSounds.SOUL_POSSESSION_COMPLETE.sound(), 1F, 1F - world.getRandom().nextFloat() * 0.2F)
        );
    
        EffectHandler.effectMap.put(INFUSION_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, BTWRSounds.VILLAGER_PRIEST_INFUSE.sound(), 2F, 1F - world.getRandom().nextFloat() * 0.2F)
        );
    
        EffectHandler.effectMap.put(GLOOM_BITES_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, BTWRSounds.GLOOM_BITES.sound(), 3F, 0.9F + world.getRandom().nextFloat() * 0.1F)
        );
    }
    
    private static void initBlockEffects() {
        EffectHandler.effectMap.put(REMOVE_STUMP_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(player, pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 0.6F);
            world.addParticle("largeexplode", pos, 0.0D, 0.0D, 0.0D);
        
            for (int i = 0; i < 20; i++) {
                double smokeX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double smokeY = pos.getY() + world.getRandom().nextDouble() - 0.5D;
                double smokeZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;
            
                double smokeVelX = (smokeX - pos.getX()) * 0.33D;
                double smokeVelY = (smokeY - pos.getY()) * 0.33D;
                double smokeVelZ = (smokeZ - pos.getZ()) * 0.33D;
            
                world.addParticle("smoke", smokeX, smokeY, smokeZ, smokeVelX, smokeVelY, smokeVelZ);
            }
        });
    
        EffectHandler.effectMap.put(LOG_STRIP_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, BTWRSounds.CHISEL_WOOD.sound(), 1F, 0.8F + world.getRandom().nextFloat() * 0.1F)
        );
    
        EffectHandler.effectMap.put(SHAFT_RIPPED_OFF_EFFECT_ID, (world, player, pos, state) -> {
            world.playSound(pos, BTWRSounds.CHISEL_STONE.sound(), 0.25F, 1.1F + world.getRandom().nextFloat() * 0.1F);
            world.playSound(pos, BTWRSounds.CHISEL_WOOD.sound(), 1.5F, 0.5F + world.getRandom().nextFloat() * 0.1F);
        });
    
        EffectHandler.effectMap.put(STONE_RIPPED_OFF_EFFECT_ID, (world, player, pos, state) -> {
            //world.playSound(pos, BTWRSounds.CHISEL_STONE.sound(), 0.375F, 1.0F + world.getRandom().nextFloat() * 0.1F);
            world.playSound(pos, BTWRSounds.ORE_BREAK.sound(), 2.0F, 0.9F);
        });
    
        EffectHandler.effectMap.put(GRAVEL_RIPPED_OFF_EFFECT_ID, (world, player, pos, state) -> {
            //world.playSound(pos, "random.anvil_land", 0.25F, world.getRandom().nextFloat() * 0.25F + 1.5F);
            world.playSound(pos, BTWRSounds.CHISEL_STONE.sound(), 0.5F, 1.1F + world.getRandom().nextFloat() * 0.1F);
            world.playSound(pos, "step.gravel", 1F, world.getRandom().nextFloat() * 0.25F + 1F);
        });
    
        EffectHandler.effectMap.put(WOOD_BLOCK_DESTROYED_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "mob.zombie.woodbreak", 0.25F, 1.0F + (world.getRandom().nextFloat() * 0.25F))
        );
    
        EffectHandler.effectMap.put(DIRT_TILLING_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, BTWRSounds.HOE_TILL.sound(), 1.0F, 1.0F)
        );
        
        EffectHandler.effectMap.put(IRON_DOOR_EFFECT_ID, (world, player, pos, state) -> {
            if (data == 0) {
                world.playSound(pos, BTWRSounds.IRON_DOOR_CLOSE.sound(), 1.0F, 1.0F);
            }
            else {
                world.playSound(pos, BTWRSounds.IRON_DOOR_OPEN.sound(), 1.0F, 1.0F);
            }
        });
        
        EffectHandler.effectMap.put(WOOD_DOOR_EFFECT_ID, (world, player, pos, state) -> {
            if (data == 0) {
                world.playSound(pos, BTWRSounds.WOOD_DOOR_CLOSE.sound(), 1.0F, 1.0F);
            }
            else {
                world.playSound(pos, BTWRSounds.WOOD_DOOR_OPEN.sound(), 1.0F, 1.0F);
            }
        });
        
        EffectHandler.effectMap.put(BLOCK_DESTROYED_WITH_IMPROPER_TOOL_EFFECT_ID, (world, player, pos, state) -> {
            int blockID = data & 0xfff;
            Block block = Block.blocksList[blockID];
        
            if (block != null) {
                int metadata = data >> 12 & 0xff;
            
                // TODO: Make this more generic and extendable
                if (block.blockMaterial == BTWBlocks.plankMaterial || block.blockMaterial == BTWBlocks.logMaterial) {
                    world.playSound(pos, "mob.zombie.woodbreak", 0.25F, 1.0F + (world.getRandom().nextFloat() * 0.25F));
                } else if (block.blockMaterial == Material.anvil) {
                    world.playSound(pos, "random.anvil_land", 1F, world.getRandom().nextFloat() * 0.25F + 0.75F);
                }
            }
        });
    
        EffectHandler.effectMap.put(MELON_EXPLODE_EFFECT_ID, (world, player, pos, state) -> {
            // 360 = melon slice based particles
            String particle = "iconcrack_360";
        
            for (int i = 0; i < 150; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double particleY = pos.getY() - 0.45D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;
            
                double particleVelX = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
                double particleVelY = world.getRandom().nextDouble() * 0.7D;
                double particleVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
            
                world.addParticle(particle, particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }
        
            world.playSound(pos, "mob.zombie.wood", 0.2F, 0.60F + (world.getRandom().nextFloat() * 0.25F));
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 0.6F);
        });
    
        EffectHandler.effectMap.put(PUMPKIN_EXPLODE_EFFECT_ID, (world, player, pos, state) -> {
            String particle = "iconcrack_" + BTWItems.cookedCarrot.itemID;
        
            for (int i = 0; i < 150; i++) {
                double particleX = pos.getX() + world.getRandom().nextDouble() - 0.5D;
                double particleY = pos.getY() - 0.45D;
                double particleZ = pos.getZ() + world.getRandom().nextDouble() - 0.5D;
            
                double particleVelX = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
                double particleVelY = world.getRandom().nextDouble() * 0.7D;
                double particleVelZ = (world.getRandom().nextDouble() - 0.5D) * 0.5D;
            
                world.addParticle(particle, particleX, particleY, particleZ, particleVelX, particleVelY, particleVelZ);
            }
        
            world.playSound(pos, "mob.zombie.wood", 0.2F, 0.60F + (world.getRandom().nextFloat() * 0.25F));
            world.playSound(pos, SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.2F + 0.6F);
        });
    
        EffectHandler.effectMap.put(GOURD_IMPACT_SOUND_EFFECT_ID, (world, player, pos, state) ->
                world.playSound(pos, "mob.zombie.wood", 0.1F, 0.40F + (world.getRandom().nextFloat() * 0.25F))
        );
    
        EffectHandler.effectMap.put(DESTROY_BLOCK_RESPECT_PARTICLE_SETTINGS_EFFECT_ID, (world, player, pos, state) -> {
            // regular block destroy does not respect particle setting options.  This effect produces the same results but won't
            // overload on particles in automated systems if particles are turned down
            int blockID = data & 0xfff;
            int metadata = data >> 12 & 0xff;
        
            Block block = Block.blocksList[blockID];
        
            if (block != null) {
                world.playSound(pos, block.stepSound.getBreakSound(), (block.stepSound.getBreakVolume() + 1.0F) / 2.0F, block.stepSound.getBreakPitch() * 0.8F);
            
                if (world.gameSettings.particleSetting <= 1) {
                    for (int i = 0; i < 4; ++i) {
                        for (int j = 0; j < 4; ++j) {
                            for (int k = 0; k < 4; ++k) {
                                if (world.gameSettings.particleSetting == 0 || world.getRandom().nextInt(3) == 0) {
                                    double particleX = pos.getX() + (i + 0.5D) / 4D;
                                    double particleY = pos.getY() + (j + 0.5D) / 4D;
                                    double particleZ = pos.getZ() + (k + 0.5D) / 4D;
                                
                                    EntityDiggingFX digEffect = new EntityDiggingFX(world,
                                            particleX, particleY, particleZ,
                                            particleX - pos.getX() - 0.5D, particleY - pos.getY() - 0.5D, particleZ - pos.getZ() - 0.5D,
                                            block, metadata);
                                
                                    digEffect.applyRenderColor(metadata);
                                    world.effectRenderer.addEffect(digEffect);
                                }
                            }
                        }
                    }
                }
            }
        });
    
        EffectHandler.effectMap.put(BLOCK_CONVERT_EFFECT_ID, (world, player, pos, state) -> {
            int blockID = data & 4095;
        
            if (blockID > 0)
            {
                Block block = Block.blocksList[blockID];
            
                if (block.shouldPlayStandardConvertSound(world, (int) pos.getX(), (int) pos.getY(), (int) pos.getZ())) {
                    StepSound stepSound = block.getStepSound(world.thePlayer.worldObj, (int) pos.getX(), (int) pos.getY(), (int) pos.getZ());
                    world.playSound(stepSound.getBreakSound(), (float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F, (float) pos.getZ() + 0.5F,
                            (stepSound.getBreakVolume() + 1.0F) / 2.0F, stepSound.getBreakPitch() * 0.8F);
                }
            }
        
            world.effectRenderer.addBlockDestroyEffects(pos,
                    data & 4095, data >> 12 & 255);
        });
        
        EffectHandler.effectMap.put(BLOCK_BREAK_EFFECT_ID, (world, player, pos, state) -> {
            BlockSoundGroup blockSoundGroup = state.getSoundGroup();

            if (block != null) {
                world.playSound(null, pos,
                        blockSoundGroup.getBreakSound(),
                        (blockSoundGroup.getVolume() + 1.0F) / 2.0F,
                        blockSoundGroup.getPitch() * 0.8F);
            }
        });
         **/
    }

    private static void effectAnimalBirth(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        world.playSound(null, pos, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.NEUTRAL, 1.0F, rand(world, 1.0F, 0.2F));


        /**
        particles(world, "reddust", 10,
                () -> x + world.rand.nextDouble(),
                () -> y + 1.0 + world.rand.nextDouble(),
                () -> z + world.rand.nextDouble());

        particles(world, "dripLava", 10,
                () -> x - 0.5 + world.rand.nextDouble(),
                () -> y + world.rand.nextDouble(),
                () -> z - 0.5 + world.rand.nextDouble());
         **/
    }


    private static void particles(ParticleEffect type, World world, int count, BlockPos pos) {
        for (int i = 0; i < count; i++) {
            world.addParticle(type, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
        }
    }

    private static float rand(World world, float base, float range) {
        return base + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * range;
    }

}
