package net.im_maker.paintable.common.util.crafting;

import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.item.custom.DippedPaintBrushItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;

public class CleaningDippedPaintBrushCrafting extends CustomRecipe {
    public CleaningDippedPaintBrushCrafting(ResourceLocation resourceLocation, CraftingBookCategory craftingBookCategory) {
        super(resourceLocation, craftingBookCategory);
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        boolean foundDippedBrush = false;

        for (int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack itemStack = craftingContainer.getItem(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof DippedPaintBrushItem) {
                    if (foundDippedBrush) {
                        return false; // More than one dipped brush found
                    }
                    foundDippedBrush = true;
                } else {
                    return false; // Other items are not allowed
                }
            }
        }
        return foundDippedBrush;
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer, RegistryAccess registryAccess) {
        return new ItemStack(PItems.PAINT_BRUSH.get()); // Returns a clean paintbrush without copying NBT
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PRecipeSerializers.CLEANING_DIPPED_PAINT_BRUSH.get();
    }

    public static class Serializer extends SimpleCraftingRecipeSerializer<CleaningDippedPaintBrushCrafting> {
        public Serializer() {
            super(CleaningDippedPaintBrushCrafting::new);
        }
    }

}