package net.im_maker.paintable;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.im_maker.paintable.common.block.PBlocks;
import net.im_maker.paintable.common.block.entity.PBlockEntities;
import net.im_maker.paintable.common.entity.PEntities;
import net.im_maker.paintable.client.PBoatRenderer;
import net.im_maker.paintable.client.PModelLayers;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.item.DyeColor;

public class PaintableClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(PBlocks.PAINT_BUCKET, RenderType.cutout());

        for (DyeColor color : DyeColor.values()) {
            RenderType renderTypeForDoors = color.getName().equals("cyan") || color.getName().equals("forest") ||color.getName().equals("olive") ? RenderType.translucent() : RenderType.cutout();
            BlockRenderLayerMap.INSTANCE.putBlock(Paintable.getBlockFromString(color + "_paint_bucket"), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(Paintable.getBlockFromString(color + "_painted_door"), renderTypeForDoors);
            BlockRenderLayerMap.INSTANCE.putBlock(Paintable.getBlockFromString(color + "_painted_trapdoor"), renderTypeForDoors);
            EntityModelLayerRegistry.registerModelLayer(PModelLayers.PAINTED_BOATS_LAYERS.get(color.getId()), BoatModel::createBodyModel);
            EntityModelLayerRegistry.registerModelLayer(PModelLayers.PAINTED_CHEST_BOATS_LAYERS.get(color.getId()), ChestBoatModel::createBodyModel);
        }

        EntityRendererRegistry.register(PEntities.BOAT, pContext -> new PBoatRenderer(pContext, false));
        EntityRendererRegistry.register(PEntities.CHEST_BOAT, pContext -> new PBoatRenderer(pContext, true));

        BlockEntityRenderers.register(PBlockEntities.SIGN, SignRenderer::new);
        BlockEntityRenderers.register(PBlockEntities.HANGING_SIGN, HangingSignRenderer::new);
    }
}