package net.im_maker.paintable.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class PaintableConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ConfigValue<Boolean> CAN_PAINT;
    public static final ConfigValue<Integer> PAINT_LIMIT;
    public static final ConfigValue<Integer> PNT_EXPLOSION_RADIUS;

    static {
        BUILDER.push("Configs for Paintable");
        CAN_PAINT = BUILDER
                .comment("Paint every colored block that has unique design/texture, like Doors, Trapdoors, Glazed Terracotta")
                .comment("Default: false")
                .define("can_paint", false);
        PAINT_LIMIT = BUILDER
                .comment("The amount of times you can paint blocks using the brush")
                .comment("Default: 16")
                .defineInRange("paint_limit", 16, 4, 64);
        PNT_EXPLOSION_RADIUS = BUILDER
                .comment("The radius of PNT explosion")
                .comment("Default: 6")
                .defineInRange("pnt_explosion_radius", 6, 1, 20);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}