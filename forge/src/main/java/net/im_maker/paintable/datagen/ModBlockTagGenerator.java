package net.im_maker.paintable.datagen;

import com.ninni.dye_depot.registry.DDDyes;
import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.util.PTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Paintable.MOD_ID, existingFileHelper);
    }

    private Block block (String block, DyeColor color) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:" + color + "_painted_" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    private Block blockS (String block, DyeColor color) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:stripped_" + color + "_painted_" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    private Block block (String block, DDDyes color) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:" + color + "_painted_" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    private Block blockS (String block, DDDyes color) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:stripped_" + color + "_painted_" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    private Block blockN (String block) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        List<DyeColor> colors = new ArrayList<>(Arrays.asList(
                DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
                DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
                DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
                DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
        ));
        List<DDDyes> colors2 = new ArrayList<>();
        if (ModList.get().isLoaded("dye_depot")) {
            colors2 = new ArrayList<>(Arrays.asList(
                    DDDyes.MAROON, DDDyes.ROSE, DDDyes.CORAL, DDDyes.INDIGO,
                    DDDyes.NAVY, DDDyes.SLATE, DDDyes.CORAL, DDDyes.AMBER,
                    DDDyes.BEIGE, DDDyes.TEAL, DDDyes.MINT, DDDyes.AQUA,
                    DDDyes.VERDANT, DDDyes.FOREST, DDDyes.GINGER, DDDyes.TAN
            ));
        }
        for (DyeColor color : colors) {
            Block block = block("log", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.LOGS).add(block);
            this.tag(BlockTags.PARROTS_SPAWNABLE_ON).add(block);
            this.tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(block);
            this.tag(BlockTags.SNAPS_GOAT_HORN).add(block);
            this.tag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE).add(block);
            this.tag(PTags.Blocks.PAINTED_LOG_TAGS[color.getId()]).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("wood", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.LOGS).add(block);
            this.tag(BlockTags.PARROTS_SPAWNABLE_ON).add(block);
            this.tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(block);
            this.tag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE).add(block);
            this.tag(PTags.Blocks.PAINTED_LOG_TAGS[color.getId()]).add(block);
        }
        for (DyeColor color : colors) {
            Block block = blockS("log", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.LOGS).add(block);
            this.tag(BlockTags.PARROTS_SPAWNABLE_ON).add(block);
            this.tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(block);
            this.tag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE).add(block);
            this.tag(PTags.Blocks.PAINTED_LOG_TAGS[color.getId()]).add(block);
        }
        for (DyeColor color : colors) {
            Block block = blockS("wood", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.LOGS).add(block);
            this.tag(BlockTags.PARROTS_SPAWNABLE_ON).add(block);
            this.tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(block);
            this.tag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE).add(block);
            this.tag(PTags.Blocks.PAINTED_LOG_TAGS[color.getId()]).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("planks", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.PLANKS).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("stairs", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.WOODEN_STAIRS).add(block);
            this.tag(BlockTags.STAIRS).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("slab", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.WOODEN_SLABS).add(block);
            this.tag(BlockTags.SLABS).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("fence", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.WOODEN_FENCES).add(block);
            this.tag(BlockTags.FENCES).add(block);
            this.tag(Tags.Blocks.FENCES).add(block);
            this.tag(Tags.Blocks.FENCES_WOODEN).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("fence_gate", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.FENCE_GATES).add(block);
            this.tag(BlockTags.UNSTABLE_BOTTOM_CENTER).add(block);
            this.tag(Tags.Blocks.FENCE_GATES).add(block);
            this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("button", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.WOODEN_BUTTONS).add(block);
            this.tag(BlockTags.BUTTONS).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("pressure_plate", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(block);
            this.tag(BlockTags.PRESSURE_PLATES).add(block);
            this.tag(BlockTags.WALL_POST_OVERRIDE).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("door", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.DOORS).add(block);
            this.tag(BlockTags.WOODEN_DOORS).add(block);
        }
        for (DyeColor color : colors) {
            Block block = block("trapdoor", color);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.TRAPDOORS).add(block);
            this.tag(BlockTags.WOODEN_TRAPDOORS).add(block);
        }
        for (DyeColor color : colors) {
            Block bricks = block("bricks", color);
            Block brick_stairs = block("brick_stairs", color);
            Block brick_slab = block("brick_slab", color);
            Block brick_wall = block("brick_wall", color);
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(bricks, brick_stairs, brick_slab, brick_wall);
            this.tag(BlockTags.STAIRS).add(brick_stairs);
            this.tag(BlockTags.SLABS).add(brick_slab);
            this.tag(BlockTags.WALLS).add(brick_wall);
        }
        for (DyeColor color : colors) {
            Block mud_bricks = block("mud_bricks", color);
            Block mud_brick_stairs = block("mud_brick_stairs", color);
            Block mud_brick_slab = block("mud_brick_slab", color);
            Block mud_brick_wall = block("mud_brick_wall", color);
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mud_bricks, mud_brick_stairs, mud_brick_slab, mud_brick_wall);
            this.tag(BlockTags.STAIRS).add(mud_brick_stairs);
            this.tag(BlockTags.SLABS).add(mud_brick_slab);
            this.tag(BlockTags.WALLS).add(mud_brick_wall);
        }
        for (DyeColor color : colors) {
            Block block = blockN(color + "_paint_bucket");
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
            this.tag(PTags.Blocks.FILLED_PAINT_BUCKET).add(block);
        }
        for (DyeColor color : colors) {
            Block block = blockN(color + "_painted_sign");
            Block blockW = blockN(color + "_painted_wall_sign");
            Block block2 = blockN(color + "_painted_hanging_sign");
            Block blockW2 = blockN(color + "_painted_wall_hanging_sign");
            this.tag(BlockTags.STANDING_SIGNS).add(block);
            this.tag(BlockTags.WALL_SIGNS).add(blockW);
            this.tag(BlockTags.CEILING_HANGING_SIGNS).add(block2);
            this.tag(BlockTags.WALL_HANGING_SIGNS).add(blockW2);
            this.tag(BlockTags.SIGNS).add(block, blockW);
            this.tag(BlockTags.WALL_POST_OVERRIDE).add(block, blockW);
            this.tag(BlockTags.ALL_SIGNS).add(block, blockW, block2, blockW2);
            this.tag(BlockTags.ALL_HANGING_SIGNS).add(block2, blockW2);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block2, blockW2);
        }
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blockN("paint_bucket"));

        this.tag(PTags.Blocks.PAINT_BRUSH_WASHING_BLOCKS).add(
                Blocks.WET_SPONGE,
                Blocks.WATER,
                Blocks.WATER_CAULDRON
        );
    }
}