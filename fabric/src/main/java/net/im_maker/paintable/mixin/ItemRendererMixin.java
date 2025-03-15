package net.im_maker.paintable.mixin;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.item.PItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nullable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "render", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useCustomModel(BakedModel value, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, com.mojang.blaze3d.vertex.PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Boolean b = (renderMode != ItemDisplayContext.GUI && renderMode != ItemDisplayContext.FIXED && renderMode != ItemDisplayContext.GROUND);
        if (stack.is(PItems.PAINT_BRUSH) && b) {
            return ((ItemRendererAccessor) this).paintable$getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(Paintable.MOD_ID, "paint_brush_in_hand", "inventory"));
        }
        for (DyeColor color : DyeColor.values()) {
            if (stack.is(PItems.DIPPED_PAINT_BRUSH.get(color.getId())) && b) {
                return ((ItemRendererAccessor) this).paintable$getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(Paintable.MOD_ID, color + "_paint_brush_in_hand", "inventory"));
            }
        }
        return value;
    }
}