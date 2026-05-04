package net.im_maker.paintable.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.im_maker.paintable.common.block.PBlocks;
import net.im_maker.paintable.common.entity.custom.PrimedPnt;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PntRenderer extends EntityRenderer<PrimedPnt> {
    private final BlockRenderDispatcher blockRenderer;

    public PntRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    public void render(PrimedPnt primedPnt, float pEntityYaw, float pPartialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.5F, 0.0F);
        int i = primedPnt.getFuse();
        if ((float)i - pPartialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)i - pPartialTicks + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.3F;
            poseStack.scale(f1, f1, f1);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5F, -0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, PBlocks.PNTS.get(primedPnt.getColor().getId()).defaultBlockState(), poseStack, multiBufferSource, pPackedLight, i / 5 % 2 == 0);
        poseStack.popPose();


        super.render(primedPnt, pEntityYaw, pPartialTicks, poseStack, multiBufferSource, pPackedLight);
    }

    public ResourceLocation getTextureLocation(PrimedPnt primedPnt) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}