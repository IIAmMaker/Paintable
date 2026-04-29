package net.im_maker.paintable.common.item.custom;

import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.util.PTags;
import net.im_maker.paintable.config.PaintableConfig;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
import net.minecraft.world.item.TooltipFlag;
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
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DippedPaintBrushItem extends Item {
    private static final String NBT_PAINT = "paint";
    private final DyeColor color;

    public DippedPaintBrushItem(DyeColor paintColor, Properties properties) {
        super(properties);
        this.color = paintColor;
    }

    private int getPaintMax() {
        return PaintableConfig.PAINT_LIMIT.get();
    }

    private void ensurePaintInitialized(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(NBT_PAINT)) {
            setPaintCount(stack, getPaintMax());
        }
    }

    public int getPaintLeft(ItemStack stack) {
        ensurePaintInitialized(stack);
        return stack.getOrCreateTag().getInt(NBT_PAINT);
    }

    public void setPaintCount(ItemStack stack, int count) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(NBT_PAINT, Math.max(0, Math.min(count, getPaintMax())));
        stack.setTag(tag);
    }

    private boolean decrementOrReplace(ItemStack stack, Player player, UseOnContext ctx, Level world) {
        if (player.isCreative()) return false;
        int left = getPaintLeft(stack);
        if (left > 1) {
            setPaintCount(stack, left - 1);
            return false;
        } else {
            player.setItemInHand(ctx.getHand(), new ItemStack(PItems.PAINT_BRUSH.get()));
            world.playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            return true;
        }
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack s = super.getDefaultInstance();
        setPaintCount(s, getPaintMax());
        return s;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltips, TooltipFlag tooltipFlag) {
        int current = getPaintLeft(itemStack);
        tooltips.add(
                Component.literal("Paint Left: " + current + "/" + getPaintMax())
                        .withStyle(Style.EMPTY.withColor(color.getMapColor().col))
        );
        super.appendHoverText(itemStack, level, tooltips, tooltipFlag);
    }

    private void paint(String blockName, ItemStack itemStack, Player player, UseOnContext context, Level world, BlockPos blockPos, BlockState blockState) {
        paint(blockName, "", "paintable", itemStack, player, context, world, blockPos, blockState, true, true);
    }

    private void paint(String blockName, String prefix, String blockNameSpace, ItemStack itemStack, Player player, UseOnContext context, Level world, BlockPos blockPos, BlockState blockState, boolean t, boolean canRepaint) {

        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, itemStack);
        }

        decrementOrReplace(itemStack, player, context, world);

        BlockPos otherPos = blockPos;
        BlockState otherState = blockState;
        if (blockState.getBlock() instanceof BedBlock && canRepaint) {
            otherPos = handleBed(blockPos, blockState, world);
            otherState = world.getBlockState(otherPos);
        } else if (blockState.getBlock() instanceof DoorBlock && canRepaint) {
            otherPos = handleDoor(blockPos, blockState, world);
            otherState = world.getBlockState(otherPos);
        }

        ResourceLocation blockLocation = t ?
                new ResourceLocation(blockNameSpace + ":" + (blockName.contains("%s") ? String.format(blockName, color + "_painted_") : color + "_painted_" + blockName)) :
                new ResourceLocation(blockNameSpace + ":" + prefix + color + blockName);
        Block block = BuiltInRegistries.BLOCK.get(blockLocation);
        if (block != null) {
            if (!blockState.hasBlockEntity()) {
                world.setBlock(otherPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_KNOWN_SHAPE);
                world.setBlockAndUpdate(blockPos, block.withPropertiesOf(blockState));
                if (!otherPos.equals(blockPos)) world.setBlockAndUpdate(otherPos, block.withPropertiesOf(otherState));
            } else {
                BlockEntity be = world.getBlockEntity(blockPos);
                if (be != null) {
                    CompoundTag data = be.saveWithFullMetadata();
                    world.setBlock(otherPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_KNOWN_SHAPE);
                    world.setBlockAndUpdate(blockPos, block.withPropertiesOf(blockState));
                    if (!otherPos.equals(blockPos)) world.setBlockAndUpdate(otherPos, block.withPropertiesOf(otherState));
                    BlockEntity newBe = world.getBlockEntity(blockPos);
                    if (newBe != null) {
                        newBe.load(data);
                        world.setBlockEntity(newBe);
                    }
                }
            }
            world.playSound(null, blockPos, SoundEvents.MUD_PLACE, SoundSource.PLAYERS, 1.0F, 0.7F);
        }
    }

    private BlockPos handleBed(BlockPos pos, BlockState state, Level world) {
        Direction d = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BedPart p = state.getValue(BlockStateProperties.BED_PART);
        return p == BedPart.FOOT ? pos.relative(d) : pos.relative(d.getOpposite());
    }

    private BlockPos handleDoor(BlockPos pos, BlockState state, Level world) {
        DoubleBlockHalf half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
        return half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
    }

    private void cleanPaintBrush(Level world, BlockPos blockpos, UseOnContext context) {
        context.getPlayer().setItemInHand(context.getHand(), new ItemStack(PItems.PAINT_BRUSH.get()));
        world.playSound((Player) null, blockpos, SoundEvents.MUD_HIT, SoundSource.PLAYERS, 1.0F, 0.7F);
    }

    private boolean cantPaint(String block) {
        boolean flag1 = block.contains(":" + color + "_") || block.contains("/" + color + "_") || block.contains("_" + color) || block.contains("_" + color + "_");
        boolean flag2 = block.contains(":light_" + color + "_") || block.contains("/light_" + color + "_") || block.contains("light_" + color) || block.contains("_light_" + color + "_");
        boolean i = block.contains("light_" + color);
        return !i ? flag1 : !flag2;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);
        ItemStack itemStack = context.getItemInHand();
        Player player = context.getPlayer();
        String blockID = BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).toString();

        if (cantPaint(blockID) || ((blockState.is(PTags.Blocks.TOGGLEABLE) && !PaintableConfig.CAN_PAINT.get())))
            return InteractionResult.PASS;

        if (blockState.is(PTags.Blocks.PAINT_BRUSH_WASHING_BLOCKS)) {
            cleanPaintBrush(world, blockPos, context);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        System.out.println(blockID);
        System.out.println(blockState.getBlock().asItem());

        for (TagKey<Block> tag : blockState.getTags().toList()) {
            ResourceLocation tagLocation = tag.location();
            String path = tagLocation.getPath();
            if (path.contains("paintable/") && !path.contains("paintable/toggleable")) {
                String blockName = "";
                String prefix = "";
                if (path.contains("__")) {
                    String[] parts = path.split("__", 2);
                    prefix = parts[0].replace("paintable/", "");
                    blockName = parts[1].replace("paintable/", "");
                } else if (path.endsWith("_")) {
                    String base = path.replace("paintable/", "");
                    int lastUnderscore = base.lastIndexOf("_");
                    if (lastUnderscore != -1) {
                        prefix = base.substring(0, lastUnderscore);
                        blockName = "";
                    }
                } else {
                    prefix = "";
                    blockName = path.replace("paintable/", "");
                }

                blockName = blockName.equals("") ? "" : "_" + blockName;
                prefix = prefix.equals("") ? "" : prefix + "_";

                String nameSpace = tagLocation.getNamespace();
                TagKey<Block> blockTag = TagKey.create(Registries.BLOCK, tagLocation);

                List<Block> blocks = BuiltInRegistries.BLOCK.getTag(blockTag).stream().flatMap(holder -> holder.stream()).map(Holder::value).toList();
                System.out.println(blockName);

                boolean foundPaintable = false;
                for (Block b : blocks) {
                    ResourceLocation id = BuiltInRegistries.BLOCK.getKey(b);
                    if (id != null && id.getPath().contains("magenta")) {
                        nameSpace = id.getNamespace();
                        Block blockTest = BuiltInRegistries.BLOCK.get(new ResourceLocation(nameSpace, prefix + color + blockName));

                        if (blockTest != Blocks.AIR) {
                            foundPaintable = true;
                            break;
                        }
                    } else if (id != null && id.getPath().contains("maroon")) {
                        nameSpace = id.getNamespace();
                        foundPaintable = true;
                        break;
                    }
                }

                if (!foundPaintable) return InteractionResult.PASS;

                Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(nameSpace, prefix + color + blockName));
                if (block == null || block == Blocks.AIR) return InteractionResult.PASS;

                paint(blockName, prefix, nameSpace, itemStack, player, context, world, blockPos, blockState, false, true);
                return InteractionResult.sidedSuccess(world.isClientSide);
            }
        }

        Map<TagKey<Block>, String> tagMap = new HashMap<>();

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