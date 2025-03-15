package net.im_maker.paintable;

import com.ninni.dye_depot.registry.DDDyes;
import net.im_maker.paintable.common.block.ModBlocks;
import net.im_maker.paintable.common.block.entity.ModBlockEntities;
import net.im_maker.paintable.common.entity.PEntities;
import net.im_maker.paintable.client.ModBoatRenderer;
import net.im_maker.paintable.client.ModModelLayers;
import net.im_maker.paintable.common.item.PItems;
import net.im_maker.paintable.common.sound.PaintableSounds;
import net.im_maker.paintable.common.util.PaintableWoodTypes;
import net.im_maker.paintable.common.util.crafting.PRecipeSerializers;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
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
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Mod(Paintable.MOD_ID)
public class Paintable {
    public static final String MOD_ID = "paintable";
    private static final Function<ItemLike, ItemStack> FUNCTION = ItemStack::new;
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

    public Paintable() {
        isDyeDepotLoaded(ModList.get().isLoaded("dye_depot"));
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        PItems.register(modEventBus);
        PaintableSounds.register(modEventBus);
        PEntities.register(modEventBus);
        PRecipeSerializers.register(modEventBus);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::addCreative);
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
        String[] woodBlockTypesC = {
                "%s_painted_slab",
                "%s_painted_stairs",
                "%s_painted_planks",
                "stripped_%s_painted_wood",
                "stripped_%s_painted_log",
                "%s_painted_wood",
                "%s_painted_log"
        };
        String[] brickBlockTypesC = {
                "painted_bricks",
                "painted_brick_stairs",
                "painted_brick_slab",
        };
        String[] brickBlockTypesR = {
                "painted_brick_wall",
                "painted_brick_slab",
                "painted_brick_stairs",
                "painted_bricks"
        };
        String[] brickMudBlockTypesC = {
                "painted_mud_bricks",
                "painted_mud_brick_stairs",
                "painted_mud_brick_slab",
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
                for (String blockType : woodBlockTypesC) {
                    String formattedBlockType = String.format(blockType, colorName);
                    ResourceLocation blockLocation = new ResourceLocation("paintable:" + formattedBlockType);
                    Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                    addAfter(entries, Blocks.PINK_GLAZED_TERRACOTTA, block);
                }
            }
            for (DyeColor color : customColorOrder) {
                String colorName = color.getName();
                for (String blockType : brickBlockTypesC) {
                    ResourceLocation blockLocation = new ResourceLocation("paintable:" + colorName + "_" + blockType);
                    Block block = ForgeRegistries.BLOCKS.getValue(blockLocation);
                    addBefore(entries, Blocks.GLASS, block);
                }
            }
            for (DyeColor color : customColorOrder) {
                String colorName = color.getName();
                for (String blockType : brickMudBlockTypesC) {
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
            addAfter(entries, Items.BRUSH, PItems.PAINT_BRUSH.get());
            for (DyeColor color : customColorOrderR) {
                String colorName = color.getName();
                ResourceLocation itemLocation = new ResourceLocation("paintable:" + colorName + "_paint_brush");
                Item item = ForgeRegistries.ITEMS.getValue(itemLocation);
                addAfter(entries, PItems.PAINT_BRUSH.get(), item);
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
            EntityRenderers.register(PEntities.BOAT.get(), pContext -> new ModBoatRenderer(pContext, false));
            EntityRenderers.register(PEntities.CHEST_BOAT.get(), pContext -> new ModBoatRenderer(pContext, true));
            ItemBlockRenderTypes.setRenderLayer(getBlockFromString("paint_bucket"), RenderType.cutout());
            for (DyeColor color : DyeColor.values()) {
                RenderType renderTypeForDoors = color.getName().equals("cyan") || color.getName().equals("forest") ||color.getName().equals("olive") ? RenderType.translucent() : RenderType.cutout();
                ItemBlockRenderTypes.setRenderLayer(getBlockFromString(color + "_paint_bucket"), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(getBlockFromString(color + "_painted_door"), renderTypeForDoors);
                ItemBlockRenderTypes.setRenderLayer(getBlockFromString(color + "_painted_trapdoor"), renderTypeForDoors);
            }
            for (DyeColor color : DyeColor.values()) {
                Sheets.addWoodType(PaintableWoodTypes.PAINTED_WOOD_TYPES[color.getId()]);
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
