package com.yadver.movesharp.forge.client;

import com.yadver.movesharp.client.MoveSharpClient;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class MoveSharpForgeClient {
    public static void onClientSetup(final FMLClientSetupEvent event) {
        // Передаем управление в наш главный клиентский класс в common
        event.enqueueWork(MoveSharpClient::init);
    }
}
