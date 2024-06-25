package net.im_maker.paintable.common.block;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.block.custom.*;
import net.im_maker.paintable.common.item.ModItems;
import net.im_maker.paintable.common.util.ModWoodTypes;
import net.minecraft.core.BlockPos;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Paintable.MOD_ID);
    //Paint Bucket
    public static final RegistryObject<Block> PAINT_BUCKET = registryBlock("paint_bucket", () -> new PaintBucketBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(0.1F, 3.0F).sound(SoundType.LANTERN)));

    public static final List<RegistryObject<Block>> FILLED_PAINT_BUCKET = registerColoredBucketBlocks("paint_bucket");
    public static final List<RegistryObject<Block>> PAINTED_LOGS = registerStrippableColoredLogs("painted_log");
    public static final List<RegistryObject<Block>> PAINTED_WOODS = registerStrippableColoredLogs("painted_wood");
    public static final List<RegistryObject<Block>> PAINTED_STRIPPED_LOGS = registerColoredLogs("painted_log");
    public static final List<RegistryObject<Block>> PAINTED_STRIPPED_WOODS = registerColoredLogs("painted_wood");
    public static final List<RegistryObject<Block>> PAINTED_PLANKS = registerColoredBlocks("painted_planks");
    public static final List<RegistryObject<Block>> PAINTED_STAIRS = registerColoredStairs("painted_stairs");
    public static final List<RegistryObject<Block>> PAINTED_SLABS = registerColoredSlabs("painted_slab");
    public static final List<RegistryObject<Block>> PAINTED_FENCES = registerColoredFences("painted_fence");
    public static final List<RegistryObject<Block>> PAINTED_FENCE_GATES = registerColoredFenceGates("painted_fence_gate");
    public static final List<RegistryObject<Block>> PAINTED_BUTTONS = registerColoredButton("painted_button");
    public static final List<RegistryObject<Block>> PAINTED_PRESSURE_PLATES = registerColoredPressurePlates("painted_pressure_plate");
    public static final List<RegistryObject<Block>> PAINTED_DOORS = registerColoredDoors("painted_door");
    public static final List<RegistryObject<Block>> PAINTED_TRAPDOORS = registerColoredTrapdoors("painted_trapdoor");
    public static final List<RegistryObject<Block>> PAINTED_SIGN = registerColoredSigns("painted_sign");
    public static final List<RegistryObject<Block>> PAINTED_WALL_SIGN = registerColoredWallSigns("painted_wall_sign");
    public static final List<RegistryObject<Block>> PAINTED_HANGING_SIGN = registerColoredHangingSigns("painted_hanging_sign");
    public static final List<RegistryObject<Block>> PAINTED_WALL_HANGING_SIGN = registerColoredWallHangingSigns("painted_wall_hanging_sign");
    public static final List<RegistryObject<Block>> PAINTED_BRICKS = registerColoredBricks("painted_bricks", 2, 6 , SoundType.STONE);
    public static final List<RegistryObject<Block>> PAINTED_BRICK_STAIRS = registerColoredBrickStairs("painted_brick_stairs", 2, 6 , SoundType.STONE);
    public static final List<RegistryObject<Block>> PAINTED_BRICK_SLABS = registerColoredBrickSlabs("painted_brick_slab", 2, 6 , SoundType.STONE);
    public static final List<RegistryObject<Block>> PAINTED_BRICK_WALL = registerColoredBrickWalls("painted_brick_wall", PAINTED_BRICKS);
    public static final List<RegistryObject<Block>> PAINTED_MUD_BRICKS = registerColoredBricks("painted_mud_bricks", 1, 3 , SoundType.MUD_BRICKS);
    public static final List<RegistryObject<Block>> PAINTED_MUD_BRICK_STAIRS = registerColoredBrickStairs("painted_mud_brick_stairs", 1, 3 , SoundType.MUD_BRICKS);
    public static final List<RegistryObject<Block>> PAINTED_MUD_BRICK_SLABS = registerColoredBrickSlabs("painted_mud_brick_slab", 1, 3 , SoundType.MUD_BRICKS);
    public static final List<RegistryObject<Block>> PAINTED_MUD_BRICK_WALL = registerColoredBrickWalls("painted_mud_brick_wall", PAINTED_MUD_BRICKS);

    private static <T extends Block> List<RegistryObject<T>> registerColoredBucketBlocks(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new FilledPaintBucketBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(0.1F, 4.0F).sound(SoundType.LANTERN)));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredBlocks(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new Block(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredStairs(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            ResourceLocation paintedPlanksLocation = new ResourceLocation("paintable:" + color + "_painted_planks");
            Block paintedPlanks = ForgeRegistries.BLOCKS.getValue(paintedPlanksLocation);
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new StairBlock(() -> paintedPlanks.defaultBlockState(), BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredSlabs(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredFences(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredFenceGates(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava(), ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredButton(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(SoundType.WOOD), ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()].setType(), 10, true));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredPressurePlates(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(color.getMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()].setType()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredDoors(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()].setType()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredTrapdoors(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(ModBlocks::never).ignitedByLava(), ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()].setType()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredBricks(String blockName, float DT, float ET, SoundType soundType) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new Block(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).strength(DT, ET).sound(soundType).requiresCorrectToolForDrops()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredBrickStairs(String blockName, float DT, float ET, SoundType soundType) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            ResourceLocation paintedBricksLocation = new ResourceLocation("paintable:" + color + "_painted_bricks");
            Block paintedBricks = ForgeRegistries.BLOCKS.getValue(paintedBricksLocation);
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new StairBlock(() -> paintedBricks.defaultBlockState(), BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).strength(DT, ET).sound(soundType).requiresCorrectToolForDrops()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredBrickSlabs(String blockName, float DT, float ET, SoundType soundType) {
    List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
        String blockId = color.getName() + "_" + blockName;
        RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).instrument(NoteBlockInstrument.BASEDRUM).strength(DT, ET).sound(soundType).requiresCorrectToolForDrops()));
        registerBlockItem(blockId, block);
        coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredBrickWalls(String blockName, List<RegistryObject<Block>> copyBlock) {
    List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
        String blockId = color.getName() + "_" + blockName;
        RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new WallBlock(BlockBehaviour.Properties.copy(copyBlock.get(color.getId()).get()).forceSolidOn()));
        registerBlockItem(blockId, block);
        coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerStrippableColoredLogs(String blockName) {
    List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
        String blockId = color.getName() + "_" + blockName;
        RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new StrippableRotatedPillarBlock(color, BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 2.0F).sound(SoundType.WOOD)));
        registerBlockItem(blockId, block);
        coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredLogs(String blockName) {
    List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
        String blockId = "stripped_" + color.getName() + "_" + blockName;
        RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(color.getMapColor()).strength(2.0F, 2.0F).sound(SoundType.WOOD)));
        registerBlockItem(blockId, block);
        coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredSigns(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredWallSigns(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredHangingSigns(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> List<RegistryObject<T>> registerColoredWallHangingSigns(String blockName) {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_" + blockName;
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()]));
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    private static <T extends Block> RegistryObject<T> registryBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    };

    private static <T extends Block>  RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){

        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
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

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
