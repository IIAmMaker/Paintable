package net.im_maker.paintable.common.item.custom;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.item.ModItems;
import net.im_maker.paintable.common.tags.ModBlockTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DippedPaintBrushItem extends Item {
    private final DyeColor color;
    private final List<DyeColor> customColorOrder = new ArrayList<>(Arrays.asList(
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
            DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
            DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
            DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
    ));

    public DippedPaintBrushItem(DyeColor paintColor, Properties properties) {
        super(properties);
        this.color = paintColor;
        customColorOrder.addAll(Arrays.asList(DyeColor.values()));
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack itemStack = new ItemStack(this);
        itemStack.setDamageValue(stack.getDamageValue() + 1);
        if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) {
            return new ItemStack(ModItems.PAINT_BRUSH.get());
        }
        return itemStack;
    }

    @Override
    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    private void paint (String blockName, ItemStack itemStack, Player player, UseOnContext context, Level world, BlockPos blockPos, BlockState blockState) {
        paint(blockName, itemStack, player, context, world,blockPos, blockState, true);
    }

        private void paint (String blockName, ItemStack itemStack, Player player, UseOnContext context, Level world, BlockPos blockPos, BlockState blockState, Boolean i) {
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
        }
        if (itemStack.getDamageValue() != 15) {
            if (player != null) {
                itemStack.hurtAndBreak(1, player, (player1) -> {
                    player1.broadcastBreakEvent(context.getHand());
                });
            }
        } else {
            context.getPlayer().setItemInHand(context.getHand(), new ItemStack(ModItems.PAINT_BRUSH.get()));
            world.playSound((Player)null, blockPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        if (i) {
            ResourceLocation blockLocation;
            if (blockName.contains("%s")) {
                blockLocation = new ResourceLocation("paintable:" + String.format(blockName, color + "_painted_"));
            } else {
                blockLocation = new ResourceLocation("paintable:" + color + "_painted_" + blockName);
            }
            Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
            if (!blockState.hasBlockEntity()) {
                world.setBlockAndUpdate(blockPos, block.withPropertiesOf(blockState));
            } else {
                BlockEntity blockE = null;
                BlockEntity blockEntity = world.getBlockEntity(blockPos);
                CompoundTag data = null;
                if (blockEntity instanceof SignBlockEntity SignBlock) {
                    data = SignBlock.serializeNBT();
                    blockE = BlockEntity.loadStatic(blockPos, block.defaultBlockState(), data);
                } else if (blockEntity instanceof HangingSignBlockEntity HangingSignBlock) {
                    data = HangingSignBlock.serializeNBT();
                    blockE = BlockEntity.loadStatic(blockPos, block.defaultBlockState(), data);
                }
                if (blockE != null) {
                    world.setBlockAndUpdate(blockPos, blockE.getBlockState().getBlock().withPropertiesOf(blockEntity.getBlockState()));
                    blockEntity = world.getBlockEntity(blockPos);
                    blockEntity.deserializeNBT(data);
                }
            }
            world.playSound((Player)null, blockPos, SoundEvents.MUD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        } else {
            Block block;
            for (IModInfo mod : ModList.get().getMods()) {
                block = Paintable.getBlockFromString(mod.getModId(), color + "_" + blockName);
                System.out.println(mod.getModId() + color);
                if (block != null && block != Blocks.AIR) {
                    world.setBlockAndUpdate(blockPos, block.withPropertiesOf(blockState));
                    System.out.println(mod.getModId() + color);
                    world.playSound((Player)null, blockPos, SoundEvents.MUD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }

    private Boolean canPaint (String block) {
        return block.contains(":" + color + "_") || block.contains("stripped_" + color + "_");
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);
        ItemStack itemStack = context.getItemInHand();
        Player player = context.getPlayer();
        InteractionResult pass = InteractionResult.PASS;
        InteractionResult success = InteractionResult.sidedSuccess(world.isClientSide);
        String blockID = ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).toString();
        if (canPaint(blockID)) {
            return pass;
        }
        if (blockState.is(BlockTags.PLANKS)) {
            this.paint("planks", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.WOODEN_STAIRS)) {
            this.paint("stairs", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.WOODEN_SLABS)) {
            this.paint("slab", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.WOODEN_FENCES)) {
            this.paint("fence", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(Tags.Blocks.FENCE_GATES_WOODEN)) {
            this.paint("fence_gate", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.WOODEN_BUTTONS)) {
            this.paint("button", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.WOODEN_PRESSURE_PLATES)) {
            this.paint("pressure_plate", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.STANDING_SIGNS)) {
            this.paint("sign", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.WALL_SIGNS)) {
            this.paint("wall_sign", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.CEILING_HANGING_SIGNS)) {
            this.paint("hanging_sign", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.WALL_HANGING_SIGNS)) {
            this.paint("wall_hanging_sign", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(ModBlockTags.PAINTABLE_BRICKS)) {
            this.paint("bricks", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(ModBlockTags.PAINTABLE_BRICK_STAIRS)) {
            this.paint("brick_stairs", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(ModBlockTags.PAINTABLE_BRICK_SLABS)) {
            this.paint("brick_slab", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(ModBlockTags.PAINTABLE_BRICK_WALLS)) {
            this.paint("brick_wall", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(ModBlockTags.PAINTABLE_MUD_BRICKS)) {
            this.paint("mud_bricks", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(ModBlockTags.PAINTABLE_MUD_BRICK_STAIRS)) {
            this.paint("mud_brick_stairs", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(ModBlockTags.PAINTABLE_MUD_BRICK_SLABS)) {
            this.paint("mud_brick_slab", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(ModBlockTags.PAINTABLE_MUD_BRICK_WALLS)) {
            this.paint("mud_brick_wall", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.LOGS) && !blockID.contains("stripped") && blockID.contains("log")) {
            this.paint("log", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.LOGS) && !blockID.contains("stripped") && blockID.contains("wood")) {
            this.paint("wood", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.LOGS) && blockID.contains("stripped") && (blockID.contains("log") || blockID.contains("stem"))) {
            this.paint("stripped_%slog", itemStack, player, context, world, blockPos, blockState);
            return success;
        } else if (blockState.is(BlockTags.LOGS) && blockID.contains("stripped") && (blockID.contains("wood") || blockID.contains("hyphae"))) {
            this.paint("stripped_%swood", itemStack, player, context, world, blockPos, blockState);
            return success;
        }else if (blockState.is(ModBlockTags.PAINTABLE_CONCRETE_VANILLA) || blockState.is(ModBlockTags.PAINTABLE_CONCRETE)) {
            this.paint("concrete", itemStack, player, context, world, blockPos, blockState, false);
            return success;
        } else {
            return pass;
        }
    }
}