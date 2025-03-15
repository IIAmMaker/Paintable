package net.im_maker.paintable.common.util;

import net.im_maker.paintable.Paintable;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModFileInfo;

import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = Paintable.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataPackRegistrar {

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            if (ModList.get().isLoaded("dye_depot")) {
                onAddPackFinders(event);
            }
        }
    }

    private static void onAddPackFinders(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(Paintable.MOD_ID);
        Path datapack_file = mod.getFile().findResource("resourcepacks/paintable_dye_depot_compat");
        event.addRepositorySource(packConsumer -> packConsumer.accept(
                Pack.create(
                        "paintable_dye_depot_compat",
                        Component.literal("Paintable DyeDepot Compat"),
                        true,
                        (path) -> new PathPackResources(path, datapack_file, true),
                        new Pack.Info(
                                Component.literal("Paintable DyeDepot Compat"),
                                SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                                FeatureFlagSet.of()
                        ),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        true,
                        PackSource.BUILT_IN
                )
        ));
    }
}
