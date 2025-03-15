package net.im_maker.paintable.datagen;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.block.ModBlocks;
import net.im_maker.paintable.common.block.custom.FilledPaintBucketBlock;
import net.im_maker.paintable.common.block.custom.PaintBucketBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Paintable.MOD_ID, exFileHelper);
    }

    private Block block (DyeColor color, String block) {
        ResourceLocation blockLocation = new ResourceLocation(Paintable.MOD_ID, color + "_painted_" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }
    private ResourceLocation blockR (DyeColor color, String block) {
        return new ResourceLocation(Paintable.MOD_ID, "block/" + color + "_painted_" + block);
    }
    private RegistryObject<Block> blockO (DyeColor color, String block) {
        ResourceLocation blockLocation = new ResourceLocation(Paintable.MOD_ID, color + "_painted_" + block);
        return RegistryObject.create(blockLocation, ForgeRegistries.BLOCKS);
    }
    private Block  blockOSSS (DyeColor color, String block) {
        ResourceLocation blockLocation = new ResourceLocation(Paintable.MOD_ID, "stripped_" + color + "_painted_" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    private void strippedLogBlock(RotatedPillarBlock block, RotatedPillarBlock baseName) {
        axisBlock(block, blockTexture(baseName), blockTexture(baseName));
    }

    private void filledPaintBucketBlock(Block b, String texture) {
        ResourceLocation paint_bucket = new ResourceLocation(Paintable.MOD_ID, "block/" + texture);
        for (int i = 0; i <= 4; i++) {
            String num = String.valueOf(i);
            String p = "";
            if (i!=0) {
                p = "_level" + num;
            }
            ConfiguredModel eModel = ConfiguredModel.builder()
                    .modelFile(models().withExistingParent("block/paint_bucket/level" + num + "/" + name(b) + "_level" + num, new ResourceLocation(Paintable.MOD_ID, "block/template_paint_bucket" + p))
                            .texture("paint_bucket", paint_bucket))
                    .buildLast();
            ConfiguredModel nModel = ConfiguredModel.builder()
                    .modelFile(models().withExistingParent("block/paint_bucket/level" + num + "/" + name(b) + "_level" + num, new ResourceLocation(Paintable.MOD_ID, "block/template_paint_bucket" + p))
                            .texture("paint_bucket", paint_bucket))
                    .rotationY(270)
                    .buildLast();
            ConfiguredModel sModel = ConfiguredModel.builder()
                    .modelFile(models().withExistingParent("block/paint_bucket/level" + num + "/" + name(b) + "_level" + num, new ResourceLocation(Paintable.MOD_ID, "block/template_paint_bucket" + p))
                            .texture("paint_bucket", paint_bucket))
                    .rotationY(90)
                    .buildLast();
            ConfiguredModel wModel = ConfiguredModel.builder()
                    .modelFile(models().withExistingParent("block/paint_bucket/level" + num + "/" + name(b) + "_level" + num, new ResourceLocation(Paintable.MOD_ID, "block/template_paint_bucket" + p))
                            .texture("paint_bucket", paint_bucket))
                    .rotationY(180)
                    .buildLast();
            getVariantBuilder(b)
                    .partialState().with(FilledPaintBucketBlock.FACING, Direction.EAST).with(FilledPaintBucketBlock.LEVEL_PAINT, i).setModels(eModel)
                    .partialState().with(FilledPaintBucketBlock.FACING, Direction.NORTH).with(FilledPaintBucketBlock.LEVEL_PAINT, i).setModels(nModel)
                    .partialState().with(FilledPaintBucketBlock.FACING, Direction.SOUTH).with(FilledPaintBucketBlock.LEVEL_PAINT, i).setModels(sModel)
                    .partialState().with(FilledPaintBucketBlock.FACING, Direction.WEST).with(FilledPaintBucketBlock.LEVEL_PAINT, i).setModels(wModel);
        }
    }

    private void paintBucketBlock(Block b, String texture) {
        ResourceLocation paint_bucket = new ResourceLocation(Paintable.MOD_ID, "block/" + texture);
        ConfiguredModel eModel = ConfiguredModel.builder()
                .modelFile(models().withExistingParent("block/paint_bucket/" + name(b), new ResourceLocation(Paintable.MOD_ID, "block/template_paint_bucket"))
                        .texture("paint_bucket", paint_bucket))
                .buildLast();
        ConfiguredModel nModel = ConfiguredModel.builder()
                .modelFile(models().withExistingParent("block/paint_bucket/" + name(b), new ResourceLocation(Paintable.MOD_ID, "block/template_paint_bucket"))
                        .texture("paint_bucket", paint_bucket))
                .rotationY(270)
                .buildLast();
        ConfiguredModel sModel = ConfiguredModel.builder()
                .modelFile(models().withExistingParent("block/paint_bucket/" + name(b), new ResourceLocation(Paintable.MOD_ID, "block/template_paint_bucket"))
                        .texture("paint_bucket", paint_bucket))
                .rotationY(90)
                .buildLast();
        ConfiguredModel wModel = ConfiguredModel.builder()
                .modelFile(models().withExistingParent("block/paint_bucket/" + name(b), new ResourceLocation(Paintable.MOD_ID, "block/template_paint_bucket"))
                        .texture("paint_bucket", paint_bucket))
                .rotationY(180)
                .buildLast();
        getVariantBuilder(b)
                .partialState().with(PaintBucketBlock.FACING, Direction.EAST).setModels(eModel)
                .partialState().with(PaintBucketBlock.FACING, Direction.NORTH).setModels(nModel)
                .partialState().with(PaintBucketBlock.FACING, Direction.SOUTH).setModels(sModel)
                .partialState().with(PaintBucketBlock.FACING, Direction.WEST).setModels(wModel);
    }

    @Override
    protected void registerStatesAndModels() {
        for (DyeColor color : DyeColor.values()) {
            logBlock((RotatedPillarBlock) block(color, "log"));
            strippedLogBlock((RotatedPillarBlock) block(color, "wood"), (RotatedPillarBlock) block(color, "log"));
            logBlock((RotatedPillarBlock) blockOSSS(color, "log"));
            strippedLogBlock((RotatedPillarBlock) blockOSSS(color, "wood"), (RotatedPillarBlock) blockOSSS(color, "log"));
            blockWithItem(blockO(color, "planks"));
            stairsBlock(((StairBlock) block(color, "stairs")), blockR(color, "planks"));
            slabBlock(((SlabBlock) block(color, "slab")), blockR(color, "planks"), blockR(color, "planks"));
            fenceBlock(((FenceBlock) block(color, "fence")), blockR(color, "planks"));
            fenceGateBlock(((FenceGateBlock) block(color, "fence_gate")), blockR(color, "planks"));
            buttonBlock(((ButtonBlock) block(color, "button")), blockR(color, "planks"));
            pressurePlateBlock(((PressurePlateBlock) block(color, "pressure_plate")), blockR(color, "planks"));
            doorBlock(((DoorBlock) block(color, "door")), modLoc("block/" + color + "_painted_door_bottom"), modLoc("block/" + color + "_painted_door_top"));
            trapdoorBlock(((TrapDoorBlock) block(color, "trapdoor")), modLoc("block/" + color + "_painted_trapdoor"), true);
            blockWithItem(blockO(color, "bricks"));
            stairsBlock(((StairBlock) block(color, "brick_stairs")), blockR(color, "bricks"));
            slabBlock(((SlabBlock) block(color, "brick_slab")), blockR(color, "bricks"), blockR(color, "bricks"));
            wallBlock(((WallBlock) block(color, "brick_wall")), blockR(color, "bricks"));
            blockWithItem(blockO(color, "mud_bricks"));
            stairsBlock(((StairBlock) block(color, "mud_brick_stairs")), blockR(color, "mud_bricks"));
            slabBlock(((SlabBlock) block(color, "mud_brick_slab")), blockR(color, "mud_bricks"), blockR(color, "mud_bricks"));
            wallBlock(((WallBlock) block(color, "mud_brick_wall")), blockR(color, "mud_bricks"));
            filledPaintBucketBlock(Paintable.getBlockFromString(color + "_paint_bucket"), color + "_paint_bucket");
            signBlock((StandingSignBlock) block(color, "sign"), (WallSignBlock) block(color, "wall_sign"), blockTexture(block(color, "planks")));
            hangingSignBlock(ModBlocks.PAINTED_HANGING_SIGN.get(color.getId()).get(), ModBlocks.PAINTED_WALL_HANGING_SIGN.get(color.getId()).get(), blockTexture(blockOSSS(color, "log")));
        }
        paintBucketBlock(Paintable.getBlockFromString("paint_bucket"), "paint_bucket");
    }

    private void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    private void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}