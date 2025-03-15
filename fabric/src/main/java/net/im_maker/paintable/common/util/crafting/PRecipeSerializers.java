package net.im_maker.paintable.common.util.crafting;

import net.im_maker.paintable.Paintable;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class PRecipeSerializers {
    public static final RecipeSerializer<CleaningDippedPaintBrushCrafting> CLEANING_DIPPED_PAINT_BRUSH = register(
            "crafting_special_cleaning_dipped_paint_brush",
            new CleaningDippedPaintBrushCrafting.Serializer()
    );

    private static <T extends RecipeSerializer<?>> T register(String name, T serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(Paintable.MOD_ID, name), serializer);
    }

    public static void init() {
        // Ensure this is called in mod initialization
    }
}
