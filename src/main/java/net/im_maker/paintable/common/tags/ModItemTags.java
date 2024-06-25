package net.im_maker.paintable.common.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.im_maker.paintable.Paintable;

public class ModItemTags {

    public static final TagKey<Item> DIPPED_PAINT_BRUSH = tag("dipped_paint_brush");
    public static final TagKey<Item> FILLED_PAINT_BUCKET = tag("filled_paint_bucket");
    public static final TagKey<Item> PAINTABLE_BRICKS = tag("paintable_bricks");
    public static final TagKey<Item> PAINTABLE_BRICK_STAIRS = tag("paintable_brick_stairs");
    public static final TagKey<Item> PAINTABLE_BRICK_SLABS = tag("paintable_brick_slab");
    public static final TagKey<Item> PAINTABLE_BRICK_WALLS = tag("paintable_brick_wall");
    public static final TagKey<Item> PAINTABLE_MUD_BRICKS = tag("paintable_mud_bricks");
    public static final TagKey<Item> PAINTABLE_MUD_BRICK_STAIRS = tag("paintable_mud_brick_stairs");
    public static final TagKey<Item> PAINTABLE_MUD_BRICK_SLABS = tag("paintable_mud_brick_slabs");
    public static final TagKey<Item> PAINTABLE_MUD_BRICK_WALLS = tag("paintable_mud_brick_walls");
    public static final TagKey<Item> PAINTABLE_CONCRETE = tag("paintable_concrete");
    public static final TagKey<Item> PAINTABLE_CONCRETE_VANILLA = tag("paintable_concrete_vanilla");
    public static final TagKey<Item> PAINTABLE_CONCRETE_DYE_DEPOT = tag("paintable_concrete_dye_depot");
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
