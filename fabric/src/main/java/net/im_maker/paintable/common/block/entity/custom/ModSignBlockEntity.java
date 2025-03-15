package net.im_maker.paintable.common.block.entity.custom;

import net.im_maker.paintable.common.block.entity.PBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModSignBlockEntity extends SignBlockEntity {
    public ModSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PBlockEntities.SIGN, blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return PBlockEntities.SIGN;
    }
}