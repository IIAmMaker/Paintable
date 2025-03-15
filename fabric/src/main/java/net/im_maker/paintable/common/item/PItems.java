package net.im_maker.paintable.common.item;

import net.im_maker.paintable.Paintable;
import net.im_maker.paintable.common.block.PBlocks;
import net.im_maker.paintable.common.item.custom.DippedPaintBrushItem;
import net.im_maker.paintable.common.item.custom.PaintBrushItem;
import net.im_maker.paintable.common.item.custom.PaintableBoatItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;

import java.util.ArrayList;
import java.util.List;

public class PItems {
    public static final Item PAINT_BRUSH = registerItem("paint_brush", new PaintBrushItem(new Item.Properties().stacksTo(1)));
    public static final List<Item> DIPPED_PAINT_BRUSH = registerColoredPaintBrushes();
    public static final List<Item> PAINTED_SIGNS = registerColoredSigns();
    public static final List<Item> PAINTED_HANGING_SIGNS = registerColoredHangingSigns();
    public static final List<Item> PAINTED_BOATS = registerColoredBoats(false);
    public static final List<Item> PAINTED_CHEST_BOATS = registerColoredBoats(true);

    private static List<Item> registerColoredPaintBrushes() {
        List<Item> coloredItems = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String itemID = color.getName() + "_" + "paint_brush";
            Item item = registerItem(itemID, new DippedPaintBrushItem(color, new Item.Properties().stacksTo(1).durability(16)));
            coloredItems.add(item);
        }
        return coloredItems;
    }

    private static List<Item> registerColoredSigns() {
        List<Item> coloredItems = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String itemID = color.getName() + "_" + "painted_sign";
            Item item = registerItem(itemID, new SignItem(new Item.Properties().stacksTo(16), PBlocks.PAINTED_SIGN.get(color.getId()), PBlocks.PAINTED_WALL_SIGN.get(color.getId())));
            coloredItems.add(item);
        }
        return coloredItems;
    }

    private static List<Item> registerColoredHangingSigns() {
        List<Item> coloredItems = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String itemID = color.getName() + "_" + "painted_hanging_sign";
            Item item = registerItem(itemID, new HangingSignItem(PBlocks.PAINTED_HANGING_SIGN.get(color.getId()), PBlocks.PAINTED_WALL_HANGING_SIGN.get(color.getId()), new Item.Properties().stacksTo(16)));
            coloredItems.add(item);
        }
        return coloredItems;
    }

    private static List<Item> registerColoredBoats(boolean hasChest) {
        List<Item> coloredItems = new ArrayList<>();

        for (DyeColor color : DyeColor.values()) {
            String itemID = color.getName() + (hasChest ? "_painted_chest_boat" : "_painted_boat");
            Item item = registerItem(itemID, new PaintableBoatItem(hasChest, color, new Item.Properties()));
            coloredItems.add(item);
        }

        return coloredItems;
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Paintable.MOD_ID, name), item);
    }

    public static void registerItems() {
        Paintable.LOGGER.info("Registering Mod Items for " + Paintable.MOD_ID);
    }
}