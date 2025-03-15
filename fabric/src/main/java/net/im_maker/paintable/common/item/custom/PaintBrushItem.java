package net.im_maker.paintable.common.item.custom;

import net.im_maker.paintable.common.block.block_values.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PaintBrushItem extends Item {
    public PaintBrushItem(Properties properties) {
        super(properties);
    }

    private void paintBrushDip (Level world,BlockPos blockpos,BlockState blockstate, UseOnContext context, Item item) {
        context.getPlayer().setItemInHand(context.getHand(), new ItemStack(item));
        world.playSound((Player)null, blockpos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        Integer a = blockstate.getValue(ModBlockStateProperties.LEVEL_PAINT);
        world.setBlockAndUpdate(blockpos, blockstate.setValue(ModBlockStateProperties.LEVEL_PAINT, a-1));
    }

    private boolean canDip (BlockState blockstate, Block block) {
        return blockstate.is(block) && blockstate.getValue(ModBlockStateProperties.LEVEL_PAINT) != 0;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);
        for (DyeColor color : DyeColor.values()) {
            ResourceLocation filledPaintBucketBlockLocation = new ResourceLocation("paintable:" + color +"_paint_bucket");
            Block filledPaintBucketBlock = BuiltInRegistries.BLOCK.get(filledPaintBucketBlockLocation);
            ResourceLocation paintBrushLocation = new ResourceLocation("paintable:" + color +"_paint_brush");
            Item paintBrush = BuiltInRegistries.ITEM.get(paintBrushLocation);
            if (this.canDip(blockstate, filledPaintBucketBlock)) {
                this.paintBrushDip(world, blockpos, blockstate, context, paintBrush);
                return InteractionResult.sidedSuccess(world.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }
}