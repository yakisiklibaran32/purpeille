package com.acikek.purpeille.recipe.oven;

import com.acikek.purpeille.Purpeille;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Random;

public record AncientOvenRecipe(Ingredient input, int damage, int cookTime,
                                ItemStack[] result, Identifier id) implements Recipe<SimpleInventory> {

    public static final Identifier ID = Purpeille.id("ancient_oven");

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return inventory.size() >= 1 && input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    public ItemStack getOutput(Random random) {
        return result[random.nextInt(result.length)];
    }

    @Override
    public ItemStack getOutput() {
        return getOutput(new Random());
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AncientOvenRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AncientOvenRecipe> {
        public static final Type INSTANCE = new Type();
    }

    public static void register() {
        Registry.register(Registry.RECIPE_TYPE, ID, Type.INSTANCE);
        Registry.register(Registry.RECIPE_SERIALIZER, ID, AncientOvenRecipeSerializer.INSTANCE);
    }
}
