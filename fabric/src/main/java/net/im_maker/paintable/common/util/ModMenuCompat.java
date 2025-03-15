package net.im_maker.paintable.common.util;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.OptionsScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ModMenuCompat implements ModMenuApi {

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        return Map.of("minecraft", parent -> new OptionsScreen(parent, Minecraft.getInstance().options));
    }
}