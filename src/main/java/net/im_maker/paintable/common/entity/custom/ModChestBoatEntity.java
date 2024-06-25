package net.im_maker.paintable.common.entity.custom;

import net.im_maker.paintable.common.entity.ModEntities;
import net.im_maker.paintable.common.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ModChestBoatEntity extends ChestBoat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);

    public ModChestBoatEntity(EntityType<? extends ChestBoat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ModChestBoatEntity(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntities.CHEST_BOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public Item getDropItem() {
        switch (getModVariant()) {
            case WHITE_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(0).get();
            }
            case ORANGE_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(1).get();
            }
            case MAGENTA_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(2).get();
            }
            case LIGHT_BLUE_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(3).get();
            }
            case YELLOW_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(4).get();
            }
            case LIME_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(5).get();
            }
            case PINK_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(6).get();
            }
            case GRAY_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(7).get();
            }
            case LIGHT_GRAY_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(8).get();
            }
            case CYAN_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(9).get();
            }
            case PURPLE_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(10).get();
            }
            case BLUE_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(11).get();
            }
            case BROWN_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(12).get();
            }
            case GREEN_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(13).get();
            }
            case RED_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(14).get();
            }
            case BLACK_PAINTED -> {
                return ModItems.PAINTED_CHEST_BOATS.get(15).get();
            }
        }
        return super.getDropItem();
    }

    public void setVariant(ModBoatEntity.Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, ModBoatEntity.Type.WHITE_PAINTED.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(ModBoatEntity.Type.byName(pCompound.getString("Type")));
        }
    }

    public ModBoatEntity.Type getModVariant() {
        return ModBoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }
}