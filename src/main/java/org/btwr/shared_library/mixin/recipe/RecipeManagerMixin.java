package org.btwr.shared_library.mixin.recipe;

import com.google.gson.JsonElement;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.btwr.shared_library.api.registry.DisabledRecipeTypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    // Disable certain recipe types
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("TAIL"))
    private void removeSmeltingRecipes(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        // Nothing registered, skip the pass entirely
        if (DisabledRecipeTypeRegistry.isEmpty()) return;

        RecipeManager self = (RecipeManager) (Object) this;

        List<RecipeEntry<?>> filtered = self.values().stream()
                .filter(entry -> !DisabledRecipeTypeRegistry.isDisabled(entry.value().getType()))
                .toList();

        self.setRecipes(filtered);
    }

}