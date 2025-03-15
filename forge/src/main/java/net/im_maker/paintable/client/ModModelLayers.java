package net.im_maker.paintable.client;

import net.im_maker.paintable.Paintable;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;
import java.util.List;

public class ModModelLayers {

    public static final List<ModelLayerLocation> PAINTED_BOATS_LAYERS = colorBoatLayers();
    public static final List<ModelLayerLocation> PAINTED_CHEST_BOATS_LAYERS = colorChestBoatLayers();

    public static List<ModelLayerLocation> colorBoatLayers() {
        List<ModelLayerLocation> boatLayers = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getName() + "_painted";
            ResourceLocation layerLocation = new ResourceLocation(Paintable.MOD_ID, "boat/" + colorName);
            ModelLayerLocation boatLayer = new ModelLayerLocation(layerLocation, "main");
            boatLayers.add(boatLayer);
        }
        return boatLayers;
    }

    public static List<ModelLayerLocation> colorChestBoatLayers() {
        List<ModelLayerLocation> chestBoatLayers = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getName() + "_painted";
            ResourceLocation layerLocation = new ResourceLocation(Paintable.MOD_ID, "chest_boat/" + colorName);
            ModelLayerLocation chestBoatLayer = new ModelLayerLocation(layerLocation, "main");
            chestBoatLayers.add(chestBoatLayer);
        }
        return chestBoatLayers;
    }
}