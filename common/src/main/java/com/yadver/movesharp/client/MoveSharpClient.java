package com.yadver.movesharp.client;

import com.yadver.movesharp.movement.PlayerSharp;
import com.yadver.movesharp.PlayerSharpAccess;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

public class MoveSharpClient {

    public static void init() {
        ClientTickEvent.CLIENT_POST.register(Client -> {
            LocalPlayer player = Client.player;
            ClientLevel level = Client.level;
            PlayerSharpAccess access = (PlayerSharpAccess) player;

            if (player != null && level != null) {
                PlayerSharp playerSharp = access.moveSharp$getWrapper();
                playerSharp.setSneaking(Client.options.keyShift.isDown());
                playerSharp.setSprinting(Client.options.keySprint.isDown());
            }
        });
    }
}
