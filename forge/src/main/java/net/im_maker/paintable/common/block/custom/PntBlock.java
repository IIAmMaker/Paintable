package net.im_maker.paintable.common.block.custom;

import net.im_maker.paintable.common.entity.custom.PrimedPnt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class PntBlock extends TntBlock {
    private final DyeColor color;

    public PntBlock(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
    }


    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        if (!level.isClientSide) {
            PrimedPnt primedPntmt = new PrimedPnt(level, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, explosion.getIndirectSourceEntity(), color);
            int i = primedPntmt.getFuse();
            primedPntmt.setFuse((short) (level.random.nextInt(i / 4) + i / 8));
            level.addFreshEntity(primedPntmt);
        }
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        explode(world, pos, igniter, color);
    }

    public static void explode(Level level, BlockPos pos, @Nullable LivingEntity igniter, DyeColor color) {
        if (!level.isClientSide) {
            PrimedPnt primedPnt = new PrimedPnt(level, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, igniter, color); // <-- now it's passed in
            level.addFreshEntity(primedPnt);
            level.playSound(null, primedPnt.getX(), primedPnt.getY(), primedPnt.getZ(),
                    SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(igniter, GameEvent.PRIME_FUSE, pos);
        }
    }

}
