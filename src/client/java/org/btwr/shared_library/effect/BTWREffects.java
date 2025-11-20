package org.btwr.shared_library.effect;

public class BTWREffects {

    private static void register(int id, EffectHandler.Effect e) {
        EffectHandler.effectMap.put(id, e);
    }

    public static void initEffects() {
        /**
        initBlockEffects();

        register(ANIMAL_BIRTHING_EFFECT_ID, BTWEffectManager::effectAnimalBirth);
        register(SAW_DAMAGE_EFFECT_ID, BTWEffectManager::effectSawDamage);
        register(NETHER_GROTH_SPORES_EFFECT_ID, BTWEffectManager::effectGrothSpores);
        register(GHAST_SCREAM_EFFECT_ID, BTWEffectManager::effectGhastScream);

        register("animal_birthing");
        register("saw_damage");
        register("nether_groth_spores");
        register("ghast_scream");
        register("burp_sound");
        register("fire_fizz");
        register("ghast_moan");
        register("mining_charge_explosion");
        register("hopper_eject_xp");
        register("item_collection_pop");
        register("xp_eject_pop");
        register("hopper_close");
        register("redstone_click");
        register("mechanical_device_explode");
        register("block_place");
        register("dynamite_fuse");
        register("low_pitch_click");
        register("wolf_hurt");
        register("chicken_hurt");
        register("block_dispenser_smoke");
        register("companion_cube_death");
        register("possessed_chicken_explosion");
        register("enderman_collect_block");
        register("enderman_convert_block");
        register("enderman_place_block");
        register("enderman_change_dimension");
        register("soul_urn_shatter");
        register("melon_explode");
        register("pumpkin_explode");
        register("gourd_impact_sound");
        register("destroy_block_respect_particle_settings");
        register("cow_regen_milk");
        register("cow_milking");
        register("cow_conversion_to_mooshroom");
        register("wolf_howl");
        register("wolf_conversion_to_dire_wolf");
        register("creeper_snip");
        register("possessed_pig_transformation");
        register("possessed_villager_transformation");
        register("sheep_regrow_wool");
        register("squid_tentacle_fling");
        register("create_snow_golem");
        register("create_iron_golem");
        register("toss_the_milk");
        register("apply_dung_to_wolf");
        register("remove_stump");
        register("shaft_ripped_off");
        register("stone_ripped_off");
        register("gravel_ripped_off");
        register("wood_block_destroyed");
        register("block_destroyed_with_improper_tool");
        register("possessed_squid_transformation");
        register("apply_mortar");
        register("loose_block_stuck_to_mortar");
        register("smoldering_log_fall");
        register("smoldering_log_explosion");
        register("water_evaporation");
        register("create_wither");
        register("lightning_strike");
        register("flaming_netherrack_fall");
        register("cactus_explosion");
        register("animal_eating");
        register("wolf_eating");
        register("failed_eating");
        register("soul_spread");
        register("possession_complete");
        register("infusion");
        register("block_convert");
        register("log_strip");
        register("dirt_tilling");
        register("gloom_bites");
        register("iron_door");
        register("block_break");
        register("wood_door");
         **/


    }

}
