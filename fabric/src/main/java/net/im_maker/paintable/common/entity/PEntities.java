package net.im_maker.paintable.common.entity;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.entity.custom.PBoat;
import net.im_maker.paintable.common.entity.custom.PChestBoat;
import net.im_maker.paintable.common.entity.custom.PrimedPnt;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class PEntities {

    public static final EntityType<PBoat> BOAT =
            registerItem("boat", EntityType.Builder.<PBoat>of(PBoat::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("boat"));
    public static final EntityType<PChestBoat> CHEST_BOAT =
            registerItem("chest_boat", EntityType.Builder.<PChestBoat>of(PChestBoat::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("chest_boat"));
    public static final EntityType<PrimedPnt> PNT =
            registerItem("pnt", EntityType.Builder.<PrimedPnt>of(PrimedPnt::new, MobCategory.MISC)
                    .fireImmune().sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(10)
                    .build(new ResourceLocation(Paintable.MOD_ID, "pnt").toString()));

    private static EntityType registerItem(String name, EntityType entityType) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Paintable.MOD_ID, name), entityType);
    }

    public static void registerEntities() {
        Paintable.LOGGER.info("Registering Mod Entities for " + Paintable.MOD_ID);
    }
}