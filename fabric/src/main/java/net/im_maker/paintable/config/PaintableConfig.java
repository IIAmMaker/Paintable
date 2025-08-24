package net.im_maker.paintable.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "Paintable-common")
public class PaintableConfig implements ConfigData {

    @ConfigEntry.Category("Paint Brush")
    @ConfigEntry.Gui.Tooltip
    public boolean canPaintBlocksWithUniqueTextures = false; // Default: false

    @ConfigEntry.Category("Paint Brush")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 4, max = 64)
    public int paintLimit = 16; // Default: 16
}
