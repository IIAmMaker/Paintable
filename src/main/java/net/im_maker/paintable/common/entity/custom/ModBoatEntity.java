package net.im_maker.paintable.common.entity.custom;

import net.im_maker.paintable.common.block.ModBlocks;
import net.im_maker.paintable.common.entity.ModEntities;
import net.im_maker.paintable.common.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.IntFunction;

public class ModBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);

    public ModBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ModBoatEntity(Level level, double pX, double pY, double pZ) {
        this(ModEntities.BOAT.get(), level);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    @Override
    public Item getDropItem() {
        switch (getModVariant()) {
            case WHITE_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(0).get();
            }
            case ORANGE_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(1).get();
            }
            case MAGENTA_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(2).get();
            }
            case LIGHT_BLUE_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(3).get();
            }
            case YELLOW_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(4).get();
            }
            case LIME_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(5).get();
            }
            case PINK_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(6).get();
            }
            case GRAY_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(7).get();
            }
            case LIGHT_GRAY_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(8).get();
            }
            case CYAN_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(9).get();
            }
            case PURPLE_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(10).get();
            }
            case BLUE_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(11).get();
            }
            case BROWN_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(12).get();
            }
            case GREEN_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(13).get();
            }
            case RED_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(14).get();
            }
            case BLACK_PAINTED -> {
                return ModItems.PAINTED_BOATS.get(15).get();
            }
        }
        return super.getDropItem();
    }

    public void setVariant(Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    public Type getModVariant() {
        return Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, Type.WHITE_PAINTED.ordinal());
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 8)) {
            this.setVariant(Type.byName(pCompound.getString("Type")));
        }
    }

    public static enum Type implements StringRepresentable {

        WHITE_PAINTED(ModBlocks.PAINTED_PLANKS.get(0).get(), "white_painted"),
        ORANGE_PAINTED(ModBlocks.PAINTED_PLANKS.get(1).get(), "orange_painted"),
        MAGENTA_PAINTED(ModBlocks.PAINTED_PLANKS.get(2).get(), "magenta_painted"),
        LIGHT_BLUE_PAINTED(ModBlocks.PAINTED_PLANKS.get(3).get(), "light_blue_painted"),
        YELLOW_PAINTED(ModBlocks.PAINTED_PLANKS.get(4).get(), "yellow_painted"),
        LIME_PAINTED(ModBlocks.PAINTED_PLANKS.get(5).get(), "lime_painted"),
        PINK_PAINTED(ModBlocks.PAINTED_PLANKS.get(6).get(), "pink_painted"),
        GRAY_PAINTED(ModBlocks.PAINTED_PLANKS.get(7).get(), "gray_painted"),
        LIGHT_GRAY_PAINTED(ModBlocks.PAINTED_PLANKS.get(8).get(), "light_gray_painted"),
        CYAN_PAINTED(ModBlocks.PAINTED_PLANKS.get(9).get(), "cyan_painted"),
        PURPLE_PAINTED(ModBlocks.PAINTED_PLANKS.get(10).get(), "purple_painted"),
        BLUE_PAINTED(ModBlocks.PAINTED_PLANKS.get(11).get(), "blue_painted"),
        BROWN_PAINTED(ModBlocks.PAINTED_PLANKS.get(12).get(), "brown_painted"),
        GREEN_PAINTED(ModBlocks.PAINTED_PLANKS.get(13).get(), "green_painted"),
        RED_PAINTED(ModBlocks.PAINTED_PLANKS.get(14).get(), "red_painted"),
        BLACK_PAINTED(ModBlocks.PAINTED_PLANKS.get(15).get(), "black_painted");

        private final String name;
        private final Block planks;
        public static final StringRepresentable.EnumCodec<ModBoatEntity.Type> CODEC = StringRepresentable.fromEnum(ModBoatEntity.Type::values);
        private static final IntFunction<ModBoatEntity.Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        private Type(Block pPlanks, String pName) {
            this.name = pName;
            this.planks = pPlanks;
        }

        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        public String toString() {
            return this.name;
        }


        public static ModBoatEntity.Type byId(int pId) {
            return BY_ID.apply(pId);
        }

        public static ModBoatEntity.Type byName(String pName) {
            return CODEC.byName(pName, WHITE_PAINTED);
        }
    }
}