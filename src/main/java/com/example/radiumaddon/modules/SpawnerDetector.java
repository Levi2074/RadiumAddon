package com.example.radiumaddon.modules;

import com.example.radiumaddon.CustomAddonInitializer;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.util.math.BlockPos;
import meteordevelopment.orbit.EventHandler;

public class SpawnerDetector extends Module {
    private final SettingColor color = new SettingColor(255, 50, 50, 150);

    public SpawnerDetector() {
        super(CustomAddonInitializer.CATEGORY, "spawner-detector", "Findet und markiert Spawner per Tracer im 12-Chunk-Radius.");
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (mc.world == null || mc.player == null) return;
        double maxDist = 12 * 16;

        for (BlockEntity be : mc.world.blockEntities) {
            if (be instanceof MobSpawnerBlockEntity) {
                BlockPos pos = be.getPos();
                if (mc.player.getPos().distanceTo(pos.toCenterPos()) <= maxDist) {
                    event.renderer.box(pos, color, color, ShapeMode.Lines, 0);
                    event.renderer.line(mc.player.getCameraPosVec(event.tickDelta), pos.toCenterPos(), color);
                }
            }
        }
    }
}
