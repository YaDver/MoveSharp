package com.yadver.movesharp.forge;

import com.yadver.movesharp.MoveSharp;
import com.yadver.movesharp.forge.client.MoveSharpNeoForgeClient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(MoveSharp.MOD_ID)
public final class MoveSharpNeoForge {
    public MoveSharpNeoForge(IEventBus eventBus) {
        MoveSharp.init();
        eventBus.addListener(MoveSharpNeoForgeClient::onClientSetup);
    }
}
