package net.im_maker.paintable.client;

import com.mojang.datafixers.util.Pair;
import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.entity.custom.PBoat;
import net.im_maker.paintable.common.entity.custom.PChestBoat;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModBoatRenderer extends BoatRenderer {
    private final Map<DyeColor, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public ModBoatRenderer(EntityRendererProvider.Context pContext, boolean pChestBoat) {
        super(pContext, pChestBoat);
        this.boatResources = Stream.of(DyeColor.values()).collect(Collectors.toUnmodifiableMap(color -> color,
                color -> Pair.of(new ResourceLocation(Paintable.MOD_ID, getTextureLocation(color, pChestBoat)), this.createBoatModel(pContext, color, pChestBoat))));
        System.out.println(boatResources);
        System.out.println("boatResources");
    }

    private static String getTextureLocation(DyeColor color, boolean pChestBoat) {
        return pChestBoat ? "textures/entity/chest_boat/" + color.getName() + "_painted.png" : "textures/entity/boat/" + color.getName() + "_painted.png";
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

    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
        if (boat instanceof PBoat modBoat) {
            return this.boatResources.get(modBoat.getModVariant().getColor());
        } else if (boat instanceof PChestBoat modChestBoat) {
            return this.boatResources.get(modChestBoat.getModVariant().getColor());
        } else {
            return null;
        }
    }

    @Mod.EventBusSubscriber(modid = Paintable.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            for (DyeColor color : DyeColor.values()) {
                event.registerLayerDefinition(ModBoatRenderer.createBoatModelName(color), BoatModel::createBodyModel);
                event.registerLayerDefinition(ModBoatRenderer.createChestBoatModelName(color), ChestBoatModel::createBodyModel);
            }
        }
    }
}
