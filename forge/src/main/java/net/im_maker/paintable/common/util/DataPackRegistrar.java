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
            onAddPackFinders(event);
        }
    }

    private static void onAddPackFinders(AddPackFindersEvent event) {
        IModFileInfo mod = ModList.get().getModFileById(Paintable.MOD_ID);
        Path clayworks_datapack_file = mod.getFile().findResource("resourcepacks/paintable_clayworks_compat");
        Path dye_depot_datapack_file = mod.getFile().findResource("resourcepacks/paintable_dye_depot_compat");
        Path oreganized_datapack_file = mod.getFile().findResource("resourcepacks/paintable_oreganized_compat");
        Path supplementaries_datapack_file = mod.getFile().findResource("resourcepacks/paintable_supplementaries_compat");
        if (ModList.get().isLoaded("clayworks")) event.addRepositorySource(
                packConsumer -> packConsumer.accept(
                Pack.create(
                        "paintable_clayworks_compat",
                        Component.literal("Paintable Clay Works Compat"),
                        true,
                        (path) -> new PathPackResources(path, clayworks_datapack_file, true),
                        new Pack.Info(
                                Component.literal("Paintable Clay Works Compat"),
                                SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                                FeatureFlagSet.of()
                        ),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        true,
                        PackSource.BUILT_IN
                )
        ));
        if (ModList.get().isLoaded("dye_depot")) event.addRepositorySource(
                packConsumer -> packConsumer.accept(
                        Pack.create(
                                "paintable_dye_depot_compat",
                                Component.literal("Paintable DyeDepot Compat"),
                                true,
                                (path) -> new PathPackResources(path, dye_depot_datapack_file, true),
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
        if (ModList.get().isLoaded("oreganized")) event.addRepositorySource(
                packConsumer -> packConsumer.accept(
                        Pack.create(
                                "paintable_oreganized_compat",
                                Component.literal("Paintable Oreganized Compat"),
                                true,
                                (path) -> new PathPackResources(path, oreganized_datapack_file, true),
                                new Pack.Info(
                                        Component.literal("Paintable Oreganized Compat"),
                                        SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA),
                                        FeatureFlagSet.of()
                                ),
                                PackType.SERVER_DATA,
                                Pack.Position.TOP,
                                true,
                                PackSource.BUILT_IN
                        )
                ));
        if (ModList.get().isLoaded("supplementaries")) event.addRepositorySource(
                packConsumer -> packConsumer.accept(
                        Pack.create(
                                "paintable_supplementaries_compat",
                                Component.literal("Paintable Supplementaries Compat"),
                                true,
                                (path) -> new PathPackResources(path, supplementaries_datapack_file, true),
                                new Pack.Info(
                                        Component.literal("Paintable Supplementaries Compat"),
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
