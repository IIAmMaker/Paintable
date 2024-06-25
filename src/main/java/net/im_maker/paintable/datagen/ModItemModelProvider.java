package net.im_maker.paintable.datagen;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Paintable.MOD_ID, existingFileHelper);
    }

    private RegistryObject<Block> block (String block) {
        ResourceLocation blockLocation = new ResourceLocation(Paintable.MOD_ID, block);
        return RegistryObject.create(blockLocation, ForgeRegistries.BLOCKS);
    }

    private RegistryObject<Block> block (DyeColor color, String block) {
        ResourceLocation blockLocation = new ResourceLocation(Paintable.MOD_ID, color + "_painted_" + block);
        return RegistryObject.create(blockLocation, ForgeRegistries.BLOCKS);
    }

    private RegistryObject<Item> item (DyeColor color, String item) {
        ResourceLocation itemLocation = new ResourceLocation(Paintable.MOD_ID, color + "_painted_" + item);
        return RegistryObject.create(itemLocation, ForgeRegistries.ITEMS);
    }

    private RegistryObject<Block> blockSSS (DyeColor color, String block) {
        ResourceLocation blockLocation = new ResourceLocation(Paintable.MOD_ID, "stripped_" + color + "_painted_" + block);
        return RegistryObject.create(blockLocation, ForgeRegistries.BLOCKS);
    }

    @Override
    protected void registerModels() {
        List<DyeColor> colors = new ArrayList<>(Arrays.asList(
                DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
                DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
                DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
                DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
        ));
        handheldItem(ModItems.PAINT_BRUSH);
        simpleBlockItem(block("paint_bucket"));
        for (DyeColor color : colors) {
            ResourceLocation paint_brushLocation = new ResourceLocation(Paintable.MOD_ID, color + "_paint_brush");
            RegistryObject<Item> paint_brush = RegistryObject.create(paint_brushLocation, ForgeRegistries.ITEMS);
            handheldItem(paint_brush);
            simpleItem(item(color, "boat"));
            simpleItem(item(color, "chest_boat"));
            evenSimplerBlockItem(block(color, "log"));
            evenSimplerBlockItem(block(color, "wood"));
            evenSimplerBlockItem(blockSSS(color, "log"));
            evenSimplerBlockItem(blockSSS(color, "wood"));
            evenSimplerBlockItem(block(color, "planks"));
            evenSimplerBlockItem(block(color, "stairs"));
            evenSimplerBlockItem(block(color, "slab"));
            fenceItem(block(color, "fence"), block(color, "planks"));
            evenSimplerBlockItem(block(color, "fence_gate"));
            buttonItem(block(color, "button"), block(color, "planks"));
            evenSimplerBlockItem(block(color, "pressure_plate"));
            simpleBlockItem(block(color, "door"));
            trapdoorItem(block(color, "trapdoor"));
            simpleItem(item(color, "sign"));
            simpleItem(item(color, "hanging_sign"));
            evenSimplerBlockItem(block(color, "bricks"));
            evenSimplerBlockItem(block(color, "brick_stairs"));
            evenSimplerBlockItem(block(color, "brick_slab"));
            wallItem(block(color, "brick_wall"), block(color, "bricks"));
            evenSimplerBlockItem(block(color, "mud_bricks"));
            evenSimplerBlockItem(block(color, "mud_brick_stairs"));
            evenSimplerBlockItem(block(color, "mud_brick_slab"));
            wallItem(block(color, "mud_brick_wall"), block(color, "mud_bricks"));
            simpleBlockItem(block(color + "_paint_bucket"));
        }
        //handheldItem(ModItems.SAPPHIRE_SWORD);

        //simpleBlockItemBlockTexture(ModBlocks.CATMINT);

        //withExistingParent(ModItems.RHINO_SPANW_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }



    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Paintable.MOD_ID,"item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(Paintable.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(Paintable.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(Paintable.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(Paintable.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Paintable.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Paintable.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Paintable.MOD_ID,"block/" + item.getId().getPath()));
    }
}