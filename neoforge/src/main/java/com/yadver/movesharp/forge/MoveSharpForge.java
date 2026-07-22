package com.yadver.movesharp.forge;

import com.yadver.movesharp.forge.client.MoveSharpForgeClient;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.yadver.movesharp.MoveSharp;

@Mod(MoveSharp.MOD_ID)
public final class MoveSharpForge {
    @SuppressWarnings("removal")
    public MoveSharpForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(MoveSharp.MOD_ID, modEventBus);

        // Run our common setup.
        MoveSharp.init();

        modEventBus.addListener(MoveSharpForgeClient::onClientSetup);
    }
}
