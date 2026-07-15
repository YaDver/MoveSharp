package com.yadver.movesharp;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

//  TODO: Добавить комментарии. А то хули, опять забью на мод и вот гадай, что за велосипед я тут изобрёл.
public class ModNetwork {

	private static final ResourceLocation CHANNEL_sneaking = new ResourceLocation(MoveSharp.MOD_ID, "sneaking");
    private static final ResourceLocation CHANNEL_sprinting = new ResourceLocation(MoveSharp.MOD_ID, "sprinting");

	public static void registerReceiver() {
		NetworkManager.registerReceiver(NetworkManager.c2s(), CHANNEL_sneaking,
				(buf, context) -> {
			if (context.getPlayer() != null) {
				PlayerSharpAccess access = (PlayerSharpAccess) context.getPlayer();
				boolean b = buf.readBoolean();
				access.moveSharp$getWrapper().setSneaking(b);
			}
		});

		NetworkManager.registerReceiver(NetworkManager.c2s(), CHANNEL_sprinting,
				(buf, context) -> {
			if (context.getPlayer() != null) {
				PlayerSharpAccess access = (PlayerSharpAccess) context.getPlayer();
				access.moveSharp$getWrapper().setSprinting(buf.readBoolean());
			}
		});
	}

	public static void playerSneaking(boolean bool) {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeBoolean(bool);
		NetworkManager.sendToServer(CHANNEL_sneaking, buf);
	}
	public static void playerSprinting(boolean bool) {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeBoolean(bool);
		NetworkManager.sendToServer(CHANNEL_sprinting, buf);
	}
}
