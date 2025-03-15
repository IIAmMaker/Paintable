package net.im_maker.paintable.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.item.PItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "render", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useCustomModel(BakedModel value, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        //if (stack.is(PItems.PAINT_BRUSH.get()) && renderMode != ItemDisplayContext.GUI) {
        //    ModelManager modelManager = ((ItemRendererAccessor) this).paintable$getItemModelShaper().getModelManager();
        //    return modelManager.getModel(new ModelResourceLocation(Paintable.MOD_ID, "paint_brush_in_hand", "inventory"));
        //}
        return value;
    }
}