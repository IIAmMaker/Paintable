package net.im_maker.paintable.common.entity.custom;

import net.im_maker.paintable.common.entity.PEntities;
import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.item.custom.DippedPaintBrushItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PChestBoat extends ChestBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(PChestBoat.class, EntityDataSerializers.INT);

    public PChestBoat(EntityType<? extends ChestBoat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PChestBoat(Level pLevel, double pX, double pY, double pZ) {
        this(PEntities.CHEST_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public Item getDropItem() {
        return PItems.PAINTED_CHEST_BOATS.get(getModVariant().getColor().getId()).get();
    }

    public void setVariant(DyeColor color) {
        this.entityData.set(DATA_ID_TYPE, color.getId());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, DyeColor.WHITE.getId());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Type", this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("Type")) {
            this.setVariant(DyeColor.byId(pCompound.getInt("Type")));
        }
    }

    public PBoat.Type getModVariant() {
        return PBoat.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (!(stack.getItem() instanceof DippedPaintBrushItem brush)) return super.interact(player, interactionHand);

        DyeColor color = brush.getColor();
        if (this.getModVariant().getColor() == color) return InteractionResult.PASS;

        this.setVariant(color);
        Level world = player.level();

        if (!world.isClientSide) {
            if (stack.getDamageValue() != 15) {
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(interactionHand));
            } else {
                player.setItemInHand(interactionHand, new ItemStack(PItems.PAINT_BRUSH.get()));
                world.playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            world.playSound(null, player.blockPosition(), SoundEvents.MUD_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(world.isClientSide);
    }
}
