package net.im_maker.paintable.common.item;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.block.ModBlocks;
import net.im_maker.paintable.common.entity.custom.ModBoatEntity;
import net.im_maker.paintable.common.item.custom.DippedPaintBrushItem;
import net.im_maker.paintable.common.item.custom.ModBoatItem;
import net.im_maker.paintable.common.item.custom.PaintBrushItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Paintable.MOD_ID);
    public static final RegistryObject<Item> PAINT_BRUSH = ITEMS.register("paint_brush", () -> new PaintBrushItem(new Item.Properties().stacksTo(1)));
    public static final List<RegistryObject<Item>> DIPPED_PAINT_BRUSH = registerColoredPaintBrushes();
    public static final List<RegistryObject<Item>> PAINTED_SIGNS = registerColoredSigns();
    public static final List<RegistryObject<Item>> PAINTED_HANGING_SIGNS = registerColoredHangingSigns();
    public static final List<RegistryObject<Item>> PAINTED_BOATS = registerColoredBoat();
    public static final List<RegistryObject<Item>> PAINTED_CHEST_BOATS = registerColoredChestBoat();

    private static <T extends Item> List<RegistryObject<T>> registerColoredPaintBrushes() {
        List<RegistryObject<T>> coloredItems = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String itemID = color.getName() + "_" + "paint_brush";
            RegistryObject<T> item = (RegistryObject<T>) ITEMS.register(itemID, () -> new DippedPaintBrushItem(color, new Item.Properties().stacksTo(1).durability(16)));
            coloredItems.add(item);
        }
        return coloredItems;
    }

    private static <T extends Item> List<RegistryObject<T>> registerColoredSigns() {
        List<RegistryObject<T>> coloredItems = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String itemID = color.getName() + "_" + "painted_sign";
            ResourceLocation paintedSignLocation = new ResourceLocation(Paintable.MOD_ID, color.getName() + "_painted_sign");
            Block paintedSign = ForgeRegistries.BLOCKS.getValue(paintedSignLocation);
            ResourceLocation paintedWallSignLocation = new ResourceLocation(Paintable.MOD_ID, color.getName() + "_painted_wall_sign");
            Block paintedWallSign = ForgeRegistries.BLOCKS.getValue(paintedWallSignLocation);
            RegistryObject<T> item = (RegistryObject<T>) ITEMS.register(itemID, () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.PAINTED_SIGN.get(color.getId()).get(), ModBlocks.PAINTED_WALL_SIGN.get(color.getId()).get()));
            coloredItems.add(item);
        }
        return coloredItems;
    }

    private static <T extends Item> List<RegistryObject<T>> registerColoredHangingSigns() {
        List<RegistryObject<T>> coloredItems = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String itemID = color.getName() + "_" + "painted_hanging_sign";
            ResourceLocation paintedHangingSignLocation = new ResourceLocation(Paintable.MOD_ID, color.getName() + "_painted_hanging_sign");
            Block paintedHangingSign = ForgeRegistries.BLOCKS.getValue(paintedHangingSignLocation);
            ResourceLocation paintedWallHangingSignLocation = new ResourceLocation(Paintable.MOD_ID, color.getName() + "_painted_wall_hanging_sign");
            Block paintedWallHangingSign = ForgeRegistries.BLOCKS.getValue(paintedWallHangingSignLocation);
            RegistryObject<T> item = (RegistryObject<T>) ITEMS.register(itemID, () -> new HangingSignItem(ModBlocks.PAINTED_HANGING_SIGN.get(color.getId()).get(), ModBlocks.PAINTED_WALL_HANGING_SIGN.get(color.getId()).get(), new Item.Properties().stacksTo(16)));
            coloredItems.add(item);
        }
        return coloredItems;
    }

    private static <T extends Item> List<RegistryObject<T>> registerColoredBoat() {
        List<RegistryObject<T>> coloredItems = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String itemID = color.getName() + "_" + "painted_boat";
            RegistryObject<T> item;
            if (color.getId() < 16) {
                 item = (RegistryObject<T>) ITEMS.register(itemID, () -> new ModBoatItem(false, ModBoatEntity.Type.byId(color.getId()), new Item.Properties()));
            } else {
                item = (RegistryObject<T>) ITEMS.register(itemID, () -> new Item(new Item.Properties()));
            }
            coloredItems.add(item);
        }
        return coloredItems;
    }

    private static <T extends Item> List<RegistryObject<T>> registerColoredChestBoat() {
        List<RegistryObject<T>> coloredItems = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String itemID = color.getName() + "_" + "painted_chest_boat";
            RegistryObject<T> item;
            if (color.getId() < 16) {
                 item = (RegistryObject<T>) ITEMS.register(itemID, () -> new ModBoatItem(true, ModBoatEntity.Type.byId(color.getId()), new Item.Properties()));
            } else {
                item = (RegistryObject<T>) ITEMS.register(itemID, () -> new Item(new Item.Properties()));
            }
            coloredItems.add(item);
        }
        return coloredItems;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}