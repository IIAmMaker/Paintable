package net.im_maker.paintable.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "Paintable-common")
public class PaintableConfig implements ConfigData {

    @ConfigEntry.Category("Paint Brush")
    @ConfigEntry.Gui.Tooltip
    public boolean canPaintBlocksWithUniqueTextures = false;

    @ConfigEntry.Category("Paint Brush")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 4, max = 64)
    public int paintLimit = 16;

    @ConfigEntry.Category("PNT Explosion Radius")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 24)
    public int pntExplosionRadius = 6;
}
