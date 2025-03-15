package net.im_maker.paintable.common.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.block.custom.ModHangingSignBlock;
import net.im_maker.paintable.common.block.custom.ModStandingSignBlock;
import net.im_maker.paintable.common.block.custom.ModWallHangingSignBlock;
import net.im_maker.paintable.common.block.custom.ModWallSignBlock;
import net.im_maker.paintable.common.block.entity.custom.ModHangingSignBlockEntity;
import net.im_maker.paintable.common.block.entity.custom.ModSignBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PBlockEntities {

    public static BlockEntityType<ModSignBlockEntity> SIGN;
    public static BlockEntityType<ModHangingSignBlockEntity> HANGING_SIGN;

    public static void  registerBlockEntities() {
        SIGN = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(Paintable.MOD_ID, "sign_block"),
                FabricBlockEntityTypeBuilder.create(ModSignBlockEntity::new,
                        Paintable.getBlocks(ModStandingSignBlock.class, ModWallSignBlock.class)).build());

        HANGING_SIGN = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(Paintable.MOD_ID, "hanging_sign_block"),
                FabricBlockEntityTypeBuilder.create(ModHangingSignBlockEntity::new,
                        Paintable.getBlocks(ModHangingSignBlock.class, ModWallHangingSignBlock.class)).build());

        Paintable.LOGGER.info("Registering Mod BlockEntities for " + Paintable.MOD_ID);
    }
}