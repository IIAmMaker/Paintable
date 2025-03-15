package net.im_maker.paintable.common.util.crafting;

import net.im_maker.paintable.Paintable;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Paintable.MOD_ID);

    public static final RegistryObject<RecipeSerializer> CLEANING_DIPPED_PAINT_BRUSH = RECIPE_SERIALIZERS.register(
            "crafting_special_cleaning_dipped_paint_brush",
            () -> new CleaningDippedPaintBrushCrafting.Serializer()
    );

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
