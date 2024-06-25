package net.im_maker.paintable.datagen;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.block.ModBlocks;
import net.im_maker.paintable.common.item.ModItems;
import net.im_maker.paintable.common.tags.ModItemTags;
import net.minecraft.client.Minecraft;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }
    
    private Item item (String item, DyeColor color) {
        ResourceLocation itemLocation = new ResourceLocation("paintable:" + color + "_painted_" + item);
        return ForgeRegistries.ITEMS.getValue(itemLocation);
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

    private Block blockN (String block) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    private void paintBrushRecipe (Consumer<FinishedRecipe> pWriter, DyeColor color, ItemLike itemLike, TagKey<Item> ing) {
        Item paintBrush = Paintable.getItemFromString(color + "_paint_brush");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, itemLike)
                .requires(paintBrush)
                .requires(ing)
                .unlockedBy(getHasName(itemLike), has(itemLike))
                .save(pWriter, itemLike.asItem() + "_from_paint_brush");
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        for (DyeColor color : DyeColor.values()) {
            Block planks = block("planks", color);
            Block stairs = block("stairs", color);
            Block slab = block("slab", color);
            Block fence = block("fence", color);
            Block fence_gate = block("fence_gate", color);
            Block button = block("button", color);
            Block pressure_plate = block("pressure_plate", color);
            Block door = block("door", color);
            Block trapdoor = block("trapdoor", color);
            Item sign = item("sign", color);
            Item hanging_sign = item("hanging_sign", color);
            Item boat = item("boat", color);
            Item chest_boat = item("chest_boat", color);
            Block log = block("log", color);
            Block wood = block("wood", color);
            Block slog = blockS("log", color);
            Block swood = blockS("wood", color);
            Block bricks = block("bricks", color);
            Block brick_stairs = block("brick_stairs", color);
            Block brick_slab = block("brick_slab", color);
            Block brick_wall = block("brick_wall", color);
            Block mud_bricks = block("mud_bricks", color);
            Block mud_brick_stairs = block("mud_brick_stairs", color);
            Block mud_brick_slab = block("mud_brick_slab", color);
            Block mud_brick_wall = block("mud_brick_wall", color);
            TagKey<Item> logs = ModItemTags.PAINTED_LOG_TAGS[color.getId()];
            planksFromLogs(pWriter, planks, logs, 4);
            woodFromLogs(pWriter, wood, log);
            woodFromLogs(pWriter, swood, slog);
            stairBuilder(stairs, Ingredient.of(planks))
                    .unlockedBy(getHasName(planks), has(planks))
                    .group("wooden_stairs")
                    .save(pWriter);
            slabBuilder(RecipeCategory.BUILDING_BLOCKS, slab, Ingredient.of(planks))
                    .unlockedBy(getHasName(planks), has(planks))
                    .group("wooden_slab")
                    .save(pWriter);
            fenceBuilder(fence, Ingredient.of(planks))
                    .unlockedBy(getHasName(planks), has(planks))
                    .group("wooden_fence")
                    .save(pWriter);
            fenceGateBuilder(fence_gate, Ingredient.of(planks))
                    .unlockedBy(getHasName(planks), has(planks))
                    .group("wooden_fence_gate")
                    .save(pWriter);
            buttonBuilder(button, Ingredient.of(planks))
                    .unlockedBy(getHasName(planks), has(planks))
                    .group("wooden_button")
                    .save(pWriter);
            pressurePlate(pWriter, pressure_plate, planks);
            doorBuilder(door, Ingredient.of(planks))
                    .unlockedBy(getHasName(planks), has(planks))
                    .group("wooden_door")
                    .save(pWriter);
            trapdoorBuilder(trapdoor, Ingredient.of(planks))
                    .unlockedBy(getHasName(planks), has(planks))
                    .group("wooden_trapdoor")
                    .save(pWriter);
            signBuilder(sign, Ingredient.of(planks))
                    .unlockedBy(getHasName(planks), has(planks))
                    .group("wooden_sign")
                    .save(pWriter);
            hangingSign(pWriter, hanging_sign, planks);
            woodenBoat(pWriter, boat, planks);
            chestBoat(pWriter, chest_boat, boat);
            stairBuilder(brick_stairs, Ingredient.of(bricks))
                    .unlockedBy(getHasName(bricks), has(bricks))
                    .group("painted_brick_stairs")
                    .save(pWriter);
            slabBuilder(RecipeCategory.BUILDING_BLOCKS, brick_slab, Ingredient.of(bricks))
                    .unlockedBy(getHasName(bricks), has(bricks))
                    .group("painted_brick_slab")
                    .save(pWriter);
            wallBuilder(RecipeCategory.BUILDING_BLOCKS, brick_wall, Ingredient.of(bricks))
                    .unlockedBy(getHasName(bricks), has(bricks))
                    .group("painted_brick_wall")
                    .save(pWriter);
            stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, brick_stairs, bricks);
            stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, brick_slab, bricks, 2);
            stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, brick_wall, bricks);
            stairBuilder(mud_brick_stairs, Ingredient.of(mud_bricks))
                    .unlockedBy(getHasName(mud_bricks), has(mud_bricks))
                    .group("painted_mud_brick_stairs")
                    .save(pWriter);
            slabBuilder(RecipeCategory.BUILDING_BLOCKS, mud_brick_slab, Ingredient.of(mud_bricks))
                    .unlockedBy(getHasName(mud_bricks), has(mud_bricks))
                    .group("painted_mud_brick_slab")
                    .save(pWriter);
            wallBuilder(RecipeCategory.BUILDING_BLOCKS, mud_brick_wall, Ingredient.of(mud_bricks))
                    .unlockedBy(getHasName(mud_bricks), has(mud_bricks))
                    .group("painted_mud_brick_wall")
                    .save(pWriter);
            stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, mud_brick_stairs, mud_bricks);
            stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, mud_brick_slab, mud_bricks, 2);
            stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, mud_brick_wall, mud_bricks);
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, blockN(color + "_paint_bucket"))
                    .pattern(" D ")
                    .pattern("DWD")
                    .pattern(" P ")
                    .define('D', Paintable.getItemFromString("minecraft", color + "_dye"))
                    .define('W', Items.WATER_BUCKET)
                    .define('P', ModBlocks.PAINT_BUCKET.get())
                    .unlockedBy(getHasName(ModBlocks.PAINT_BUCKET.get()), has(ModBlocks.PAINT_BUCKET.get()))
                    .save(pWriter);
            paintBrushRecipe(pWriter, color, planks, ItemTags.PLANKS);
            paintBrushRecipe(pWriter, color, stairs, ItemTags.WOODEN_STAIRS);
            paintBrushRecipe(pWriter, color, slab, ItemTags.WOODEN_SLABS);
            paintBrushRecipe(pWriter, color, fence, ItemTags.WOODEN_FENCES);
            paintBrushRecipe(pWriter, color, fence_gate, Tags.Items.FENCE_GATES_WOODEN);
            paintBrushRecipe(pWriter, color, button, ItemTags.WOODEN_BUTTONS);
            paintBrushRecipe(pWriter, color, pressure_plate, ItemTags.WOODEN_PRESSURE_PLATES);
            paintBrushRecipe(pWriter, color, sign, ItemTags.SIGNS);
            paintBrushRecipe(pWriter, color, hanging_sign, ItemTags.HANGING_SIGNS);
            paintBrushRecipe(pWriter, color, boat, ItemTags.BOATS);
            paintBrushRecipe(pWriter, color, chest_boat, ItemTags.CHEST_BOATS);
            paintBrushRecipe(pWriter, color, bricks, ModItemTags.PAINTABLE_BRICKS);
            paintBrushRecipe(pWriter, color, brick_stairs, ModItemTags.PAINTABLE_BRICK_STAIRS);
            paintBrushRecipe(pWriter, color, brick_slab, ModItemTags.PAINTABLE_BRICK_SLABS);
            paintBrushRecipe(pWriter, color, brick_wall, ModItemTags.PAINTABLE_BRICK_WALLS);
            paintBrushRecipe(pWriter, color, mud_bricks, ModItemTags.PAINTABLE_MUD_BRICKS);
            paintBrushRecipe(pWriter, color, mud_brick_stairs, ModItemTags.PAINTABLE_MUD_BRICK_STAIRS);
            paintBrushRecipe(pWriter, color, mud_brick_slab, ModItemTags.PAINTABLE_MUD_BRICK_SLABS);
            paintBrushRecipe(pWriter, color, mud_brick_wall, ModItemTags.PAINTABLE_MUD_BRICK_WALLS);
        }
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.PAINT_BRUSH.get())
                .pattern("FFF")
                .pattern(" I ")
                .pattern(" S ")
                .define('F', Items.FEATHER)
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.PAINT_BRUSH.get())
                .requires(ModItemTags.DIPPED_PAINT_BRUSH)
                .unlockedBy(getHasName(ModItems.PAINT_BRUSH.get()), has(ModItems.PAINT_BRUSH.get()))
                .save(pWriter, ModItems.PAINT_BRUSH.get() + "_from_dipped_paint_brush");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.PAINT_BUCKET.get())
                .pattern(" N ")
                .pattern("NBN")
                .define('N', Items.IRON_NUGGET)
                .define('B', Items.BUCKET)
                .unlockedBy(getHasName(Items.BUCKET), has(Items.BUCKET))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PAINT_BUCKET.get())
                .requires(ModItemTags.FILLED_PAINT_BUCKET)
                .unlockedBy(getHasName(ModBlocks.PAINT_BUCKET.get()), has(ModBlocks.PAINT_BUCKET.get()))
                .save(pWriter,  "paint_bucket_from_filled_paint_bucket");
    }
}