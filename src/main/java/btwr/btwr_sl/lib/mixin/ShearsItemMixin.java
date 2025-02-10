package btwr.btwr_sl.lib.mixin;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin extends Item {
    public ShearsItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "createToolComponent", at = @At("HEAD"), cancellable = true)
    private static void onCreateToolComponent(CallbackInfoReturnable<ToolComponent> cir) {
        cir.setReturnValue(new ToolComponent(MODIFIED_SHEARS_COMPONENT_LIST, 1.0f, 1));
    }

    @Unique
    private static final List<ToolComponent.Rule> MODIFIED_SHEARS_COMPONENT_LIST = List.of(
            ToolComponent.Rule.ofAlwaysDropping(BTWRConventionalTags.Blocks.WEB_BLOCKS, 15.0f),
            ToolComponent.Rule.of(BTWRConventionalTags.Blocks.SHEARS_EFFICIENT, 20f)
    );

}
