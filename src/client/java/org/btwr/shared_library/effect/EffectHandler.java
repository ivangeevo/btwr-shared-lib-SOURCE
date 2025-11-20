package org.btwr.shared_library.effect;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class EffectHandler {
    public static Map<Integer, Effect> effectMap = new HashMap<>();

    public static boolean playEffect(int effectID, World world, PlayerEntity player, BlockPos pos, BlockState state) {
        Effect effect = effectMap.get(effectID);

        if (effect != null) {
            effect.playEffect(world, player, pos, state);
            return true;
        }

        return false;
    }

    public interface Effect {
        void playEffect(World world, PlayerEntity player, BlockPos pos, BlockState state);
    }
}