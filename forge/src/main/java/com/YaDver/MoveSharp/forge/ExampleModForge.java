package com.YaDver.MoveSharp.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.YaDver.MoveSharp.MoveSharp;

@Mod(MoveSharp.MOD_ID)
public final class ExampleModForge {
    @SuppressWarnings("removal")
    public ExampleModForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(MoveSharp.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        MoveSharp.init();
    }
}
