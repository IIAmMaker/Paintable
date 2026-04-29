package net.im_maker.paintable.common.entity;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.entity.custom.PBoat;
import net.im_maker.paintable.common.entity.custom.PChestBoat;
import net.im_maker.paintable.common.entity.custom.PrimedPnt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Paintable.MOD_ID);


    public static final RegistryObject<EntityType<PBoat>> BOAT =
            ENTITY_TYPES.register("boat", () -> EntityType.Builder.<PBoat>of(PBoat::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("boat"));
    public static final RegistryObject<EntityType<PChestBoat>> CHEST_BOAT =
            ENTITY_TYPES.register("chest_boat", () -> EntityType.Builder.<PChestBoat>of(PChestBoat::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("chest_boat"));
    public static final RegistryObject<EntityType<PrimedPnt>> PNT =
            ENTITY_TYPES.register("pnt", () -> EntityType.Builder.<PrimedPnt>of(PrimedPnt::new, MobCategory.MISC)
                    .fireImmune().sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(10)
                    .build(new ResourceLocation(Paintable.MOD_ID, "pnt").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}