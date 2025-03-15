package net.im_maker.paintable.common.recipe;

import com.google.gson.JsonObject;
import net.im_maker.paintable.Paintable;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PaintingRecipe implements Recipe<SimpleContainer> {
    private final Ingredient input;
    private final Ingredient brush;
    private final ItemStack output;
    private final ResourceLocation id;

    public PaintingRecipe(Ingredient input, Ingredient brush, ItemStack output, ResourceLocation id) {
        this.input = input;
        this.brush = brush;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return input.equals(pContainer.getItem(0)) && brush.equals(pContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    public ItemStack getINPUTItem(RegistryAccess pRegistryAccess) {
        return input.getItems()[1];
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<PaintingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "painting";
    }

    public static class Serializer implements RecipeSerializer<PaintingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Paintable.MOD_ID, "painting");

        @Override
        public PaintingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonObject ingredients = GsonHelper.getAsJsonObject(pSerializedRecipe, "unpainted_block");
            JsonObject ingredientsBrush = GsonHelper.getAsJsonObject(pSerializedRecipe, "brush");
            Ingredient input = Ingredient.fromJson(ingredients);
            Ingredient inputBrush = Ingredient.fromJson(ingredientsBrush);
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result_block"));


            return new PaintingRecipe(input, inputBrush, output, pRecipeId);
        }

        @Override
        public @Nullable PaintingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            Ingredient inputBrush = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();
            return new PaintingRecipe(input, inputBrush, output ,pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PaintingRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pRecipe.brush.toNetwork(pBuffer);
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
