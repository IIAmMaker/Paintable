package net.im_maker.paintable;

import com.ninni.dye_depot.registry.DDDyes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.im_maker.paintable.common.block.PBlocks;
import net.im_maker.paintable.common.block.entity.PBlockEntities;
import net.im_maker.paintable.common.entity.PEntities;
import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.util.DataPackRegistrar;
import net.im_maker.paintable.common.util.crafting.PRecipeSerializers;
import net.im_maker.paintable.config.PaintableServerConfigs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Paintable implements ModInitializer {
    public static final String MOD_ID = "paintable";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Function<ItemLike, ItemStack> FUNCTION = ItemStack::new;
    public static PaintableServerConfigs CONFIG;
    public static List<DyeColor> customColorOrder = new ArrayList<>(Arrays.asList(
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
            DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
            DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
            DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
    ));
    public static List<DyeColor> customColorOrderR = new ArrayList<>(customColorOrder);
    static {
        Collections.reverse(customColorOrderR);
    }

    @Override
    public void onInitialize() {
        isDyeDepotLoaded(FabricLoader.getInstance().isModLoaded("dye_depot"));
        DataPackRegistrar.loadBuiltinResourcePacks();
        CONFIG = PaintableServerConfigs.getInstance();
        PItems.registerItems();
        PBlocks.registerBlocks();
        PBlockEntities.registerBlockEntities();
        PEntities.registerEntities();
        PRecipeSerializers.init();
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(Paintable::addToBuildingBlocksTap);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(Paintable::addToColoredBlocksTap);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(Paintable::addToFunctionalBlocksTap);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(Paintable::addToToolsAndUtilitiesTap);
    }

    public static void isDyeDepotLoaded(Boolean b) {
        if (b) {
            customColorOrder = new ArrayList<>(Arrays.asList(
                    DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
                    DyeColor.BROWN, DDDyes.MAROON.get(), DDDyes.ROSE.get(), DyeColor.RED,
                    DDDyes.CORAL.get(), DDDyes.GINGER.get(), DyeColor.ORANGE, DDDyes.TAN.get(),
                    DDDyes.BEIGE.get(), DyeColor.YELLOW, DDDyes.AMBER.get(), DDDyes.OLIVE.get(),
                    DyeColor.LIME, DDDyes.FOREST.get(), DyeColor.GREEN, DDDyes.VERDANT.get(),
                    DDDyes.TEAL.get(), DyeColor.CYAN, DDDyes.MINT.get(), DDDyes.AQUA.get(),
                    DyeColor.LIGHT_BLUE, DyeColor.BLUE, DDDyes.SLATE.get(), DDDyes.NAVY.get(),
                    DDDyes.INDIGO.get(), DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
            ));
            customColorOrderR = new ArrayList<>(customColorOrder);
            Collections.reverse(customColorOrderR);
        } else {
            customColorOrder = customColorOrder;
            customColorOrderR = customColorOrderR;
        }
    }

    public static void addToColoredBlocksTap(FabricItemGroupEntries entries) {
        String[] woodBlockTypes = {
                "%s_painted_slab",
                "%s_painted_stairs",
                "%s_painted_planks",
                "stripped_%s_painted_wood",
                "stripped_%s_painted_log",
                "%s_painted_wood",
                "%s_painted_log"
        };
        String[] brickBlockTypes = {
                "painted_bricks",
                "painted_brick_stairs",
                "painted_brick_slab",
        };
        String[] brickMudBlockTypes = {
                "painted_mud_bricks",
                "painted_mud_brick_stairs",
                "painted_mud_brick_slab",
        };

        // Colored Blocks Tab
        for (DyeColor color : customColorOrderR) {
            String colorName = color.getName();
            for (String blockType : woodBlockTypes) {
                String formattedBlockType = String.format(blockType, colorName);
                ResourceLocation blockLocation = new ResourceLocation("paintable:" + formattedBlockType);
                Block block = BuiltInRegistries.BLOCK.get(blockLocation);
                entries.addAfter(Blocks.PINK_GLAZED_TERRACOTTA, block);
            }
        }

        for (DyeColor color : customColorOrder) {
            String colorName = color.getName();
            for (String blockType : brickBlockTypes) {
                ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                Block block = BuiltInRegistries.BLOCK.get(blockLocation);
                entries.addBefore(Blocks.GLASS, block);
            }
        }

        for (DyeColor color : customColorOrder) {
            String colorName = color.getName();
            for (String blockType : brickMudBlockTypes) {
                ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                Block block = BuiltInRegistries.BLOCK.get(blockLocation);
                entries.addBefore(Blocks.GLASS, block);
            }
        }

        entries.addAfter(Blocks.PINK_SHULKER_BOX, PBlocks.PAINT_BUCKET);
        for (DyeColor color : customColorOrderR) {
            String colorName = color.getName();
            ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_paint_bucket");
            Block block = BuiltInRegistries.BLOCK.get(blockLocation);
            entries.addAfter(PBlocks.PAINT_BUCKET, block);
        }
    }

    // New method for Tools and Utilities Tab
    public static void addToToolsAndUtilitiesTap(FabricItemGroupEntries entries) {
        entries.addAfter(Items.BRUSH, PItems.PAINT_BRUSH);
        for (DyeColor color : customColorOrderR) {
            String colorName = color.getName();
            ResourceLocation itemLocation = new ResourceLocation("paintable:" + colorName + "_paint_brush");
            Item item = BuiltInRegistries.ITEM.get(itemLocation);
            entries.addAfter(PItems.PAINT_BRUSH, item);
        }

        String[] boatTypes = {"painted_chest_boat", "painted_boat"};
        for (DyeColor color : customColorOrderR) {
            String colorName = color.getName();
            for (String boatType : boatTypes) {
                ResourceLocation itemLocation = new ResourceLocation("paintable:" + colorName + "_" + boatType);
                Item item = BuiltInRegistries.ITEM.get(itemLocation);
                entries.addAfter(Items.BAMBOO_CHEST_RAFT, item);
            }
        }
    }

    // New method for Building Blocks Tab
    public static void addToBuildingBlocksTap(FabricItemGroupEntries entries) {
        String[] woodBlockTypes = {
                "%s_painted_button",
                "%s_painted_pressure_plate",
                "%s_painted_trapdoor",
                "%s_painted_door",
                "%s_painted_fence_gate",
                "%s_painted_fence",
                "%s_painted_slab",
                "%s_painted_stairs",
                "%s_painted_planks",
                "stripped_%s_painted_wood",
                "stripped_%s_painted_log",
                "%s_painted_wood",
                "%s_painted_log"
        };
        String[] brickBlockTypesR = {
                "painted_brick_wall",
                "painted_brick_slab",
                "painted_brick_stairs",
                "painted_bricks"
        };
        String[] brickMudBlockTypesR = {
                "painted_mud_brick_wall",
                "painted_mud_brick_slab",
                "painted_mud_brick_stairs",
                "painted_mud_bricks"
        };

        for (DyeColor color : customColorOrderR) {
            String colorName = color.getName();
            for (String blockType : woodBlockTypes) {
                String formattedBlockType = String.format(blockType, colorName);
                ResourceLocation blockLocation = new ResourceLocation("paintable:" + formattedBlockType);
                Block block = BuiltInRegistries.BLOCK.get(blockLocation);
                entries.addAfter(Blocks.WARPED_BUTTON, block);
            }
        }

        for (DyeColor color : customColorOrderR) {
            String colorName = color.getName();
            for (String blockType : brickBlockTypesR) {
                ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                Block block = BuiltInRegistries.BLOCK.get(blockLocation);
                entries.addAfter(Blocks.BRICK_WALL, block);
            }
        }

        for (DyeColor color : customColorOrderR) {
            String colorName = color.getName();
            for (String blockType : brickMudBlockTypesR) {
                ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                Block block = BuiltInRegistries.BLOCK.get(blockLocation);
                entries.addAfter(Blocks.MUD_BRICK_WALL, block);
            }
        }
    }

    // New method for Functional Blocks Tab
    public static void addToFunctionalBlocksTap(FabricItemGroupEntries entries) {
        entries.addAfter(Blocks.PINK_SHULKER_BOX, PBlocks.PAINT_BUCKET);
        for (DyeColor color : customColorOrderR) {
            String colorName = color.getName();
            ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_paint_bucket");
            Block block = BuiltInRegistries.BLOCK.get(blockLocation);
            entries.addAfter(PBlocks.PAINT_BUCKET, block);
        }

        String[] blockTypes = {"painted_hanging_sign", "painted_sign"};
        for (DyeColor color : customColorOrderR) {
            String colorName = color.getName();
            for (String blockType : blockTypes) {
                ResourceLocation itemLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                Item item = BuiltInRegistries.ITEM.get(itemLocation);
                entries.addAfter(Items.WARPED_HANGING_SIGN, item);
            }
        }
    }

    public static Block getBlockFromString (String block) {
        return getBlockFromString(Paintable.MOD_ID, block);
    }
    public static Block getBlockFromString (String nameSpace, String block) {
        ResourceLocation blockLocation = new ResourceLocation(nameSpace, block);
        return BuiltInRegistries.BLOCK.get(blockLocation);
    }

    public static Item getItemFromString (String item) {
        return getItemFromString(Paintable.MOD_ID, item);
    }
    public static Item getItemFromString (String nameSpace, String item) {
        ResourceLocation itemLocation = new ResourceLocation(nameSpace, item);
        return BuiltInRegistries.ITEM.get(itemLocation);
    }

    public static Block[] getBlocks(Class<?>... blockClasses) {
        return BuiltInRegistries.BLOCK.stream()
                .filter(block -> Stream.of(blockClasses).anyMatch(clazz -> clazz.isInstance(block)))
                .toArray(Block[]::new);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
