package btwr.btwr_sl.lib.util;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;

public enum CraftingSoundConfig {
    WOODEN_SOUND(
        BTWRConventionalTags.Items.ON_CRAFT_WOODEN_SOUND,
        SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,
        0.1F,
        1.25F,
        0.25F
    ),
    SLIME_SOUND(
        BTWRConventionalTags.Items.ON_CRAFT_SLIME_SOUND,
        SoundEvents.ENTITY_SLIME_ATTACK,
        0.5F,
        0.09F,
        0.01F
    ),
    SHEARS_CUT_SOUND(
        BTWRConventionalTags.Items.ON_CRAFT_SHEARS_CUT_SOUND,
        SoundEvents.ENTITY_SHEEP_SHEAR,
        0.8F,
        1.0F,
        0.0F
    ),
    FIZZ_SOUND(
            BTWRConventionalTags.Items.ON_CRAFT_FIZZ_SOUND,
            SoundEvents.BLOCK_FIRE_EXTINGUISH,
            0.25F,
            2.5F,
            0.2F
    ),
    WOODEN_TOOL_SOUND(
            BTWRConventionalTags.Items.ON_CRAFT_WOODEN_TOOL_SOUND,
            SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,
            0.1F,
            1.25F,
            0.25F
    ),
    STONE_TOOL_SOUND(
            BTWRConventionalTags.Items.ON_CRAFT_STONE_TOOL_SOUND,
            SoundEvents.BLOCK_ANVIL_LAND,
            0.1F,
            1.75F,
            0.25F
    ),
    METALLIC_TOOL_SOUND(
            BTWRConventionalTags.Items.ON_CRAFT_METALLIC_TOOL_SOUND,
            SoundEvents.BLOCK_ANVIL_USE,
            0.5F,
            1.25F,
            0.25F
    );

    private final TagKey<Item> tag;
    private final SoundEvent sound;
    private final float volume;
    private final float basePitch;
    private final float pitchVariance;

    CraftingSoundConfig(TagKey<Item> tag, SoundEvent sound, float volume, float basePitch, float pitchVariance) {
        this.tag = tag;
        this.sound = sound;
        this.volume = volume;
        this.basePitch = basePitch;
        this.pitchVariance = pitchVariance;
    }

    public TagKey<Item> getTag() {
        return tag;
    }

    public SoundEvent getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getBasePitch() {
        return basePitch;
    }

    public float getPitchVariance() {
        return pitchVariance;
    }
}
