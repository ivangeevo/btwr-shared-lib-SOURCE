package org.btwr.shared_library.api.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.btwr.shared_library.BTWRSLMod;
import org.btwr.shared_library.util.utils.IdUtils;

public class BTWRSounds {
    //public static final SoundEvent PLAYER_HURT = register("player_hurt");
    public static final SoundEvent BLOOD_WOOD_BREAK = register("block/blood_wood/break", 6);
    public static final SoundEvent BLOOD_WOOD_STEP = register("block/blood_wood/step", 6);

    public static final SoundEvent BONE_BREAK = register("block/bone/break", 5);
    public static final SoundEvent BONE_STEP = register("block/bone/step", 5);

    public static final SoundEvent NETHER_PLANTS_BREAK = register("block/nether_plants/break", 6);
    public static final SoundEvent NETHER_PLANTS_STEP = register("block/nether_plants/step", 5);

    public static final SoundEvent CLAY_BRICK_BREAK = register("block/clay_brick/break", 6);
    public static final SoundEvent CLAY_BRICK_STEP = register("block/clay_brick/step", 6);

    public static final SoundEvent CLAY_BREAK = register("block/clay/break", 4);
    public static final SoundEvent CLAY_STEP = register("block/clay/step", 6);

    public static final SoundEvent CROP_PLACE = register("block/crop/place", 6);

    public static final SoundEvent DIRT_BREAK = register("block/dirt/break", 4);
    public static final SoundEvent DIRT_STEP = register("block/dirt/step", 6);

    public static final SoundEvent GEM_BREAK = register("block/gem/break", 4);
    public static final SoundEvent GEM_PLACE = register("block/gem/place", 4);
    public static final SoundEvent GEM_STEP = register("block/gem/step", 14);

    public static final SoundEvent LEAVES_BREAK = register("block/leaves/break", 7);
    public static final SoundEvent LEAVES_STEP = register("block/leaves/step", 5);

    public static final SoundEvent METAL_BREAK = register("block/metal/break", 4);
    public static final SoundEvent METAL_STEP = register("block/metal/step", 6);

    public static final SoundEvent NETHER_GROTH_BREAK = register("block/nether_groth/break", 6);
    public static final SoundEvent NETHER_GROTH_STEP = register("block/nether_groth/step", 6);

    public static final SoundEvent NETHERRACK_BREAK = register("block/netherrack/break", 6);
    public static final SoundEvent NETHERRACK_STEP = register("block/netherrack/step", 6);

    public static final SoundEvent ORE_BREAK = register("block/ore/break", 4);
    public static final SoundEvent ORE_STEP = register("block/ore/step", 5);

    public static final SoundEvent PLANTS_BREAK = register("block/plants/break", 4);
    public static final SoundEvent PLANTS_STEP = register("block/plants/step", 6);

    public static final SoundEvent SAPLING_BREAK = register("block/sapling/break", 6);
    public static final SoundEvent SAPLING_STEP = register("block/sapling/step", 6);

    public static final SoundEvent SMALL_OBJECT_BREAK = register("block/small_object/break", 5);
    public static final SoundEvent SMALL_OBJECT_STEP = register("block/small_object/step", 5);

    public static final SoundEvent SOULFORGED_STEEL_BREAK = register("block/soulforged_steel/break", 4);
    public static final SoundEvent SOULFORGED_STEEL_STEP = register("block/soulforged_steel/step", 6);

    public static final SoundEvent SOUL_SAND_BREAK = register("block/soul_sand/break", 9);
    public static final SoundEvent SOUL_SAND_STEP = register("block/soul_sand/step", 5);

    public static final SoundEvent STONE_BREAK = register("block/stone/break", 5);
    public static final SoundEvent STONE_PLACE = register("block/stone/place", 6);
    public static final SoundEvent STONE_STEP = register("block/stone/step", 6);

    public static final SoundEvent STONE_STRATA_2_BREAK = register("block/stone_strata_2/break", 4);
    public static final SoundEvent STONE_STRATA_2_STEP = register("block/stone_strata_2/step", 6);

    public static final SoundEvent STONE_STRATA_3_BREAK = register("block/stone_strata_3/break", 5);
    public static final SoundEvent STONE_STRATA_3_STEP = register("block/stone_strata_3/step", 6);

