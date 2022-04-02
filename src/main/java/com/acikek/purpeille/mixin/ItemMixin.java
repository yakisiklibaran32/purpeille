package com.acikek.purpeille.mixin;

import com.acikek.purpeille.warpath.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "appendTooltip", at = @At(value = "TAIL"))
    private void appendWarpath(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        Revelation revelation = Revelation.getFromNbt(stack);
        if (revelation != null) {
            Aspect aspect = Aspect.getFromNbt(stack);
            tooltip.add(Warpath.getWarpath(revelation, aspect));
            if (aspect != null && Synergy.getSynergy(revelation, aspect) == Synergy.IDENTICAL) {
                tooltip.add(revelation.getRite());
            }
        }
    }
}
