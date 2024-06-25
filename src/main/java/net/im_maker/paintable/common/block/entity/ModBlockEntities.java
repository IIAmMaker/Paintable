package net.im_maker.paintable.common.block.entity;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.block.entity.custom.ModHangingSignBlockEntity;
import net.im_maker.paintable.common.block.entity.custom.ModSignBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Paintable.MOD_ID);

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("sign_block_entity", () ->
            BlockEntityType.Builder.of(ModSignBlockEntity::new, getAllSignBlocks()).build(null));
    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> HANGING_SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES.register("hanging_sign_block_entity", () ->
            BlockEntityType.Builder.of(ModHangingSignBlockEntity::new, getAllHangingSignBlocks()).build(null));

    private static Block[] getAllSignBlocks() {
        Block[] signBlocks = new Block[DyeColor.values().length * 2];
        int index = 0;
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getName();
            ResourceLocation paintedSignLocation = new ResourceLocation("paintable:" + colorName + "_painted_sign");
            ResourceLocation paintedWallSignLocation = new ResourceLocation("paintable:" + colorName + "_painted_wall_sign");
            Block paintedSign = ForgeRegistries.BLOCKS.getValue(paintedSignLocation);
            Block paintedWallSign = ForgeRegistries.BLOCKS.getValue(paintedWallSignLocation);
            signBlocks[index++] = paintedSign;
            signBlocks[index++] = paintedWallSign;
        }
        return signBlocks;
    }

    private static Block[] getAllHangingSignBlocks() {
        Block[] signBlocks = new Block[DyeColor.values().length * 2];
        int index = 0;
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getName();
            ResourceLocation paintedSignLocation = new ResourceLocation("paintable:" + colorName + "_painted_hanging_sign");
            ResourceLocation paintedWallSignLocation = new ResourceLocation("paintable:" + colorName + "_painted_wall_hanging_sign");
            Block paintedSign = ForgeRegistries.BLOCKS.getValue(paintedSignLocation);
            Block paintedWallSign = ForgeRegistries.BLOCKS.getValue(paintedWallSignLocation);
            signBlocks[index++] = paintedSign;
            signBlocks[index++] = paintedWallSign;
        }
        return signBlocks;
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
