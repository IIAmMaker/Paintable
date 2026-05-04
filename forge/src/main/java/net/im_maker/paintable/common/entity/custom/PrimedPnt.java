package net.im_maker.paintable.common.entity.custom;

import net.im_maker.paintable.common.entity.PEntities;
import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.item.custom.DippedPaintBrushItem;
import net.im_maker.paintable.config.PaintableConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayerFactory;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class PrimedPnt extends PrimedTnt {
    private static final EntityDataAccessor<Integer> DATA_COLOR =
            SynchedEntityData.defineId(PrimedPnt.class, EntityDataSerializers.INT);

    private LivingEntity owner;

    public PrimedPnt(EntityType<? extends PrimedPnt> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
        setColor(DyeColor.WHITE);
    }

    public PrimedPnt(Level level, double x, double y, double z, @Nullable LivingEntity igniter, DyeColor color) {
        this(PEntities.PNT.get(), level);
        this.setPos(x, y, z);
        double d0 = level.random.nextDouble() * (Math.PI * 2);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2D, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.owner = igniter;
        setColor(color);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_COLOR, DyeColor.WHITE.getId());
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLOR));
    }

    public void setColor(DyeColor color) {
        this.entityData.set(DATA_COLOR, color.getId());
    }

    public LivingEntity getOwner() {
        return this.owner;
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {

            if (this.level().isClientSide) {

            }
            if (!this.level().isClientSide){
                this.explode();
            }

            this.discard();
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    protected void explode() {
        if (this.level().isClientSide) return;

        final int RADIUS = PaintableConfig.PNT_EXPLOSION_RADIUS.get();
        final int RAY_COUNT = 1500;
        Vec3 origin = Vec3.atCenterOf(this.blockPosition());
        int PARTICLE_COUNT = 400;
        RandomSource rand = this.level().getRandom();
        Set<BlockPos> painted = new HashSet<>();
        ServerLevel serverLevel = (ServerLevel) this.level();
        ServerPlayer fakePlayer = FakePlayerFactory.getMinecraft(serverLevel);

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            double spread = 7.5;
            double px = origin.x + (rand.nextDouble() - 0.5) * spread;
            double py = origin.y + (rand.nextDouble() - 0.5) * spread;
            double pz = origin.z + (rand.nextDouble() - 0.5) * spread;
            double vx = (rand.nextDouble() - 0.5) * 0.05;
            double vy = (rand.nextDouble() - 0.5) * 0.05;
            double vz = (rand.nextDouble() - 0.5) * 0.05;
            serverLevel.sendParticles(new DustParticleOptions(new Vector3f(getColor().getTextureDiffuseColors()[0], getColor().getTextureDiffuseColors()[1], getColor().getTextureDiffuseColors()[2]), 1.2f), px, py, pz, 0, vx, vy, vz, 1.0);
        }

        ItemStack brushStack = new ItemStack(PItems.DIPPED_PAINT_BRUSHES.get(getColor().getId()).get());
        DippedPaintBrushItem brush = (DippedPaintBrushItem) brushStack.getItem();
        brush.setPaintCount(brushStack, 9999);
        fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, brushStack);
        fakePlayer.setGameMode(GameType.CREATIVE);

        for (int i = 0; i < RAY_COUNT; i++) {
            double z = rand.nextDouble() * 2.0 - 1.0;
            double t = rand.nextDouble() * Math.PI * 2.0;
            double r = Math.sqrt(1.0 - z * z);
            Vec3 dir = new Vec3(r * Math.cos(t), r * Math.sin(t), z);

            Vec3 start = origin.add(dir.scale(0.6));
            Vec3 end = origin.add(dir.scale(RADIUS));

            ClipContext ctx = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this);
            HitResult hr = this.level().clip(ctx);
            BlockHitResult bhr = (BlockHitResult) hr;
            BlockPos hitPos = bhr.getBlockPos();
            BlockState state = this.level().getBlockState(hitPos);

            if (hr.getType() != HitResult.Type.BLOCK) continue;
            if (hitPos.distSqr(this.blockPosition()) > (long) RADIUS * RADIUS) continue;
            if (!painted.add(hitPos)) continue;
            if (state.isAir() || state.liquid()) continue;

            BlockHitResult blockHit = new BlockHitResult(Vec3.atCenterOf(hitPos), Direction.UP, hitPos, false);
            UseOnContext ctxUse = new UseOnContext(fakePlayer, InteractionHand.MAIN_HAND, blockHit);
            brush.useOn(ctxUse);
        }
        this.level().explode(null, this.getX(), this.getY(), this.getZ(), 0.0F, Level.ExplosionInteraction.TNT);
    }
}
