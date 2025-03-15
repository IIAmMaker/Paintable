package net.im_maker.paintable.common.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CraftingContext {
    private static final ThreadLocal<ItemStack> currentRecipeResult = new ThreadLocal<>();

    public static void setCurrentRecipeResult(ItemStack result) {
        currentRecipeResult.set(result);
    }

    public static boolean isCurrentRecipeResult(Item item) {
        return currentRecipeResult.get() != null && currentRecipeResult.get().is(item);
    }

    public static void clear() {
        currentRecipeResult.remove();
    }
}
