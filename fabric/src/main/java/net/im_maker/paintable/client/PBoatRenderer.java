package net.im_maker.paintable.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.entity.custom.PBoat;
import net.im_maker.paintable.common.entity.custom.PChestBoat;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.WaterPatchModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.DyeColor;
import org.joml.Quaternionf;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PBoatRenderer extends BoatRenderer {
    private final Map<DyeColor, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public PBoatRenderer(EntityRendererProvider.Context pContext, boolean pChestBoat) {
        super(pContext, pChestBoat);
        this.boatResources = Stream.of(DyeColor.values()).collect(Collectors.toUnmodifiableMap(color -> color,
                color -> Pair.of(new ResourceLocation(Paintable.MOD_ID, getTextureLocation(color, pChestBoat)), this.createBoatModel(pContext, color, pChestBoat))));
        System.out.println(boatResources);
        System.out.println("boatResources");
    }

    private static String getTextureLocation(DyeColor color, boolean pChestBoat) {
        return pChestBoat ? "textures/entity/chest_boat/" + color.getName() + "_painted.png"
                : "textures/entity/boat/" + color.getName() + "_painted.png";
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context pContext, DyeColor color, boolean pChestBoat) {
        ModelLayerLocation modelLayer = pChestBoat ? createChestBoatModelName(color) : createBoatModelName(color);
        ModelPart modelPart = pContext.bakeLayer(modelLayer);
        return pChestBoat ? new ChestBoatModel(modelPart) : new BoatModel(modelPart);
    }

    public static ModelLayerLocation createBoatModelName(DyeColor color) {
        return createLocation("boat/" + color.getName() + "_painted", "main");
    }

    public static ModelLayerLocation createChestBoatModelName(DyeColor color) {
        return createLocation("chest_boat/" + color.getName() + "_painted", "main");
    }

    private static ModelLayerLocation createLocation(String path, String model) {
        return new ModelLayerLocation(new ResourceLocation(Paintable.MOD_ID, path), model);
    }

    public void render(Boat boat, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.375F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        float f = (float)boat.getHurtTime() - partialTicks;
        float f1 = boat.getDamage() - partialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)boat.getHurtDir()));
        }

        float f2 = boat.getBubbleAngle(partialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            poseStack.mulPose((new Quaternionf()).setAngleAxis(boat.getBubbleAngle(partialTicks) * ((float)Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }
        Pair<ResourceLocation, ListModel<Boat>> pair = getModelWithLocation(boat);
        ResourceLocation resourcelocation = pair.getFirst();
        ListModel<Boat> listmodel = pair.getSecond();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        listmodel.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = buffer.getBuffer(listmodel.renderType(resourcelocation));
        listmodel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!boat.isUnderWater()) {
            VertexConsumer vertexconsumer1 = buffer.getBuffer(RenderType.waterMask());
            if (listmodel instanceof WaterPatchModel) {
                WaterPatchModel waterpatchmodel = (WaterPatchModel)listmodel;
                waterpatchmodel.waterPatch().render(poseStack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }

        poseStack.popPose();
        //render(boat, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        if (boat instanceof PBoat modBoat) {
            return this.boatResources.get(modBoat.getModVariant().getColor());
        } else if (boat instanceof PChestBoat modChestBoat) {
            return this.boatResources.get(modChestBoat.getModVariant().getColor());
        } else {
            return null;
        }
    }
}
