package com.acikek.purpeille.mixin;

import com.acikek.purpeille.advancement.ModCriteria;
import com.acikek.purpeille.warpath.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
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
        if (world != null && Type.REVELATION.hasNbt(stack)) {
            Revelations revelation = Revelations.getFromNbt(stack);
            Aspects aspect = Aspects.getFromNbt(stack);
            tooltip.add(Warpath.getWarpath(revelation, aspect, true));
            if (aspect != null && Synergy.getSynergy(revelation.value, aspect.value) == Synergy.IDENTICAL) {
                tooltip.add(revelation.value.getRite());
            }
        }
    }

    @Inject(method = "onCraft", at = @At(value = "TAIL"))
    private void triggerCriterion(ItemStack stack, World world, PlayerEntity player, CallbackInfo ci) {
        if (!world.isClient() && Type.REVELATION.hasNbt(stack)) {
            Revelations revelation = Revelations.getFromNbt(stack);
            Aspects aspect = Aspects.getFromNbt(stack);
            ModCriteria.WARPATH_CREATED.trigger((ServerPlayerEntity) player, stack, revelation, aspect, Synergy.getSynergy(revelation, aspect));
        }
    }
}
