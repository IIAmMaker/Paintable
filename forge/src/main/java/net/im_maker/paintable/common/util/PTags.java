package net.im_maker.paintable.common.util;

import net.im_maker.paintable.Paintable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PTags {

    public static class Blocks {
        public static final TagKey<Block> FILLED_PAINT_BUCKET = tag("filled_paint_bucket");
        public static final TagKey<Block> TOGGLEABLE = tag("toggleable");
        public static final TagKey<Block> PAINT_BRUSH_WASHING_BLOCKS = tag("paint_brush_washing_blocks");
        public static final TagKey<Block>[] PAINTED_LOG_TAGS = createPaintedLogTags();

        private static TagKey<Block>[] createPaintedLogTags() {
            TagKey<Block>[] tags = new TagKey[DyeColor.values().length];
            for (int i = 0; i < DyeColor.values().length; i++) {
                DyeColor dyeColor = DyeColor.values()[i];
                String tagName = dyeColor.getName() + "_painted_log";
                tags[i] = BlockTags.create(new ResourceLocation(Paintable.MOD_ID, tagName));
            }
            return tags;
        }

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Paintable.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> DIPPED_PAINT_BRUSH = tag("dipped_paint_brush");
        public static final TagKey<Item> FILLED_PAINT_BUCKET = tag("filled_paint_bucket");
        public static final TagKey<Item>[] PAINTED_LOG_TAGS = createPaintedLogTags();

        private static TagKey<Item>[] createPaintedLogTags() {
            TagKey<Item>[] tags = new TagKey[DyeColor.values().length];
            for (int i = 0; i < DyeColor.values().length; i++) {
                DyeColor dyeColor = DyeColor.values()[i];
                String tagName = dyeColor.getName() + "_painted_log";
                tags[i] = ItemTags.create(new ResourceLocation(Paintable.MOD_ID, tagName));
            }
            return tags;
        }

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Paintable.MOD_ID, name));
        }
    }
    public static class Entities {
        public static final TagKey<EntityType<?>> UNPAINTABLE_BOAT = tag("unpaintable_boat");
        public static TagKey<EntityType<?>> create(ResourceLocation name) {
            return TagKey.create(Registries.ENTITY_TYPE, name);
        }
        private static TagKey<EntityType<?>> tag(String name) {
            return create(new ResourceLocation(Paintable.MOD_ID, name));
        }
    }
}