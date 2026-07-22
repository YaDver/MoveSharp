package com.yadver.movesharp.forge.client;

import com.yadver.movesharp.MoveSharp;
import com.yadver.movesharp.client.MoveSharpClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = MoveSharp.MOD_ID)
public class MoveSharpNeoForgeClient {
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(MoveSharpClient::init);
    }
}
