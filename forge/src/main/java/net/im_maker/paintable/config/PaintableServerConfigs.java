package net.im_maker.paintable.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class PaintableServerConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ConfigValue<Boolean> CAN_PAINT;

    static {
        BUILDER.push("Configs for Paintable");
        CAN_PAINT = BUILDER.comment("Paint every colored block that has unique design, like Doors, Trapdoors, Glazed Terracotta").define("can_paint", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}