package net.im_maker.paintable.common.item.custom;

import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.util.PTags;
import net.im_maker.paintable.config.PaintableServerConfigs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DippedPaintBrushItem extends Item {
    private final DyeColor color;

    public DippedPaintBrushItem(DyeColor paintColor, Properties properties) {
        super(properties);
        this.color = paintColor;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }

    private void paint(String blockName, ItemStack itemStack, Player player, UseOnContext context, Level world, BlockPos blockPos, BlockState blockState) {
        paint(blockName, "", "paintable", itemStack, player, context, world, blockPos, blockState, true, true, true);
    }

    private void paint(String blockName, String prefix, String blockNameSpace, ItemStack itemStack, Player player, UseOnContext context, Level world, BlockPos blockPos, BlockState blockState, boolean i, boolean t, boolean canRepaint) {
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, itemStack);
        }
        if (itemStack.getDamageValue() != 15) {
            itemStack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(context.getHand()));
        } else {
            player.setItemInHand(context.getHand(), new ItemStack(PItems.PAINT_BRUSH.get()));
            world.playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        Boolean n = false;
        BlockPos newBlockPos = blockPos;
        BlockState newBlockState = blockState;
        if (blockState.getBlock() instanceof BedBlock && canRepaint) {
            Direction d = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING);
            BedPart p = blockState.getValue(BlockStateProperties.BED_PART);
            n = true;
            newBlockPos = p == BedPart.FOOT ? blockPos.relative(d) : blockPos.relative(d.getOpposite());
            //newBlockState = world.getBlockState(newBlockPos); //For some reason, it doesn't work like this, but it does with this:
            BedPart newPart = p == BedPart.FOOT ? BedPart.HEAD : BedPart.FOOT;
            newBlockState = world.getBlockState(newBlockPos).setValue(BlockStateProperties.BED_PART, newPart);
        }
        if (blockState.getBlock() instanceof DoorBlock && canRepaint) {
            DoubleBlockHalf p = blockState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
            n = true;
            newBlockPos = p == DoubleBlockHalf.LOWER ? blockPos.above() : blockPos.below();
            //newBlockState = world.getBlockState(newBlockPos); //For some reason, it doesn't work like this, but it does with this:
            DoubleBlockHalf newPart = p == DoubleBlockHalf.LOWER ? DoubleBlockHalf.UPPER : DoubleBlockHalf.LOWER;
            newBlockState = world.getBlockState(newBlockPos).is(BlockTags.WOODEN_DOORS) ? world.getBlockState(newBlockPos).setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, newPart) : world.getBlockState(newBlockPos);
        }
        if (i) {
            ResourceLocation blockLocation = t ?
                    new ResourceLocation(blockNameSpace + ":" + (blockName.contains("%s") ? String.format(blockName, color + "_painted_") : color + "_painted_" + blockName)) :
                    new ResourceLocation(blockNameSpace + ":" + prefix + color + "_" + blockName);
            Block block = BuiltInRegistries.BLOCK.get(blockLocation);
            if (block != null) {
                if (!blockState.hasBlockEntity()) {
                    world.setBlock(newBlockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_KNOWN_SHAPE);
                    world.setBlockAndUpdate(blockPos, block.withPropertiesOf(blockState));
                    if (n) world.setBlockAndUpdate(newBlockPos, block.withPropertiesOf(newBlockState));
                } else {
                    BlockEntity blockEntity = world.getBlockEntity(blockPos);
                    if (blockEntity != null) {
                        CompoundTag data = blockEntity.saveWithFullMetadata();
                        world.setBlock(newBlockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_KNOWN_SHAPE);
                        world.setBlockAndUpdate(blockPos, block.withPropertiesOf(blockState));
                        if (n) world.setBlockAndUpdate(newBlockPos, block.withPropertiesOf(newBlockState));
                        BlockEntity newBlockEntity = world.getBlockEntity(blockPos);
                        if (newBlockEntity != null) {
                            newBlockEntity.load(data);
                            world.setBlockEntity(newBlockEntity);
                        }
                    }
                }
                world.playSound(null, blockPos, SoundEvents.MUD_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    private void cleanPaintBrush (Level world,BlockPos blockpos, UseOnContext context) {
        context.getPlayer().setItemInHand(context.getHand(), new ItemStack(PItems.PAINT_BRUSH.get()));
        world.playSound((Player)null, blockpos, SoundEvents.MUD_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    private boolean cantPaint(String block) {
        return block.contains(":" + color + "_") || block.contains("_" + color + "_") || block.contains("/" + color + "_");
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);
        ItemStack itemStack = context.getItemInHand();
        Player player = context.getPlayer();
        String blockID = BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).toString();

        if (cantPaint(blockID) || ((blockState.is(PTags.Blocks.TOGGLEABLE) && PaintableServerConfigs.CAN_PAINT.get()))) return InteractionResult.PASS;

        if (blockState.is(PTags.Blocks.PAINT_BRUSH_WASHING_BLOCKS)) {
            cleanPaintBrush(world, blockPos, context);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        for (TagKey<Block> tag : blockState.getTags().toList()) {
            ResourceLocation tagLocation = tag.location();
            if (tagLocation.getPath().contains("paintable/") && !tagLocation.getPath().contains("paintable/toggleable")) {
                String path = tagLocation.getPath();
                String blockName;
                String prefix;
                if (path.contains("_c_")) {
                    String[] parts = path.split("_c_");
                    prefix = parts[0].replace("paintable/", "") + "_";
                    blockName = parts[1].replace("paintable/", "");
                } else {
                    prefix = "";
                    blockName = path.replace("paintable/", "");
                }
                String nameSpace = tagLocation.getNamespace();
                TagKey<Block> blockTag = TagKey.create(Registries.BLOCK, tagLocation);
                List<Block> blocks = BuiltInRegistries.BLOCK.getTag(blockTag).stream().flatMap(holder -> holder.stream()).map(Holder::value).toList();
                boolean cantPaint = true;
                for (Block block : blocks) {
                    ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(block);
                    if (blockId != null && blockId.getPath().contains("magenta")) {
                        nameSpace = blockId.getNamespace();
                        Block blockTest = BuiltInRegistries.BLOCK.get(new ResourceLocation(nameSpace, prefix + color + "_" + blockName));
                        if (blockTest != Blocks.AIR) {
                            cantPaint = false;
                            break;
                        }
                    } else if (blockId != null && blockId.getPath().contains("maroon")) {
                        nameSpace = blockId.getNamespace();
                        cantPaint = false;
                        break;
                    }
                }
                if (cantPaint) return InteractionResult.PASS;
                Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(nameSpace, color + "_" + blockName));
                if (block == null) return InteractionResult.PASS;
                System.out.println(blockName);
                System.out.println(prefix);
                System.out.println(prefix);
                paint(blockName, prefix, nameSpace, itemStack, player, context, world, blockPos, blockState, true, false, true);
                return InteractionResult.sidedSuccess(world.isClientSide);
            }
        }

        Map<TagKey<Block>, String> tagMap = new HashMap<>();
        //tagMap.put(BlockTags.PLANKS, "planks");
        //tagMap.put(BlockTags.WOODEN_STAIRS, "stairs");
        //tagMap.put(BlockTags.WOODEN_SLABS, "slab");
        //tagMap.put(BlockTags.WOODEN_FENCES, "fence");
        //tagMap.put(BlockTags.FENCE_GATES, "fence_gate");
        //tagMap.put(BlockTags.WOODEN_BUTTONS, "button");
        //tagMap.put(BlockTags.WOODEN_PRESSURE_PLATES, "pressure_plate");
        //tagMap.put(BlockTags.STANDING_SIGNS, "sign");
        //tagMap.put(BlockTags.WALL_SIGNS, "wall_sign");
        //tagMap.put(BlockTags.CEILING_HANGING_SIGNS, "hanging_sign");
        //tagMap.put(BlockTags.WALL_HANGING_SIGNS, "wall_hanging_sign");
        //tagMap.put(ModBlockTags.PAINTABLE_BRICKS, "bricks");
        //tagMap.put(ModBlockTags.PAINTABLE_BRICK_STAIRS, "brick_stairs");
        //tagMap.put(ModBlockTags.PAINTABLE_BRICK_SLABS, "brick_slab");
        //tagMap.put(ModBlockTags.PAINTABLE_BRICK_WALLS, "brick_wall");
        //tagMap.put(ModBlockTags.PAINTABLE_MUD_BRICKS, "mud_bricks");
        //tagMap.put(ModBlockTags.PAINTABLE_MUD_BRICK_STAIRS, "mud_brick_stairs");
        //tagMap.put(ModBlockTags.PAINTABLE_MUD_BRICK_SLABS, "mud_brick_slab");
        //tagMap.put(ModBlockTags.PAINTABLE_MUD_BRICK_WALLS, "mud_brick_wall");

        for (Map.Entry<TagKey<Block>, String> entry : tagMap.entrySet()) {
            if (blockState.is(entry.getKey())) {
                paint(entry.getValue(), itemStack, player, context, world, blockPos, blockState);
                return InteractionResult.sidedSuccess(world.isClientSide);
            }
        }

        if (blockState.is(BlockTags.LOGS)) {
            if (!blockID.contains("stripped")) {
                if (blockID.contains("log") || blockID.contains("stem")) {
                    paint("log", itemStack, player, context, world, blockPos, blockState);
                } else if (blockID.contains("wood") || blockID.contains("hyphae")) {
                    paint("wood", itemStack, player, context, world, blockPos, blockState);
                }
            } else {
                if (blockID.contains("log") || blockID.contains("stem")) {
                    paint("stripped_%slog", itemStack, player, context, world, blockPos, blockState);
                } else if (blockID.contains("wood") || blockID.contains("hyphae")) {
                    paint("stripped_%swood", itemStack, player, context, world, blockPos, blockState);
                }
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return InteractionResult.PASS;
    }

    public final DyeColor getColor() {
        return color;
    }
}