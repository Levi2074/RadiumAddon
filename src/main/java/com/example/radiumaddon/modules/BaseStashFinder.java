package com.example.radiumaddon.modules;

import com.example.radiumaddon.CustomAddonInitializer;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureKeys;
import meteordevelopment.orbit.EventHandler;

public class BaseStashFinder extends Module {
    public BaseStashFinder() {
        super(CustomAddonInitializer.CATEGORY, "base-stash-finder", "Findet Spielerbasen im 16-Chunk-Radius.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.world == null || mc.player == null) return;

        if (mc.world.getRegistryManager().getOrThrow(net.minecraft.registry.RegistryKeys.STRUCTURE).getOptional(StructureKeys.TRIAL_CHAMBERS).isPresent()) {
            if (mc.world.getBiome(mc.player.getBlockPos()).getKey().get().getValue().getPath().contains("trial")) {
                return; 
            }
        }

        int chestCount = 0;
        BlockPos playerPos = mc.player.getBlockPos();
        double radiusBlocks = 16 * 16;

        for (BlockEntity be : mc.world.blockEntities) {
            if (be instanceof ChestBlockEntity) {
                if (be.getPos().withinDistance(playerPos, radiusBlocks)) {
                    chestCount++;
                }
            }
        }

        if (chestCount >= 10) {
            info("Moeglicher Stash detektiert! Kisten in Reichweite: " + chestCount);
        }
    }
}
