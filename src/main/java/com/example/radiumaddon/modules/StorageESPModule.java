package com.example.radiumaddon.modules;

import com.example.radiumaddon.CustomAddonInitializer;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.BooleanSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.BarrelBlockEntity;
import meteordevelopment.orbit.EventHandler;

public class StorageESPModule extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Boolean> chests = sgGeneral.add(new BooleanSetting.Builder().name("chests").defaultValue(true).build());
    public final Setting<Boolean> shulkers = sgGeneral.add(new BooleanSetting.Builder().name("shulkers").defaultValue(true).build());
    public final Setting<Boolean> barrels = sgGeneral.add(new BooleanSetting.Builder().name("barrels").defaultValue(false).build());

    private final SettingColor color = new SettingColor(0, 255, 255, 100);

    public StorageESPModule() {
        super(CustomAddonInitializer.CATEGORY, "storage-esp", "Zeigt ausgewaehlte Storage-Bloecke an.");
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (mc.world == null) return;

        for (BlockEntity be : mc.world.blockEntities) {
            boolean shouldRender = false;
            if (be instanceof ChestBlockEntity && chests.get()) shouldRender = true;
            if (be instanceof ShulkerBoxBlockEntity && shulkers.get()) shouldRender = true;
            if (be instanceof BarrelBlockEntity && barrels.get()) shouldRender = true;

            if (shouldRender) {
                event.renderer.box(be.getPos(), color, color, ShapeMode.Both, 0);
            }
        }
    }
}
