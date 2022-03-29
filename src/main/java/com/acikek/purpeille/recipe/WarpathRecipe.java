package com.acikek.purpeille.recipe;

import com.acikek.purpeille.Purpeille;
import com.acikek.purpeille.item.ModItems;
import com.acikek.purpeille.warpath.Component;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class WarpathRecipe extends SpecialCraftingRecipe {

    public static SpecialRecipeSerializer<WarpathRecipe> SERIALIZER;

    public static final TagKey<Item> WARPATH_BASE = TagKey.of(Registry.ITEM_KEY, Purpeille.id("warpath_base"));

    public WarpathRecipe(Identifier id) {
        super(id);
    }

    public Pair<ItemStack, Pair<Integer, Integer>> getCraftingData(CraftingInventory inventory) {
        ItemStack base = null;
        Pair<Integer, Integer> components = new Pair<>(-1, -1);
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (base == null && stack.isIn(WARPATH_BASE)) {
                base = stack;
            }
            else if (components.getLeft() == -1 && stack.isOf(ModItems.SMOLDERED_PURPEILLE_INGOT)) {
                components.setLeft(i);
            }
            else if (components.getRight() == -1 && stack.isOf(ModItems.PRESERVED_DUST)) {
                components.setRight(i);
            }
            else if (base != null && !stack.isEmpty()) {
                return null;
            }
        }
        if (base == null) {
            return null;
        }
        return new Pair<>(base, components);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        Pair<ItemStack, Pair<Integer, Integer>> data = getCraftingData(inventory);
        return data != null && !data.getLeft().getOrCreateNbt().contains(Component.Type.REVELATION.nbtKey) && data.getRight().getLeft() != -1;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        Pair<ItemStack, Pair<Integer, Integer>> data = getCraftingData(inventory);
        ItemStack stack = data.getLeft().copy();
        Component.apply(stack, data.getRight().getLeft(), data.getRight().getRight());
        return stack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 9;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static void register() {
        SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, Purpeille.id("crafting_special_warpath"), new SpecialRecipeSerializer<>(WarpathRecipe::new));
    }
}