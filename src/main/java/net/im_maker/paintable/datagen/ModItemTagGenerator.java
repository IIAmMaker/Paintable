package net.im_maker.paintable.datagen;

import com.ninni.dye_depot.registry.DDDyes;
import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.tags.ModBlockTags;
import net.im_maker.paintable.common.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture,
                               CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, providerCompletableFuture, lookupCompletableFuture, Paintable.MOD_ID, existingFileHelper);
    }

    private Block block (String block, DyeColor color) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:" + color + "_painted_" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    private Block blockS (String block, DyeColor color) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:stripped_" + color + "_painted_" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    private Item item (String item) {
        ResourceLocation itemLocation = new ResourceLocation("paintable:" + "_painted_" + item);
        return ForgeRegistries.ITEMS.getValue(itemLocation);
    }

    private Item itemN (String item) {
        ResourceLocation itemLocation = new ResourceLocation("paintable:" + item);
        return ForgeRegistries.ITEMS.getValue(itemLocation);
    }

    private Block block (String block) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:" + "_painted_" + block);
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
            this.tag(ItemTags.LOGS).add(block.asItem());
            this.tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(block.asItem());
            this.tag(ModItemTags.PAINTED_LOG_TAGS[color.getId()]).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("wood", color);
            this.tag(ItemTags.LOGS).add(block.asItem());
            this.tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(block.asItem());
            this.tag(ModItemTags.PAINTED_LOG_TAGS[color.getId()]).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = blockS("log", color);
            this.tag(ItemTags.LOGS).add(block.asItem());
            this.tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(block.asItem());
            this.tag(ModItemTags.PAINTED_LOG_TAGS[color.getId()]).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = blockS("wood", color);
            this.tag(ItemTags.LOGS).add(block.asItem());
            this.tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(block.asItem());
            this.tag(ModItemTags.PAINTED_LOG_TAGS[color.getId()]).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("planks", color);
            this.tag(ItemTags.PLANKS).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("stairs", color);
            this.tag(ItemTags.WOODEN_STAIRS).add(block.asItem());
            this.tag(ItemTags.STAIRS).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("slab", color);
            this.tag(ItemTags.WOODEN_SLABS).add(block.asItem());
            this.tag(ItemTags.SLABS).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("fence", color);
            this.tag(ItemTags.WOODEN_FENCES).add(block.asItem());
            this.tag(ItemTags.FENCES).add(block.asItem());
            this.tag(Tags.Items.FENCES).add(block.asItem());
            this.tag(Tags.Items.FENCES_WOODEN).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("fence_gate", color);
            this.tag(ItemTags.FENCE_GATES).add(block.asItem());
            this.tag(Tags.Items.FENCE_GATES).add(block.asItem());
            this.tag(Tags.Items.FENCE_GATES_WOODEN).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("button", color);
            this.tag(ItemTags.WOODEN_BUTTONS).add(block.asItem());
            this.tag(ItemTags.BUTTONS).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("pressure_plate", color);
            this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("door", color);
            this.tag(ItemTags.DOORS).add(block.asItem());
            this.tag(ItemTags.WOODEN_DOORS).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block block = block("trapdoor", color);
            this.tag(ItemTags.TRAPDOORS).add(block.asItem());
            this.tag(ItemTags.WOODEN_TRAPDOORS).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Block bricks = block("bricks", color);
            Block brick_stairs = block("brick_stairs", color);
            Block brick_slab = block("brick_slab", color);
            Block brick_wall = block("brick_wall", color);
            this.tag(ModItemTags.PAINTABLE_BRICKS).add(bricks.asItem(), Blocks.BRICKS.asItem());
            this.tag(ItemTags.STAIRS).add(brick_stairs.asItem());
            this.tag(ModItemTags.PAINTABLE_BRICK_STAIRS).add(brick_stairs.asItem(), Blocks.BRICK_STAIRS.asItem());
            this.tag(ItemTags.SLABS).add(brick_slab.asItem());
            this.tag(ModItemTags.PAINTABLE_BRICK_SLABS).add(brick_slab.asItem(), Blocks.BRICK_SLAB.asItem());
            this.tag(ItemTags.WALLS).add(brick_wall.asItem());
            this.tag(ModItemTags.PAINTABLE_BRICK_WALLS).add(brick_stairs.asItem(), Blocks.BRICK_WALL.asItem());
        }
        for (DyeColor color : colors) {
            Block mud_bricks = block("mud_bricks", color);
            Block mud_brick_stairs = block("mud_brick_stairs", color);
            Block mud_brick_slab = block("mud_brick_slab", color);
            Block mud_brick_wall = block("mud_brick_wall", color);
            this.tag(ModItemTags.PAINTABLE_MUD_BRICKS).add(mud_bricks.asItem(), Blocks.MUD_BRICKS.asItem());
            this.tag(ItemTags.STAIRS).add(mud_brick_stairs.asItem());
            this.tag(ModItemTags.PAINTABLE_MUD_BRICK_STAIRS).add(mud_brick_stairs.asItem(), Blocks.MUD_BRICK_STAIRS.asItem());
            this.tag(ItemTags.SLABS).add(mud_brick_slab.asItem());
            this.tag(ModItemTags.PAINTABLE_MUD_BRICK_SLABS).add(mud_brick_slab.asItem(), Blocks.MUD_BRICK_SLAB.asItem());
            this.tag(ItemTags.WALLS).add(mud_brick_wall.asItem());
            this.tag(ModItemTags.PAINTABLE_MUD_BRICK_WALLS).add(mud_brick_wall.asItem(), Blocks.MUD_BRICK_WALL.asItem());
        }
        for (DyeColor color : colors) {
            Block concrete = Paintable.getBlockFromString("minecraft", color + "_concrete");
            this.tag(ModItemTags.PAINTABLE_CONCRETE_VANILLA).add(concrete.asItem());
        }
        if (ModList.get().isLoaded("dye_depot")) {
            for (DDDyes color : colors2) {
                Block concrete_dye_depot = Paintable.getBlockFromString("dye_depot", color + "_concrete");
                this.tag(ModItemTags.PAINTABLE_CONCRETE_DYE_DEPOT).add(concrete_dye_depot.asItem());
            }
            this.tag(ModItemTags.PAINTABLE_CONCRETE).addTags(ModItemTags.PAINTABLE_CONCRETE_VANILLA, ModItemTags.PAINTABLE_CONCRETE_DYE_DEPOT);
        } else {
            this.tag(ModItemTags.PAINTABLE_CONCRETE).addTag(ModItemTags.PAINTABLE_CONCRETE_VANILLA);
        }
        for (DyeColor color : colors) {
            Block block = blockN(color + "_paint_bucket");
            this.tag(ModItemTags.FILLED_PAINT_BUCKET).add(block.asItem());
        }
        for (DyeColor color : colors) {
            Item item = itemN(color + "_paint_brush");
            this.tag(ModItemTags.DIPPED_PAINT_BRUSH).add(item);
        }
        for (DyeColor color : colors) {
            Item item = itemN(color + "_painted_sign");
            Item item2 = itemN(color + "_painted_hanging_sign");
            this.tag(ItemTags.SIGNS).add(item);
            this.tag(ItemTags.HANGING_SIGNS).add(item2);
        }
        for (DyeColor color : colors) {
            Item item = itemN(color + "_boat");
            Item item2 = itemN(color + "_chest_boat");
            this.tag(ItemTags.BOATS).add(item, item2);
            this.tag(ItemTags.CHEST_BOATS).add(item2);
        }
    }
}