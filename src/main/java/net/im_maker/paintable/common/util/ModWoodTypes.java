package net.im_maker.paintable.common.util;

import net.im_maker.paintable.Paintable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    private static final DyeColor[] PAINTED_COLORS = DyeColor.values();

    public static final WoodType[] PAINTED_WOOD_TYPES = createPaintedWoodTypes();

    private static WoodType[] createPaintedWoodTypes() {
        WoodType[] woodTypes = new WoodType[PAINTED_COLORS.length];
        for (int i = 0; i < PAINTED_COLORS.length; i++) {
            DyeColor color = PAINTED_COLORS[i];
            String woodTypeName = color.getName() + "_painted";
            woodTypes[i] = WoodType.register(new WoodType(Paintable.MOD_ID + ":" + woodTypeName, BlockSetType.OAK));
        }
        return woodTypes;
    }
}