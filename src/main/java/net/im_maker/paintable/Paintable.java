package net.im_maker.paintable;

import net.im_maker.paintable.common.block.ModBlocks;
import net.im_maker.paintable.common.block.entity.ModBlockEntities;
import net.im_maker.paintable.common.entity.ModEntities;
import net.im_maker.paintable.common.entity.client.ModBoatRenderer;
import net.im_maker.paintable.common.entity.client.ModModelLayers;
import net.im_maker.paintable.common.item.ModItems;
import net.im_maker.paintable.common.sound.ModSounds;
import net.im_maker.paintable.common.util.ModWoodTypes;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Mod(Paintable.MOD_ID)
@Mod.EventBusSubscriber(modid = Paintable.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Paintable {
    public static final String MOD_ID = "paintable";
    private static final Function<ItemLike, ItemStack> FUNCTION = ItemStack::new;

    public Paintable() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::addCreative);
    }

    @SubscribeEvent
    public void onMissing(MissingMappingsEvent event) {
        for (DyeColor color : DyeColor.values()) {
            for (MissingMappingsEvent.Mapping mapping : event.getMappings(ForgeRegistries.Keys.BLOCKS, "paintable")) {
                ResourceLocation oldId = mapping.getKey();
                if (oldId.toString().equals("paintable:"+ color +"_painted_planks_stairs")) {
                    ResourceLocation newId = new ResourceLocation("paintable", color +"_painted_stairs");
                    mapping.remap(ForgeRegistries.BLOCKS.getValue(newId));
                }
                if (oldId.toString().equals("paintable:"+ color +"_painted_planks_slab")) {
                    ResourceLocation newId = new ResourceLocation("paintable", color +"_painted_slab");
                    mapping.remap(ForgeRegistries.BLOCKS.getValue(newId));
                }
            }
            for (MissingMappingsEvent.Mapping mapping : event.getMappings(ForgeRegistries.Keys.ITEMS, "paintable")) {
                ResourceLocation oldId = mapping.getKey();
                if (oldId.toString().equals("paintable:"+ color +"_painted_planks_stairs")) {
                    ResourceLocation newId = new ResourceLocation("paintable", color +"_painted_stairs");
                    mapping.remap(ForgeRegistries.ITEMS.getValue(newId));
                }
                if (oldId.toString().equals("paintable:"+ color +"_painted_planks_slab")) {
                    ResourceLocation newId = new ResourceLocation("paintable", color +"_painted_slab");
                    mapping.remap(ForgeRegistries.ITEMS.getValue(newId));
                }
            }
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        /*
        RenderType cutout = RenderType.cutout();
        RenderType translucent = RenderType.translucent();
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RED_PAINTED_DOOR.get(), cutout);
        */

        BlockEntityRenderers.register(ModBlockEntities.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.HANGING_SIGN_BLOCK_ENTITIES.get(), HangingSignRenderer::new);
    }

    private static void addAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike after, ItemLike... blocks) {
        for (int i = blocks.length - 1; i >= 0; i--) {
            ItemLike block = blocks[i];
            map.putAfter(FUNCTION.apply(after), FUNCTION.apply(block), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private static void addBefore(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike before, ItemLike... blocks) {
        for (ItemLike block : blocks) {
            map.putBefore(FUNCTION.apply(before), FUNCTION.apply(block), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
        List<DyeColor> customColorOrder = new ArrayList<>(Arrays.asList(
                DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
                DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
                DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
                DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK));
        List<DyeColor> customColorOrderR = new ArrayList<>(Arrays.asList(
                DyeColor.PINK, DyeColor.MAGENTA, DyeColor.PURPLE, DyeColor.BLUE,
                DyeColor.LIGHT_BLUE, DyeColor.CYAN, DyeColor.GREEN, DyeColor.LIME,
                DyeColor.YELLOW, DyeColor.ORANGE, DyeColor.RED, DyeColor.BROWN,
                DyeColor.BLACK, DyeColor.GRAY, DyeColor.LIGHT_GRAY, DyeColor.WHITE));
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
        String[] brickBlockTypes = {
                "painted_bricks",
                "painted_brick_stairs",
                "painted_brick_slab",
                "painted_brick_wall"
        };
        String[] brickBlockTypesR = {
                "painted_brick_wall",
                "painted_brick_slab",
                "painted_brick_stairs",
                "painted_bricks"
        };
        String[] brickMudBlockTypes = {
                "painted_mud_bricks",
                "painted_mud_brick_stairs",
                "painted_mud_brick_slab",
                "painted_mud_brick_wall"
        };
        String[] brickMudBlockTypesR = {
                "painted_mud_brick_wall",
                "painted_mud_brick_slab",
                "painted_mud_brick_stairs",
                "painted_mud_bricks"
        };
        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                for (String blockType : woodBlockTypes) {
                    String formattedBlockType = String.format(blockType, colorName);
                    ResourceLocation blockLocation = new ResourceLocation("paintable:" + formattedBlockType);
                    Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                    addAfter(entries, Blocks.PINK_GLAZED_TERRACOTTA, block);
                }
            }
            for (DyeColor color : customColorOrder) {
                String colorName = color.getName();
                for (String blockType : brickBlockTypes) {
                    ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                    Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                    addBefore(entries, Blocks.GLASS, block);
                }
            }
            for (DyeColor color : customColorOrder) {
                String colorName = color.getName();
                for (String blockType : brickMudBlockTypes) {
                    ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                    Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                    addBefore(entries, Blocks.GLASS, block);
                }
            }
            addAfter(entries, Blocks.PINK_SHULKER_BOX, ModBlocks.PAINT_BUCKET.get());
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_paint_bucket");
                Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                addAfter(entries, ModBlocks.PAINT_BUCKET.get(), block);
            }
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            addAfter(entries, Items.BRUSH, ModItems.PAINT_BRUSH.get());
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                ResourceLocation itemLocation = new ResourceLocation("paintable:" + colorName + "_paint_brush");
                Item item = ForgeRegistries.ITEMS.getValue(itemLocation);
                addAfter(entries, ModItems.PAINT_BRUSH.get(), item);
            }
            String[] boatTypes = {"painted_chest_boat", "painted_boat"};
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                for (String boatType : boatTypes) {
                    ResourceLocation itemLocation = new ResourceLocation("paintable:" + colorName + "_" + boatType);
                    Item item = ForgeRegistries.ITEMS.getValue(itemLocation);
                    addAfter(entries, Items.BAMBOO_CHEST_RAFT, item);
                }
            }
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                for (String blockType : woodBlockTypes) {
                    String formattedBlockType = String.format(blockType, colorName);
                    ResourceLocation blockLocation = new ResourceLocation("paintable:" + formattedBlockType);
                    Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                    addAfter(entries, Blocks.WARPED_BUTTON, block);
                }
            }
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                for (String blockType : brickBlockTypesR) {
                    ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                    Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                    addAfter(entries, Blocks.BRICK_WALL, block);
                }
            }
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                for (String blockType : brickMudBlockTypesR) {
                    ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                    Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                    addAfter(entries, Blocks.MUD_BRICK_WALL, block);
                }
            }
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            addAfter(entries, Blocks.PINK_SHULKER_BOX, ModBlocks.PAINT_BUCKET.get());
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_paint_bucket");
                Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                addAfter(entries, ModBlocks.PAINT_BUCKET.get(), block);
            }
            String[] blockTypes = {"painted_hanging_sign", "painted_sign"};
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                for (String blockType : blockTypes) {
                    ResourceLocation itemLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                    Item item = ForgeRegistries.ITEMS.getValue(itemLocation);
                    addAfter(entries, Items.WARPED_HANGING_SIGN, item);
                }
            }
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerLayer (EntityRenderersEvent.RegisterLayerDefinitions event) {
            for (DyeColor color : DyeColor.values()) {
                if (color.getId() < 16) {
                    event.registerLayerDefinition(ModModelLayers.PAINTED_BOATS_LAYERS.get(color.getId()), BoatModel::createBodyModel);
                    event.registerLayerDefinition(ModModelLayers.PAINTED_CHEST_BOATS_LAYERS.get(color.getId()), ChestBoatModel::createBodyModel);
                }
            }
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.BOAT.get(), pContext -> new ModBoatRenderer(pContext, false));
            EntityRenderers.register(ModEntities.CHEST_BOAT.get(), pContext -> new ModBoatRenderer(pContext, true));

            for (DyeColor color : DyeColor.values()) {
                Sheets.addWoodType(ModWoodTypes.PAINTED_WOOD_TYPES[color.getId()]);
            }
        }
    }

    public static Block getBlockFromString (String block) {
        return getBlockFromString(Paintable.MOD_ID, block);
    }
    public static Block getBlockFromString (String nameSpace, String block) {
        ResourceLocation blockLocation = new ResourceLocation(nameSpace, block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    public static Item getItemFromString (String item) {
        return getItemFromString(Paintable.MOD_ID, item);
    }
    public static Item getItemFromString (String nameSpace, String item) {
        ResourceLocation itemLocation = new ResourceLocation(nameSpace, item);
        return ForgeRegistries.ITEMS.getValue(itemLocation);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
