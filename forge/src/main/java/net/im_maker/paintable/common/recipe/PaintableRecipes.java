package net.im_maker.paintable.common.recipe;

import net.im_maker.paintable.Paintable;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PaintableRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Paintable.MOD_ID);

    public static final RegistryObject<RecipeSerializer<PaintingRecipe>> PAINTING_SERIALIZERS =
            SERIALIZERS.register("painting", () -> PaintingRecipe.Serializer.INSTANCE);

    public static void register (IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
