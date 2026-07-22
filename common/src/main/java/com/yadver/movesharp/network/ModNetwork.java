package com.yadver.movesharp.network;

import com.yadver.movesharp.MoveSharp;
import com.yadver.movesharp.PlayerSharpAccess;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

//  TODO: Добавить комментарии. А то хули, опять забью на мод и вот гадай, что за велосипед я тут изобрёл.
public class ModNetwork {
	public static void registerReceiver() {
		NetworkManager.registerReceiver(NetworkManager.c2s(), ClientInputPacket.TYPE, ClientInputPacket.CODEC,
				(packet, context) -> {
			if (context.getPlayer() != null) {
				switch (packet.keyID()) {
					case "SNEAK_KEY" -> {
						PlayerSharpAccess access = (PlayerSharpAccess) context.getPlayer();
						access.moveSharp$getWrapper().setSneaking(packet.isPressed());
					} case "SPRINT_KEY" -> {
						PlayerSharpAccess access = (PlayerSharpAccess) context.getPlayer();
						access.moveSharp$getWrapper().setSprinting(packet.isPressed());
					}
				}
			}
		});
	}

	public static void playerSneaking(boolean bool) {
		NetworkManager.sendToServer(new ClientInputPacket("SNEAK_KEY", bool));
	}
	public static void playerSprinting(boolean bool) {
		NetworkManager.sendToServer(new ClientInputPacket("SPRINT_KEY", bool));
	}
}
