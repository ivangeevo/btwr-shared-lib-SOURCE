package btwr.btwr_sl.lib.mixin;

import btwr.btwr_sl.lib.gui.FOVManager;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AbstractClientPlayerEntity.class, priority = 99999)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    /**
     * Directly modifies the speed attribute getter to remove modifiers
     * derived from excluded mod namespaces
     * @return Attribute value without excluded modifiers
     */
    @ModifyExpressionValue(
            method = "getFovMultiplier",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D")
    )
    private double modifyAttribValue(double original) {
        return FOVManager.getInstance().offsetAttribute(this, original);
    }
}
