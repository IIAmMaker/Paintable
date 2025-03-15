package net.im_maker.paintable.common.entity.custom;

import net.im_maker.paintable.common.block.ModBlocks;
import net.im_maker.paintable.common.entity.PEntities;
import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.item.custom.DippedPaintBrushItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class PBoat extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(PBoat.class, EntityDataSerializers.INT);

    public PBoat(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PBoat(Level level, double pX, double pY, double pZ) {
        this(PEntities.BOAT.get(), level);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public Item getDropItem() {
        return PItems.PAINTED_BOATS.get(getModVariant().getColor().getId()).get();
    }

    public void setVariant(DyeColor color) {
        this.entityData.set(DATA_ID_TYPE, color.getId());
    }

    public Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, DyeColor.WHITE.getId());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Type", this.getModVariant().getColor().getId());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("Type")) {
            this.setVariant(DyeColor.byId(pCompound.getInt("Type")));
        }
    }

    public static class Type implements StringRepresentable {
        private static final Map<Integer, Type> BY_ID = new HashMap<>();
        private final DyeColor color;
        private final String name;
        private final Block planks;

        static {
            for (DyeColor color : DyeColor.values()) {
                new Type(color);
            }
        }

        private Type(DyeColor color) {
            this.color = color;
            this.name = color.getSerializedName() + "_painted";
            this.planks = ModBlocks.PAINTED_PLANKS.get(color.getId()).get();
            BY_ID.put(color.getId(), this);
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public DyeColor getColor() {
            return this.color;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public static Type byId(int id) {
            return BY_ID.getOrDefault(id, new Type(DyeColor.byId(id)));
        }
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
