package com.example.radiumaddon;

import com.example.radiumaddon.modules.SusChunkFinder;
import com.example.radiumaddon.modules.SpawnerDetector;
import com.example.radiumaddon.modules.StorageESPModule;
import com.example.radiumaddon.modules.BaseStashFinder;
import com.example.radiumaddon.gui.CustomCustomScreen;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.Addon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.meteorclient.gui.GuiThemes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class CustomAddonInitializer implements Addon, ModInitializer {
    public static final Category CATEGORY = new Category("Radium Tools");

    @Override
    public void onInitialize() {
        MeteorClient.EVENT_BUS.subscribe(this);
        Modules.get().add(new SusChunkFinder());
        Modules.get().add(new SpawnerDetector());
        Modules.get().add(new StorageESPModule());
        Modules.get().add(new BaseStashFinder());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen == null && InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_ENTER)) {
            mc.setScreen(new CustomCustomScreen(GuiThemes.get()));
        }
    }

    @Override
    public String getPackage() {
        return "com.example.radiumaddon";
    }
}
