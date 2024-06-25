package net.im_maker.paintable.datagen.loot;

import net.im_maker.paintable.common.block.ModBlocks;
import net.im_maker.paintable.common.block.custom.FilledPaintBucketBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }


    private Item itemC (String item, DyeColor color) {
        ResourceLocation itemLocation = new ResourceLocation("paintable:" + color + "_painted_" + item);
        return ForgeRegistries.ITEMS.getValue(itemLocation);
    }

    private Block blockC (String block, DyeColor color) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:" + color + "_painted_" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    private Block block (String block) {
        ResourceLocation blockLocation = new ResourceLocation("paintable:" + block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    @Override
    protected void generate() {
        for (DyeColor color : DyeColor.values()) {
            this.dropSelf(blockC("log", color));
            this.dropSelf(blockC("wood", color));
            this.dropSelf(block("stripped_" + color + "_painted_log"));
            this.dropSelf(block("stripped_" + color + "_painted_wood"));
            this.dropSelf(blockC("planks", color));
            this.dropSelf(blockC("stairs", color));
            this.add(blockC("slab", color), (block) -> {
                return this.createSlabItemTable(block);
            });
            this.dropSelf(blockC("fence", color));
            this.dropSelf(blockC("fence_gate", color));
            this.add(blockC("door", color), (block) -> {
                return this.createDoorTable(block);
            });
            this.dropSelf(blockC("trapdoor", color));
            this.dropSelf(blockC("button", color));
            this.dropSelf(blockC("pressure_plate", color));
            this.dropSelf(blockC("sign", color));
            this.createSingleItemTable(blockC("sign", color));
            this.dropOther(blockC("sign", color), itemC("sign", color));
            this.dropOther(blockC("wall_sign", color), itemC("sign", color));
            this.dropOther(blockC("hanging_sign", color), itemC("hanging_sign", color));
            this.dropOther(blockC("wall_hanging_sign", color), itemC("hanging_sign", color));
            this.dropSelf(blockC("bricks", color));
            this.dropSelf(blockC("brick_stairs", color));
            this.add(blockC("brick_slab", color), (block) -> {
                return this.createSlabItemTable(block);
            });
            this.dropSelf(blockC("brick_wall", color));
            this.dropSelf(blockC("mud_bricks", color));
            this.dropSelf(blockC("mud_brick_stairs", color));
            this.add(blockC("mud_brick_slab", color), (block) -> {
                return this.createSlabItemTable(block);
            });
            this.dropSelf(blockC("mud_brick_wall", color));
            this.dropSelf(ModBlocks.PAINT_BUCKET.get());
            this.dropOther(block(color + "_paint_bucket"), ModBlocks.PAINT_BUCKET.get());
            this.add(block(color + "_paint_bucket"), (block) -> {
                return this.createFilledPaintBucketItemTable(block);
            });
        }
    }

    protected LootTable.Builder createFilledPaintBucketItemTable(Block pBlock) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(pBlock)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(FilledPaintBucketBlock.LEVEL_PAINT, 4))))
                        .add(LootItem.lootTableItem(ModBlocks.PAINT_BUCKET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(FilledPaintBucketBlock.LEVEL_PAINT, 3))))
                        .add(LootItem.lootTableItem(ModBlocks.PAINT_BUCKET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(FilledPaintBucketBlock.LEVEL_PAINT, 2))))
                        .add(LootItem.lootTableItem(ModBlocks.PAINT_BUCKET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(FilledPaintBucketBlock.LEVEL_PAINT, 1))))
                        .add(LootItem.lootTableItem(ModBlocks.PAINT_BUCKET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(FilledPaintBucketBlock.LEVEL_PAINT, 0)))));
    }

    protected LootTable.Builder createCandleDrops(Block pCandleBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(pCandleBlock, LootItem.lootTableItem(pCandleBlock).apply(List.of(2, 3, 4), (integer) -> {
            return SetItemCountFunction.setCount(ConstantValue.exactly((float)integer.intValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pCandleBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CandleBlock.CANDLES, integer)));
        }))));
    }

    protected LootTable.Builder createItemDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)));
    }

    protected static LootTable.Builder createCandleCakeDrops(Block pCandleCakeBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(pCandleCakeBlock)));
    }

    protected LootTable.Builder createItemDropsFromShears(Block pBlock, Item item) {
        return createSilkTouchOrShearsDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}