    public static final SoundEvent STONE_BRICK_BREAK = register("block/stone_brick/break", 6);
    public static final SoundEvent STONE_BRICK_STEP = register("block/stone_brick/step", 5);

    public static final SoundEvent VINE_BREAK = register("block/vine/break", 4);
    public static final SoundEvent VINE_STEP = register("block/vine/step", 5);

    public static final SoundEvent WATER_PLANTS_BREAK = register("block/water_plants/break", 6);
    public static final SoundEvent WATER_PLANTS_STEP = register("block/water_plants/step", 6);

    //------ Block Sounds (Unique) ------//

    public static final SoundEvent CHEST_CLOSE = register("block/chest/close", 3);
    public static final SoundEvent CHEST_OPEN = register("block/chest/open");

    public static final SoundEvent WOOD_DOOR_CLOSE = register("block/door/close", 6);
    public static final SoundEvent WOOD_DOOR_OPEN = register("block/door/open", 4);

    public static final SoundEvent IRON_DOOR_CLOSE = register("block/iron_door/close", 4);
    public static final SoundEvent IRON_DOOR_OPEN = register("block/iron_door/open", 4);

    public static final SoundEvent TRAPDOOR_CLOSE = register("block/trapdoor/close", 3);
    public static final SoundEvent TRAPDOOR_OPEN = register("block/trapdoor/open", 5);

    //------ Item Sounds ------//

    public static final SoundEvent CHISEL_STONE = register("item/chisel/stone", 2);
    public static final SoundEvent CHISEL_WOOD = register("item/chisel/wood", 4);

    public static final SoundEvent HOE_TILL = register("item/hoe/till", 4);

    //------ Entity Sounds ------//

    public static final SoundEvent ENDERMAN_CHANGE_DIMENSION = register("entity/enderman/change_dimension", 2);

    public static final SoundEvent SQUID_DEATH = register("entity/squid/death", 3);
    public static final SoundEvent SQUID_HURT = register("entity/squid/hurt", 4);
    public static final SoundEvent SQUID_IDLE = register("entity/squid/idle", 5);

    public static final SoundEvent VILLAGER_PRIEST_INFUSE = register("entity/villager/priest_infuse", 2);

    public static final SoundEvent WITCH_DEATH = register("entity/witch/death", 3);
    public static final SoundEvent WITCH_DRINK = register("entity/witch/drink", 4);
    public static final SoundEvent WITCH_HURT = register("entity/witch/hurt", 3);
    public static final SoundEvent WITCH_IDLE = register("entity/witch/idle", 5);
    public static final SoundEvent WITCH_THROW = register("entity/witch/throw", 3);

    public static final SoundEvent PAINTING_PLACE = register("entity/painting/place", 4);
    public static final SoundEvent PAINTING_BREAK = register("entity/painting/break", 3);

    public static final SoundEvent ITEM_FRAME_PLACE = register("entity/itemframe/place", 4);
    public static final SoundEvent ITEM_FRAME_BREAK = register("entity/itemframe/break", 3);
    public static final SoundEvent ITEM_FRAME_ITEM_ADD = register("entity/itemframe/add_item", 4);
    public static final SoundEvent ITEM_FRAME_ITEM_REMOVE = register("entity/itemframe/remove_item", 4);
    public static final SoundEvent ITEM_FRAME_ITEM_ROTATE = register("entity/itemframe/rotate_item", 4);

    //------ Other Sounds ------//

    public static final SoundEvent GLOOM_BITES = register("misc/gloom/bite", 2);

    public static final SoundEvent SOUL_SCREAM = register("misc/soul/scream", 5);
    public static final SoundEvent SOUL_SPREAD = register("misc/soul/spread", 13);
    public static final SoundEvent SOUL_POSSESSION_COMPLETE = register("misc/soul/possession_complete");

    public static SoundEvent register(String path) {
        return register(path, 1);
    }
    
    public static SoundEvent register(String path, int variantCount) {
        Identifier id = IdUtils.ofSL(path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {
        BTWRSLMod.LOGGER.info("Registering sound events for BTWR: Shared Library");
    }

}