package net.im_maker.paintable.common.util;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class PWoodTypes {
    private static final DyeColor[] PAINTED_COLORS = DyeColor.values();

    public static final WoodType[] PAINTED_WOOD_TYPES = createPaintedWoodTypes();

    public static TagKey<Block> createBlockTag(ResourceLocation name) {
        return TagKey.create(Registries.BLOCK, name);
    }

    private static WoodType[] createPaintedWoodTypes() {
        WoodType[] woodTypes = new WoodType[PAINTED_COLORS.length];
        for (int i = 0; i < PAINTED_COLORS.length; i++) {
            DyeColor color = PAINTED_COLORS[i];
            String woodTypeName = color.getName() + "_painted";

            BlockSetType blockSetType = new BlockSetType(woodTypeName);

            woodTypes[i] = WoodTypeRegistry.register(new ResourceLocation("paintable", woodTypeName), blockSetType);
        }
        return woodTypes;
    }
}