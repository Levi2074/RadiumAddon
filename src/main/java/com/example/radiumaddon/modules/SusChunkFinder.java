package com.example.radiumaddon.modules;

import com.example.radiumaddon.CustomAddonInitializer;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import meteordevelopment.orbit.EventHandler;

import java.util.HashSet;
import java.util.Set;

public class SusChunkFinder extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    
    private final Setting<Integer> radius = sgGeneral.add(new IntSetting.Builder()
        .name("radius-chunks")
        .description("Radius in Chunks.")
        .defaultValue(8)
        .min(1).max(16)
        .build());

    private final Set<ChunkPos> scannedChunks = new HashSet<>();

    public SusChunkFinder() {
        super(CustomAddonInitializer.CATEGORY, "sus-chunk-finder", "Scannt Chunks nach spezifischen Bloecken.");
    }

    @Override
    public void onDeactivate() {
        scannedChunks.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.world == null || mc.player == null) return;
        ChunkPos playerChunk = mc.player.getChunkPos();

        for (int x = -radius.get(); x <= radius.get(); x++) {
            for (int z = -radius.get(); z <= radius.get(); z++) {
                ChunkPos cPos = new ChunkPos(playerChunk.x + x, playerChunk.z + z);
                if (scannedChunks.contains(cPos)) continue;

                if (checkChunkForUnusualBlocks(cPos)) {
                    warning("Suspicious chunk found at: %d, %d", cPos.x, cPos.z);
                }
                scannedChunks.add(cPos);
            }
        }
    }

    private boolean checkChunkForUnusualBlocks(ChunkPos cPos) {
        int startX = cPos.getStartX();
        int startZ = cPos.getStartZ();
        int count = 0;
        for (int y = mc.world.getBottomY(); y < mc.world.getTopY(); y += 4) {
            for (int x = 0; x < 16; x += 4) {
                for (int z = 0; z < 16; z += 4) {
                    if (mc.world.getBlockState(new BlockPos(startX + x, y, startZ + z)).isOf(Blocks.HOPPER)) {
                        count++;
                    }
                }
            }
        }
        return count > 4;
    }
}
