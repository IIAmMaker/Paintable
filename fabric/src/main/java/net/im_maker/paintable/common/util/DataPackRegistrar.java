package net.im_maker.paintable.common.util;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.im_maker.paintable.Paintable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class DataPackRegistrar {

    private static void registerBuiltinDyeDepotDataPack(ModContainer modContainer, String packId) {
        ResourceManagerHelper.registerBuiltinResourcePack(
                new ResourceLocation(Paintable.MOD_ID, packId),
                modContainer,
                "Paintable DyeDepot Compat",
                ResourcePackActivationType.ALWAYS_ENABLED
        );
    }

    private static void registerBuiltinSupplementariesDataPack(ModContainer modContainer, String packId) {
        ResourceManagerHelper.registerBuiltinResourcePack(
                new ResourceLocation(Paintable.MOD_ID, packId),
                modContainer,
                "Paintable Supplementaries Compat",
                ResourcePackActivationType.ALWAYS_ENABLED
        );
    }

    public static void loadBuiltinResourcePacks() {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(Paintable.MOD_ID);
        if (modContainer.isPresent()) {
            if (FabricLoader.getInstance().isModLoaded("dye_depot")) {
                registerBuiltinDyeDepotDataPack(modContainer.get(), "paintable_dye_depot_compat");
            }
            if (FabricLoader.getInstance().isModLoaded("supplementaries")) {
                registerBuiltinSupplementariesDataPack(modContainer.get(), "paintable_supplementaries_compat");
            }
        }
    }
}