package net.im_maker.paintable.common.block.custom;

import net.im_maker.paintable.Paintable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

public class StrippableRotatedPillarBlock extends RotatedPillarBlock {
    private final DyeColor color;
    public StrippableRotatedPillarBlock(DyeColor paintColor, Properties properties) {
        super(properties);
        this.color = paintColor;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            if (state.is(Paintable.getBlockFromString(color + "_painted_log"))) {
                Level level = context.getLevel();
                BlockPos blockpos = context.getClickedPos();
                level.playSound(context.getPlayer(), blockpos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                return Paintable.getBlockFromString("stripped_" + color + "_painted_log").withPropertiesOf(state);
            }
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
