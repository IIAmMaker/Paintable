package net.im_maker.paintable.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class StrippableLogBlock extends RotatedPillarBlock {
    public StrippableLogBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.getItemInHand(interactionHand).getItem() instanceof AxeItem) {
            Block block = blockState.getBlock();
            ResourceLocation blockString = BuiltInRegistries.BLOCK.getKey(block);

            String fullPath = blockString.getPath();
            String[] parts = fullPath.split("\\.");
            String lastPart = parts[parts.length - 1];
            String strippedBlockString = "stripped_" + lastPart;

            Block strippedBlock = BuiltInRegistries.BLOCK.get(new ResourceLocation(blockString.getNamespace(),strippedBlockString));
            level.playSound(player, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlockAndUpdate(blockPos, strippedBlock.withPropertiesOf(blockState));
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }
}