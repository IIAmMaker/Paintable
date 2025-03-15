package net.im_maker.paintable.common.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.block.custom.*;
import net.im_maker.paintable.common.util.PWoodTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.List;

public class PBlocks {
    //Paint Bucket
    public static final Block PAINT_BUCKET = registryBlock("paint_bucket", new PaintBucketBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(0.1F, 3.0F).sound(SoundType.LANTERN)));

    public static final List<Block> FILLED_PAINT_BUCKET = registerColoredBucketBlocks("paint_bucket");
    public static final List<Block> PAINTED_LOGS = registerStrippableColoredLogs("painted_log");
    public static final List<Block> PAINTED_WOODS = registerStrippableColoredLogs("painted_wood");
    public static final List<Block> PAINTED_STRIPPED_LOGS = registerColoredLogs("painted_log");
    public static final List<Block> PAINTED_STRIPPED_WOODS = registerColoredLogs("painted_wood");
    public static final List<Block> PAINTED_PLANKS = registerColoredBlocks("painted_planks");
    public static final List<Block> PAINTED_STAIRS = registerColoredStairs("painted_stairs");
    public static final List<Block> PAINTED_SLABS = registerColoredSlabs("painted_slab");
    public static final List<Block> PAINTED_FENCES = registerColoredFences("painted_fence");
    public static final List<Block> PAINTED_FENCE_GATES = registerColoredFenceGates("painted_fence_gate");
    public static final List<Block> PAINTED_BUTTONS = registerColoredButton("painted_button");
    public static final List<Block> PAINTED_PRESSURE_PLATES = registerColoredPressurePlates("painted_pressure_plate");
    public static final List<Block> PAINTED_DOORS = registerColoredDoors("painted_door");
    public static final List<Block> PAINTED_TRAPDOORS = registerColoredTrapdoors("painted_trapdoor");
    public static final List<Block> PAINTED_SIGN = registerColoredSigns("painted_sign");
    public static final List<Block> PAINTED_WALL_SIGN = registerColoredWallSigns("painted_wall_sign");
    public static final List<Block> PAINTED_HANGING_SIGN = registerColoredHangingSigns("painted_hanging_sign");
    public static final List<Block> PAINTED_WALL_HANGING_SIGN = registerColoredWallHangingSigns("painted_wall_hanging_sign");
    public static final List<Block> PAINTED_BRICKS = registerColoredBricks("painted_bricks", 2, 6 , SoundType.STONE);
    public static final List<Block> PAINTED_BRICK_STAIRS = registerColoredBrickStairs("painted_brick_stairs", 2, 6 , SoundType.STONE);
    public static final List<Block> PAINTED_BRICK_SLABS = registerColoredBrickSlabs("painted_brick_slab", 2, 6 , SoundType.STONE);
    public static final List<Block> PAINTED_BRICK_WALL = registerColoredBrickWalls("painted_brick_wall", PAINTED_BRICKS);
    public static final List<Block> PAINTED_MUD_BRICKS = registerColoredBricks("painted_mud_bricks", 1, 3 , SoundType.MUD_BRICKS);
    public static final List<Block> PAINTED_MUD_BRICK_STAIRS = registerColoredBrickStairs("painted_mud_brick_stairs", 1, 3 , SoundType.MUD_BRICKS);
    public static final List<Block> PAINTED_MUD_BRICK_SLABS = registerColoredBrickSlabs("painted_mud_brick_slab", 1, 3 , SoundType.MUD_BRICKS);
    public static final List<Block> PAINTED_MUD_BRICK_WALL = registerColoredBrickWalls("painted_mud_brick_wall", PAINTED_MUD_BRICKS);

    private static List<Block> registerColoredBucketBlocks(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new FilledPaintBucketBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(0.1F, 4.0F).sound(SoundType.LANTERN)));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredBlocks(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new Block(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredStairs(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            ResourceLocation paintedPlanksLocation = new ResourceLocation("paintable:" + color + "_painted_planks");
            Block paintedPlanks = BuiltInRegistries.BLOCK.get(paintedPlanksLocation);
            Block block = registryBlock(blockId, new StairBlock(paintedPlanks.defaultBlockState(), BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredSlabs(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new SlabBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredFences(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new FenceBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredFenceGates(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava(), PWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredButton(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(SoundType.WOOD), PWoodTypes.PAINTED_WOOD_TYPES[color.getId()].setType(), 10, true));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredPressurePlates(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(color.getMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), PWoodTypes.PAINTED_WOOD_TYPES[color.getId()].setType()));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredDoors(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new DoorBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), PWoodTypes.PAINTED_WOOD_TYPES[color.getId()].setType()));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredTrapdoors(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(PBlocks::never).ignitedByLava(), PWoodTypes.PAINTED_WOOD_TYPES[color.getId()].setType()));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredBricks(String blockName, float DT, float ET, SoundType soundType) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlock(blockId, new Block(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).strength(DT, ET).sound(soundType).requiresCorrectToolForDrops()));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredBrickStairs(String blockName, float DT, float ET, SoundType soundType) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            ResourceLocation paintedBricksLocation = new ResourceLocation("paintable:" + color + "_painted_bricks");
            Block paintedBricks = BuiltInRegistries.BLOCK.get(paintedBricksLocation);
            Block block = registryBlock(blockId, new StairBlock(paintedBricks.defaultBlockState(), BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).strength(DT, ET).sound(soundType).requiresCorrectToolForDrops()));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredBrickSlabs(String blockName, float DT, float ET, SoundType soundType) {
    List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
        String blockId = color.getName() + "_" + blockName;
        Block block = registryBlock(blockId, new SlabBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).strength(DT, ET).sound(soundType).requiresCorrectToolForDrops()));
        coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredBrickWalls(String blockName, List<Block> copyBlock) {
    List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
        String blockId = color.getName() + "_" + blockName;
        Block block = registryBlock(blockId, new WallBlock(BlockBehaviour.Properties.copy(copyBlock.get(color.getId())).forceSolidOn()));
        coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerStrippableColoredLogs(String blockName) {
    List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
        String blockId = color.getName() + "_" + blockName;
        Block block = registryBlock(blockId, new StrippableLogBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 2.0F).sound(SoundType.WOOD)));
        coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredLogs(String blockName) {
    List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
        String blockId = "stripped_" + color.getName() + "_" + blockName;
        Block block = registryBlock(blockId, new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 2.0F).sound(SoundType.WOOD)));
        coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredSigns(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlockWithoutItem(blockId, new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), PWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredWallSigns(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlockWithoutItem(blockId, new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), PWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredHangingSigns(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlockWithoutItem(blockId, new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), PWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static List<Block> registerColoredWallHangingSigns(String blockName) {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            Block block = registryBlockWithoutItem(blockId, new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), PWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static Block registryBlockWithoutItem(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Paintable.MOD_ID, name), block);
    }

    private static Block registryBlock(String name, Block block) {
        registryBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Paintable.MOD_ID, name), block);
    }

    private static Item registryBlockItem(String name, Block block) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Paintable.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    private static ButtonBlock woodenButton(BlockSetType pSetType, FeatureFlag... pRequiredFeatures) {
        BlockBehaviour.Properties blockbehaviour$properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (pRequiredFeatures.length > 0) {
            blockbehaviour$properties = blockbehaviour$properties.requiredFeatures(pRequiredFeatures);
        }

        return new ButtonBlock(blockbehaviour$properties, pSetType, 30, true);
    }

    private static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }

    public static void  registerBlocks() {
        Paintable.LOGGER.info("Registering Mod Blocks for " + Paintable.MOD_ID);
    }
}
